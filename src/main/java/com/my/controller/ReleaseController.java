package com.my.controller;

import com.my.service.ReleaseService;
import io.swagger.annotations.Api;

import java.io.*;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.my.model.PackageMeta;
import com.my.model.ReleaseInfo;
import com.my.service.UpgradeService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles requests for the Release service.
 */
@Api(value = "/release")
@Controller
public class ReleaseController {

    private static final Logger logger = LoggerFactory.getLogger(ReleaseController.class);

    @Autowired
    private ReleaseService releaseService;
    
    @RequestMapping(value = "/release/info", method = RequestMethod.GET)
    public @ResponseBody ReleaseInfo getReleaseInfo() throws IOException, ParseException {
        logger.info("Start getReleaseInfo.");
        return releaseService.getReleaseInfo();
    }

    @RequestMapping(value = "/release/create", method = RequestMethod.POST)
    public @ResponseBody ReleaseInfo createRelease(@RequestBody MultipartFile file) {
        logger.info("Start createRelease.");
        return releaseService.createRelease(file);
    }

    @RequestMapping(value = "/release/download")
    public ModelAndView download(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("name") String name) throws Exception {

//		String storeName = "Spring3.xAPI_zh.chm";
        String storeName="房地.txt";
        String contentType = "application/octet-stream";
        ReleaseController.download(request, response, storeName, contentType);
        return null;
    }

    //文件下载 主要方法
    public static void download(HttpServletRequest request,
                                HttpServletResponse response, String storeName, String contentType
    ) throws Exception {
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //获取项目根目录
        String ctxPath = request.getSession().getServletContext()
                .getRealPath("");
        //获取下载文件露肩
        String downLoadPath = "./meta.json";
        //获取文件的长度
        long fileLength = new File(downLoadPath).length();
        //设置文件输出类型
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename="
                + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
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
        //关闭流
        bis.close();
        bos.close();
    }



}
