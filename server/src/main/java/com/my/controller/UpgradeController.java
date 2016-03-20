package com.my.controller;

import com.my.model.FileUploadForm;
import com.my.model.UpgradeInfo;
import com.my.service.UpgradeService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Handles requests for the Upgrade service.
 */
@Controller
public class UpgradeController {

    private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);

    @Autowired
    private UpgradeService upgradeService;

    @RequestMapping(value = "/upgrade/info", method = RequestMethod.GET)
    public @ResponseBody UpgradeInfo getUpgradeInfo(@RequestParam("clientVersion") String clientVersion,
                                                    @RequestParam("module") String module) {
        logger.info("Start getUpgradeInfo. clientVersion=" + clientVersion + " module=" + module);
        return upgradeService.getUpgradeInfo(clientVersion, module);
    }

    @RequestMapping(value = "/upgrade/download")
    public ModelAndView download(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("name") String name) throws Exception {
        logger.info("Start download. name=" + name);
        upgradeService.download(response, name);
        return null;
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
