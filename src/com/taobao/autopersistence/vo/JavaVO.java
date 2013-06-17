package com.taobao.autopersistence.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
 * 类JavaVO.java的实现描述：用来实现java pojo的类
 * @author zhanzui.ldh 2012-9-15 下午4:55:05
 */
public class JavaVO {
    /**
     * java pojo的类名简称
     */
    private String simpleClassName;
    
    /**
     * java pojo的类名全称
     */
    private String className;
   
    /**
     * 表主键对应的java类属性
     */
    private String primaryKey;
    
    /**
     * pojo引用的类型
     */
    Set<String> referenceSet = new HashSet<String>();
    
    private List<POJOElement>  pojoElementList = new ArrayList<POJOElement>();
    
    public String getSimpleClassName() {
        return simpleClassName;
    }
    
    public void setSimpleClassName(String simpleClassName) {
        this.simpleClassName = simpleClassName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }  
    
    public String getPrimaryKey() {
        return primaryKey;
    }

    
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Set<String> getSet() {
        return referenceSet;
    }
    
    public void setSetType(String javaType) {
        referenceSet.add(javaType);
    }


    
    public List<POJOElement> getPojoElementList() {
        return pojoElementList;
    }
    
    public void setPOJOElement(POJOElement pojoElement) {
        pojoElementList.add(pojoElement);
    }
    
    /**
     * pojo所在包的名称
     * @return
     */
    public String getVOPackage(){
        return className.substring(0,className.lastIndexOf("."));
    }
    
    /**
     * pojo的类名简称的首字母小写,作为参数使用
     * @return
     */
    public String getVO2Param(){
        String cs = new String(new char[]{Character.toLowerCase(simpleClassName.charAt(0))})+simpleClassName.substring(1);
        return cs; 
    }
}
