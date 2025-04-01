package com.Backend;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.*;


@Listeners(TestListener.class)
public class CalculatorTest extends BaseTest {
    private Calculator calculator;


    //TestNG tests annotation, reports: surefire
    @BeforeClass(groups = {"smoke"})
    public void setup() {
        calculator = new Calculator();
        System.out.println("Calculator initialized");
    }

    @Test(groups = {"arithmetic", "smoke"})
    public void testAddition() {
        System.out.println("Running addition test");
        int result = calculator.add(2, 3);
        Assert.assertEquals(result, 5, "Addition result is incorrect");
    }

    @Test(groups = {"arithmetic", "smoke"}, dependsOnMethods = "testAddition")
    public void testSubtraction() {
        System.out.println("Running subtraction test");
        int result = calculator.subtract(5, 3);
        Assert.assertEquals(result, 3, "Subtraction result is incorrect");
    }

    @Test(groups = {"arithmetic", "regression"}, dependsOnMethods = "testSubtraction")
    public void testMultiplication() {
        System.out.println("Running multiplication test");
        int result = calculator.multiply(4, 3);
        Assert.assertEquals(result, 12, "Multiplication result is incorrect");
    }

    @Test(groups = {"arithmetic", "regression"},
            dependsOnMethods = "testMultiplication",
            expectedExceptions = IllegalArgumentException.class)
    public void testDivisionByZero() {
        System.out.println("Running division by zero test");
        calculator.divide(10, 0);
    }

    @Test(groups = {"logical", "integration"},
            dataProvider = "numberData",
            dataProviderClass = DataProviderTest.class,
            enabled = true)
    public void testIsEven(int number, boolean expected) {
        System.out.println("Testing if " + number + " is even");
        Assert.assertEquals(calculator.isEven(number), expected);
    }

    @Test(groups = {"integration"}, dataProvider = "arithmeticData", dataProviderClass = DataProviderTest.class)
    public void myTestAddition(int a, int b, int sum, int aa, int bb, double sumSum) {
        int result = calculator.add(a, b);
        int result2 = calculator.add(aa, bb);
        Assert.assertEquals(result, sum);
        Assert.assertEquals(result2, sumSum);
    }


    //TestNG tests annotation, reports: Allure
    @Test(groups = {"smoke"})
    @Description("Тест для проверки функционала X")
    public void testFunctionalityX_With_Allure_report() {
        stepOne();
        stepTwo();
    }

    @Step("Шаг 1: Выполнение первого шага")
    public void stepOne() {
        // Логика теста
        System.out.println("Allure step 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Step("Шаг 2: Выполнение второго шага")
    public void stepTwo() {
        // Логика теста
        System.out.println("Allure step 2 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }


    @AfterClass
    public void tearDown() {
        System.out.println("Cleaning up after Calculator tests");
    }
}
