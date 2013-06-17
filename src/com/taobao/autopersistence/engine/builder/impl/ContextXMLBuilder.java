package com.taobao.autopersistence.engine.builder.impl;

import java.io.File;
import java.util.List;
import org.apache.velocity.VelocityContext;
import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.builder.AbstractResourceBuilder;
import com.taobao.autopersistence.util.VelocityUtil;

/**
 * 
 * 类DataSourceXMLBuilder.java的实现描述：生成datasource,sqlmap的xml文件
 * @author zhanzui.ldh 2012-10-18 下午02:56:16
 */
@SuppressWarnings("unused")
public class ContextXMLBuilder extends AbstractResourceBuilder{
    
    public ContextXMLBuilder(PersistenceContext context){
          super(context);
    }  
    
    @Override
    public void buildFile() {
        try {
            VelocityContext vc = new VelocityContext();
            vc.put("charset", context.getString("charset"));
            vc.put("daopackage", context.getString("daopackage"));
            vc.put("servicepackage", context.getString("servicepackage"));
            vc.put("javaVOList", javaVOList);
            String resource = context.getString("resource");
            String sqlmapPath = resource + "/conf/";
            File file = new File(sqlmapPath + context.getString("projectName")+".context.xml");
            if(!file.exists()){
                    file.createNewFile();
                }
            transform("template/spring/template.context.xml",file, vc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void createFileDir() {
    }
   
}
