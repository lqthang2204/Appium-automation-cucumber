package StepDefinition;

import ExecuteTest.RunScripts;
import ManageDriver.AndroidDriverProvider;
import ManageDriver.Hook;
import UserManagement.User;
import Utilities.Configuration;
import Utilities.PageUtil;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.*;
import io.cucumber.java.en.Given;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Steps {
    public RunScripts test  = new RunScripts();
    Scenario scenario;
    public Map<String, String> mapFileYaml = new HashMap<>();
    public PageUtil pageUtil =new PageUtil();
//    WebDriver driver;
    public Map<String, String> mapSaveText;
    public AppiumDriver driver;
    public AppiumDriverLocalService service;
    public List<User> list;
    public void openApp() throws MalformedURLException {
         this.driver = new Hook().getAndroidDriver();
         WebDriverRunner.setWebDriver(this.driver);
    }
    @Before
    public void setUp(){
        this.mapFileYaml=  pageUtil.findFileToName(new File(System.getProperty("user.dir") + "/src/test/resources/pages"),this.mapFileYaml);
        mapSaveText = new HashMap<>();
    }

    @Given("I open application")
    public void i_open_application() throws MalformedURLException {
        openApp();
        test.setWait(this.driver);

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
        test.getAction(action,null, this.mapSaveText, this.list);

    }
    @Given("I perform {word} action with override values")
    public void i_perform_action_search_product_action_with_override_values(String action, DataTable table) throws InterruptedException {
        test.getAction(action,table, this.mapSaveText, this.list);
    }


    @Given("I wait for element {} to be {}")
    public void i_wait_for_element_fitbit_title_to_be_displayed(String element, String condition) {
        test.WaitToCondition(element, condition);
    }
    @Given("I type {string} into element {}")
    public void TypeToElement(String value, String element) throws ParseException {
        test.TypeValueToElement(value, element, this.mapSaveText, this.list);
    }

    @Given("I scroll to element {}")
    public void i_scroll_to_element_option_language_with_text(String element) {
      test.scrollToElement(element);
    }
    @Given("I verify the text for element {word} is {string}")
    public void verifyElement(String element, String value) throws ParseException {
        test.verifyElementHaveValue(element, value, this.mapSaveText, this.list);

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
        test.ClickKeyboard(key.toLowerCase());
    }
    @Given("I verify attribute element {} has css property {} with value {string}")
    public void i_verify_attribute_element_user_field_has_css_property_placeholder_with_value(String element, String property, String value) throws ParseException {
        test.VerifyProperty(element, property, value, mapSaveText, this.list);
    }
    @Given("I become a random user")
    public void i_become_a_random_user() throws ParseException {
       list =  test.getUser(this.list);

    }
    @Given("I am {word} created by the file")
    public void i_am_alabama_user_created_by_the_file(String nameFile) {
        list = test.getUserFormFile(this.list, nameFile);

    }
    @After
    public void CloseApp(Scenario scenario){
        this.scenario = scenario;
        if(scenario.isFailed() && this.driver!=null){
            final  byte[]  screenshot = ((TakesScreenshot)this.driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png",scenario.getName());
        }
        System.out.println("close webdriver.................");
//        WebDriverRunner.closeWebDriver();
        if(Configuration.RUN_SERVICE_AUTO && Hook.service.isRunning()){
           Hook.service.stop();
        }
//        if(Hook.service!=null){
//            if(Hook.service.isRunning()){
//                System.out.println("============================== Stop services=========================");
//                Hook.service.stop();
//            }
//        }



//        WebDriverRunner.clearBrowserCache();

    }
}

