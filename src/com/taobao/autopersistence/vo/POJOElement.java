package com.taobao.autopersistence.vo;

public class POJOElement {
    /**
     * 该列对应的java的pojo属性名
     */
    private String javaName;
    /**
     * 该列对应的java的pojo属性类型
     */
    private String javaType;
    /**
     * 该列对应的java的pojo属性简化类型,如java.lang.Integer的简化类型为Integer
     */
    private String javaSimpleType;
    /**
     * pojo属性注释
     */
    private String remarks;
    
    public String getJavaName() {
        return javaName;
    }
    
    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }
    
    public String getJavaType() {
        return javaType;
    }
    
    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
    
    public String getJavaSimpleType() {
        return javaSimpleType;
    }
    
    public void setJavaSimpleType(String javaSimpleType) {
        this.javaSimpleType = javaSimpleType;
    }

    
    public String getRemarks() {
        return remarks;
    }

    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
