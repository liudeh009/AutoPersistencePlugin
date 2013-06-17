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
 * 类DAOBuilder.java的实现描述：生成dao文件
 * @author zhanzui.ldh 2012-10-10 上午10:04:28
 */
public class DAOBuilder extends AbstractResourceBuilder{
    
    /**
     * com.taobao.dbName.dao
     */
    private String daopackage;
    private String daopackageDir;
    private String daoimplpackageDir;

    public DAOBuilder(PersistenceContext context){
        super(context);
    }  
    
    @Override
    public void buildFile() {
        try {
            buildBaseDAO();
            buildInterfaceDAO();
            buildImplDAO();
        } catch (Exception e) {
            e.printStackTrace();
        }
      
    }
    
    private void buildBaseDAO() throws Exception{
        VelocityContext vc = new VelocityContext();
        vc.put("daopackage", daopackage);
        vc.put("author", context.getString("author"));
        vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        File file = new File(daopackageDir + "/BaseDAO.java");
        if(!file.exists()){
            file.createNewFile();
        }
        transform("template/dao/base.dao.txt",file, vc);
    }
    
    private void buildInterfaceDAO() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("daopackage", daopackage);
            File file = new File(daopackageDir + "/"+javaVO.getSimpleClassName()+"DAO.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/dao/interface.dao.txt",file, vc);
        }
    }
    
    private void buildImplDAO() throws Exception{
        for(int i=0;i<javaVOList.size();i++){
            VelocityContext vc = new VelocityContext();
            JavaVO javaVO = javaVOList.get(i);
            vc.put("javaVO", javaVO);
            vc.put("author", context.getString("author"));
            vc.put("nowTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            vc.put("daopackage", daopackage);
            File file = new File(daoimplpackageDir + "/"+javaVO.getSimpleClassName()+"DAOImpl.java");
            if(!file.exists()){
                file.createNewFile();
            }
            transform("template/dao/impl.dao.txt",file, vc);
        }
    }
    
    /**
     * 返回./src/main/java/com/taobao/dbName/dao
     */
    public void createFileDir() {
        //创建VO文件目录
        String javadir =  context.getString("javadir");
        String projectName = context.getString("projectName");
        daopackage = rule.getPackageName(projectName, "dao");
        context.put("daopackage", daopackage);
        daopackageDir = daopackage.replace(".", "/");
        daopackageDir = javadir + "/" + daopackageDir;
        daoimplpackageDir = daopackageDir + "/impl";
        File file = new File(daoimplpackageDir);
        if(!file.exists()){
            file.mkdirs();
        }
    }

}
