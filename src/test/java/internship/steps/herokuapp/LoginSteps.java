package internship.steps.herokuapp;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import internship.config.Config;
import internship.pages.herokuapp.selenide.LoginPage;
import io.qameta.allure.Step;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();

    @Step("Открыть страницу логина")
    public LoginSteps openLoginPage() {
        Configuration.pageLoadTimeout = 60000;
        Selenide.open(Config.INSTANCE.herokuappLoginUrl());
        return this;
    }

    @Step("Ввести логин: {username}")
    public LoginSteps enterUsername(String username) {
        loginPage.enterUsername(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginSteps enterPassword(String password) {
        loginPage.enterPassword(password);
        return this;
    }

    @Step("Нажать Login")
    public LoginSteps clickLogin() {
        loginPage.clickLogin();
        return this;
    }

    @Step("Проверить сообщение об ошибке: {expectedMessage}")
    public LoginSteps verifyErrorMessage(String expectedMessage) {
        String actual = loginPage.getErrorMessage();
        if (!actual.contains(expectedMessage)) {
            throw new AssertionError(
                    "Ожидалось: " + expectedMessage + ", но получено: " + actual
            );
        }
        return this;
    }
}