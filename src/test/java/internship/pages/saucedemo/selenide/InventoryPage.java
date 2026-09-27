package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.constants.Constants.HEADER_LABEL_TEXT;
import static internship.selectors.SauceDemoSelectors.*;

public class InventoryPage {

    private final SelenideElement headerLabel = $(Selectors.byCssSelector(HEADER_LABEL));

    private final SelenideElement addToCartBtn1 = $(Selectors.byId(ADD_TO_CART_BTN1));
    private final SelenideElement addToCartBtn2 = $(Selectors.byId(ADD_TO_CART_BTN2));
    private final SelenideElement shoppingCartLink = $(Selectors.byAttribute(
            "data-test",
            SHOPPING_CART_LINK));

    public InventoryPage addFirstPurchaseToCart() {
        addToCartBtn1.click();
        return this;
    }

    public InventoryPage addSecondPurchaseToCart() {
        addToCartBtn2.click();
        return this;
    }

    public void openCart() {
        shoppingCartLink.click();
    }

    public InventoryPage checkPageHeader() {
        String pageHeader = headerLabel.getText();
        if (!pageHeader.equals(HEADER_LABEL_TEXT)) {
            throw new AssertionError(
                    "Ожидался заголовок: " + HEADER_LABEL_TEXT + ", но получен: " + pageHeader);
        }
        return this;
    }

    public String getHeaderText() {
        return headerLabel.getText();
    }
}
