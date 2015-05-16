package lee;

import javax.persistence.*;
/**
 * Description:
 * <br/>Copyright (C), 2008-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
@Entity
public class Person
{
	//标识属性
	private int id;
	//普通属性age
	private int age;
	//组件属性name
	@Embedded
	@AttributeOverrides({
	@AttributeOverride(name="first", column = @Column(name="first_name")),
	@AttributeOverride(name="last", column = @Column(name="last_name"))
	})
	private Name name;

	//无参数的构造器
	public Person()
	{
	}
	//初始化全部属性的构造器
	public Person(int id , 
		int age , Name name)
	{
		this.id = id;
		this.age = age;
		this.name = name;
	}

	//id属性的setter和getter方法
	public void setId(int id)
	{
		this.id = id;
	}
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId()
	{
		return this.id;
	}

	//age属性的setter和getter方法
	public void setAge(int age)
	{
		this.age = age;
	}
	@Basic @Column(name = "person_age", nullable = false)
	public int getAge()
	{
		return this.age;
	}

	//name属性的setter和getter方法
	public void setName(Name name)
	{
		this.name = name;
	}
	public Name getName()
	{
		return this.name;
	}
}