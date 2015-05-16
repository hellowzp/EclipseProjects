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
	//����first����
	private String first;
	//����last����
	private String last;

	//�޲����Ĺ�����
	public Name()
	{
	}
	//��ʼ��first��last���ԵĹ�����
	public Name(String first , String last)
	{
		this.first = first;
		this.last = last;
	}

	//first���Ե�setter��getter����
	public void setFirst(String first)
	{
		this.first = first;
	}
	public String getFirst()
	{
		return this.first;
	}

	//last���Ե�setter��getter����
	public void setLast(String last)
	{
		this.last = last;
	}
	public String getLast()
	{
		return this.last;
	}
}