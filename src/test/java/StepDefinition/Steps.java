package StepDefinition;

import ExecuteTest.RunTest;
import ManageDriver.AndroidDriverProvider;
import Utilities.Configuration;
import Utilities.PageUtil;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.appium.SelenideAppium;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class Steps {
    public RunTest test;
    public Map<String, String> mapFileYaml = new HashMap<>();
    public PageUtil pageUtil =new PageUtil();
    WebDriver driver;
    public void openApp() throws MalformedURLException {
         driver  = AndroidDriverProvider.getAndroidDriver();
         WebDriverRunner.setWebDriver(driver);
    }
    @Before
    public void start(){
        Configuration.readConfig();
        test = new RunTest();
        this.mapFileYaml=  pageUtil.findFileToName(new File(System.getProperty("user.dir") + "/src/test/resources/pages"),this.mapFileYaml);


    }

    @Given("I open application")
    public void i_open_application() throws MalformedURLException {
        openApp();
        test.setWait(driver);
    }
    @Given("I change the page spec to {word}")
    public void changePage(String page) throws FileNotFoundException {
        test.changePageSpec(page, this.mapFileYaml);
    }
    @Given("I click element {}")
    public void clickElement(String element) {
        test.clickElement(element);
    }

    @Given("I wait for element {word} to be {}")
    public void i_wait_for_element_fitbit_title_to_be_displayed(String element, String condition) {
        test.WaitToCondition(element, condition);
    }
    @After
    public void CloseApp(){
        System.out.println("close webdriver.................");
        WebDriverRunner.closeWebDriver();
    }
}

