package StepDefinition;

import ExecuteTest.RunScripts;
import ManageDriver.AndroidDriverProvider;
import ManageDriver.Hook;
import Utilities.Configuration;
import Utilities.PageUtil;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
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
//    WebDriver driver;
    public ThreadLocal<AppiumDriver> driver = new ThreadLocal<AppiumDriver>();
    public Map<String, String> mapSaveText;

    public void setDriver(AppiumDriver driver){
            this.driver.set(driver);
    }
    public  AppiumDriver getDriver(){
        return this.driver.get();
    }
    public AppiumDriverLocalService service;
    public void openApp() throws MalformedURLException {
         setDriver(new Hook().getAndroidDriver());
         WebDriverRunner.setWebDriver(getDriver());
        this.mapFileYaml=  pageUtil.findFileToName(new File(System.getProperty("user.dir") + "/src/test/resources/pages"),this.mapFileYaml);
        mapSaveText = new HashMap<>();
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
    @Given("I perform {word} action")
    public void i_perform_click_if_existing_action(String action) throws InterruptedException {
        test.getAction(action);

    }

    @Given("I wait for element {} to be {}")
    public void i_wait_for_element_fitbit_title_to_be_displayed(String element, String condition) {
        test.WaitToCondition(element, condition);
    }
    @Given("I type {string} into element {}")
    public void TypeToElement(String value, String element) {
        test.TypeValueToElement(value, element, this.mapSaveText);
    }

    @Given("I scroll to element {}")
    public void i_scroll_to_element_option_language_with_text(String element) {
      test.scrollToElement(element);
    }
    @Given("I verify the text for element {word} is {string}")
    public void verifyElement(String element, String value) {
        test.verifyElementHaveValue(element, value, this.mapSaveText);

    }
    @Given("I save text for element {word} with key {string}")
    public void i_save_text_for_element_title_suggestion_with_key(String element, String key) {
        test.saveTextFromElement(element, key, this.mapSaveText);

    }
    @Given("I click keyboard {word} button on element {word}")
    public void i_click_keyboard_enter_button_on_element_search_product(String valueKeys, String element) {
                test.clickKeyboard(valueKeys, element);
    }
    @Given("I click {word} button in keyboard")
    public void i_click_search_button_in_keyboard(String key) {
        test.ClickKeyboard(key);
    }
    @After
    public void CloseApp(){
        System.out.println("close webdriver.................");
        WebDriverRunner.closeWebDriver();
        if(Hook.service.isRunning()){
            System.out.println("============================== Stop services=========================");
            Hook.service.stop();
        }

//        WebDriverRunner.clearBrowserCache();

    }
}

