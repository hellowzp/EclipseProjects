package lee;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.tool.hbm2ddl.*;

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
//		//��Configuration����SessionFactory
//		SessionFactory sf = conf.buildSessionFactory();
		//����SchemaExport����
		SchemaExport se = new SchemaExport(conf);
		//���������ʽ���õ�SQL�ű�
		se.setFormat(true)
			//���ñ���SQL�ű����ļ���
			.setOutputFile("new.sql")
			//���SQL�ű�����ִ��SQL�ű�
			.create(true, true);
	}
}
