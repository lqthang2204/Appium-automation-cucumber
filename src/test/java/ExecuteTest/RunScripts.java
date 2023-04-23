package ExecuteTest;

import ElementsPages.*;
import UserManagement.User;
import UserManagement.UserAddress;
import Utilities.Configuration;
import Utilities.Util;
import com.codeborne.selenide.*;
import com.codeborne.selenide.appium.AppiumClickOptions;
import com.github.javafaker.Address;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.nativekey.PressesKey;
import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.Duration;
import java.util.*;


public class RunScripts {
    AppiumDriver appiumDriver;
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
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
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
        if (value == "") {
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

    public void getAction(String action, DataTable dataTable, Map<String, String> map) throws InterruptedException {
        boolean flag = false;
        String data ="";
        try {
            List<Action> listActions = page.getActions();
            By by = null;
            long timeout;
            Wait wait = null;
            for (int i = 0; i < listActions.size(); i++) {
                if (listActions.get(i).getId().equals(action)) {
                    List<ActionElements> listActionElements = listActions.get(i).getActionElements();
                    for (int j = 0; j < listActionElements.size(); j++) {
                        if (listActionElements.get(j).getCondition() != null && listActionElements.get(j).getTimeout() != 0) {
                            flag = true;
                            timeout = listActionElements.get(j).getTimeout();
                           Element element = listActionElements.get(j).getElement();
                            if(dataTable!=null){
                                Map<String, String> mapOverride = dataTable.asMap(String.class, String.class);
                                 data = getTextFromAction(element.id,mapOverride, map);
                            }
                            List<Locator> listLocator = listActionElements.get(j).getElement().getList();
                            for (int k = 0; k < listLocator.size(); k++) {
                                if (listLocator.get(k).getDevice().equalsIgnoreCase(Configuration.PLATFORM_NAME)) {
                                    by = getBy(listLocator.get(k), "");
                                    break;
                                }
                            }
                            try {
                                wait = new FluentWait(appiumDriver).withTimeout(Duration.ofMillis(timeout));
                                switch (listActionElements.get(j).getCondition()) {
                                    case "DISPLAYED":
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
                                if (listActionElements.get(j).inputType != "") {
                                    runType(by, listActionElements.get(j).inputType, data);
                                }
                            } catch (Exception e) {
                                System.out.println("fail is running action");
                                e.printStackTrace();
                            }
                        } else {
                            Element element = listActionElements.get(j).getElement();
                            if(dataTable!=null){
                                Map<String, String> mapOverride = dataTable.asMap(String.class, String.class);
                                data = getTextFromAction(element.id,mapOverride, map);
                            }
                            List<Locator> listLocator = listActionElements.get(j).getElement().getList();
                            for (int k = 0; k < listLocator.size(); k++) {
                                if (listLocator.get(k).getDevice().equalsIgnoreCase(Configuration.PLATFORM_NAME)) {
                                    by = getBy(listLocator.get(k), "");
                                    break;
                                }
                            }
                            wait = new FluentWait(appiumDriver).withTimeout(Duration.ofMillis(com.codeborne.selenide.Configuration.timeout));
                            switch (listActionElements.get(j).getCondition()) {
                                case "DISPLAYED":
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
                            if (listActionElements.get(j).inputType != "") {
                                runType(by, listActionElements.get(j).inputType, data);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(flag);
        }


    }


    public void runType(By by, String status, String value) {
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
        switch (status) {
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


    public void scrollToElement(String element) {
        By by = getBytoElement(element);
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
    }

    public void setWait(AppiumDriver driver) {
        this.appiumDriver = driver;
        wait = new SelenideWait(driver, com.codeborne.selenide.Configuration.timeout, com.codeborne.selenide.Configuration.pollingInterval);
    }

    public void WaitToCondition(String element, String condition) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
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

    public void TypeValueToElement(String value, String element, Map<String, String> map) {
        By by = getBytoElement(element);
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
        if (map.containsKey(value)) {
            value = map.get(value);
        }
        Selenide.$(by).shouldBe(Condition.visible).setValue(value);
    }

    public void verifyElementHaveValue(String element, String value, Map<String, String> map) {
        By by = getBytoElement(element);
        Actions action = new Actions(this.appiumDriver);
        action.scrollToElement(Selenide.$(by));
        String expected = Selenide.$(by).getText();
        if (map != null && map.containsKey(value)) {
            value = map.get(value);
        }
        Assert.assertEquals(expected, value);
    }

    public void saveTextFromElement(String element, String key, Map<String, String> map) {
        try {
            By by = getBytoElement(element);
            Selenide.$(by).isDisplayed();
            String valueOfElement = Selenide.$(by).getText();
            map.put("KEY." + key, valueOfElement);
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }


    }

    public void clickKeyboard(String valueKey, String element) {
        try {
            By by = getBytoElement(element);
            Selenide.$(by).sendKeys(Keys.valueOf(valueKey));
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }

    }

    public void ClickKeyboard(String key) {
        AndroidDriver androidDriver;
        switch (key) {
            case "search":
                androidDriver = (AndroidDriver) this.appiumDriver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
                break;
            case "back":
                ((PressesKey) appiumDriver).pressKey(new KeyEvent(AndroidKey.BACK));
                break;
            default:
                System.out.println("Not supper key have value " + key);
        }

    }
    public void VerifyProperty(String element, String property, String value, Map<String, String> map){
        By by =   getBytoElement(element);
        String attribute = Selenide.$(by).getAttribute(property);
        if(map.containsKey(value)){
           value =  map.get(value);
        }
        System.out.println("attribute = "+ attribute);
        Assert.assertEquals(attribute, value);
    }

    public AndroidDriver castAndroidDriver(AppiumDriver driver) {
        return (AndroidDriver) driver;
    }

    public By getBytoElement(String element) {
        List<String> list = getElementToText(element);
        Locator locator;
        By by;
        if (list == null) {
            locator = findLocator(element);
            by = getBy(locator, "");
        } else {
            locator = findLocator(list.get(1));
            by = getBy(locator, list.get(0));
        }
        return by;
    }


    public String getTextFromAction(String element, Map<String, String> mapDataTable, Map<String, String> mapSavetext) {
        String data = "";
        if (mapDataTable.containsKey(element)) {
            data = mapDataTable.get(element);
            if (mapSavetext.containsKey(data)) {
                data = mapSavetext.get(data);
                return data;
            }
        }
        return data;

    }
    public String getprofileUser(String value, List<User> list) throws ParseException {
        if(value.contains("USER.")){
            User user;
            String[] arr = value.split(".");
            if(Util.isNumber(arr[1]) && Util.checkLength(arr)){
               user =   list.get(Integer.parseInt(arr[1]));
            }else{
                user = list.get(0);
            }
            switch (arr[1])
            {
                case "firstName" :
                    value =  user.getFirstName();
                    break;
                case "lastName" :
                    value =   user.getLastName();
                break;
                case "middleName" :
                    value =   user.getMiddleName();
                break;
                case "fullName" :
                    value =   user.getFullName();
                break;
                case "prefix" :
                    value =   user.getPrefix();
                break;
                case "suffix" :
                    value =   user.getSuffix();
                break;
                case "email" :
                    value =   user.getEmail();
                break;
                case "password" :
                    value =   user.getPassword();
                break;
                case "dob" :
                    value =   Util.convertMilisecondsToDob(user.getDob());
                break;
                case "city" :
                    value =   user.getUserAddresses().getCity();
                break;
                case "state" :
                    value =   user.getUserAddresses().getState();
                break;
                case "street" :
                    value =   user.getUserAddresses().getStreetOne();
                break;
                case "zip" :
                    value =   user.getUserAddresses().getZip();
                break;
                case "phoneNumber" :
                    value =   user.getUserAddresses().getPhoneNumber();
                break;
                default:
                    System.out.println("not support key value");
                    throw new RuntimeException();
            }
        }
        return value;

    }
    public List<User> getUser(List<User> list) throws ParseException {
        if(list == null){
            list = new LinkedList<>();
        }
        Faker fakeUser = new Faker();
        User user = new User();
        UserAddress address = new UserAddress();
        Name name = fakeUser.name();
        Address addressFaker = fakeUser.address();
        user.setFirstName(name.firstName());
        user.setLastName(name.lastName());
        user.setMiddleName(name.nameWithMiddle());
        user.setFullName(name.fullName());
        user.setPrefix(name.prefix());
        user.setSuffix(name.suffix());
        address.setCity(addressFaker.city());
        address.setState(addressFaker.state());
        address.setZip(addressFaker.zipCode());
        address.setPhoneNumber(fakeUser.phoneNumber().phoneNumber());
        address.setStreetOne(addressFaker.streetAddress());
        user.setDob(Util.convertDobToMiliseconds(fakeUser.date().birthday()));
        user.setEmail(fakeUser.bothify(name.fullName().replace(" ","")+"Test@gmail.com"));
        user.setPassword(fakeUser.internet().password(6,9,true,true,true));
        user.setUserAddresses(address);
        list.add(user);
        return list;
    }
}
