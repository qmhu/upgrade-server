package com.my.model;


import java.io.Serializable;
import java.util.List;

public class ReleasePlan implements Serializable {

    private static final long serialVersionUID = -7568613177795333522L;

    private String client_version;
    private List<String> download_files;
    
	public String getClient_version() {
		return client_version;
	}
	public void setClient_version(String client_version) {
		this.client_version = client_version;
	}
	public List<String> getDownload_files() {
		return download_files;
	}
	public void setDownload_files(List<String> download_files) {
		this.download_files = download_files;
	}

}
