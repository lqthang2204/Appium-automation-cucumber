package ManageDriver;

import Utilities.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AndroidDriverProvider {
    public static WebDriver getAndroidDriver(){
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("automationName", Configuration.AUTOMATION_NAME);
            desiredCapabilities.setCapability("platformName",Configuration.PLATFORM_NAME);
            desiredCapabilities.setCapability("udid",Configuration.UD_ID);
            desiredCapabilities.setCapability("appPackage",Configuration.APP_PACKAGE);
            desiredCapabilities.setCapability("appActivity",Configuration.APP_ACTIVITY);
            URL appiumSerPath  = new URL(Configuration.PATH_SERVER);
            return new AndroidDriver(appiumSerPath, desiredCapabilities);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[ERR] could not create appium session");
        }
    }
}