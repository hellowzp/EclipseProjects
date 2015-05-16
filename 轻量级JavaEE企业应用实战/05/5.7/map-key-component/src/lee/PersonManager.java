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
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		//创建Person对象
		Person yeeku = new Person();
		//为Person对象设置属性
		yeeku.setAge(29);
		//创建一个Map集合
		Map<Name , Integer> nickPower = 
			new HashMap<Name , Integer>();
		//向Map集合里放入Name对象
		nickPower.put(new Name("Wawa" , "Wawa") , 89);
		nickPower.put(new Name("Yeeku" , "Lee") , 96);
		//设置Map集合属性
		yeeku.setNickPower(nickPower);
		session.save(yeeku);
		tx.commit();
		HibernateUtil.closeSession();
	}
}