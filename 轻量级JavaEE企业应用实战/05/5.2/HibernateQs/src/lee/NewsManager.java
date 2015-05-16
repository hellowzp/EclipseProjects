package lee;

import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Description:
 * <br/>Copyright (C), 2008-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class NewsManager
{
	public static void main(String[] args) throws Exception
	{
		//ʵ����Configuration�����д���Ĭ�ϼ���hibernate.cfg.xml�ļ�
		Configuration conf = new Configuration().configure();
		//��Configuration����SessionFactory
		SessionFactory sf = conf.buildSessionFactory();
		//ʵ����Session
		Session sess = sf.openSession();
		//��ʼ����
		Transaction tx = sess.beginTransaction();
		//������Ϣʵ��
		News n = new News();
		//������Ϣ�������Ϣ����
		n.setTitle("���Java���˳�����");
		n.setContent("���Java���˳����ˣ�"
			+ "��վ��ַhttp://www.crazyjava.org");
		//������Ϣ
		sess.save(n);
		//�ύ����
		tx.commit();
		//�ر�Session
		sess.close();
	}
}
