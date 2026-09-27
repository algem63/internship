package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutStepOnePage extends BasePage {

    private final By firstNameField = By.id(FIRST_NAME_FIELD);
    private final By lastNameField = By.id(LAST_NAME_FIELD);
    private final By postalCodeField = By.id(POSTAL_CODE_FIELD);
    private final By continueBtn = By.id(CONTINUE_BTN);

    public CheckoutStepOnePage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public CheckoutStepOnePage typeFirstName(String firstName) {
        waitUntilVisible(firstNameField).sendKeys(firstName);
        return this;
    }

    public CheckoutStepOnePage typeLastName(String lastName) {
        waitUntilVisible(lastNameField).sendKeys(lastName);
        return this;
    }

    public CheckoutStepOnePage typePostalCode(String postalCode) {
        waitUntilVisible(postalCodeField).sendKeys(postalCode);
        return this;
    }

    public void clickContinueBtn() {
        waitUntilClickable(continueBtn).click();
    }
}