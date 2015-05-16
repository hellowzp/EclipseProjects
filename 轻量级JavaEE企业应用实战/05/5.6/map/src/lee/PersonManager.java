package lee;

import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.*;

/**
 * @author  yeeku.H.lee kongyeeku@163.com
 * @version  1.0
 * <br>Copyright (C), 2005-2008, yeeku.H.Lee
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date: 
 */
public class PersonManager
{
	public static void main(String[] args)
	{
		PersonManager mgr = new PersonManager();
		mgr.createAndStorePerson();
		HibernateUtil.sessionFactory.close();
	}

	private void createAndStorePerson()
	{
		//打开线程安全的session对象
		Session session = HibernateUtil.currentSession();
		//打开事务
		Transaction tx = session.beginTransaction();
		//创建Person对象
		Person yeeku = new Person();
		//为Person对象设置属性
		yeeku.setAge(29);
		yeeku.setName("Yeeku.H.Lee");
		//创建Map集合
		Map m = new HashMap();
		m.put("语文" , 67f);
		m.put("英文" , 45f);
		//设置Map集合属性
		yeeku.setScores(m);
		session.save(yeeku);
		tx.commit();
		HibernateUtil.closeSession();
	}
}