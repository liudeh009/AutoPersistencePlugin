package com.taobao.autopersistence.engine.builder;

import java.io.File;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.rule.SwitchRule;
import com.taobao.autopersistence.util.VelocityUtil;
import com.taobao.autopersistence.vo.DatabaseVO;
import com.taobao.autopersistence.vo.JavaVO;

/**
 * 
 * 类ResourceBuilder.java的实现描述：抽象类
 * @author zhanzui.ldh 2012-10-10 上午10:00:19
 */
public abstract class AbstractResourceBuilder implements ResourceBuilder{
    protected PersistenceContext context;
    protected SwitchRule rule;
    protected DatabaseVO  databaseVO;
    protected List<JavaVO> javaVOList;
    
    public AbstractResourceBuilder(PersistenceContext context){
          this.context = context;
    }
    /**
     * 初始化
     * @return
     */
   @SuppressWarnings("unchecked")
    public void init(){
       rule = (SwitchRule)context.get("rule");
       databaseVO = (DatabaseVO)context.get("databaseVO");
       javaVOList = (List<JavaVO>)context.get("javaVOList");
   }
   
   public void doWork(){
       init();
       createFileDir();
       buildFile();
   }
   
   protected void transform(String templateFile,File resultFile,VelocityContext vc) throws Exception{
       VelocityUtil.createFile(templateFile, resultFile, vc);
   }
}
