package com.Backend;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - выполняется один раз перед всеми тестами в наборе");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - выполняется перед тестами из <test> в testng.xml");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - выполняется перед первым тестом в классе");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - выполняется перед каждым тестовым методом");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - выполняется после каждого тестового метода");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class - выполняется после всех тестов в классе");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - выполняется после тестов из <test> в testng.xml");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - выполняется один раз после всех тестов в наборе");
    }
}
