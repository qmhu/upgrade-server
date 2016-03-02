/**
 * 
 */
package com.my.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.my.model.PackageMeta;

/**
 * @author I311862
 *
 */
@Service("upgradeService")
public class UpgradeService {

    private static final Logger logger = LoggerFactory.getLogger(UpgradeService.class);


    public PackageMeta getUpgradeMeta() {
    	PackageMeta upgradeMeta = new PackageMeta();
        return upgradeMeta;
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
