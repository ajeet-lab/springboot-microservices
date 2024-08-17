package com.learn.junit.service;

import org.junit.*;

public class CalculatorServiceTests {

    public int counter=0;

    // Invoke once before the class
    @BeforeClass
    public static void init(){
        System.out.println("Initiate before calling class");
    }

    // Invoke before each of method
    @Before
    public void beforeEachClass(){
        System.out.println("initiate before each class...");
    }

    // Invoke after each of method
    @After
    public void afterEachClass(){
        System.out.println("After each method...");
        counter=0;
    }

    @Test
    public void addTwoNumbersTest(){
        for(int i=0; i<20; i++){
            counter+=i;
        }
       int result =  CalculatorService.addTwoNumbers(12, 45);
       int excepted=57;
        System.out.println("Total int sum of first case : "+counter);
        Assert.assertEquals(excepted, result);
    }

    @Test
    public void divideTwoNumbersTest(){
        for(int i=0; i<5; i++){
            counter+=i;
        }
        int result =  CalculatorService.divideTwoNumbers(15, 3);
        int excepted=5;
        System.out.println("Total int sum of second case : "+counter);
        Assert.assertEquals(excepted, result);
    }

    @Test
    public void sumAnyNumbersTest(){
        for(int i=0; i<3; i++){
            counter+=i;
        }
        int result =  CalculatorService.sumAnyOfNumbers(new int[] {12, 45, 3});
        int excepted=60;
        System.out.println("Total int sum of third case : "+counter);
        Assert.assertEquals(excepted, result);
    }


    // Invoke once after the class
    @AfterClass
    public static void cleanUp(){
        System.out.println("Cleanup after classes..");
    }

}
