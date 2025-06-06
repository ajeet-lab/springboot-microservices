package com.crick.apis.helper;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeConverter {

   // public String add530Hours(String inputTime){
   public static void main(String[] args) {

       String timeString = "08:00 AM";
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a"); // Define the pattern

       // Parse the string to LocalTime
       LocalTime time = LocalTime.parse(timeString, formatter);

       System.out.println("Parsed time: " + time);

        //return outputTime;
    }
}
