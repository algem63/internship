package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class CartPage extends BasePage {

    private final By checkoutBtn = By.id(CHECKOUT_BTN);

    public CartPage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public void clickCheckoutBtn() {
        waitUntilClickable(checkoutBtn).click();
    }
}