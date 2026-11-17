package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {
    protected DashboardPage dashboard;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Selenide.clearBrowserCookies();
        Selenide.localStorage().clear();
        Selenide.refresh();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(DataHelper.getAuthInfo());
        dashboard = verificationPage.validVerify(DataHelper.getVerificationCode());
    }
}
