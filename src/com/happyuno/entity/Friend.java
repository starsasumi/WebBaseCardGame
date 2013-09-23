package com.happyuno.entity;

/**
 * Friend entity. @author MyEclipse Persistence Tools
 */

public class Friend implements java.io.Serializable {

	// Fields

	private Integer fid;
	private String id;
	private String frid;

	// Constructors

	/** default constructor */
	public Friend() {
	}

	/** full constructor */
	public Friend(String id, String frid) {
		this.id = id;
		this.frid = frid;
	}

	// Property accessors

	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrid() {
		return this.frid;
	}

	public void setFrid(String frid) {
		this.frid = frid;
	}

}