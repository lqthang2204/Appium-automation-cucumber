package ExecuteTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class RunTest {

    public void openApp(){
        AppiumDriver appiumDriver;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("automationName","uiautomator2");
        desiredCapabilities.setCapability("platformName","android");
        desiredCapabilities.setCapability("udid","HVA222NA");
        desiredCapabilities.setCapability("appPackage","com.lazada.android");
        desiredCapabilities.setCapability("appActivity","com.lazada.activities.EnterActivity");
        try {
            URL appiumSerPath  = new URL("http://localhost:4723/wd/hub");
            appiumDriver = new AndroidDriver(appiumSerPath, desiredCapabilities);

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[ERR] could not create appim session");
        }
    }
}
