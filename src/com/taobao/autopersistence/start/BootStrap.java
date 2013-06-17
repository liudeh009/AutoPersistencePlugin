package com.taobao.autopersistence.start;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import com.taobao.autopersistence.context.PersistenceContext;
import com.taobao.autopersistence.engine.builder.ResourceBuilder;
import com.taobao.autopersistence.engine.builder.impl.ApplicationContextXMLBuilder;
import com.taobao.autopersistence.engine.builder.impl.ContextXMLBuilder;
import com.taobao.autopersistence.engine.builder.impl.DAOBuilder;
import com.taobao.autopersistence.engine.builder.impl.DAOTestCaseBuilder;
import com.taobao.autopersistence.engine.builder.impl.DataSourceXMLBuilder;
import com.taobao.autopersistence.engine.builder.impl.IbatisXMlBuilder;
import com.taobao.autopersistence.engine.builder.impl.POJOBuilder;
import com.taobao.autopersistence.engine.builder.impl.ServiceBuilder;
import com.taobao.autopersistence.engine.builder.impl.ServiceTestCaseBuilder;
import com.taobao.autopersistence.engine.db.DBInfoParser;
import com.taobao.autopersistence.engine.rule.SwitchRule;
import com.taobao.autopersistence.engine.rule.impl.DefaultSwitchRule;
import com.taobao.autopersistence.util.VelocityUtil;


/**
 * 
 * 类BootStrap.java的实现描述：启动类 
 * @author zhanzui.ldh 2012-9-15 上午10:42:50
 */
public class BootStrap {
   private PersistenceContext context;
   private DBInfoParser dbInfoparser;
   private List<ResourceBuilder> builderList;
   
   public BootStrap(){
       this(null);
   }
   
   public BootStrap(SwitchRule rule){
       context = new PersistenceContext();
       if(rule == null){
           rule = new DefaultSwitchRule(context);
       }
       context.put("rule", rule);
       dbInfoparser = new DBInfoParser(context);
       builderList = getDefaultBuilderList();
   }
   
   /**
    * 初始化上下文
    */
   public void init(){
       Properties  props = new Properties();
       try {
          props.load(BootStrap.class.getClassLoader().getResourceAsStream("default-auto.properties"));
          InputStream in = BootStrap.class.getClassLoader().getResourceAsStream("auto.properties");
          if(in != null){
              props.load(in);
           }
         } catch (IOException e) {
          e.printStackTrace();
        }
       Set<Entry<Object, Object>> set = props.entrySet();
       for(Entry<Object, Object> entry : set){
           context.put((String)entry.getKey(), entry.getValue());
       }
   }
   
   public void pre(){
       VelocityUtil.init(context);//初始化Velocity
       dbInfoparser.parser();
   }
   
   public void start(){
       pre();
       for(ResourceBuilder builder : builderList){
           builder.doWork();
       }
   }
   
   private List<ResourceBuilder> getDefaultBuilderList(){
       List<ResourceBuilder> defaultBuilderList = new ArrayList<ResourceBuilder>();
       
       //1.生成pojo.ibatis.xml文件
       ResourceBuilder xmlBuilder = new IbatisXMlBuilder(context);
       defaultBuilderList.add(xmlBuilder);
       
       //2.生成pojo类
       ResourceBuilder pojoBuilder = new POJOBuilder(context);
       defaultBuilderList.add(pojoBuilder);
       
       //3.生成dao相关类
       ResourceBuilder daoBuilder = new DAOBuilder(context);
       defaultBuilderList.add(daoBuilder);
       
       //4.生成service相关类
       ResourceBuilder serviceBuilder = new ServiceBuilder(context);
       defaultBuilderList.add(serviceBuilder);
       
       //5. 生成xx.datasource.xml和 template.sqlmap.xml
       ResourceBuilder dataSourceXMLBuilder = new DataSourceXMLBuilder(context);
       defaultBuilderList.add(dataSourceXMLBuilder);
       
       //6.生成xx.context.xml
       ResourceBuilder contextXMLBuilder = new ContextXMLBuilder(context);
       defaultBuilderList.add(contextXMLBuilder);
       
       //7.生成applicationContext.xml
       ResourceBuilder appContextXMLBuilder = new ApplicationContextXMLBuilder(context);
       defaultBuilderList.add(appContextXMLBuilder);
       
       //8.生成dao的测试用例
       ResourceBuilder daoTestCaseBuilder = new DAOTestCaseBuilder(context);
       defaultBuilderList.add(daoTestCaseBuilder);
       
       //9.生成service的测试用例
       ResourceBuilder serviceTestCaseBuilder = new ServiceTestCaseBuilder(context);
       defaultBuilderList.add(serviceTestCaseBuilder);
       return defaultBuilderList;
   }
   
   
   public DBInfoParser getDbInfoparser() {
      return dbInfoparser;
   }

    public PersistenceContext getContext() {
        return context;
    }
     
    public List<ResourceBuilder> getBuilderList() {
        return builderList;
    }

    public static void main(String[] args) throws Exception {
           BootStrap bootStrap = new BootStrap();
           bootStrap.init();
           bootStrap.start();
       }
    }
