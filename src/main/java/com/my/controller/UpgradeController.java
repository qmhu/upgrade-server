package com.my.controller;

import com.my.service.UpgradeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles requests for the Upgrade service.
 */
@Api(value = "/upgrade")
@Controller
public class UpgradeController {

    private static final Logger logger = LoggerFactory.getLogger(UpgradeController.class);

    @Autowired
    private UpgradeService upgradeService;

    @RequestMapping(value = "/upgrade/info", method = RequestMethod.GET)
    public
    @ResponseBody
    void getUpgradeInfo(@RequestParam("name") String name) {
        logger.info("Start getUpgradeMeta. name=" + name);
    }

    @RequestMapping(value = "/release/download")
    public ModelAndView download(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam("name") String name) throws Exception {

        String storeName = "房地.txt";
        String contentType = "application/octet-stream";
        ReleaseController.download(request, response, storeName, contentType);
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
