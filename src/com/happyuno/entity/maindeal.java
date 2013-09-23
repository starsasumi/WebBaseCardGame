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

import java.text.ParseException;
import java.util.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
public class maindeal {
	private Session session;
	private Transaction tr;
public maindeal(){
	session=HibernateSessionFactory.getSession();
}
@SuppressWarnings("unchecked")
/*
 * ��ѯ���������ؽ���
 */
public List query(String hql){
	tr=session.beginTransaction();
	Query query=session.createQuery(hql);
	tr.commit();
	return query.list();
	
}
/*
 * ɾ�������ɾ��Ŀ�����
 */
public void delete(Object o){
	tr=session.beginTransaction();
	session.delete(o);
	tr.commit();
	
}
/*
 * �������������Ŀ�����
 */
public void saveorupdate(Object o){
	tr=session.beginTransaction();
	session.saveOrUpdate(o);
	tr.commit();
}
public static void main(String args[]) throws ParseException{
	maindeal md=new maindeal();
	//User u=new User();
	Friend u=new Friend();
	u.setId("14444");
	u.setFrid("11132");
	//u.setName("jack");
	//u.setPassword("123456");
	md.saveorupdate(u);
}
}