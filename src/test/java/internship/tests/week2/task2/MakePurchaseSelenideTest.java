package internship.tests.week2.task2;

import com.codeborne.selenide.Configuration;
import internship.steps.saucedemo.SauceDemoSelenideSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static internship.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

public class MakePurchaseSelenideTest {

    @BeforeEach
    public void setUp() {
        Configuration.pageLoadTimeout = 10000;
    }

    @ParameterizedTest(name = "Оформление заказа с помощью Selenide (браузер: {0})")
    @ValueSource(strings = {"chrome", "firefox"})
    void selenideAuthTest(String browser) {
        Configuration.browser = browser;

        String pageHeader = new SauceDemoSelenideSteps()
                .open()
                .loginAsStandardUser()
                .addItemsToCart()
                .fillInUserData(FIRST_NAME, LAST_NAME, POSTAL_CODE)
                .getCompletePurchasePageTitleText();
        assertEquals(TITLE_LABEL_TEXT, pageHeader);
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }
}
