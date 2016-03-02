package com.my.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReleaseInfo implements Serializable {

    private static final long serialVersionUID = -7568613177795333322L;

    private List<ReleaseFile> files;
    private List<ReleasePlan> plans;
    
	public List<ReleaseFile> getFiles() {
		return files;
	}
	public void setFiles(List<ReleaseFile> files) {
		this.files = files;
	}
	public List<ReleasePlan> getPlans() {
		return plans;
	}
	public void setPlans(List<ReleasePlan> plans) {
		this.plans = plans;
	}

}
