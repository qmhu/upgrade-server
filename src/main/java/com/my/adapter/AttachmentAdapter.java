/**
 * 
 */
package com.eshop.adapter;

import org.springframework.stereotype.Component;

import com.eshop.model.Attachment;

/**
 * @author I311862
 *
 */
@Component
public class AttachmentAdapter {

    public static final String UPLOAD_BASE_PATH = "/occ-eshop/wp-content/uploads/";

    public void adaptAttachment(Attachment attachment) {
        attachment.setFilename(attachment.getFilename().substring(attachment.getFilename().lastIndexOf("/") + 1));
        attachment.setUrl(UPLOAD_BASE_PATH + attachment.getUrl());
    }

}
