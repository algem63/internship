package internship.utils;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static internship.selectors.HerokuAppSelectors.*;

public final class TestDataProvider {

    private TestDataProvider() {}

    public static Stream<Arguments> provideBrowsersAndSelectors() {
        return Stream.of("chrome", "firefox")
                .flatMap(browser -> Stream.of(
                        Arguments.of(browser, TEST_1),
                        Arguments.of(browser, TEST_2),
                        Arguments.of(browser, TEST_3)
                ));
    }

    public static Stream<Arguments> provideBrowsersAndCredentials() {
        return Stream.of("chrome", "firefox")
                .flatMap(browser -> Stream.of(
                        Arguments.of(browser, "", "SuperSecretPassword!"),
                        Arguments.of(browser, "a", "SuperSecretPassword!"),
                        Arguments.of(browser, "!@#$%", "SuperSecretPassword!"),
                        Arguments.of(browser, "' OR 1=1 --", "SuperSecretPassword!")
                ));
    }

    static Stream<Arguments> validCredentialsProvider() {
        return Stream.of(
                Arguments.of("Валидные логин и пароль", "admin", "password123")
        );
    }

    static Stream<Arguments> invalidCredentialsProvider() {
        return Stream.of(
                Arguments.of("Несуществующий логин и валидный пароль", "fake_user", "password123", 401),
                Arguments.of("Пустая строка в логине и валидный пароль", "", "password123", 400),
                Arguments.of("Null в логине и валидный пароль", null, "password123", 400),
                Arguments.of("Валидный username и пустая строка в пароле", "admin", "", 400),
                Arguments.of("Валидный логин и null в пароле", "admin", null, 400),
                Arguments.of("Валидный логин и неверный пароль", "admin", "wrong_password", 401),
                Arguments.of("Пустые строки в логине и пароле", "", "", 400),
                Arguments.of("Null в логине и пароле", null, null, 400),
                Arguments.of("Несуществующие пользователь и пароль", "fake_user", "wrong_password", 401),
                Arguments.of("Невалидный логин (1000 символов) и верный пароль", "a".repeat(1000), "password123", 400),
                Arguments.of("Невалидный логин (1000 символов) и неверный пароль", "a".repeat(1000), "wrong_password", 400),
                Arguments.of("Невалидный логин (1000 символов) и пустая строка в пароле", "a".repeat(1000), "", 400),
                Arguments.of("Невалидный логин (1000 символов) и null в пароле", "a".repeat(1000), null, 400),
                Arguments.of("Валидный логин и невалидный пароль (1000 символов)", "admin", "a".repeat(1000), 400),
                Arguments.of("Невалидный логин (1000) и невалидный пароль (1000)", "a".repeat(1000), "a".repeat(1000), 400),
                Arguments.of("Пустая строка в логине и невалидный пароль (1000)", "", "a".repeat(1000), 400),
                Arguments.of("Отсутствует поле 'username' и невалидный пароль (1000)", null, "a".repeat(1000), 400),
                Arguments.of("Логин в верхнем регистре", "ADMIN", "password123", 401),
                Arguments.of("Пароль в верхнем регистре", "admin", "PASSWORD123", 401),
                Arguments.of("Пробелы в логине", " admin ", "password123", 400),
                Arguments.of("Пробелы в пароле", " admin ", " password123 ", 401),
                Arguments.of("Массив в логине", new String[] { "admin" }, " password123 ", 400),
                Arguments.of("Массив в пароле", "admin", new String[] {"password123"}, 400),
                Arguments.of("SQL-инъекция 1", "' OR '1'='1", "' OR '1'='1", 401),
                Arguments.of("SQL-инъекция 2", "admin'--", "", 401)
        );
    }
}