package internship.tests.week2.task3;

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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumTests {

    private WebDriver driver;

    @ParameterizedTest(name = "Поиск элементов в таблице с помощью Selenium (Браузер: {0})")
    @MethodSource("internship.utils.TestDataProvider#provideBrowsersAndSelectors")
    void performTest(String browser, String locator) {
        driver = WebDriverFactory.createDriver(browser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.INSTANCE.herokuappTablesUrl());
        List<WebElement> elements = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator))
        );
        assertFalse(elements.isEmpty(), "Элементы не найдены по селектору: " + locator);
        elements.forEach(element -> {
                assertTrue(element.isDisplayed(), "Элемент не отображается: " + element);
                assertTrue(element.isEnabled(), "Элемент не кликабелен: " + element);
            }
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}