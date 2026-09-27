package internship.pages.herokuapp.selenide;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static internship.conditions.CustomConditions.isDarkGreyColor;
import static internship.selectors.HerokuAppSelectors.*;

public class DynamicLoadingPage {

    private final SelenideElement startButton = $(START_BTN);
    private final SelenideElement helloWorldDiv = $(HELLO_WORLD_DIV);

    public void clickStartButton() {
        startButton.click();
    }

    public String getHelloWorldDivText() {
        return helloWorldDiv.shouldBe(isDarkGreyColor, Duration.ofSeconds(10)).text();
    }
}
