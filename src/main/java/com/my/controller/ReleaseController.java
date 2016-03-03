package com.my.controller;

import com.my.service.ReleaseService;
import io.swagger.annotations.Api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.model.PackageMeta;
import com.my.model.ReleaseInfo;
import com.my.service.UpgradeService;

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
    public @ResponseBody ReleaseInfo getReleaseInfo() {
        logger.info("Start getReleaseInfo.");
        return releaseService.getReleaseInfo();
    }

   /* @RequestMapping(value = "/attachment/create", method = RequestMethod.POST)
    public @ResponseBody Attachment createAttachment(@RequestParam("name") String name,
            @RequestParam("type") String type, @RequestParam("size") int size,
            @RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
        logger.info("Start createAttachment.");
        Attachment attachment = attachmentService.createAttachment(file, name, type, size, description);
        return attachment;
    }*/

}
