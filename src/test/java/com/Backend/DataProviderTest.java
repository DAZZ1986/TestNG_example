package com.Backend;

import org.testng.annotations.DataProvider;

public class DataProviderTest {
    @DataProvider(name = "numberData")
    public static Object[][] numberDataProvider() {
        return new Object[][] {
                {2, true},
                {3, false},
                {0, true},
                {-4, true},
                {-5, false}
        };
    }

    @DataProvider(name = "arithmeticData")
    public static Object[][] arithmeticDataProvider() {
        return new Object[][] {
                {1, 1, 2, 0, 1, 1.0},
                {4, 2, 6, 2, 8, 10.0},
                {10, 5, 15, 5, 50, 55.0}
        };
    }
}
