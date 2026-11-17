package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private static final Pattern BALANCE_PATTERN = Pattern.compile("-?\\d+");

    public DashboardPage() {
        $("[data-test-id='dashboard']").shouldBe(visible, Duration.ofSeconds(15));
    }

    private SelenideElement findCard(String cardNumber) {
        var last4 = cardNumber.substring(cardNumber.length() - 4);
        return $$(".list__item").findBy(text(last4));
    }

    public int getBalance(String cardNumber) {
        var itemText = findCard(cardNumber).getText().toLowerCase();
        var start = itemText.indexOf("баланс:");
        if (start == -1) {
            throw new IllegalStateException(
                    "В тексте карточки нет «баланс:». Реальный текст: " + itemText);
        }
        var tail = itemText.substring(start + "баланс:".length())
                .replace('\u00A0', ' ')
                .replace(" ", "");
        Matcher matcher = BALANCE_PATTERN.matcher(tail);
        if (!matcher.find()) {
            throw new IllegalStateException(
                    "Не удалось извлечь баланс. Реальный текст: " + itemText);
        }
        return Integer.parseInt(matcher.group());
    }

    public TransferPage goToReplenish(String cardNumber) {
        SelenideElement button = findCard(cardNumber).$(byText("Пополнить"));
        SelenideElement amountField = $("[data-test-id='amount'] input");

        for (int attempt = 0; attempt < 3; attempt++) {
            button.shouldBe(visible, enabled).click();
            try {
                amountField.shouldBe(visible, Duration.ofSeconds(10));
                return new TransferPage();
            } catch (ElementNotFound ignored) {
            }
        }
        throw new AssertionError("Не удалось открыть форму перевода после трёх кликов по «Пополнить»");
    }
}

