/*
 * Copyright 2013 Xiaoqiang Xv.
 *
 * This file is part of WebBaseCardGame.
 *
 * WebBaseCardGame is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * WebBaseCardGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with WebBaseCardGame.
 * 
 * If not, see <http://www.gnu.org/licenses/>.
 */

package com.happyuno.entity;

import java.util.Date;

/**
 * Usemeg entity. @author MyEclipse Persistence Tools
 */

public class Usemeg implements java.io.Serializable {

	// Fields

	private Integer mid;
	private String id;
	private Date time;
	private Integer uexp;
	private Integer umoney;

	// Constructors

	/** default constructor */
	public Usemeg() {
	}

	/** full constructor */
	public Usemeg(String id, Date time, Integer uexp, Integer umoney) {
		this.id = id;
		this.time = time;
		this.uexp = uexp;
		this.umoney = umoney;
	}

	// Property accessors

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getUexp() {
		return this.uexp;
	}

	public void setUexp(Integer uexp) {
		this.uexp = uexp;
	}

	public Integer getUmoney() {
		return this.umoney;
	}

	public void setUmoney(Integer umoney) {
		this.umoney = umoney;
	}

}