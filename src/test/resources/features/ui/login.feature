Feature: Проверка аутентификации

  Scenario Outline: Аутентификация с неверным именем пользователя
    Given Открываем страницу аутентификации
    When Вводим имя пользователя "<username>"
    And Вводим верный пароль
    And Нажимаем кнопку Login
    Then Должно появиться сообщение об ошибке "Your username is invalid!"

    Examples:
      | username        |
      |                 |
      | a               |
      | @#$%            |
      | ' OR 1=1 --     |
