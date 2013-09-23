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