package com.fembase.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;


public class ConfigManager {

	protected Log log = LogFactory.getLog(getClass());
    public static final ConfigManager instance = new ConfigManager();

    private Properties properties;

    private ConfigManager() {
        init();
    }

    public void init() {
        // init log4j
        PropertyConfigurator.configure(getConfigPath("log4j.properties"));
        properties = new Properties();
        // init config
        
        String configPath = getConfigPath("config.properties");
        File file = new File(configPath);
        if ( !file.exists()) {
            configPath = getConfigPath("config.properties.online");
        }
        
        try {
        	System.out.println("configPath=" +configPath);
            FileInputStream is = new FileInputStream(configPath);
            properties.load(is);
            is.close();
        } catch (Exception e) {
        	
            log.error("create ConfigManager failed", e);
        }
    }

    public void shutdown() {

    }

    private String getConfigPath(String fileName) {
        String path = ConfigManager.class.getResource("/").getPath();
        path = path.replace("classes", "conf");
        return path + fileName;
    }

    public String getValue(String configKey) {
        String value = properties.getProperty(configKey);
        return value == null ? "" : value;
    }
    
    public String getEnv() {
        return getValue("define.env");
    }
    
    public boolean isEnvLocal() {
        return getEnv().equals("local");
    }

    public boolean isEnvTest() {
        return getEnv().equals("test");
    }

    public boolean isEnvOnline() {
        return getEnv().equals("online");
    }
    
    public String getResDate() {
        return getValue("res.date");
    }
    
    public String getTaskResDate() {
    	return getValue("taskres.date");
    }
}