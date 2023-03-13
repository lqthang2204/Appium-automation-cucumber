package StepDefinition;

import ExecuteTest.RunScripts;
import ManageDriver.AndroidDriverProvider;
import Utilities.Configuration;
import Utilities.PageUtil;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class Steps {
    public RunScripts test  = new RunScripts();;
    public Map<String, String> mapFileYaml = new HashMap<>();
    public PageUtil pageUtil =new PageUtil();
    AndroidDriverProvider provider = new AndroidDriverProvider();
//    WebDriver driver;
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();

    public void setDriver(AppiumDriver driver){
            this.driver.set(driver);
    }
    public  AppiumDriver getDriver(){
        return this.driver.get();
    }
    public void openApp() throws MalformedURLException {
         setDriver(provider.getAndroidDriver());
         WebDriverRunner.setWebDriver(getDriver());
        this.mapFileYaml=  pageUtil.findFileToName(new File(System.getProperty("user.dir") + "/src/test/resources/pages"),this.mapFileYaml);
    }

    @Given("I open application")
    public void i_open_application() throws MalformedURLException {
        openApp();
        test.setWait(getDriver());
    }
    @Given("I change the page spec to {word}")
    public void changePage(String page) throws FileNotFoundException {
        test.changePageSpec(page, this.mapFileYaml);
    }
    @Given("I click element {}")
    public void clickElement(String element) {
        test.clickElement(element);
    }

    @Given("I wait for element {} to be {}")
    public void i_wait_for_element_fitbit_title_to_be_displayed(String element, String condition) {
        test.WaitToCondition(element, condition);
    }
    @Given("I type {string} into element {word}")
    public void TypeToElement(String value, String element) {
        test.TypeValueToElement(value, element);
    }
    @Given("I {word} into element have text {}")
    public void ConditionHaveText(String condition, String text) {
        test.ExecuteWithText(condition,text);
    }

    @Given("I scroll to element {}")
    public void i_scroll_to_element_option_language_with_text(String element) {
      test.scrollToElement(element);
    }
    @After
    public void CloseApp(){
        System.out.println("close webdriver.................");
        WebDriverRunner.closeWebDriver();

    }
}

