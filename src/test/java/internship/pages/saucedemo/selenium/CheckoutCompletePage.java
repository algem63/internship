package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutCompletePage extends BasePage {

    private final By titleLabel = By.cssSelector("[data-test='" + TITLE_LABEL + "']");

    public CheckoutCompletePage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public String getTitleLabelText() {
        return waitUntilVisible(titleLabel).getText();
    }
}