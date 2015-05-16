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
	//标识属性
	private int id;
	//普通属性name
	private String name;
	//普通属性age
	private int age;
	//集合属性，保留该对象关联的考试成绩
	private Map scores = new HashMap();

	//无参数的构造器
	public Person()
	{
	}
	//初始化全部属性的构造器
	public Person(int id , String name , int age , Map scores)
	{
		this.id = id;
		this.name = name;
		this.age = age;
		this.scores = scores;
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

	//scores属性的setter和getter方法
	public void setScores(Map scores)
	{
		this.scores = scores;
	}
	public Map getScores()
	{
		return this.scores;
	}
}