/**
 *
 */
package com.my.service;

import com.my.exception.BusinessException;
import com.my.model.UpgradeInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author I311862
 */
@Service("upgradeService")
public class UpgradeService {

    private static final Logger logger = LoggerFactory.getLogger(UpgradeService.class);

    @Autowired
    private ReleaseService releaseService;

    public UpgradeInfo getUpgradeInfo(String clientVersion, String module) {
        UpgradeInfo upgradeInfo = new UpgradeInfo();
        upgradeInfo.setVersion(releaseService.getReleaseVersion());
        upgradeInfo.setReleaseFiles(releaseService.getReleaseFileForUpgrade(clientVersion, module));

        return upgradeInfo;
    }

    public void download(HttpServletResponse response, String filename) throws FileNotFoundException {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String downLoadPath = this.releaseService.getDownloadFilePath(filename);
            if (downLoadPath == null) {
                throw new BusinessException("Download file not exist :" + filename);
            }

            String fileMd5 = getMD5ForFile(downLoadPath);
            //获取文件的长度
            long fileLength = new File(downLoadPath).length();
            //设置文件输出类型
            response.setContentType("application/octet-stream");
            response.setHeader("Content-MD5", fileMd5);
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(filename.getBytes("utf-8"), "ISO8859-1"));
            //设置输出长度
            response.setHeader("Content-Length", String.valueOf(fileLength));
            //获取输入流
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            //输出流
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception ex) {
            logger.error("Meet exception during download file, exception is:" + ex.getMessage());
            ex.printStackTrace();
            throw new BusinessException("Meet exception during download file, exception is:" + ex.getMessage());
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("Meet exception during download file, exception is:" + e.getMessage());
            }
        }

    }

    public String getMD5ForFile(String filename) throws IOException, NoSuchAlgorithmException {
        InputStream fis =  new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        byte[] contentByte = complete.digest();
        String result = "";

        for (int i=0; i < contentByte.length; i++) {
            result += Integer.toString( ( contentByte[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
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
