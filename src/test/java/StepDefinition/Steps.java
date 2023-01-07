package StepDefinition;

import ExecuteTest.RunTest;
import Utilities.Configuration;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

public class Steps {
    public RunTest test;
    @Before
    public void start(){
        Configuration.readConfig();
        test = new RunTest();
    }

    @Given("I open application")
    public void i_open_application() {
        test.openApp();
    }

}

