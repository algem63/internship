package internship.steps.herokuapp;

import com.codeborne.selenide.Selenide;
import internship.pages.herokuapp.selenide.DynamicLoadingPage;

public class HerokuAppSelenideSteps {

    private final DynamicLoadingPage dynamicLoadingPage = new DynamicLoadingPage();


    public HerokuAppSelenideSteps open(String url) {
        Selenide.open(url);
        return this;
    }

    public String clickStartAndCheckDivText() {
        dynamicLoadingPage.clickStartButton();
        return dynamicLoadingPage.getHelloWorldDivText();
    }
}
