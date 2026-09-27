package internship.pages.saucedemo.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static internship.selectors.SauceDemoSelectors.*;

public class LoginPage extends BasePage {

    private final By loginField = By.id(LOGIN_FIELD);
    private final By passwordField = By.id(PASSWORD_FIELD);
    private final By loginButton = By.id(LOGIN_BUTTON);

    public LoginPage(WebDriverWait webDriverWait) {
        super(webDriverWait);
    }

    public LoginPage typeUserName(String userName) {
        waitUntilVisible(loginField).sendKeys(userName);
        return this;
    }

    public LoginPage typePassword(String password) {
        waitUntilVisible(passwordField).sendKeys(password);
        return this;
    }

    public void clickLoginButton() {
        waitUntilClickable(loginButton).click();
    }
}
