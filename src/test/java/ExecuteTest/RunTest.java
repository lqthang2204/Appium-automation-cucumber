package ExecuteTest;

import ElementsPages.Element;
import ElementsPages.Locator;
import ElementsPages.Page;
import Utilities.Configuration;
import com.codeborne.selenide.*;
import com.codeborne.selenide.appium.AppiumClickOptions;
import com.codeborne.selenide.appium.SelenideAppium;
import com.codeborne.selenide.appium.commands.AppiumClick;
import com.codeborne.selenide.commands.ShouldBe;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RunTest {
    WebDriver appiumDriver;
    public Map<String, Page> map = new HashMap<>();
    public Page page;
    public SelenideWait wait;

    public void getDriver() {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability("automationName", Configuration.AUTOMATION_NAME);
            desiredCapabilities.setCapability("platformName", Configuration.PLATFORM_NAME);
            desiredCapabilities.setCapability("udid", Configuration.UD_ID);
            desiredCapabilities.setCapability("appPackage", Configuration.APP_PACKAGE);
            desiredCapabilities.setCapability("appActivity", Configuration.APP_ACTIVITY);
            URL appiumSerPath = new URL(Configuration.PATH_SERVER);
            appiumDriver = new AndroidDriver(appiumSerPath, desiredCapabilities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("[ERR] could not create appium session");
        }
    }

    public Page changePageSpec(String pageYaml, Map<String, String> mapFileYaml) throws FileNotFoundException {
        String pathFile = null;
        if (map.containsKey(pageYaml)) {
            page = map.get(pageYaml);
        } else {
            try {
                pathFile = mapFileYaml.get(pageYaml + ".yaml");
                Yaml yaml = new Yaml(new Constructor(Page.class));
                InputStream input = new FileInputStream(pathFile);
                page = yaml.load(input);
                map.put(pageYaml, page);
            } catch (Exception e) {
                System.out.println("path file yaml " + pathFile);
                throw new FileNotFoundException();
            }
        }
        return page;
    }

    public Locator findLocator(String element) {
        Locator locator = null;
        List<Element> list = page.getElements();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(element)) {
                List<Locator> listLocators = list.get(i).getList();
                for (int j = 0; j < listLocators.size(); j++) {
                    if (listLocators.get(j).getDevice().equalsIgnoreCase(Configuration.PLATFORM_NAME)) {
                        locator = listLocators.get(j);
                        break;
                    }
                }
            }
        }

        if (locator == null) {
            throw new NotFoundException("Not Found Element " + element + " in page");
        }
        return locator;
    }

    public By getBy(Locator locator, String value) {
        if(value==""){
             value = locator.getValue();
        }
        switch (locator.getType()) {
            case "XPATH":
                return By.xpath(value);
            case "ACCESSIBILITY_ID":
                return AppiumBy.accessibilityId(value);
            case "CSS":
                return By.cssSelector(value);
            case "CLASS_NAME":
                return By.className(value);
            case "LINK_TEXT":
                return By.linkText(value);
            case "PARTIALLINK_TEXT":
                return By.partialLinkText(value);
            case "ID":
                return By.id(value);
            case "NAME":
                return By.name(value);
            default:
                throw new NotFoundException("the locator type " + locator.getType() + " is not supported.");
        }
    }

    public void clickElement(String element) {
        List<String> list = getElementToText(element);
        Locator locator;
        By by;
        if(list==null){
             locator = findLocator(element);
             by = getBy(locator, "");
        }else{
            locator = findLocator(list.get(1));
             by = getBy(locator, list.get(0));
        }
        wait.until(ExpectedConditions.elementToBeClickable(by));
//        appiumDriver.findElement(by).click();
//        Selenide.$(by).click();
        Selenide.$(by).click(AppiumClickOptions.tap());
    }

    public void setWait(WebDriver driver) {
        this.appiumDriver = driver;
        wait = new SelenideWait(driver, com.codeborne.selenide.Configuration.timeout, com.codeborne.selenide.Configuration.pollingInterval);
    }

    public void WaitToCondition(String element, String condition) {
        Locator locator = findLocator(element);
        By by = getBy(locator,"");
        switch (condition) {
            case "DISPLAYED":
                Selenide.$(by).shouldBe(Condition.appear);
                break;
            case "NOT_DISPLAYED":
                Selenide.$(by).shouldBe(Condition.disappear);
                break;
            case "EXIST":
                Selenide.$(by).shouldBe(Condition.exist);
                break;
            case "NOT_EXIST":
                Selenide.$(by).shouldBe(Condition.not(Condition.exist));
                break;
            case "ENABLED":
                Selenide.$(by).shouldBe(Condition.enabled);
                break;
            case "NOT_ENABLED":
                Selenide.$(by).shouldBe(Condition.disabled);
                break;
            case "SELECTED":
                Selenide.$(by).shouldBe(Condition.selected);
                break;
            case "NOT_SELECTED":
                Selenide.$(by).shouldBe(Condition.not(Condition.selected));
                break;
            case "CHECKED":
                Selenide.$(by).shouldBe(Condition.checked);
                break;
            case "NOT_CHECKED":
                Selenide.$(by).shouldBe(Condition.not(Condition.checked));
                break;
            case "FOCUSED":
                Selenide.$(by).shouldBe(Condition.focused);
                break;
            case "NOT_FOCUSED":
                Selenide.$(by).shouldBe(Condition.not(Condition.focused));
                break;
            case "HIDEN":
                Selenide.$(by).shouldBe(Condition.hidden);
                break;
            case "NOT_HIDDEN":
                Selenide.$(by).shouldBe(Condition.not(Condition.hidden));
                break;
            default:
                throw new NotFoundException("Not Support Condition for wait " + condition);

        }
    }

    public List<String> getElementToText(String element) {
        if (element.contains("with text")) {
            List<String> list = new ArrayList<>();
            String value = "";
            String[] text = element.split("with text");
            element = text[1].trim().replace("\"", "");
            Locator locator = findLocator(text[0].trim());
            value = locator.getValue();
            if (value.contains("{text}")) {
//                if(map.containsKey(element)){
//                    element = map.get(element);
//                }
                list.add(value.replace("{text}", element));
                list.add(text[0].trim());
                return list;
            }
        }
        return null;
    }
}
