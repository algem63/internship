package internship.tests.week2.task5;

import internship.config.Config;
import internship.utils.WebDriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static internship.constants.Constants.EXPECTED_ERROR;
import static internship.selectors.HerokuAppSelectors.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumLoginTest {

    private WebDriver driver;

    @ParameterizedTest(name = "Проверка невалидного логина (Браузер: {0} | Логин: ''{1}'')")
    @MethodSource("internship.utils.TestDataProvider#provideBrowsersAndCredentials")
    void testInvalidLogin(String browser, String username, String password) {
        driver = WebDriverFactory.createDriver(browser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.INSTANCE.herokuappLoginUrl());

        WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(USERNAME))
        );
        usernameField.clear();
        usernameField.sendKeys(username);

        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PASSWORD))
        );
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(LOGIN_BTN))
        );
        loginButton.click();

        WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ERROR_MESSAGE))
        );
        assertTrue(
                errorMessage.getText().contains(EXPECTED_ERROR),
                "Ожидалась ошибка: '" + EXPECTED_ERROR + "', но получена: '" + errorMessage.getText() + "'"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}