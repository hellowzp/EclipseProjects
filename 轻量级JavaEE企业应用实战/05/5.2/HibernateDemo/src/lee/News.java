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
	//消息类的标识属性
	private int id;
	//消息标题
	private String title;
	//消息内容
	private String content;
	//构造器
	public News()
	{
	}
	//标识属性的setter和getter方法
	public void setId(int id) 
	{
		this.id = id; 
	}
	public int getId()
	{
		return (this.id); 
	}
	//消息标题的setter方法和getter方法
	public void setTitle(String title) 
	{
		this.title = title; 
	}
	public String getTitle() 
	{
		return (this.title); 
	}

	//消息内容的setter方法和getter方法
	public void setContent(String content)
	{
		this.content = content; 
	}
	public String getContent()
	{
		return (this.content); 
	}
}