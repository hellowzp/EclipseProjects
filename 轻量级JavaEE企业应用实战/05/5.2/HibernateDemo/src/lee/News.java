/**
 * 
 */
package lee;

/**
 * @author yeeku
 *
 */
public class News
{
	//��Ϣ��ı�ʶ����
	private int id;
	//��Ϣ����
	private String title;
	//��Ϣ����
	private String content;
	//������
	public News()
	{
	}
	//��ʶ���Ե�setter��getter����
	public void setId(int id) 
	{
		this.id = id; 
	}
	public int getId()
	{
		return (this.id); 
	}
	//��Ϣ�����setter������getter����
	public void setTitle(String title) 
	{
		this.title = title; 
	}
	public String getTitle() 
	{
		return (this.title); 
	}

	//��Ϣ���ݵ�setter������getter����
	public void setContent(String content)
	{
		this.content = content; 
	}
	public String getContent()
	{
		return (this.content); 
	}
}