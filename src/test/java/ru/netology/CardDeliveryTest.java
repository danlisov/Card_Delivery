package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    private String generateDate(int daysAdd, String pattern) {
        return LocalDate.now().plusDays(daysAdd).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldOrderCard() {
        open("http://localhost:9999");
        String planningDate = generateDate(5, "dd.MM.yyyy");

        $("[data-test-id='city'] input").setValue("Калуга");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Лысов Денис");
        $("[data-test-id='phone'] input").setValue("+79157140687");
        $("[data-test-id='agreement']").click();
        $(withText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate),Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
