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
 * 类ServiceBuilder.java的实现描述：生成service文件
 * @author zhanzui.ldh 2012-10-10 上午10:05:06
 */
public class ServiceBuilder extends AbstractResourceBuilder {
    /**
     * com.taobao.dbName.dao
     */
    private String servicepackage;
    private String servicepackageDir;
    private String serviceimplpackageDir;

    public ServiceBuilder(PersistenceContext context){
        super(context);
    }  
  
    @Override
    public void buildFile() {
        try {
            buildInterfaceService();
            buildImplService();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    
    
    private void buildInterfaceService() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("servicepackage", servicepackage);
            File file = new File(servicepackageDir+"/"+javaVO.getSimpleClassName()+"Service.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/service/interface.service.txt",file, vc);
        }
    }
    
    private void buildImplService() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("servicepackage", servicepackage);
            vc.put("daopackage", context.getString("daopackage"));
            File file = new File(serviceimplpackageDir+"/"+javaVO.getSimpleClassName()+"ServiceImpl.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/service/impl.service.txt",file, vc);
        }
    }
    
    /**
     * 返回./src/main/java/com/taobao/dbName/dao
     */
    public void createFileDir() {
        //创建VO文件目录
        String javadir =  context.getString("javadir");
        String projectName = context.getString("projectName");
        servicepackage = rule.getPackageName(projectName, "service");
        context.put("servicepackage", servicepackage);
        servicepackageDir = servicepackage.replace(".", "/");
        servicepackageDir = javadir + "/" + servicepackageDir;
        serviceimplpackageDir = servicepackageDir + "/impl";
        File file = new File(serviceimplpackageDir);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
