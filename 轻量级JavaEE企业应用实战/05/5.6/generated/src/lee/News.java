package lee;

import java.util.Date;

/**
 * Description:
 * <br/>Copyright (C), 2008-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class News
{
	//消息类的标识属性
	private int id;
	//消息标题
	private String title;
	//消息内容
	private String content;
	//消息的全部内容，该属性由系统触发器提供
	private String fullContent;

	//无参数的构造器
	public News()
	{
	}
	//初始化全部属性的构造器
	public News(int id , String title , String content , String fullContent)
	{
		this.id = id;
		this.title = title;
		this.content = content;
		this.fullContent = fullContent;
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

	//title属性的setter和getter方法
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return this.title;
	}

	//content属性的setter和getter方法
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContent()
	{
		return this.content;
	}

	//fullContent属性的setter和getter方法
	public void setFullContent(String fullContent)
	{
		this.fullContent = fullContent;
	}
	public String getFullContent()
	{
		return this.fullContent;
	}

}