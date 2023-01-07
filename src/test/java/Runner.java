import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
//import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = {"StepDefinition"})
public class Runner {

}
