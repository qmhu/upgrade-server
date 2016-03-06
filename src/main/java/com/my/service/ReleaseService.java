/**
 *
 */
package com.my.service;

import com.my.exception.BusinessException;
import com.my.model.ReleaseFile;
import com.my.model.ReleaseInfo;
import com.my.model.ReleaseModule;
import com.my.model.ReleasePlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author I311862
 */
@Service("releaseService")
public class ReleaseService {

    private static final Logger logger = LoggerFactory.getLogger(ReleaseService.class);
    private String metaFile;
    private ReleaseInfo releaseInfo;

    public ReleaseService() throws IOException {
        Properties props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application.properties"));
        this.metaFile = props.getProperty("configFolder") + "meta.json";
    }

    public ReleaseInfo getReleaseInfo() throws IOException, ParseException {
        if (releaseInfo == null) {
            logger.info("begin load ReleaseInfo from meta.json, path is:" + this.metaFile);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(this.metaFile));
            ReleaseInfo releaseTmp = this.generateReleaseInfo(obj);
            if (!validateReleaseInfo(releaseTmp)) {
                throw new BusinessException("invalid releaseinfo");
            }

            this.releaseInfo = releaseTmp;
        }

        return this.releaseInfo;
    }

    private ReleaseInfo generateReleaseInfo(Object obj) {
        ReleaseInfo releaseTmp = new ReleaseInfo();
        List<ReleaseFile> files = new ArrayList<ReleaseFile>();
        List<ReleaseModule> modules = new ArrayList<ReleaseModule>();
        releaseTmp.setFiles(files);
        releaseTmp.setModules(modules);

        JSONObject configObject = (JSONObject) obj;

        JSONArray modulesArray = (JSONArray) configObject.get("modules");
        JSONArray filesArray = (JSONArray) configObject.get("files");

        Iterator<JSONObject> iteratorModule = modulesArray.iterator();
        while (iteratorModule.hasNext()) {
            JSONObject mObject = iteratorModule.next();
            ReleaseModule releaseModule = new ReleaseModule();
            releaseModule.setModule((String) mObject.get("module"));

            List<ReleasePlan> releasePlans = new ArrayList<ReleasePlan>();
            releaseModule.setPlans(releasePlans);

            JSONArray releasePlanArray = (JSONArray) (mObject.get("plans"));
            Iterator<JSONObject> releasePlansIterator = releasePlanArray.iterator();
            while (releasePlansIterator.hasNext()) {
                JSONObject rpObject = releasePlansIterator.next();
                ReleasePlan releasePlan = new ReleasePlan();
                releasePlan.setClient_version((String)rpObject.get("client_version"));

                List<String> downloadFiles = new ArrayList<String>();
                releasePlan.setDownload_files(downloadFiles);
                JSONArray downloadFileArray = (JSONArray) (mObject.get("download_files"));
                Iterator<String> downloadFileIterator = downloadFileArray.iterator();
                while (downloadFileIterator.hasNext()){
                    downloadFiles.add(downloadFileIterator.next());
                }

                releasePlans.add(releasePlan);
            }

            modules.add(releaseModule);
        }

        Iterator<JSONObject> iteratorFile = filesArray.iterator();
        while (iteratorFile.hasNext()) {
            ReleaseFile releaseFile = new ReleaseFile();
            JSONObject rfObject = iteratorFile.next();
            releaseFile.setName((String) rfObject.get("name"));
            releaseFile.setDest((String) rfObject.get("desc"));
            releaseFile.setSrc((String) rfObject.get("src"));
            releaseFile.setType((String) rfObject.get("type"));
            files.add(releaseFile);
        }

        return releaseTmp;
    }

    private boolean validateReleaseInfo(ReleaseInfo releaseInfo) {
        boolean isValid = true;
        List<ReleaseFile> releaseFiles = releaseInfo.getFiles();
        for (ReleaseFile releaseFile : releaseFiles) {
            if (releaseFile.getName().trim().equals("")) {
                isValid = false;
            }

            if (releaseFile.getSrc().trim().equals("")) {
                isValid = false;
            }

            if (releaseFile.getDest().trim().equals("")) {
                isValid = false;
            }

            if (!releaseFile.getType().trim().equals("dir") && !releaseFile.getType().trim().equals("file")) {
                isValid = false;
            }

        }

        if (releaseInfo.getVersion().trim().equals("")){
            isValid = false;
        }

        List<ReleaseModule> releaseModules = releaseInfo.getModules();
        for (ReleaseModule releaseModule : releaseModules) {
            if (releaseModule.getModule().trim().equals("")) {
                isValid = false;
            }

            for(ReleasePlan releasePlan : releaseModule.getPlans()) {
                if (releasePlan.getClient_version().trim().equals("")) {
                    isValid = false;
                }

                if (releasePlan.getClient_version().trim().equals("")) {
                    isValid = false;
                }

                if (!this.isValidVersion(releasePlan.getClient_version())) {
                    isValid = false;
                }

                for (String filename : releasePlan.getDownload_files()){
                    if (filename.trim().equals("")) {
                        isValid = false;
                    }
                }
            }
        }

        return isValid;
    }

    private boolean isValidVersion(String version) {
        return Pattern.compile("^(\\d+\\.)?(\\d+\\.)?(\\d+)?$").matcher(version).matches();
    }

    private Integer versionCompare(String str1, String str2) {
        String[] vals1 = str1.split("\\.");
        String[] vals2 = str2.split("\\.");
        int i = 0;
        // set index to first non-equal ordinal or length of shortest version string
        while (i < vals1.length && i < vals2.length && vals1[i].equals(vals2[i])) {
            i++;
        }
        // compare first non-equal ordinal number
        if (i < vals1.length && i < vals2.length) {
            int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
            return Integer.signum(diff);
        }
        // the strings are equal or one string is a substring of the other
        // e.g. "1.2.3" = "1.2.3" or "1.2.3" < "1.2.3.4"
        else {
            return Integer.signum(vals1.length - vals2.length);
        }
    }
    

    public ReleaseInfo createRelease(MultipartFile file) {
        // 1.store file in tmp dir
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String configUpload = new String(bytes);
                JSONParser parser = new JSONParser();
                Object configObj = parser.parse(configUpload);
                ReleaseInfo releaseTmp = this.generateReleaseInfo(configObj);

                if (!validateReleaseInfo(releaseTmp)) {
                    throw new BusinessException("invalid release info, please check your meta.json file");
                }

                // Create the file on server
                File metaJsonFile = new File(this.metaFile);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(metaJsonFile));
                stream.write(bytes);
                stream.close();

                logger.info("Save ReleaseInfo in server, File Location=" + metaJsonFile.getAbsolutePath());
                this.releaseInfo = releaseTmp;
            } catch (Exception e) {
                logger.error("Save ReleaseInfo failed, exception:" + e.getMessage());
                throw new BusinessException("Save ReleaseInfo failed, filename is exception:" + e.getMessage());
            }
        } else {
            logger.error("Upload file failed because file is empty");
            throw new BusinessException("Upload file failed because file is empty");
        }

        return this.releaseInfo;
    }

    public String getDownloadFilePath(String filename){
        List<ReleaseFile> releaseFiles = this.releaseInfo.getFiles();
        for(ReleaseFile releaseFile : releaseFiles){
            if (releaseFile.getName().equals(filename)){
                return releaseFile.getSrc();
            }
        }

        return null;
    }

    public String getReleaseVersion(){
        return this.releaseInfo.getVersion();
    }

    public List<ReleaseFile> getReleaseFileForUpgrade(String clientVersion, String module){
        for (ReleaseModule releaseModule : this.releaseInfo.getModules()){
            if (releaseModule.getModule().equals(module)){
                for (ReleasePlan releasePlan : releaseModule.getPlans()){
                    if (releasePlan.getClient_version().equals())
                }

            }
        }
    }

}
