package lee;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
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

	private void createAndStorePerson()
	{
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
//		//创建Person对象
//		Person wawa = new Person();
//		//为Person对象设置属性
//		wawa.setAge(21);
//		wawa.setName("Wawa");
//		//创建Set集合
//		Set<String> s = new HashSet<String>();
//		s.add("Wild Java Camp");
//		s.add("Sun SCJP");
//		//设置Set集合属性
//		wawa.setTrainings(s);
//		session.save(wawa);

		Person p = (Person)session.get(Person.class , 1);
//		p.getTrainings().add("CCNP");

System.out.println(p.getTrainings());

		tx.commit();
		HibernateUtil.closeSession();
	}
}