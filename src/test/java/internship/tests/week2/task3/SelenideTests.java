package internship.tests.week2.task3;

import com.codeborne.selenide.*;
import internship.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.codeborne.selenide.Selenide.$$x;

public class SelenideTests {

    @BeforeAll
    public static void setUp() {
        Configuration.pageLoadTimeout = 10000;
    }

    @ParameterizedTest(name = "Поиск элементов в таблице с помощью Selenide (Браузер: {0})")
    @MethodSource("internship.utils.TestDataProvider#provideBrowsersAndSelectors")
    void performTest(String browser, String locator) {
        Configuration.browser = browser;

        Selenide.open(Config.INSTANCE.herokuappTablesUrl());
        $$x(locator)
                .shouldHave(CollectionCondition.sizeGreaterThan(0))
                .forEach(element -> element.shouldBe(Condition.clickable));
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
