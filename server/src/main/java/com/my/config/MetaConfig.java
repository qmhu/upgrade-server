package com.my.config;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.my.model.ReleaseInfo;

@Service
public class MetaConfig {
	
	private String metaFile;
	private List<String> files;

    public MetaConfig() throws IOException{
    	
    }
    
  
}
  