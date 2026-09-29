package internship.steps.saucedemo;

import com.codeborne.selenide.Selenide;
import internship.config.Config;
import internship.pages.saucedemo.selenide.InventoryPage;
import internship.pages.saucedemo.selenide.LoginPage;
import internship.pages.saucedemo.selenide.CartPage;
import internship.pages.saucedemo.selenide.CheckoutCompletePage;
import internship.pages.saucedemo.selenide.CheckoutStepOnePage;
import internship.pages.saucedemo.selenide.CheckoutStepTwoPage;
import io.qameta.allure.Step;

public class SauceDemoSelenideSteps {

    private final LoginPage loginPage = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();
    private final CartPage cartPage = new CartPage();
    private final CheckoutStepOnePage stepOnePage = new CheckoutStepOnePage();
    private final CheckoutStepTwoPage stepTwoPage = new CheckoutStepTwoPage();
    private final CheckoutCompletePage completePage = new CheckoutCompletePage();

    @Step("Открыть главную страницу SauceDemo")
    public SauceDemoSelenideSteps open() {
        Selenide.open(Config.INSTANCE.saucedemoBaseUrl());
        return this;
    }

    @Step("Авторизация как standard_user")
    public SauceDemoSelenideSteps loginAsStandardUser() {
        loginPage
                .typeUserName(Config.INSTANCE.saucedemoUsername())
                .typePassword(Config.INSTANCE.saucedemoPassword())
                .clickLoginButton();
        return this;
    }

    @Step("Добавить два товара в корзину")
    public SauceDemoSelenideSteps addItemsToCart() {
        inventoryPage
                .checkPageHeader()
                .addFirstPurchaseToCart()
                .addSecondPurchaseToCart()
                .openCart();
        return this;
    }

    @Step("Заполнить данные пользователя: {firstName} {lastName}, индекс: {postalCode}")
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

    @Step("Получить заголовок страницы завершения заказа")
    public String getCompletePurchasePageTitleText() {
        return completePage.getTitleLabelText();
    }

    @Step("Получить заголовок инвентаря")
    public String getInventoryPageHeaderText() {
        return inventoryPage.getHeaderText();
    }
}
