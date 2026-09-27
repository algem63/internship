package internship.steps.saucedemo;

import com.codeborne.selenide.Selenide;
import internship.config.Config;
import internship.pages.saucedemo.selenide.InventoryPage;
import internship.pages.saucedemo.selenide.LoginPage;
import internship.pages.saucedemo.selenide.CartPage;
import internship.pages.saucedemo.selenide.CheckoutCompletePage;
import internship.pages.saucedemo.selenide.CheckoutStepOnePage;
import internship.pages.saucedemo.selenide.CheckoutStepTwoPage;

public class SauceDemoSelenideSteps {

    private final LoginPage loginPage = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage();
    private final CheckoutStepTwoPage stepTwoPage = new CheckoutStepTwoPage();
    private final CheckoutCompletePage completePage = new CheckoutCompletePage();

    public SauceDemoSelenideSteps open() {
        Selenide.open(Config.INSTANCE.saucedemoBaseUrl());
        return this;
    }

    public SauceDemoSelenideSteps loginAsStandardUser() {
        loginPage
                .typeUserName(Config.INSTANCE.saucedemoUsername())
                .typePassword(Config.INSTANCE.saucedemoPassword())
                .clickLoginButton();
        return this;
    }

    public SauceDemoSelenideSteps addItemsToCart() {
        inventoryPage
                .checkPageHeader()
                .addFirstPurchaseToCart()
                .addSecondPurchaseToCart()
                .openCart();
        return this;
    }

    public SauceDemoSelenideSteps fillInUserData(String firstName, String lastName, String postalCode) {
        cartPage
                .clickCheckoutBtn();
        stepOnePage
                .typeFirstName(firstName)
                .typeLastName(lastName)
                .typePostalCode(postalCode)
                .clickContinueBtn();
        stepTwoPage
                .clickFinishBtn();
        return this;
    }

    public String getCompletePurchasePageTitleText() {
        return completePage.getTitleLabelText();
    }

    public String getInventoryPageHeaderText() {
        return inventoryPage.getHeaderText();
    }
}
