package com.my.model;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/3/10.
 */
public class FileUploadForm {
    private List<MultipartFile> files = null;

    public FileUploadForm(){}
    public FileUploadForm(List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
