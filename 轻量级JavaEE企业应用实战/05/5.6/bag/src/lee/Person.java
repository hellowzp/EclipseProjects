package lee;

import java.util.List;
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
	//标识属性
	private int id;
	//普通属性name
	private String name;
	//普通属性age
	private int age;
	//集合属性，保留该对象关联的学校
	private Collection<String> schools =
		new ArrayList<String>();

	//无参数的构造器
	public Person()
	{
	}
	//初始化全部属性的构造器
	public Person(int id , String name , 
		int age , Collection<String> schools)
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.schools = schools;
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

	//name属性的setter和getter方法
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
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

	//schools属性的setter和getter方法
	public void setSchools(Collection<String> schools)
	{
		this.schools = schools;
	}
	public Collection<String> getSchools()
	{
		return this.schools;
	}
}