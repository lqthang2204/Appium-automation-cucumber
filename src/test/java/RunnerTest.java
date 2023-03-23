import ManageDriver.Hook;
import Utilities.Configuration;
import com.codeborne.selenide.Config;
import com.codeborne.selenide.Selenide;
import io.cucumber.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;
import org.testng.annotations.*;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/featureTiki", glue = {"StepDefinition"}, tags = "", plugin = {"json:target/cucumber.json", "pretty"})


public class RunnerTest extends AbstractTestNGCucumberTests {

    @BeforeTest(alwaysRun = true)
    @Parameters({"automation_name","platform_name","udid","appPackage","appActivity","port_number","timeout","pollingInterval","runServiceAuto"})
    public void start(String automation_name, String platform_name, String udid, String appPackage, String appActivity, String port_number, long timeout, long pollingInterval,String runServiceAuto){
        System.out.println("=====================================reading config to XML file==========================================");
        if(System.getProperty("automation_name")==null){
            Configuration.AUTOMATION_NAME =  automation_name;
            Configuration.PLATFORM_NAME = platform_name;
            Configuration.UD_ID = udid;
            Configuration.APP_PACKAGE = appPackage;
            Configuration.APP_ACTIVITY = appActivity;
            Configuration.PATH_SERVER = "http://127.0.0.1:"+port_number+"/wd/hub";
            com.codeborne.selenide.Configuration.timeout = timeout;
            com.codeborne.selenide.Configuration.pollingInterval = pollingInterval;
            Configuration.RUN_SERVICE_AUTO = Boolean.parseBoolean(runServiceAuto);
            Configuration.PORT_NUMBER = Integer.parseInt(port_number);
        }else{
            Configuration.AUTOMATION_NAME =  System.getProperty("automation_name");
            Configuration.PLATFORM_NAME = System.getProperty("platform_name");
            Configuration.UD_ID = System.getProperty("udid");
            Configuration.APP_PACKAGE = System.getProperty("appPackage");
            Configuration.APP_ACTIVITY = System.getProperty("appActivity");
            String port = System.getProperty("port_number");
            Configuration.PATH_SERVER = "http://127.0.0.1:"+port+"/wd/hub";

        }
    }
    @AfterSuite
    public void closeService(){
        if(Hook.service.isRunning()){
            System.out.println("Closing service have url "+ Hook.service.getUrl());
            Hook.service.stop();
        }

    }
}
