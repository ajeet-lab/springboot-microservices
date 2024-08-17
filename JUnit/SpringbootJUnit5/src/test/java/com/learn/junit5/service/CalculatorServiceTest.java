package com.learn.junit5.service;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class CalculatorServiceTest {

    @BeforeAll
    public static void init(){
        System.out.println("Initiate value before calling methods");
    }

    @AfterAll
    public static void cleanUp(){
        System.out.println("Initiate value after called methods");
    }

    @BeforeEach
    public void executeBeforeEachMethod(){
        System.out.println("Execute this method before each method");
    }

    @AfterEach
    public void executeAfterEachMethod(){
        System.out.println("Execute this method after each method");
    }

    @Test
    public void addTwoNumbers(){
        int results = CalculatorService.addToNumbers(12, 10);
        int expectedResult = 22;
        Assertions.assertEquals(expectedResult, results, "Test fails !!");
    }

    @Test
    public void sumAnyNumbers(){
        double results = CalculatorService.sumAnyNumbers(new int[]{2, 3, 5, 6});
        double expectedResult = 16;
        Assertions.assertEquals(expectedResult, results, "Test fails !!");
    }
}
