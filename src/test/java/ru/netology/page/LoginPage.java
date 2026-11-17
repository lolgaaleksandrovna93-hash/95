package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.ex.ElementNotFound;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.shouldBe(visible).setValue(info.getLogin());
        passwordField.shouldBe(visible).setValue(info.getPassword());

        SelenideElement codeField = $("[data-test-id='code'] input");
        for (int attempt = 0; attempt < 5; attempt++) {
            loginButton.shouldBe(enabled).click();
            try {
                codeField.shouldBe(visible, Duration.ofSeconds(15));
                return new VerificationPage();
            } catch (ElementNotFound ignored) {
            }
        }
        errorNotification.shouldBe(visible, Duration.ofSeconds(10));
        throw new AssertionError("Не удалось перейти к вводу кода. Текущий URL: "
                + WebDriverRunner.getWebDriver().getCurrentUrl());
    }

    public void checkError(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(text(expectedText));
    }
}
