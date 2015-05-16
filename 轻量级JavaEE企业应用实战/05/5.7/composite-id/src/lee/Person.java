package lee;

import java.util.*;
/**
 * Description:
 * <br/>Copyright (C), 2008-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Person implements 
	java.io.Serializable
{
	//����first���ԣ���Ϊ��ʶ���Եĳ�Ա
	private String first;
	//����last���ԣ���Ϊ��ʶ���Եĳ�Ա
	private String last;
	//��ͨ����age
	private int age;

	//�޲����Ĺ�����
	public Person()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public Person(String first , String last , int age)
	{
		this.first = first;
		this.last = last;
		this.age = age;
	}

	//first���Ե�setter��getter����
	public void setFirst(String first)
	{
		this.first = first;
	}
	public String getFirst()
	{
		return this.first;
	}

	//last���Ե�setter��getter����
	public void setLast(String last)
	{
		this.last = last;
	}
	public String getLast()
	{
		return this.last;
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

	//��дequals����������first��last�����ж�
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj != null 
			&& obj.getClass() == Person.class)
		{
			Person target = (Person)obj;
			if (target.getFirst().equals(getFirst())
				&& target.getLast().equals(getLast()))
			{
				return true;
			}
		}
		return false;
	}
	//��дhashCode����������first��last����hashCodeֵ
	public int hashCode()
	{
		return getFirst().hashCode() * 7 
			+ getLast().hashCode();
	}
}