package internship.pages.herokuapp.selenide;

import com.codeborne.selenide.SelenideElement;
import internship.selectors.HerokuAppSelectors;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement userNameField = $(HerokuAppSelectors.USERNAME);
    private final SelenideElement passwordField = $(HerokuAppSelectors.PASSWORD);
    private final SelenideElement errorMsgField = $(HerokuAppSelectors.ERROR_MESSAGE);
    private final SelenideElement loginButton = $(HerokuAppSelectors.LOGIN_BTN);

    public LoginPage enterUsername(String username) {
        userNameField.setValue(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return this;
    }

    public String getErrorMessage() {
        return errorMsgField.getText();
    }
}
