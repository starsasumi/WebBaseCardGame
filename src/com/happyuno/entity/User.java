package com.happyuno.entity;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String password;
	private String adm;
	private Integer money;
	private Integer grade;
	private Integer exp;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String id) {
		this.id = id;
	}

	/** full constructor */
	public User(String id, String name, String password, String adm,
			Integer money, Integer grade, Integer exp) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.adm = adm;
		this.money = money;
		this.grade = grade;
		this.exp = exp;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdm() {
		return this.adm;
	}

	public void setAdm(String adm) {
		this.adm = adm;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getGrade() {
		return this.grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getExp() {
		return this.exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

}