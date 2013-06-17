package com.taobao.autopersistence.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 类tableVO.java的实现描述：表对象
 * 
 * @author zhanzui.ldh 2012-9-15 下午4:12:28
 */
public class TableVO {
    /**
     * 数据表名称
     */
    private String tableName;
    /**
     * 主键
     */
    private String primaryKey;
    /**
     * 表注释
     */
    private String remarks;
    
    /**
     * 数据表列的列表
     */
    List<ColumnVO> columnList = new ArrayList<ColumnVO>(); ;
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
    public String getPrimaryKey() {
        return primaryKey;
    }

    
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    public List<ColumnVO> getColumnList() {
        return columnList;
    }

    
    public void setColumnVO(ColumnVO columnVO) {
        columnList.add(columnVO);
    }
   
}
