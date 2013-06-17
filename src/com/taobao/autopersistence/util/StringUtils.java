package com.taobao.autopersistence.util;

/**
 * 
 * 类StringUtils.java的实现描述：字符串工具类
 * @author zhanzui.ldh 2013-6-4 上午10:00:08
 */
public class StringUtils {
  /**
   * 去掉字符串两边的空白字符
   * @param text
   * @return
   */
  public static String trim(String text){
      if(text == null){
          return null;
      }
      return text.trim();
  }
  
  /**
   * 字符串是否为空判断
   * @param text
   * @return
   */
  public static boolean isBlank(String text){
      if(text == null || "".equals(text.trim())){
          return true;
      }
      return false;
  }
  
  /**
   * 大写转小写
   * @param projectName
   * @return
   */
  public static String upperToLower(String projectName){
      StringBuilder sb = new  StringBuilder("");
      for(int i = 0;i<projectName.length();i++){
          sb.append(projectName.substring(i,i+1).toLowerCase());
      }
      return sb.toString();
  }
}
