package ru.netology.bdd.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement TransferPageHeading = $(byText("Пополнение карты"));

    private final SelenideElement amountInput = $("[data-test-id='amount'] input");
    private final SelenideElement fromCardInput = $("[data-test-id='from'] input");

    private final SelenideElement buttonTransfer = $("[data-test-id='action-transfer']");
    private final SelenideElement errorMessage = $("[data-test-id='error-notification']");

    public TransferPage() {
        TransferPageHeading.shouldBe(visible);
    }

    public DashboardPage makeValidTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        amountInput.setValue(amountToTransfer);
        fromCardInput.setValue(cardInfo.getCardNumber());
        buttonTransfer.click();
    }

    public void findErrorMessage(String expectedText) {
        errorMessage.shouldBe(text(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
