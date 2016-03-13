package com.my.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/3/6.
 */
public class UpgradeInfo implements Serializable {

    private static final long serialVersionUID = -2568613777795233322L;

    private String version;
    private List<ReleaseFile> releaseFiles;

    public List<ReleaseFile> getReleaseFiles() {
        return releaseFiles;
    }

    public void setReleaseFiles(List<ReleaseFile> releaseFiles) {
        this.releaseFiles = releaseFiles;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
