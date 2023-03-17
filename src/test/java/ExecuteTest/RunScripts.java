package ExecuteTest;

import ElementsPages.*;
import Utilities.Configuration;
import com.codeborne.selenide.*;
import com.codeborne.selenide.appium.AppiumClickOptions;
import com.codeborne.selenide.selector.ByText;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumFluentWait;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.cucumber.java.eo.Se;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RunScripts {
    WebDriver appiumDriver;
    public Map<String, Page> map = new HashMap<>();
    public Page page;
    public SelenideWait wait;

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
            } catch (FileNotFoundException e) {
                System.out.println("path file yaml " + pathFile);
                throw new FileNotFoundException(e.getMessage());
            }
            catch (Exception ex){
                throw  new RuntimeException(ex.getMessage());
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
         By by = getBytoElement(element);
         Selenide.$(by).shouldBe(Condition.enabled).click(AppiumClickOptions.tap());
    }
    public void getAction(String action) throws InterruptedException {
        boolean flag = false;
        try {
            List<Action> listActions = page.getActions();
            By by = null;
            long timeout;
            Wait wait = null;
            for(int i=0;i<listActions.size();i++){
                if(listActions.get(i).getId().equals(action)){
                    List<ActionElements> listActionElements = listActions.get(i).getActionElements();
                    for(int j=0;j<listActionElements.size();j++){
                        if(listActionElements.get(j).getCondition()!=null){
                            if(listActionElements.get(j).getTimeout()  !=0){
                                flag = true;
                            }
                            timeout = listActionElements.get(j).getTimeout()  ==0 ? com.codeborne.selenide.Configuration.timeout : listActionElements.get(j).getTimeout();
                            List<Locator> listLocator = listActionElements.get(j).getElement().getList();
                            for(int k=0;k<listLocator.size();k++){
                                if(listLocator.get(k).getDevice().equalsIgnoreCase(Configuration.PLATFORM_NAME)){
                                    by = getBy(listLocator.get(k), "");
                                    break;

                                }
                            }
                            wait= new FluentWait(appiumDriver).withTimeout(Duration.ofMillis(timeout));
                            switch (listActionElements.get(j).getCondition()){
                                case  "DISPLAYED":
                                    wait.until(ExpectedConditions.visibilityOf(Selenide.$(by)));
                                    break;
                                case "NOT_DISPLAYED":
                                    wait.until(ExpectedConditions.invisibilityOf(Selenide.$(by)));
                                    break;
                                case "ENABLED":
                                    wait.until(ExpectedConditions.elementToBeClickable(Selenide.$(by)));
                                    break;
                                case "NOT_ENABLED":
                                    wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(Selenide.$(by))));
                                    break;
                            }
                            if(listActionElements.get(j).inputType!=""){
                                runType(by, listActionElements.get(j).inputType,"");
                            }

                        }
                        else{
                            runType(by, listActionElements.get(j).inputType,"");
                        }

                    }

                }

            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(flag);
        }


    }
    public void runType(By by, String status,String value){
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
        switch (status){
            case "text":
                Selenide.$(by).setValue(value);
                break;
            case "click":
                Selenide.$(by).click();
                break;
            default:
                throw new RuntimeException();


        }
    }


    public void scrollToElement(String element){
        By by = getBytoElement(element);
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
    }
    public void setWait(WebDriver driver) {
        this.appiumDriver = driver;
        wait = new SelenideWait(driver, com.codeborne.selenide.Configuration.timeout, com.codeborne.selenide.Configuration.pollingInterval);
    }
    public void WaitToCondition(String element, String condition) {
        By by = getBytoElement(element);
//        Locator locator = findLocator(element);
//        By by = getBy(locator,"");
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
                Selenide.$(by).shouldNot((Condition.selected));
                break;
            case "CHECKED":
                Selenide.$(by).shouldBe(Condition.checked);
                break;
            case "NOT_CHECKED":
                Selenide.$(by).shouldNot((Condition.checked));
                break;
            case "FOCUSED":
                Selenide.$(by).shouldBe(Condition.focused);
                break;
            case "NOT_FOCUSED":
                Selenide.$(by).shouldNot((Condition.focused));
                break;
            case "HIDEN":
                Selenide.$(by).shouldBe(Condition.hidden);
                break;
            case "NOT_HIDDEN":
                Selenide.$(by).shouldNot((Condition.hidden));
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
    public void TypeValueToElement(String value,String element){
        By by = getBytoElement(element);
//        Locator locator = findLocator(element);
//        By by = getBy(locator,"");
        Selenide.$(by).shouldBe(Condition.visible).setValue(value);
    }
    public void verifyElementHaveValue(String element, String value){
        By by = getBytoElement(element);
        String expected = Selenide.$(by).getText();
        Assert.assertEquals(expected, value);
    }
    public By getBytoElement(String element){
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
        return by;
    }
}
