package com.tungns.pkkq;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class Utils {
  public static LocalDate convertStringToDate (String input) {
    LocalDate date = null;
    try {
      DateTimeFormatter f = DateTimeFormatter.ofPattern("d/M/uuuu").withResolverStyle(ResolverStyle.STRICT);
      date = LocalDate.parse(input, f);
    } catch (Exception ex) {
      System.out.println("Wrong formatted date");
    }
    return date;
  }
}