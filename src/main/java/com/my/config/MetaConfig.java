package com.my.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MetaConfig {

    public MetaConfig(){
    	Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
    	
    	
    }

}
