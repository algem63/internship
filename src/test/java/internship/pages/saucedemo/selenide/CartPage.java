package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.selectors.SauceDemoSelectors.*;

public class CartPage {

    private final SelenideElement checkoutBtn = $(Selectors.byId(CHECKOUT_BTN));

    public void clickCheckoutBtn() {
        checkoutBtn.click();
    }
}
