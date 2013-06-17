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
public class DAOTestCaseBuilder extends AbstractResourceBuilder{
    /**
     * com.taobao.dbName.dao.testcase
     */
    private String daotestpackage;
    private String daotestpackageDir;
    
    public DAOTestCaseBuilder(PersistenceContext context){
        super(context);
    }  
    
    @Override
    public void buildFile() {
        try {
            buildBaseTestCase();
            buildTestCaseDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    
    private void buildBaseTestCase() throws Exception{
        VelocityContext vc = new VelocityContext();
        vc.put("author", context.getString("author"));
        vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        vc.put("daotestpackage", daotestpackage);
        File file = new File(daotestpackageDir + "/BaseTestCase.java");
        if(!file.exists()){
            file.createNewFile();
        }
        transform("template/test/template.base.testcase.txt",file, vc);
    }
    
    private void buildTestCaseDao() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("daopackage", context.getString("daopackage"));
            vc.put("daotestpackage", daotestpackage);
            File file = new File(daotestpackageDir + "/" + javaVO.getSimpleClassName()+"DAOTest.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/test/template.dao.testcase.txt",file, vc);
        }
    }
    
    /**
     * 返回./src/main/java/com/taobao/dbName/dao
     */
    public void createFileDir() {
        //创建VO文件目录
        String javadir =  context.getString("javatest");
        String projectName = context.getString("projectName");
        daotestpackage = rule.getPackageName(projectName, "dao.testcase");
        context.put("daotestpackage", daotestpackage);
        daotestpackageDir = daotestpackage.replace(".", "/");
        daotestpackageDir = javadir + "/" + daotestpackageDir;
        File file = new File(daotestpackageDir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

}
