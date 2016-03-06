package com.my.model;

import java.io.Serializable;
import java.util.List;

public class ReleaseInfo implements Serializable {

    private static final long serialVersionUID = -7568613177795333322L;

    private String version;
    private List<ReleaseFile> files;
    private List<ReleaseModule> modules;

    public List<ReleaseModule> getModules() {
        return modules;
    }

    public void setModules(List<ReleaseModule> modules) {
        this.modules = modules;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<ReleaseFile> getFiles() {
        return files;
    }

    public void setFiles(List<ReleaseFile> files) {
        this.files = files;
    }

}
