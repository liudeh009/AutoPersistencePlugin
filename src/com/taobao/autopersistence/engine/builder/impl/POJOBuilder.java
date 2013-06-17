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
 * 类POJOBuilder.java的实现描述：生成pojo
 * @author zhanzui.ldh 2012-10-10 上午10:02:11
 */
public class POJOBuilder extends AbstractResourceBuilder{
    private String javadir; 
    
    public POJOBuilder(PersistenceContext context){
        super(context);
    } 
   
    
    @Override
    public void buildFile() {
        try {
            init();
            for(int i=0;i<javaVOList.size();i++){
                VelocityContext vc = new VelocityContext();
                JavaVO javaVO = javaVOList.get(i);
                vc.put("javaVO", javaVO);
                vc.put("author", context.getString("author"));
                vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                File file = new File(javadir + "/"+javaVO.getSimpleClassName()+".java");
                if(!file.exists()){
                    file.createNewFile();
                }
                transform("template/pojo/template.pojo.txt",file, vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void createFileDir() {
        //创建VO文件目录
        javadir =  context.getString("javadir");
        String projectName = context.getString("projectName");
        String javapackage = rule.getPackageName(projectName, "pojo");
        javapackage = javapackage.replace(".", "/");
        javadir = javadir + "/" + javapackage;
        File file = new File(javadir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

}
