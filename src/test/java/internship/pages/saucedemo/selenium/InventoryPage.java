package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class InventoryPage extends BasePage {

    private final By headerLabel = By.cssSelector(HEADER_LABEL);
    private final By addToCartBtn1 = By.id(ADD_TO_CART_BTN1);
    private final By addToCartBtn2 = By.id(ADD_TO_CART_BTN2);
    private final By shoppingCartLink = By.cssSelector("[data-test='" + SHOPPING_CART_LINK + "']");

    public InventoryPage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public InventoryPage addFirstPurchaseToCart() {
        waitUntilClickable(addToCartBtn1).click();
        return this;
    }

    public InventoryPage addSecondPurchaseToCart() {
        waitUntilClickable(addToCartBtn2).click();
        return this;
    }

    public void openCart() {
        waitUntilClickable(shoppingCartLink).click();
    }

    public String getHeaderText() {
        return waitUntilVisible(headerLabel).getText();
    }
}
