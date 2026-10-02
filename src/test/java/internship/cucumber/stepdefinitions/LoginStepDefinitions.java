package internship.cucumber.stepdefinitions;

import internship.steps.herokuapp.LoginSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinitions {

    private final LoginSteps loginSteps = new LoginSteps();

    @Given("Открываем страницу аутентификации")
    public void iOpenTheLoginPage() {
        loginSteps.openLoginPage();
    }

    @When("Вводим имя пользователя {string}")
    public void iEnterUsername(String username) {
        loginSteps.enterUsername(username);
    }

    @And("Вводим верный пароль")
    public void iEnterValidPassword() {
        loginSteps.enterPassword("SuperSecretPassword!");
    }

    @And("Нажимаем кнопку Login")
    public void iClickTheLoginButton() {
        loginSteps.clickLogin();
    }

    @Then("Должно появиться сообщение об ошибке {string}")
    public void iShouldSeeTheErrorMessage(String expectedMessage) {
        loginSteps.verifyErrorMessage(expectedMessage);  // ← Всё!
    }
}