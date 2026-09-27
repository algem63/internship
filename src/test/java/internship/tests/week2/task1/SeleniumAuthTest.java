package internship.tests.week2.task1;

import internship.steps.saucedemo.SauceDemoSeleniumSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static internship.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumAuthTest {

    private SauceDemoSeleniumSteps steps;

    @ParameterizedTest(name = "Авторизация с помощью Selenium (браузер: {0})")
    @ValueSource(strings = {"chrome", "firefox"})
    void seleniumAuthTest(String browser) {
        steps = new SauceDemoSeleniumSteps(browser);
        String pageHeader = steps
                .open()
                .loginAsStandardUser()
                .getInventoryPageHeaderText();
        assertEquals(HEADER_LABEL_TEXT, pageHeader);
    }

    @AfterEach
    void tearDown() {
        if (steps != null) {
            steps.closeBrowser();
        }
    }
}
