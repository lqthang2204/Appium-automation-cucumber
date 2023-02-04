package ExecuteTest;

import ElementsPages.Element;
import ElementsPages.Page;
import Utilities.Configuration;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunTest {
    AppiumDriver appiumDriver;
    public Map<String, Page> map = new HashMap<>();
    public void openApp(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("automationName", Configuration.AUTOMATION_NAME);
        desiredCapabilities.setCapability("platformName",Configuration.PLATFORM_NAME);
        desiredCapabilities.setCapability("udid",Configuration.UD_ID);
        desiredCapabilities.setCapability("appPackage",Configuration.APP_PACKAGE);
        desiredCapabilities.setCapability("appActivity",Configuration.APP_ACTIVITY);
        try {
            URL appiumSerPath  = new URL(Configuration.PATH_SERVER);
            appiumDriver = new AndroidDriver(appiumSerPath, desiredCapabilities);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("[ERR] could not create appim session");
        }
    }
    public Page changePageSpec(String pageYaml, Map<String,String> mapFileYaml) throws FileNotFoundException {
        Page page ;
        String pathFile = null;
        if(map.containsKey(pageYaml)){
            page = map.get(pageYaml);
        }else {
            try {
                 pathFile = mapFileYaml.get(pageYaml + ".yaml");
                Yaml yaml = new Yaml(new Constructor(Page.class));
                InputStream input = new FileInputStream(pathFile);
                page = yaml.load(input);
                List<Element> list = page.getElements();
                System.out.println(list.get(0).getId() +"=====================thang");
            }
            catch (Exception e){
                System.out.println("path file yaml "+ pathFile);
                throw new FileNotFoundException();
            }
        }
        return  page;

    }
}
