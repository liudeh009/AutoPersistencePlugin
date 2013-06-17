package com.taobao.autopersistence.vo;

/**
 * 
 * 类ConfigVO.java的实现描述：配置参数的实体类
 * @author zhanzui.ldh 2013-5-20 下午04:25:47
 */
public class ConfigVO {
    /**数据库驱动**/
    private String driver = "com.mysql.jdbc.Driver";
    /**数据库链接**/
    private String url;
    /**数据库用户名**/
    private String userName = "root";
    /**数据库密码**/
    private String password = "123456";
    /**代码作者**/
    private String author = "zhanzui.ldh";
    /**项目编码**/
    private String charset = "UTF-8";
    /**代码目录**/
    private String javadir = "./src/main/java";
    /**测试代码目录**/
    private String javaTestDir = "./src/test/java";
    /**资源文件目录**/
    private String resource = "./src/main/resources";
    /**java 包前缀**/
    private String pjpackage = "com.taobao";
    /**项目路径**/
    private String pjPath;
    
    public ConfigVO(String pjPath){
       this.pjPath = pjPath;
    }
    
    public String getDriver() {
        return driver;
    }
    
    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getCharset() {
        return charset;
    }
    
    public void setCharset(String charset) {
        this.charset = charset;
    }
    
    public String getJavadir() {
        return javadir;
    }
    
    public void setJavadir(String javadir) {
        this.javadir = javadir;
    }
    
    public String getJavaTestDir() {
        return javaTestDir;
    }
    
    public void setJavaTestDir(String javaTestDir) {
        this.javaTestDir = javaTestDir;
    }
    
    public String getResource() {
        return resource;
    }
    
    public void setResource(String resource) {
        this.resource = resource;
    }
    
    public String getPjpackage() {
        return pjpackage;
    }
    
    public void setPjpackage(String pjpackage) {
        this.pjpackage = pjpackage;
    }

    
    public String getPjPath() {
        return pjPath;
    }
    
    public void setPjPath(String pjPath) {
        this.pjPath = pjPath;
    }
}
