package internship.tests.week2.task4;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import internship.config.Config;
import internship.steps.herokuapp.HerokuAppSelenideSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ElementVisibilityTest {

    @BeforeAll
    public static void setUp() {
        Configuration.pageLoadTimeout = 10000;
    }

    @ParameterizedTest(name = "Ожидание загрузки стиля (браузер: {0})")
    @ValueSource(strings = {"chrome", "firefox"})
    public void testElementVisibility(String browser) {
        Configuration.browser = browser;

        String divText = new HerokuAppSelenideSteps()
                .open(Config.INSTANCE.herokuappDynamicLoadingUrl())
                .clickStartAndCheckDivText();
        assertEquals("Hello World!", divText);
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
