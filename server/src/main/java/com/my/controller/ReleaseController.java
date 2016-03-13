package com.my.controller;

import com.my.model.FileUploadForm;
import com.my.model.ReleaseInfo;
import com.my.service.ReleaseService;
import io.swagger.annotations.Api;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public
    @ResponseBody
    ReleaseInfo getReleaseInfo() throws IOException, ParseException {
        logger.info("Start getReleaseInfo.");
        return releaseService.getReleaseInfo();
    }

    @RequestMapping(value = "release", method = RequestMethod.GET)
    public String displayForm() {
        return "FileUpload";
    }

    @RequestMapping(value = "/release/upload", method = RequestMethod.POST)
    public String upload(
            @ModelAttribute("uploadForm") FileUploadForm uploadForm,
            Model map) {
        logger.info("Submit form");
        List<MultipartFile> files = uploadForm.getFiles();

        List<String> fileNames = new ArrayList<String>();

        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                //Handle file content - multipartFile.getInputStream()

            }
        }

        map.addAttribute("files", fileNames);
        return "FileUploadSuccess";
    }

}
