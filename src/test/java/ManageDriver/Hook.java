package ManageDriver;

import Utilities.Configuration;
import Utilities.PageUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

public class Hook {
    public static AppiumDriverLocalService service;
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();

    public ThreadLocal<AppiumDriver> getDriver() {
        return driver;
    }

    public void setDriver(ThreadLocal<AppiumDriver> driver) {
        this.driver = driver;
    }

    public AppiumDriver getAndroidDriver(){
        if(Configuration.RUN_SERVICE_AUTO){
            //find path node in enviromen variable
            String pathNode =  new PageUtil().getPath("nodejs");
            if(pathNode!=""){
                service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File(pathNode)).usingPort(Configuration.PORT_NUMBER).withArgument(() -> "--base-path", "/wd/hub"));
            }else{
                service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingPort(Configuration.PORT_NUMBER).withArgument(() -> "--base-path", "/wd/hub"));
            }
            service.start();
        }
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("automationName", Configuration.AUTOMATION_NAME);
            desiredCapabilities.setCapability("platformName",Configuration.PLATFORM_NAME);
            desiredCapabilities.setCapability("udid",Configuration.UD_ID);
            desiredCapabilities.setCapability("appPackage",Configuration.APP_PACKAGE);
            desiredCapabilities.setCapability("appActivity",Configuration.APP_ACTIVITY);
            URL appiumSerPath  = new URL(Configuration.PATH_SERVER);
            System.out.println("Configuration.PATH_SERVER=============================="+ Configuration.PATH_SERVER);
           this.driver.set(new AndroidDriver(appiumSerPath, desiredCapabilities));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[ERR] could not create appium session");
        }
        return  driver.get();
    }
    public void quit(){
        this.driver.get().quit();
        if(this.service!=null){
            Hook.service.stop();

        }
    }
}
