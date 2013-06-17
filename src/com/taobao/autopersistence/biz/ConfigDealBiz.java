package com.taobao.autopersistence.biz;

import java.sql.Connection;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.builder.ResourceBuilder;
import com.taobao.autopersistence.start.BootStrap;
import com.taobao.autopersistence.util.StringUtils;
import com.taobao.autopersistence.vo.ConfigVO;
/**
 * 
 * 类ConfigDealBiz.java的实现描述：对参数进行校验和组装
 * @author zhanzui.ldh 2013-5-31 上午11:36:05
 */
public class ConfigDealBiz {
    private BootStrap bootStrap;
   
    public ConfigDealBiz(){
        bootStrap = new BootStrap(); 
    }
    
    public boolean dbParameterValidate(String driver,String url, String userName, String password){
        String[] strArray = dbParameterTransform(driver,url);
        Connection conn = bootStrap.getDbInfoparser().getConnection(strArray[0], strArray[1], userName, password);
        return conn==null? false : true;
    }
  
    /**
     * jdbc:oracle:thin:@192.168.2.66:1521:orcl66
     * jdbc:mysql://localhost/aliyun_kaoshi?useUnicode=true&amp;characterEncoding=utf8
     * @param configVO
     */
   public void deal(ConfigVO configVO,IProgressMonitor monitor){
       String pjPath = configVO.getPjPath();
       
       String driver = configVO.getDriver();
       String url = configVO.getUrl();
       String[] strArray = dbParameterTransform(driver,url);
       
       String userName = configVO.getUserName();
       String password = configVO.getPassword();
       String author = configVO.getAuthor();
       String charset = configVO.getCharset();
       String javaDir =configVO.getJavadir();
       String javaDirTest =configVO.getJavaTestDir();
       String resource =configVO.getResource();
       String pjPackage = configVO.getPjpackage();

      PersistenceContext context = this.bootStrap.getContext();
      context.put("driver", strArray[0]);
      context.put("url", strArray[1]);
      context.put("user", userName);
      context.put("password", password);
      
      context.put("author", author);
      if(StringUtils.isBlank(charset)){
          context.put("charset", "gbk"); 
      }else{
          context.put("charset", charset); 
      }
      if(StringUtils.isBlank(javaDir)){
          context.put("javadir", pjPath);
      }else{
          context.put("javadir", javaDir.replace(".", pjPath));
      }
     
      if(StringUtils.isBlank(javaDirTest)){
          context.put("javatest", pjPath);
      }else{
          context.put("javatest", javaDirTest.replace(".", pjPath));
      }
     
      if(StringUtils.isBlank(resource)){
          context.put("resource", pjPath);
      }else{
          context.put("resource", resource.replace(".", pjPath));
      }
      
      if(StringUtils.isBlank(pjPackage)){
          context.put("package", "com.taobao");
      }else{
          context.put("package", pjPackage);
      }
      
      String projectName = pjPath.substring(pjPath.lastIndexOf("\\") + 1,pjPath.length());
      projectName = StringUtils.upperToLower(projectName);
      context.put("projectName", projectName); 
      
      if(monitor == null){
          this.bootStrap.start();
      }else{
          this.bootStrap.pre();
          List<ResourceBuilder>  builderList = this.bootStrap.getBuilderList();
          monitor.beginTask("start.. ", builderList.size());
          int count = 1;
          for(ResourceBuilder builder : builderList){
              builder.doWork();
              monitor.worked(count++); 
          }
      }
   }
   
   private  String[] dbParameterTransform(String driver,String url){
       String[] strArray = new String[2];
       if(driver != null && !"".equals(driver)){
           if("oracle".equals(driver)){
               driver = "oracle.jdbc.driver.OracleDriver";
               url = "jdbc:oracle:thin:@" + url;
           }else{
               driver = "com.mysql.jdbc.Driver";
               url = "jdbc:mysql://" + url + "?useUnicode=true&amp;characterEncoding=utf8";
           }
        }else{
            driver = "com.mysql.jdbc.Driver";
            url = "jdbc:mysql://" + url + "?useUnicode=true&amp;characterEncoding=utf8";
        }
       strArray[0] = driver;
       strArray[1] = url;
       return strArray;
   }
   
   public static void main(String[] args) {
       ConfigDealBiz configDealBiz = new ConfigDealBiz();
//       ConfigVO configVO = new ConfigVO("D:/workspace2012/AutoPersistencePlugin");
//       configVO.setAuthor("zhanzui.ldh");
//       configVO.setCharset("UTF-8");
//       configVO.setDriver("com.mysql.jdbc.Driver");
//       configVO.setUrl("localhost/aliyun_kaoshi");
//       configVO.setUserName("root");
//       configVO.setPassword("123456");
//       configVO.setJavadir("./src/main/java");
//       configVO.setJavaTestDir("./src/test/java");
//       configVO.setPjpackage("com.taobao");
//       configVO.setPjPath("D:/workspace2012/AutoPersistencePlugin");
//       configVO.setResource("./src/main/resources");
//       configDealBiz.deal(configVO,null);
       String pjPath = "C:/a/b/c";
       pjPath = pjPath.substring(pjPath.lastIndexOf("/") + 1,pjPath.length());
       System.out.println(pjPath);
   }
}
