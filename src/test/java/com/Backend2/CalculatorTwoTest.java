package com.Backend2;

import io.qameta.allure.*;
import io.qameta.allure.Step;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;


@Epic("Калькулятор")
@Feature("Базовые операции")
@Listeners(com.Backend2.AllureListener.class)
public class CalculatorTwoTest {

    private int add(int a, int b) {
        return a + b;
    }

    @Test(description = "Проверка сложения чисел")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Пользователь может сложить два числа")
    @Description("Тест проверяет корректность работы операции сложения")
    public void testAddition() {
        int result = stepAddNumbers(2, 3);
        stepVerifyResult(result, 5);
        attachTestData("Результат вычисления: " + result);
    }

    @Step("Шаг 1. Сложение чисел {a} и {b}")
    private int stepAddNumbers(int a, int b) {
        return add(a, b);
    }

    @Step("Шаг 2. Проверка результата (ожидаем: {expected})")
    private void stepVerifyResult(int actual, int expected) {
        assertEquals(actual, expected, "Неверный результат сложения");
    }

    @Attachment(value = "Тестовые данные", type = "text/plain")
    private String attachTestData(String data) {
        return data;
    }
}