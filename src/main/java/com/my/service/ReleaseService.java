/**
 * 
 */
package com.my.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.model.PackageMeta;
import com.my.model.ReleaseFile;
import com.my.model.ReleaseInfo;
import com.my.model.ReleasePlan;

/**
 * @author I311862
 *
 */
@Service("releaseService")
public class ReleaseService {

    private static final Logger logger = LoggerFactory.getLogger(ReleaseService.class);
    private String metaFile;
    private ReleaseInfo releaseInfo;
    
    public ReleaseService() throws IOException{
    	Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application.properties"));
    	this.metaFile = props.getProperty("configFolder") + "meta.json";
    }

    public ReleaseInfo getReleaseInfo() {
    	if(releaseInfo == null){
    		logger.info("begin load ReleaseInfo from meta.json, path is:" + this.metaFile);
    		
    		releaseInfo = new ReleaseInfo();
    		List<ReleaseFile> files = new ArrayList<ReleaseFile>();
    		List<ReleasePlan> plans = new ArrayList<ReleasePlan>();
    		releaseInfo.setFiles(files);
    		releaseInfo.setPlans(plans);
    		
    		JSONParser parser = new JSONParser();
            try {
     
                Object obj = parser.parse(new FileReader(this.metaFile));
     
                JSONObject configObject = (JSONObject) obj;
     
                JSONArray plansArray = (JSONArray) configObject.get("plans");
                JSONArray filesArray = (JSONArray) configObject.get("files");
                
                Iterator<ReleasePlan> iteratorPlan = plansArray.iterator();
                while (iteratorPlan.hasNext()) {
                	plans.add(iteratorPlan.next());
                }
                
                Iterator<ReleaseFile> iteratorFile = filesArray.iterator();
                while (iteratorFile.hasNext()) {
                	files.add(iteratorFile.next());
                }
     
            } catch (Exception e) {
            	logger.error("Meet exception during load ReleaseInfo from meta.json, exception is:" + e.getMessage());
            }
            
            
        	
    	}
    	
        return this.releaseInfo;
    }
    
    private void validateReleaseInfo(ReleaseInfo releaseInfo){
    	
    }
    

    /*public Attachment createAttachment(MultipartFile file, String name, String type, int size, String description) {

        // 1.store file in tmp dir
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

            } catch (Exception e) {
                logger.error("Upload file failed, filename is " + name + " exception:" + e.getMessage());
                throw new BusinessException("Upload file failed, filename is " + name + " exception:" + e.getMessage());
            }
        } else {
            logger.error("Upload file failed because file is empty, filename is " + name);
            throw new BusinessException("Upload file failed because file is empty, filename is " + name);
        }

        // 2.save attachement to DB

        return null;
    }*/

}
