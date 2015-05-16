package lee;

import java.util.*;
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
	//�������ԣ������ö���������ѵ
	private SortedSet<String> trainings
		= new TreeSet<String>();

	//�޲����Ĺ�����
	public Person()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public Person(int id , String name , int age , SortedSet<String> trainings)
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.trainings = trainings;
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

	//trainings���Ե�setter��getter����
	public void setTrainings(SortedSet<String> trainings)
	{
		this.trainings = trainings;
	}
	public SortedSet<String> getTrainings()
	{
		return this.trainings;
	}
}