package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutStepTwoPage {

    private final SelenideElement finishBtn = $(Selectors.byId(FINISH_BTN));

    public void clickFinishBtn() {
        finishBtn.click();
    }
}
