package lee;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
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
	//创建并保存Person对象
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
		//创建List集合
		List schools = new ArrayList();
		schools.add("小学");
		schools.add("中学");
		//设置List集合属性
		yeeku.setSchools(schools);
		session.save(yeeku);
		tx.commit();
		HibernateUtil.closeSession();
	}
}