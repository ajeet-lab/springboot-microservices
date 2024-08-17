package com.learn.junit5.service;

public class CalculatorService {

    public static int addToNumbers(int a, int b){
        return a+b;
    }

    public static double productToNumbers(int a, int b){
        return a*b;
    }

    public static int sumAnyNumbers(int ...numbers){
        int sum=0;
        for(int n:numbers){
            sum+=n;
        }
        return sum;
    }


}
