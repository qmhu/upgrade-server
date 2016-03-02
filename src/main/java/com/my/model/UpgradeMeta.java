package com.my.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class UpgradeMeta implements Serializable {

    private static final long serialVersionUID = -7568613177795333322L;

    private String target_version;
    private Map<String, String> files;
    
	public String getTarget_version() {
		return target_version;
	}
	public void setTarget_version(String target_version) {
		this.target_version = target_version;
	}
	public Map<String, String> getFiles() {
		return files;
	}
	public void setFiles(Map<String, String> files) {
		this.files = files;
	}

}
