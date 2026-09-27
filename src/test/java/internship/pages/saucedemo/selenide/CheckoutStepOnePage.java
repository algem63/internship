package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutStepOnePage {

    private final SelenideElement firstNameField = $(Selectors.byId(FIRST_NAME_FIELD));
    private final SelenideElement lastNameField = $(Selectors.byId(LAST_NAME_FIELD));
    private final SelenideElement postalCodeField = $(Selectors.byId(POSTAL_CODE_FIELD));
    private final SelenideElement continueBtn = $(Selectors.byId(CONTINUE_BTN));

    public CheckoutStepOnePage typeFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
        return this;
    }

    public CheckoutStepOnePage typeLastName(String lastName) {
        lastNameField.sendKeys(lastName);
        return this;
    }

    public CheckoutStepOnePage typePostalCode(String postalCode) {
        postalCodeField.sendKeys(postalCode);
        return this;
    }

    public void clickContinueBtn() {
        continueBtn.click();
    }
}
