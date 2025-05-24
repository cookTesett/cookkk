package stepdefinitions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/test/resources/features",
        glue = { "stepdefinitions" },
        plugin = { "json:target/cucumber-report.json", "summary" },
        monochrome = true,
        snippets = SnippetType.CAMELCASE
)


public class RunTest {
}
