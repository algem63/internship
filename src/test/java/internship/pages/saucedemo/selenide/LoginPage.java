package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.selectors.SauceDemoSelectors.*;

public class LoginPage {

    private final SelenideElement loginField = $(Selectors.byId(LOGIN_FIELD));
    private final SelenideElement passwordField = $(Selectors.byId(PASSWORD_FIELD));
    private final SelenideElement loginButton = $(Selectors.byId(LOGIN_BUTTON));

    public LoginPage typeUserName(String userName) {
        loginField.sendKeys(userName);
        return this;
    }

    public LoginPage typePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
