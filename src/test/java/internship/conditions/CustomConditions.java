package internship.conditions;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.WebElement;

public final class CustomConditions {

    private CustomConditions() {}

    public static final WebElementCondition isDarkGreyColor = new WebElementCondition("dark-grey-color") {

        @Override
        public @NonNull CheckResult check(@NonNull Driver driver, WebElement element) {
            String color = element.getCssValue("color");
            boolean isDarkGrey = color.equals("rgba(34, 34, 34, 1)") || color.equals("rgb(34, 34, 34)");
            return new CheckResult(isDarkGrey, color);
        }
    };
}
