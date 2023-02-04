package StepDefinition;

import ExecuteTest.RunTest;
import Utilities.Configuration;
import Utilities.PageUtil;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Steps {
    public RunTest test;
    public Map<String, String> mapFileYaml = new HashMap<>();
    public PageUtil pageUtil =new PageUtil();
    @Before
    public void start(){
        Configuration.readConfig();
        test = new RunTest();
        this.mapFileYaml=  pageUtil.findFileToName(new File(System.getProperty("user.dir") + "/src/test/resources/pages"),this.mapFileYaml);

    }

    @Given("I open application")
    public void i_open_application() {
        test.openApp();
    }
    @Given("I change the page spec to {word}")
    public void changePage(String page) throws FileNotFoundException {
        test.changePageSpec(page, this.mapFileYaml);
    }

}

