package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutStepTwoPage extends BasePage {

    private final By finishBtn = By.id(FINISH_BTN);

    public CheckoutStepTwoPage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public void clickFinishBtn() {
        waitUntilClickable(finishBtn).click();
    }
}