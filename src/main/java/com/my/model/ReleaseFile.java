package com.my.model;

import java.awt.List;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ReleaseFile implements Serializable {

    private static final long serialVersionUID = -7568613177795333322L;

    private String name;
    private String src;
    private String desc;
    private String type;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    

}
