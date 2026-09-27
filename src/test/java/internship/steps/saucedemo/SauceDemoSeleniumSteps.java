package internship.steps.saucedemo;

import internship.config.Config;
import internship.pages.saucedemo.selenium.InventoryPage;
import internship.pages.saucedemo.selenium.LoginPage;
import internship.pages.saucedemo.selenium.CartPage;
import internship.pages.saucedemo.selenium.CheckoutCompletePage;
import internship.pages.saucedemo.selenium.CheckoutStepOnePage;
import internship.pages.saucedemo.selenium.CheckoutStepTwoPage;
import internship.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoSeleniumSteps {

    private final WebDriver driver;

    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;
    private final CartPage cartPage;
    private final CheckoutStepOnePage stepOnePage;
    private final CheckoutStepTwoPage stepTwoPage;
    private final CheckoutCompletePage completePage;

    public SauceDemoSeleniumSteps(String browser) {
        driver = WebDriverFactory.createDriver(browser);
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        loginPage = new LoginPage(webDriverWait);
        inventoryPage = new InventoryPage(webDriverWait);
        cartPage = new CartPage(webDriverWait);
        stepOnePage = new CheckoutStepOnePage(webDriverWait);
        stepTwoPage = new CheckoutStepTwoPage(webDriverWait);
        completePage = new CheckoutCompletePage(webDriverWait);
    }

    public SauceDemoSeleniumSteps open() {
        driver.get(Config.INSTANCE.saucedemoBaseUrl());
        return this;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    public SauceDemoSeleniumSteps loginAsStandardUser() {
        loginPage
                .typeUserName(Config.INSTANCE.saucedemoUsername())
                .typePassword(Config.INSTANCE.saucedemoPassword())
                .clickLoginButton();
        return this;
    }

    public SauceDemoSeleniumSteps addItemsToCart(){
        inventoryPage
                .addFirstPurchaseToCart()
                .addSecondPurchaseToCart()
                .openCart();
        return this;
    }

    public SauceDemoSeleniumSteps fillInUserData(String firstName, String lastName, String postalCode) {
        cartPage.clickCheckoutBtn();
        stepOnePage
                .typeFirstName(firstName)
                .typeLastName(lastName)
                .typePostalCode(postalCode)
                .clickContinueBtn();
        stepTwoPage.clickFinishBtn();
        return this;
    }

    public String getCompletePurchasePageTitleText() {
        return completePage.getTitleLabelText();
    }

    public String getInventoryPageHeaderText() {
        return inventoryPage.getHeaderText();
    }
}
