package com.Backend;

import com.Backend.BaseTest;
import org.testng.annotations.Test;

public class GroupTest extends BaseTest {
    @Test(groups = {"smoke"})
    public void smokeTest1() {
        System.out.println("Smoke Test 1");
    }

    @Test(groups = {"smoke"})
    public void smokeTest2() {
        System.out.println("Smoke Test 2");
    }

    @Test(groups = {"regression"})
    public void regressionTest1() {
        System.out.println("Regression Test 1");
    }

    @Test(groups = {"regression", "integration"})
    public void integrationTest1() {
        System.out.println("Integration Test 1");
    }

    @Test(groups = {"performance"})
    public void performanceTest1() {
        System.out.println("Performance Test 1");
    }
}
