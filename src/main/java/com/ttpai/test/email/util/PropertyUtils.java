package com.ttpai.test.email.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Desc:properties文件获取工具类
 * Created by hafiz.zhang on 2016/9/15.
 */
public class PropertyUtils {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
    private static Properties props;

    synchronized static private void loadProps(String fileName){
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtils.class.getClassLoader().getResourceAsStream(fileName);
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("projects.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    /**  
     * 传递键值对的Map，更新properties文件  
     *   
     * @param fileName  
     *            文件名(放在resource源包目录下)，需要后缀  
     * @param keyValueMap  
     *            键值对Map  
     */  
    public static void updateProperties(String fileName, Map<String, String> keyValueMap) {  
    	 
        String filePath = PropertyUtils.class.getClassLoader()  
                .getResource(fileName).getFile();// 文件的路径  
        System.out.println("propertiesPath:" + filePath);  
        Properties props = new Properties();  
        BufferedReader br = null;  
        BufferedWriter bw = null;  
  
        try {  
            // 从输入流中读取属性列表（键和元素对）  
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            props.load(br);  
            br.close();  
  
            // 写入属性文件  
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            props.clear();// 清空旧的文件  
            for (String key : keyValueMap.keySet()) {
            	props.setProperty(key, keyValueMap.get(key));  
            }
            props.store(bw, "");
            bw.close();  
        } catch (IOException e) {  
            System.err.println("Visit " + filePath + " for updating " + ""+ " value error");  
        } finally {  
            try {  
                br.close();  
                bw.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    public static void main(String[] args) {
    	Map<String, String> keyValueMap = new HashMap<String, String>();
		//更新配置文件
		keyValueMap.put("JSESSIONID", "123");
		keyValueMap.put("NSC_kjbpxv-iuuq", "34");
		PropertyUtils.updateProperties("cookies.properties", keyValueMap);
	}
    
    public static String getProperty(String fileName, String key){
//        if(null == props) {
        loadProps(fileName);
//        }
        return props.getProperty(key);
    }

    public static String getProperty(String fileName, String key, String defaultValue) {
//        if(null == props) {
        loadProps(fileName);
//        }
        return props.getProperty(key, defaultValue);
    }
}