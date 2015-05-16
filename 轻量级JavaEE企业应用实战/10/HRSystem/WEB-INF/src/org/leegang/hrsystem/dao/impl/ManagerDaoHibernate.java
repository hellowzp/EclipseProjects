package org.leegang.hrsystem.dao.impl;

import java.util.*; 

import org.leegang.hrsystem.model.*;
import org.leegang.hrsystem.dao.base.*;
import org.leegang.hrsystem.dao.*;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class ManagerDaoHibernate extends YeekuHibernateDaoSupport
	implements ManagerDao
{
	/**
	 * ���ݱ�ʶ����������Managerʵ��
	 * @param id ��Ҫ���ص�Managerʵ���ı�ʶ����ֵ
	 * @return ָ����ʶ���Զ�Ӧ��Managerʵ��
	 */
	public Manager get(String dept)
	{
		return (Manager)getHibernateTemplate()
			.get(Manager.class , dept);
	}

	/**
	 * �־û�ָ����Managerʵ��
	 * @param manager ��Ҫ���־û���Managerʵ��
	 * @return Managerʵ�����־û���ı�ʶ����ֵ
	 */
	public String save(Manager manager)
	{
		return (String)getHibernateTemplate()
			.save(manager);
	}

	/**
	 * �޸�ָ����Managerʵ��
	 * @param manager ��Ҫ���޸ĵ�Managerʵ��
	 */
	public void update(Manager manager)
	{
		getHibernateTemplate()
			.update(manager);
	}

	/**
	 * ɾ��ָ����Managerʵ��
	 * @param manager ��Ҫ��ɾ����Managerʵ��
	 */
	public void delete(Manager manager)
	{
		getHibernateTemplate()
			.delete(manager);
	}

	/**
	 * ���ݱ�ʶ����ɾ��Managerʵ��
	 * @param id ��Ҫ��ɾ����Managerʵ���ı�ʶ����ֵ
	 */
	public void delete(String dept)
	{
		getHibernateTemplate()
			.delete(get(dept));
	}

	/**
	 * ��ѯȫ����Managerʵ��
	 * @return ���ݿ���ȫ����Managerʵ��
	 */
	public List<Manager> findAll()
	{
		return (List<Manager>)getHibernateTemplate()
			.find("from Manager");
	}

	/**
	 * �����û����������ѯ����
	 * @param name ������û���
	 * @param pass ���������
	 * @return �����û���������ľ���
	 */ 
	public List<Manager> findByNameAndPass(String name , String pass)
	{
		return (List<Manager>)getHibernateTemplate()
			.find("from Manager where name = ? and pass = ?"
			, new String[]{name , pass});
	}

	/**
	 * �����û������Ҿ���
	 * @param name ���������
	 * @return ���ֶ�Ӧ�ľ���
	 */
	public Manager findByName(String name)
	{
		List<Manager> ml = (List<Manager>)getHibernateTemplate()
			.find("from Manager m where m.name=?" , name);
		if (ml != null && ml.size() > 0)
		{
			return ml.get(0);
		}
		return null;
	}
}
