package internship.selectors;

public interface HerokuAppSelectors {

    // === Login Page ===
    String LOGIN_BTN = "#login button";
    String USERNAME = "#username";
    String PASSWORD = "#password";
    String ERROR_MESSAGE = "#flash";

    // === Dynamic Loading Page ===
    String START_BTN = "#start button";
    String HELLO_WORLD_DIV = "#finish h4";

    // === Tables Page ===
    String TEST_1 = """
            //table[@id='table1']
            //tr[td[normalize-space()='Smith'][count(preceding-sibling::td) = 0]]
            /td[
                count(preceding-sibling::td) =
                count(//table[@id='table1']//th[normalize-space()='Email']/preceding-sibling::th)
              ]
              [contains(text(), '@') and contains(text(), '.')]
            """;

    String TEST_2 = """
            //table[@id='table1']
            //td[normalize-space()='$100.00']
            [count(preceding-sibling::td) = count(//table[@id='table1']//th[normalize-space()='Due']
            /preceding-sibling::th)]/ancestor::tr//a[normalize-space()='edit']
            """;

    String TEST_3 = """
            //table[@id='table1']
            //td[count(preceding-sibling::td) = count(//table[@id='table1']
            //th[normalize-space()='Web Site']/preceding-sibling::th)]
            [starts-with(normalize-space(text()), 'http://')]
            """;
}