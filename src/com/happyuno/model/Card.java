/*
 * Copyright 2013 Xiaxing Shi.
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

package com.happyuno.model;

/**
 * Uno 一共 108 张牌，每张牌都有颜色（color）与标记（mark）两个属性。
 * 
 * 其中，color 可为 Red、Yellow、Green、Blue、Wild。
 * mark 可为数字 0~9，Skip、Reverse、Draw 2、Draw 4、Wild。
 * 
 * 注意，为保证 Card 类的工整，Wild 牌的 COLOR_WILD 与 MARK_WILD 任然是相互独立的属性。
 * @author starsasumi
 *
 */
public class Card {
	// Color
	public static final int COLOR_RED = 0;
	public static final int COLOR_YELLOW = 1;
	public static final int COLOR_GREEN = 2;
	public static final int COLOR_BLUE = 3;
	public static final int COLOR_WILD = 4;
	
	// Mark
	public static final int MARK_DRAW_TWO = 10;
	public static final int MARK_SKIP = 11;
	public static final int MARK_REVERSE = 12;
	public static final int MARK_WILD = 13;
	public static final int MARK_DRAW_FOUR = 14;
	
	private int id;		// id
	private int color;  // color
	private int mark;   // OrdinaryCard
	
/* * Getters & Setters * */
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}
	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}
	/**
	 * @param id the id to set
	 */
	protected void setId(int id) {
		this.id = id;
	}
	/**
	 * @param color the color to set
	 */
	protected void setColor(int color) {
		this.color = color;
	}
	/**
	 * @param mark the mark to set
	 */
	protected void setMark(int mark) {
		this.mark = mark;
	}

/* * Static Tool Methods, Controller is free to use* */
	public static boolean isNumberCard(Card card) {
		return false;
	}
	
	public static boolean isFunctionCard(Card card) {
		return false;
	}
	
	public static boolean isWildCard(Card card) {
		return false;
	}
	
}
