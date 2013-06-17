package com.taobao.autopersistence.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
  public static Long getLongTime(String sDate){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try {
       return sdf.parse(sDate).getTime();
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      return 0L;
  }
  
  public static String getDate(Long time){
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(time);
      Date date = cal.getTime();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
      try {
        return sdf.format(date);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
      return null;
  }
  public static void main(String[] args) {
    System.out.println(getLongTime("2012-08-12 12:12:15"));
    System.out.println(getLongTime("2012-08-12 12:18:15"));
    System.out.println(getDate(1344745095000L));
  }
}
