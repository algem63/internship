package internship.pages.saucedemo.selenide;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static internship.selectors.SauceDemoSelectors.*;

public class CheckoutCompletePage {

    private final SelenideElement titleLabel = $(Selectors.byAttribute("data-test", TITLE_LABEL));

    public String getTitleLabelText() {
        return titleLabel.getText();
    }
}
