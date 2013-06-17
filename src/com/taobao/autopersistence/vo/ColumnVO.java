package com.taobao.autopersistence.vo;
/**
 * 
 * 类ColumnVO.java的实现描述：表的列对象
 * @author zhanzui.ldh 2012-9-15 下午4:22:05
 */
public class ColumnVO {
    /**
     * 列名称
     */
    private String columnName;
    /**
     * 列数据类型
     */
    private String columnType;
    /**
     * 列注释
     */
    private String columnRemarks;
    
    public String getColumnName() {
        return columnName;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    public String getColumnType() {
        return columnType;
    }
    
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnRemarks() {
        return columnRemarks;
    }
    
    public void setColumnRemarks(String columnRemarks) {
        this.columnRemarks = columnRemarks;
    }
}
