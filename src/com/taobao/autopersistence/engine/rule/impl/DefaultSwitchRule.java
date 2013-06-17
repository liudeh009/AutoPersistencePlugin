package com.taobao.autopersistence.engine.rule.impl;

import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.rule.SwitchRule;
/**
 * 
 * 类DbAndJavaPropsSwitchRule.java的实现描述：转换规则实现类
 * @author zhanzui.ldh 2012-10-10 上午10:20:00
 */
public class DefaultSwitchRule implements SwitchRule{
    private PersistenceContext context;
    
    public DefaultSwitchRule(PersistenceContext context){
        this.context = context;
    }
    
    @Override
    public String dbNameConvertProjectName(String dbName){
        String[] str = dbName.split("_"); 
        String project = "";
       if(str!=null && str.length>0){
           project = str[0];
          for(int i=1;i<str.length;i++){
              project += str[i];
          }
          project = project.toLowerCase();
       }
       return project;
    }
    
    @Override
    public String tableNameConvertClassName(String tableName){
        String[] str = tableName.split("_"); 
        String project = "";
       if(str!=null && str.length>0){
          for(int i=0;i<str.length;i++){
              String cs = new String(new char[]{Character.toUpperCase(str[i].charAt(0))})+str[i].substring(1);
              project += cs;
          }
       }
      return project;    
    }

    @Override
    public String  dbElementConvertJavaElement(String dbElement){
        String[] str = dbElement.split("_"); 
        String project = "";
       if(str!=null && str.length>0){
           project = str[0];
          for(int i=1;i<str.length;i++){
              String cs = new String(new char[]{Character.toUpperCase(str[i].charAt(0))})+str[i].substring(1);
              project += cs;
              
          }
       }
      return project;    
    }
    
    @Override
    public  String  getPackageName(String projectName,String type){
       String prefixPackage =  context.getString("package");
       String project = dbNameConvertProjectName(projectName);
       return prefixPackage+"."+project+"." + type;    
    }
}
