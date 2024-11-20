package com.bci.integration.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Generic {

  static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy-MM-dd HH:mm:ss");
  private static final String PASSWORD_PATTERN =
      "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";

  private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

  public static boolean isValid(final String password) {
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }

  public static LocalDateTime convertStringToDate(String dateString) {
    return LocalDateTime.parse(dateString, CUSTOM_FORMATTER);
  }

  public static String convertDateToString(LocalDateTime dateString) {
    return dateString.format(CUSTOM_FORMATTER);
  }

  public static String getLocalTime() {
    LocalDateTime ldt = LocalDateTime.now();
    return ldt.format(CUSTOM_FORMATTER);
  }
}
