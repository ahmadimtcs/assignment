package com.assignment.openweather.utils;

import java.time.LocalDate;
import java.util.Base64;
import org.springframework.util.StringUtils;

public class Md5Util {

  public static String generateKey(Object... params) {
    if (params.length == 0) {
      return "";
    }
    String paramString = StringUtils.arrayToDelimitedString(params, "#");
    String key = LocalDate.now().toString() + "#" + paramString;
    return Base64.getEncoder().encodeToString(key.getBytes());
  }

}
