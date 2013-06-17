package com.taobao.autopersistence.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Properties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.taobao.autopersistence.context.PersistenceContext;


public class VelocityUtil {
    public static String mdoelCharset = "GBK";
    public static String charset = "GBK";
    public static void init(PersistenceContext context){
        charset = context.getString("charset");
        Properties props=new Properties();
        props.put("runtime.log","velocity.log");
        props.put("resource.loader","file,jar,classpath,string");
        props.setProperty("jar.resource.loader.class","org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        props.setProperty("classpath.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        props.setProperty("string.resource.loader.class","org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        props.put("input.encoding", charset);
        props.put("output.encoding", charset);
        try {
            Velocity.init(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    /**
      public static void createXMLFile(String templateFile,File resultFile,VelocityContext vc) throws Exception{
    	  //Template template=Velocity.getTemplate("/src/main/resources/template.ibatis.xml");
    	    Template template=Velocity.getTemplate(templateFile);
    		StringWriter out=new StringWriter();
    		template.merge(vc, out);
    		out.close();
    		formatXml(out.toString(),resultFile);
    		
      }*/
  
    public static void createFile(String templateFile,File resultFile,VelocityContext vc) throws Exception{
        //Template template=Velocity.getTemplate("/src/main/resources/template.ibatis.xml");
         InputStream in = VelocityUtil.class.getClassLoader().getResourceAsStream(templateFile);
         Reader reader = new BufferedReader(new InputStreamReader(in, mdoelCharset));
         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFile), charset));
         Velocity.evaluate(vc, out, "", reader);
         out.flush();
         out.close();
         reader.close();
         in.close();
	}
}
