/**
 *
 */
package com.my.service;

import com.my.exception.BusinessException;
import com.my.model.ReleaseFile;
import com.my.model.ReleaseInfo;
import com.my.model.ReleasePlan;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
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

    public ReleaseInfo getReleaseInfo() {
        if (releaseInfo == null) {
            logger.info("begin load ReleaseInfo from meta.json, path is:" + this.metaFile);

            ReleaseInfo releaseTmp = new ReleaseInfo();
            List<ReleaseFile> files = new ArrayList<ReleaseFile>();
            List<ReleasePlan> plans = new ArrayList<ReleasePlan>();
            releaseTmp.setFiles(files);
            releaseTmp.setPlans(plans);

            JSONParser parser = new JSONParser();
            try {

                Object obj = parser.parse(new FileReader(this.metaFile));

                JSONObject configObject = (JSONObject) obj;

                JSONArray plansArray = (JSONArray) configObject.get("plans");
                JSONArray filesArray = (JSONArray) configObject.get("files");

                Iterator<JSONObject> iteratorPlan = plansArray.iterator();
                while (iteratorPlan.hasNext()) {
                    JSONObject rpObject = iteratorPlan.next();
                    ReleasePlan releasePlan = new ReleasePlan();
                    releasePlan.setClient_version((String) rpObject.get("client_version"));
                    List<String> include_files = new ArrayList<String>();
                    releasePlan.setInclude_files(include_files);

                    JSONArray include_filesArray = (JSONArray) (rpObject.get("include_files"));
                    Iterator<String> include_filesIterator = include_filesArray.iterator();
                    while (include_filesIterator.hasNext()) {
                        include_files.add(include_filesIterator.next());
                    }
                    plans.add(releasePlan);
                }

                Iterator<JSONObject> iteratorFile = filesArray.iterator();
                while (iteratorFile.hasNext()) {
                    ReleaseFile releaseFile = new ReleaseFile();
                    JSONObject rfObject = iteratorFile.next();
                    releaseFile.setName((String) rfObject.get("name"));
                    releaseFile.setDesc((String) rfObject.get("desc"));
                    releaseFile.setSrc((String) rfObject.get("src"));
                    releaseFile.setType((String) rfObject.get("type"));
                    files.add(releaseFile);
                }

                if (!validateReleaseInfo(releaseTmp)) {
                    throw new BusinessException("invalid releaseinfo");
                }

                this.releaseInfo = releaseTmp;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Meet exception during load ReleaseInfo from meta.json, exception is:" + e.getMessage());
            }
        }

        return this.releaseInfo;
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

            if (releaseFile.getDesc().trim().equals("")) {
                isValid = false;
            }

            if (!releaseFile.getType().trim().equals("dir") && !releaseFile.getType().trim().equals("file")) {
                isValid = false;
            }

        }

        List<ReleasePlan> releasePlans = releaseInfo.getPlans();
        for (ReleasePlan releasePlan : releasePlans) {
            if (releasePlan.getClient_version().trim().equals("")) {
                isValid = false;
            }

            if (releasePlan.getClient_version().trim().equals("")) {
                isValid = false;
            }

            if (!this.isValidVersion(releasePlan.getClient_version())) {
                isValid = false;
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
