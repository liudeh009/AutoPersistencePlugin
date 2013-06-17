package com.taobao.autopersistence.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类DatabaseVO.java的实现描述：数据库对象
 * @author zhanzui.ldh 2012-9-15 下午4:12:15
 */
public class DatabaseVO {
    /**
     * 数据库名称
     */
    private String dbName;
    /**
     * 数据库表列表
     */
    private List<TableVO> tableList = new ArrayList<TableVO>();
    
    public String getDbName() {
        return dbName;
    }
    
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    public List<TableVO> getTableList() {
        return tableList;
    }
    
    public void setTableVO(TableVO tableVO) {
        tableList.add(tableVO);
    }
}
