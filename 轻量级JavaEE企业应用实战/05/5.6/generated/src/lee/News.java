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
	//��Ϣ��ı�ʶ����
	private int id;
	//��Ϣ����
	private String title;
	//��Ϣ����
	private String content;
	//��Ϣ��ȫ�����ݣ���������ϵͳ�������ṩ
	private String fullContent;

	//�޲����Ĺ�����
	public News()
	{
	}
	//��ʼ��ȫ�����ԵĹ�����
	public News(int id , String title , String content , String fullContent)
	{
		this.id = id;
		this.title = title;
		this.content = content;
		this.fullContent = fullContent;
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

	//title���Ե�setter��getter����
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return this.title;
	}

	//content���Ե�setter��getter����
	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContent()
	{
		return this.content;
	}

	//fullContent���Ե�setter��getter����
	public void setFullContent(String fullContent)
	{
		this.fullContent = fullContent;
	}
	public String getFullContent()
	{
		return this.fullContent;
	}

}