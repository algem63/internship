package internship.cucumber.stepdefinitions;

import internship.api.AuthApiClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AuthStepDefinitions {

    private final AuthApiClient authApiClient = new AuthApiClient();
    private String username;
    private String password;

    @Given("Используем валидные логин и пароль")
    public void usingValidCredentials() {
        username = "admin";
        password = "password123";
    }

    @Given("Используем НЕвалидные логин и пароль")
    public void usingInvalidCredentials() {
        username = "admin";
        password = "wrongPassword";
    }

    @When("Отправляем запрос на аутентификацию")
    public void iSendAnAuthenticationRequest() {
        authApiClient.authenticate(username, password);
    }

    @Then("Статус-код ответа должен быть {int}")
    public void responseStatusCodeShouldBe(int expectedStatusCode) {
        authApiClient.verifyStatusCode(expectedStatusCode);
    }

    @Then("Ответ должен содержать токен")
    public void responseShouldContainAToken() {
        authApiClient.verifyTokenPresent();
    }

    @Then("Ответ должен содержать сообщение об ошибке")
    public void responseShouldContainAnErrorMessage() {
        authApiClient.verifyErrorMessage("Bad credentials");
    }
}