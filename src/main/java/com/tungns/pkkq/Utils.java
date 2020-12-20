package com.tungns.pkkq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  public static LocalDate convertStringToDate (String input) {
    LocalDate date = null;
    try {
      DateTimeFormatter f = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
      date = LocalDate.parse(input, f);
    } catch (Exception ex) {
      System.out.println("Invalid date");
    }
    return date;
  }
  
  public static int convertStringToNumber(String input) {
    int output = -1;
    try {
      output = Integer.parseInt(input);
    } catch (Exception ex) {
      System.out.println("Invalid number");
    }
    return output;
  }
  
  public static boolean validateInputString(String input) {
    if (null == input || input.length() == 0) {
      System.out.println("Invalid String");
      return false;
    }
    return true;
  }
}