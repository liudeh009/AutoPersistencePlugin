package com.taobao.autopersistence.engine.builder.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.velocity.VelocityContext;
import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.builder.AbstractResourceBuilder;
import com.taobao.autopersistence.vo.JavaVO;

/**
 * 
 * 类DAOBuilder.java的实现描述：生成TestCase文件
 * @author zhanzui.ldh 2012-10-10 上午10:04:28
 */
public class ServiceTestCaseBuilder extends AbstractResourceBuilder {
    /**
     * com.taobao.dbName.dao.testcase
     */
    private String servicetestpackage;
    private String servicetestpackageDir;
    
    public ServiceTestCaseBuilder(PersistenceContext context){
              super(context);
    }  
  
    @Override
    public void buildFile() {
        try {
            buildTestCaseService();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
   
    
    private void buildTestCaseService() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("servicepackage", context.getString("servicepackage"));
            vc.put("daotestpackage", context.getString("daotestpackage"));
            vc.put("servicetestpackage", servicetestpackage);
            File file = new File(servicetestpackageDir + "/"+javaVO.getSimpleClassName()+"ServiceTest.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/test/template.service.testcase.txt",file, vc);
        }
    }
    
    /**
     * 返回./src/main/java/com/taobao/dbName/dao
     */
    public void createFileDir() {
        //创建VO文件目录
        String javadir =  context.getString("javatest");
        String projectName = context.getString("projectName");
        servicetestpackage = rule.getPackageName(projectName, "service.testcase");
        context.put("servicetestpackage", servicetestpackage);
        servicetestpackageDir = servicetestpackage.replace(".", "/");
        servicetestpackageDir = javadir + "/" + servicetestpackageDir;
        File file = new File(servicetestpackageDir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

}
