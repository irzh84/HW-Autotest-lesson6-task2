package ru.netology.bdd.steps;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.DashboardPage;
import ru.netology.bdd.page.LoginPage;
import ru.netology.bdd.page.TransferPage;
import ru.netology.bdd.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.bdd.data.DataHelper.*;
import static ru.netology.bdd.data.DataHelper.getSecondCardInfo;

public class MyStepdefs {
    private static LoginPage loginPage;

    private static VerificationPage verificationPage;
    private static DashboardPage dashboardPage;
    private static TransferPage transferPage;
    DataHelper.CardInfo firstCardInfo;
    DataHelper.CardInfo secondCardInfo;
    int firstCardBalance;
    int secondCardBalance;

    @Пусть("пользователь залогинен с именем «vasya» и паролем «qwerty123»")
    public void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
        firstCardInfo = getFirstCardInfo();
        secondCardInfo = getSecondCardInfo();
        firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою первую карту с главной страницы")
    public void shouldTransfer5000FromSecondToFirst(String amount, String cardSecond) {
        var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
        dashboardPage = transferPage.makeValidTransfer(amount, secondCardInfo);
    }

    @Тогда("баланс его 1 карты из списка на главной странице должен стать 15 000 рублей")
    public void verifyBalanceFirstCard() {
        var amount = 5000;
        var expectedBalanceFirstCard = firstCardBalance + amount;
        var expectedBalanceSecondCard = secondCardBalance - amount;
        var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardInfo);

        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }


}
