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
		Name n = new Name("Yeeku" , "Lee");
		Map power = new HashMap();
		power.put("����" , 96);
		power.put("�ǻ�" , 98);
		n.setPower(power);
		yeeku.setName(n);
		session.save(yeeku);
		tx.commit();
		HibernateUtil.closeSession();
	}
}