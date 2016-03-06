package com.my.model;

import java.io.Serializable;

public class ReleaseFile implements Serializable {

    private static final long serialVersionUID = -2568613177795333322L;

    private String name;
    private String src;
    private String dest;
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
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
    

}
