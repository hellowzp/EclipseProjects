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
//		//����Person����
//		Person wawa = new Person();
//		//ΪPerson������������
//		wawa.setAge(21);
//		wawa.setName("Wawa");
//		//����Set����
//		SortedSet<String> s = new TreeSet<String>();
//		s.add("Wild Java Camp");
//		s.add("Sun SCJP");
//		//����Set��������
//		wawa.setTrainings(s);
//		session.save(wawa);

		Person p = (Person)session.get(Person.class , 1);
		p.getTrainings().add("CCNP");

		tx.commit();
		HibernateUtil.closeSession();
	}
}