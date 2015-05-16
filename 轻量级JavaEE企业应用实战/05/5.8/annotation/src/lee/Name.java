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
@Embeddable
public class Name
{
	//定义first属性
	private String first;
	//定义last属性
	private String last;

	//无参数的构造器
	public Name()
	{
	}
	//初始化first、last属性的构造器
	public Name(String first , String last)
	{
		this.first = first;
		this.last = last;
	}

	//first属性的setter和getter方法
	public void setFirst(String first)
	{
		this.first = first;
	}
	public String getFirst()
	{
		return this.first;
	}

	//last属性的setter和getter方法
	public void setLast(String last)
	{
		this.last = last;
	}
	public String getLast()
	{
		return this.last;
	}
}