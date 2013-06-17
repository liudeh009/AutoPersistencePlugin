package com.taobao.autopersistence.engine.builder.impl;

import java.io.File;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.builder.AbstractResourceBuilder;
import com.taobao.autopersistence.engine.builder.ResourceBuilder;
import com.taobao.autopersistence.engine.rule.SwitchRule;
import com.taobao.autopersistence.util.VelocityUtil;
import com.taobao.autopersistence.vo.DatabaseVO;
import com.taobao.autopersistence.vo.JavaVO;

/**
 * 
 * 类DataSourceXMLBuilder.java的实现描述：生成datasource,sqlmap的xml文件
 * @author zhanzui.ldh 2012-10-18 下午02:56:16
 */
@SuppressWarnings("unused")
public class DataSourceXMLBuilder extends AbstractResourceBuilder{
    
    public DataSourceXMLBuilder(PersistenceContext context){
        super(context);
    }  
    
    @Override
    public void buildFile() {
        try {
            buildDatasourceXMLFile();
            buildSqlmapXMLFile();
        }  catch (Exception e) {
            e.printStackTrace();
        } 
    }
   
    private void buildDatasourceXMLFile() {
        try {
            VelocityContext vc = new VelocityContext();
            vc.put("charset", context.getString("charset"));
            vc.put("projectName", context.getString("projectName"));
            vc.put("driver", context.getString("driver"));
            vc.put("url", context.getString("url"));
            vc.put("user", context.getString("user"));
            vc.put("password", context.getString("password"));
            
            String resource = context.getString("resource");
            String sqlmapPath = resource + "/conf/";
            File file = new File(sqlmapPath + context.getString("projectName")+".datasource.xml");
            if(!file.exists()){
                    file.createNewFile();
                }
            transform("template/datasource/template.datasource.xml",file, vc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void buildSqlmapXMLFile() {
        try {
            VelocityContext vc = new VelocityContext();
            vc.put("charset", context.getString("charset"));
            vc.put("javaVOList", javaVOList);
            String resource = context.getString("resource");
            String sqlmapPath = resource + "/conf/";
            File file = new File(sqlmapPath + context.getString("projectName")+".sqlmap.xml");
            if(!file.exists()){
                    file.createNewFile();
                }
            transform("template/datasource/template.sqlmap.xml",file, vc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void createFileDir() {
    }
   
}
