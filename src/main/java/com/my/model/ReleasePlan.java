package com.my.model;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReleasePlan implements Serializable {

    private static final long serialVersionUID = -7568613177795333322L;

    private String client_version;
    private List<String> include_files;
    
	public String getClient_version() {
		return client_version;
	}
	public void setClient_version(String client_version) {
		this.client_version = client_version;
	}
	public List<String> getInclude_files() {
		return include_files;
	}
	public void setInclude_files(List<String> include_files) {
		this.include_files = include_files;
	}

}
