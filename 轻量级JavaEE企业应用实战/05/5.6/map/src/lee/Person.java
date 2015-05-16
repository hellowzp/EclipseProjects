package lee;

import java.util.HashMap;
import java.util.Map;
/**
 * @author  yeeku.H.lee kongyeeku@163.com
 * @version  1.0
 * <br>Copyright (C), 2005-2008, yeeku.H.Lee
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date: 
 */
public class Person
{
	//��ʶ����
	private int id;
	//��ͨ����name
	private String name;
	//��ͨ����age
	private int age;
	//�������ԣ������ö�������Ŀ��Գɼ�
	private Map scores = new HashMap();

	//�޲����Ĺ�����
	public Person()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public Person(int id , String name , int age , Map scores)
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.scores = scores;
	}

	//id���Ե�setter��getter����
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}

	//name���Ե�setter��getter����
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	//age���Ե�setter��getter����
	public void setAge(int age)
	{
		this.age = age;
	}
	public int getAge()
	{
		return this.age;
	}

	//scores���Ե�setter��getter����
	public void setScores(Map scores)
	{
		this.scores = scores;
	}
	public Map getScores()
	{
		return this.scores;
	}
}