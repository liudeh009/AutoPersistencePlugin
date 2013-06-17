package com.taobao.autopersistence.engine.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.rule.SwitchRule;
import com.taobao.autopersistence.engine.rule.impl.DefaultSwitchRule;
import com.taobao.autopersistence.util.MysqlType2JavaTypeUtil;
import com.taobao.autopersistence.vo.ColumnVO;
import com.taobao.autopersistence.vo.DatabaseVO;
import com.taobao.autopersistence.vo.JavaVO;
import com.taobao.autopersistence.vo.POJOElement;
import com.taobao.autopersistence.vo.TableVO;

/**
 * 
 * 类DBInfoParser.java的实现描述：
 * 解析数据库的信息
 * @author zhanzui.ldh 2012-10-10 上午09:53:44
 */
public class DBInfoParser {
    private PersistenceContext context;
    private SwitchRule rule;
    private Pattern pattern;
    
    public DBInfoParser(PersistenceContext context){
        this.context = context;
        rule = (SwitchRule)context.get("rule");
        pattern = Pattern.compile("^.*_{0,1}([0-9]*)[1-9]+([0-9]*)$",Pattern.MULTILINE);
    }
    
    public PersistenceContext getContext() {
        return context;
    }
    
    public SwitchRule getRule() {
        return rule;
    }

    private Connection getConnection(){
        Connection conn = null;
        try {
            String driver = context.getString("driver");
            String url = context.getString("url");
            String userName = context.getString("user");
            String password = context.getString("password");
            conn = getConnection(driver, url, userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
   }
    
    public Connection getConnection(String driver,String url,String userName,String password){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,userName, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
   }
    
    public void parser(){
        DatabaseVO databaseVO = getDatabaseVO();
        context.put("databaseVO", databaseVO);
        List<JavaVO> javaVOList = getJavaVOList(databaseVO);
        context.put("javaVOList", javaVOList);
    }
    
    private DatabaseVO getDatabaseVO(){
        DatabaseVO dbVO = null;
        try {
            Connection conn = getConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String catalog = conn.getCatalog();//数据库名
            dbVO = new DatabaseVO();
            dbVO.setDbName(catalog);
            String projectName = rule.dbNameConvertProjectName(catalog);
            if(context.getString("projectName") == null){
                context.put("projectName", projectName); 
            }
            
            ResultSet rs = dbMetaData.getTables(catalog, "%", "%", new String[]{"table"});
            while(rs.next()){ //获取数据库表的列表
                String tableName = rs.getString("TABLE_NAME");
                if(filterTableName(tableName)){
                    continue;
                }
                TableVO tableVO = new TableVO();
                tableVO.setTableName(tableName);
                String remarks = rs.getString("REMARKS");//表的解释性注释
                tableVO.setRemarks(remarks);
                dbVO.setTableVO(tableVO);
                
                ResultSet keyRS = dbMetaData.getPrimaryKeys(catalog, "%", tableName);
                while(keyRS.next()){//取表主键
                    String primaryKey = keyRS.getString("COLUMN_NAME");
                    tableVO.setPrimaryKey(primaryKey);
                }
                
                ResultSet clrs = dbMetaData.getColumns(catalog, "%", tableName, "%");
                while(clrs.next()){ //取表的字段
                    ColumnVO columnVO = new ColumnVO();
                    String columnName = clrs.getString("COLUMN_NAME");
                    columnVO.setColumnName(columnName);
                    String columnRemarks = clrs.getString("REMARKS");//列的解释性注释
                    columnVO.setColumnRemarks(columnRemarks);
                    String dataTypeName = clrs.getString("TYPE_NAME").toLowerCase();//java.sql.Types类型   名称   
                    columnVO.setColumnType(dataTypeName);
                    tableVO.setColumnVO(columnVO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return dbVO;
    }
    
    private List<JavaVO> getJavaVOList(DatabaseVO databaseVO){
        if(databaseVO==null){
            throw new RuntimeException("databaseVO is null");
        }
        List<TableVO> tableVOList = databaseVO.getTableList();
        String packageName = rule.getPackageName(context.getString("projectName"),"pojo");
        List<JavaVO> javaVOList = new ArrayList<JavaVO>();
        for(TableVO tableVO : tableVOList){
            JavaVO javaVO = new JavaVO();
            
            String simpleClassName = rule.tableNameConvertClassName(tableVO.getTableName());
            javaVO.setSimpleClassName(simpleClassName);
            javaVO.setClassName(packageName + "." + simpleClassName);
            
            List<ColumnVO> columnVOList = tableVO.getColumnList();
            for(ColumnVO columnVO : columnVOList){
                POJOElement element = new POJOElement();
                String columnName = columnVO.getColumnName();
                if(columnName.equals(tableVO.getPrimaryKey())){
                    javaVO.setPrimaryKey(rule.dbElementConvertJavaElement(columnName));
                }
                element.setJavaName(rule.dbElementConvertJavaElement(columnName));
                
                //将数据库的类型转化为java类型
                element.setJavaType(MysqlType2JavaTypeUtil.getJavaType(columnVO.getColumnType()));
                element.setJavaSimpleType(MysqlType2JavaTypeUtil.getSimpleJavaType(columnVO.getColumnType()));
                
                element.setRemarks(columnVO.getColumnRemarks());
                if("datetime".equals(columnVO.getColumnType())){
                    javaVO.setSetType(element.getJavaType());
                }
                javaVO.setPOJOElement(element);
            }
            javaVOList.add(javaVO);
        }
        return javaVOList;
    }    
    
    /**
     * 过滤掉重复的表
     * @param tableName 表名
     * @return
     */
    private boolean filterTableName(String tableName){
        Matcher matcher = pattern.matcher(tableName);
        return matcher.matches();
    }
    
    public static void main(String[] args) {
        PersistenceContext context = new PersistenceContext();
        context.put("rule", new DefaultSwitchRule(context));
        context.put("driver","com.mysql.jdbc.Driver");
        context.put("url","jdbc:mysql://localhost/aliyun_kaoshi?useUnicode=true&amp;characterEncoding=utf8");
        context.put("user","root");
        context.put("password","123456");
        DBInfoParser dbInfoParser = new  DBInfoParser(context);
        dbInfoParser.parser();
    }
}
