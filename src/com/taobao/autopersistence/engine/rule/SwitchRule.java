package com.taobao.autopersistence.engine.rule;

/**
 * 
 * 类SwitchRule.java的实现描述：转换规则接口
 * @author zhanzui.ldh 2012-10-10 上午10:18:00
 */
public interface SwitchRule {
    /**
     * 根据一定的规则将数据库名转化项目名,如将a_b转为ab
     * @param dbName
     * @return
     */
    public String  dbNameConvertProjectName(String dbName);
    /**
     * 根据一定的规则将表名转化为pojo名,如将exam_result转为ExamResult
     * @param tableName
     * @return
     */
    public String  tableNameConvertClassName(String tableName);
    
    
    /**
     * 根据一定的规则将表字段转化为pojo的属性,如将user_name转为userName
     * @param dbElement
     * @return
     */
    public String  dbElementConvertJavaElement(String dbElement);
    
    
    /**
     * 如将com.taobao.a_b.dao转为com.taobao.ab.dao
     * @param projectName
     * @param type  vo,dao,service
     * @return package.projectName.dao
     */
    public String  getPackageName(String projectName,String type);
    
}
