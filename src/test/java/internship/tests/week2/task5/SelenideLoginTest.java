package internship.tests.week2.task5;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import internship.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static internship.constants.Constants.*;
import static internship.selectors.HerokuAppSelectors.*;

public class SelenideLoginTest {

    @BeforeAll
    public static void setUp() {
        Configuration.pageLoadTimeout = 10000;
    }

    @ParameterizedTest(name = "Проверка невалидного логина (Браузер: {0} | Логин: ''{1}'')")
    @MethodSource("internship.utils.TestDataProvider#provideBrowsersAndCredentials")
    void testInvalidLogin(String browser, String username, String password) {
        Configuration.browser = browser;
        Selenide.open(Config.INSTANCE.herokuappLoginUrl());

        $(USERNAME).setValue(username);
        $(PASSWORD).setValue(password);
        $(LOGIN_BTN).click();
        $(ERROR_MESSAGE).shouldHave(text(EXPECTED_ERROR));
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
