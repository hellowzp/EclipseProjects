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
	//标识属性
	private int id;
	//普通属性age
	private int age;
	//组件属性name
	private Map<Name , Integer> nickPower
		= new HashMap<Name , Integer>();

	//无参数的构造器
	public Person()
	{
	}
	//初始化全部属性的构造器
	public Person(int id , int age , 
		Map<Name , Integer> nickPower)
	{
		this.id = id;
		this.age = age;
		this.nickPower = nickPower;
	}

	//id属性的setter和getter方法
	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}

	//age属性的setter和getter方法
	public void setAge(int age)
	{
		this.age = age;
	}
	public int getAge()
	{
		return this.age;
	}

	//nickPower属性的setter和getter方法
	public void setNickPower(Map<Name , Integer> nickPower)
	{
		this.nickPower = nickPower;
	}
	public Map<Name , Integer> getNickPower()
	{
		return this.nickPower;
	}
}