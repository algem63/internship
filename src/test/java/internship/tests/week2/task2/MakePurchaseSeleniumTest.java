package internship.tests.week2.task2;

import internship.steps.saucedemo.SauceDemoSeleniumSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static internship.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MakePurchaseSeleniumTest {

    private SauceDemoSeleniumSteps steps;

    @ParameterizedTest(name = "Оформление заказа через Selenium (браузер: {0})")
    @ValueSource(strings = {"chrome", "firefox"})
    void seleniumPurchaseTest(String browser) {
        steps = new SauceDemoSeleniumSteps(browser);
        String pageHeader = steps
                .open()
                .loginAsStandardUser()
                .addItemsToCart()
                .fillInUserData(FIRST_NAME, LAST_NAME, POSTAL_CODE)
                .getCompletePurchasePageTitleText();
        assertEquals(TITLE_LABEL_TEXT, pageHeader);
    }

    @AfterEach
    void tearDown() {
        if (steps != null) {
            steps.closeBrowser();
        }
    }
}