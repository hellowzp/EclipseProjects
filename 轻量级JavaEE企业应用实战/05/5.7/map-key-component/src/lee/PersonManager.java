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
		//����Person����
		Person yeeku = new Person();
		//ΪPerson������������
		yeeku.setAge(29);
		//����һ��Map����
		Map<Name , Integer> nickPower = 
			new HashMap<Name , Integer>();
		//��Map���������Name����
		nickPower.put(new Name("Wawa" , "Wawa") , 89);
		nickPower.put(new Name("Yeeku" , "Lee") , 96);
		//����Map��������
		yeeku.setNickPower(nickPower);
		session.save(yeeku);
		tx.commit();
		HibernateUtil.closeSession();
	}
}