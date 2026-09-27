package internship.selectors;

public interface SauceDemoSelectors {

    // === Login Page ===
    String LOGIN_FIELD = "user-name";
    String PASSWORD_FIELD = "password";
    String LOGIN_BUTTON = "login-button";

    // === Inventory Page ===
    String HEADER_LABEL = "[data-test='primary-header'] .app_logo";
    String ADD_TO_CART_BTN1 = "add-to-cart-sauce-labs-backpack";
    String ADD_TO_CART_BTN2 = "add-to-cart-sauce-labs-bike-light";
    String SHOPPING_CART_LINK = "shopping-cart-link";

    // === Cart Page ===
    String CHECKOUT_BTN = "checkout";

    // === Checkout Step One ===
    String FIRST_NAME_FIELD = "first-name";
    String LAST_NAME_FIELD = "last-name";
    String POSTAL_CODE_FIELD = "postal-code";
    String CONTINUE_BTN = "continue";

    // === Checkout Step Two ===
    String FINISH_BTN = "finish";

    // === Checkout Complete ===
    String TITLE_LABEL = "title";
}