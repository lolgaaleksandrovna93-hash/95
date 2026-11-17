package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTest extends BaseTest {

    @Test
    void shouldTransferFromFirstToSecond() {
        int expectedFirst = DataHelper.firstCard().getBalance() - 5000;
        int expectedSecond = DataHelper.secondCard().getBalance() + 5000;

        var transferPage = dashboard.goToReplenish(DataHelper.secondCard().getNumber());
        transferPage.makeTransfer("5000", DataHelper.firstCard().getNumber());

        assertEquals(expectedFirst, dashboard.getBalance(DataHelper.firstCard().getNumber()));
        assertEquals(expectedSecond, dashboard.getBalance(DataHelper.secondCard().getNumber()));
    }

    @Test
    void shouldTransferFromSecondToFirst() {
        int expectedSecond = DataHelper.secondCard().getBalance() - 5000;
        int expectedFirst = DataHelper.firstCard().getBalance() + 5000;

        var transferPage = dashboard.goToReplenish(DataHelper.firstCard().getNumber());
        transferPage.makeTransfer("5000", DataHelper.secondCard().getNumber());

        assertEquals(expectedSecond, dashboard.getBalance(DataHelper.secondCard().getNumber()));
        assertEquals(expectedFirst, dashboard.getBalance(DataHelper.firstCard().getNumber()));
    }

    @Test
    void shouldNotTransferMoreThanBalance() {
        var transferPage = dashboard.goToReplenish(DataHelper.firstCard().getNumber());
        transferPage.makeTransfer("20000", DataHelper.secondCard().getNumber());
        transferPage.checkError("Ошибка! Произошла ошибка");
    }
}
