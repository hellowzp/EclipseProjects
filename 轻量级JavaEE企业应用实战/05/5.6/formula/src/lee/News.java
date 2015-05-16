package lee;

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
	//��Ϣȫ�����ݣ���ϵͳ���ݹ�ʽ����
	private String fullContent;
	//������
	public News()
	{
	}

	public News(int id , String title , String content 
		, String fullContent)
	{
		this.id = id;
		this.title = title;
		this.content = content;
		this.fullContent = fullContent;

	}

	public void setId(int id)
	{
		this.id = id;
	}
	public int getId()
	{
		return this.id;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return this.title;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	public String getContent()
	{
		return this.content;
	}

	public void setFullContent(String fullContent)
	{
		this.fullContent = fullContent;
	}
	public String getFullContent()
	{
		return this.fullContent;
	}

}