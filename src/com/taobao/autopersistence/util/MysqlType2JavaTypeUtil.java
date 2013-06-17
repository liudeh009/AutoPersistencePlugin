package com.taobao.autopersistence.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 类DBType2JavaTypeUtil.java的实现描述：mysql数据库类型到java类型的转换
 * @author zhanzui.ldh 2012-9-15 下午4:35:41
 */
public class MysqlType2JavaTypeUtil {
  private static Map<String,String> map = new HashMap<String,String>();
  private static Map<String,String> simpleMap = new HashMap<String,String>();
  static{
      map.put("int", "java.lang.Integer");
      map.put("tinyint", "java.lang.Short");
      map.put("bigint", "java.lang.Long");
      map.put("int unsigned", "java.lang.Long");
      map.put("char", "java.lang.String");
      map.put("varchar", "java.lang.String");
      map.put("text", "java.lang.String");
      map.put("datetime", "java.util.Date");
      map.put("timestamp", "java.util.Date");
      map.put("double", "java.lang.Double");
      map.put("float", "java.lang.Float");
  }
  
  static{
      simpleMap.put("int", "Integer");
      simpleMap.put("tinyint", "Short");
      simpleMap.put("bigint", "Long");
      simpleMap.put("int unsigned", "Long");
      simpleMap.put("char", "String");
      simpleMap.put("varchar", "String");
      simpleMap.put("text", "String");
      simpleMap.put("datetime", "Date");
      simpleMap.put("timestamp", "Date");
      simpleMap.put("double", "Double");
      simpleMap.put("float", "Float");
  }
  
  public static String getJavaType(String javaType){
      return map.get(javaType);
  }
  
 public static String getSimpleJavaType(String javaSimpleType){
     return simpleMap.get(javaSimpleType);
  }
}
