package com.bci.integration.util;
import java.util.regex.Pattern;
import lombok.Getter;

public class PasswordValidator {

  private final Pattern passwordPattern;
  @Getter
  private final String errorMessage;

  public PasswordValidator( String passwordRegex, String errorMessage) {
    this.passwordPattern = Pattern.compile(passwordRegex);
    this.errorMessage = errorMessage;
  }

  public boolean isValid(String password) {
    return passwordPattern.matcher(password).matches();
  }

}
