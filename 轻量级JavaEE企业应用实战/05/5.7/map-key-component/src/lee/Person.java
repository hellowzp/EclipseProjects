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
public class Person
{
	//��ʶ����
	private int id;
	//��ͨ����age
	private int age;
	//�������name
	private Map<Name , Integer> nickPower
		= new HashMap<Name , Integer>();

	//�޲����Ĺ�����
	public Person()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public Person(int id , int age , 
		Map<Name , Integer> nickPower)
	{
		this.id = id;
		this.age = age;
		this.nickPower = nickPower;
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

	//age���Ե�setter��getter����
	public void setAge(int age)
	{
		this.age = age;
	}
	public int getAge()
	{
		return this.age;
	}

	//nickPower���Ե�setter��getter����
	public void setNickPower(Map<Name , Integer> nickPower)
	{
		this.nickPower = nickPower;
	}
	public Map<Name , Integer> getNickPower()
	{
		return this.nickPower;
	}
}