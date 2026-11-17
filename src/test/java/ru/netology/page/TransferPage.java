package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement amountField = $("[data-test-id='amount'] input");
    private final SelenideElement fromField = $("[data-test-id='from'] input");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public TransferPage() {
        amountField.shouldBe(visible, Duration.ofSeconds(15));
    }

    public DashboardPage makeTransfer(String amount, String fromCard) {
        amountField.setValue(amount);
        fromField.setValue(fromCard);
        transferButton.click();
        return new DashboardPage();
    }

    public void checkError(String expectedText) {
        errorNotification.shouldBe(visible).shouldHave(text(expectedText));
    }
}
