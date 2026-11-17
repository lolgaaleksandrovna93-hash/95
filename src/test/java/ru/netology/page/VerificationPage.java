package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton = $("[data-test-id='action-verify']");

    public VerificationPage() {
    }

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        codeField.shouldBe(visible).setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}
