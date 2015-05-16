package org.leegang.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;

import org.leegang.hrsystem.service.EmpManager;
import org.leegang.hrsystem.exception.HrException;
import org.leegang.hrsystem.action.base.EmpBaseAction;
import static org.leegang.hrsystem.service.EmpManager.*;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, leegang.hrsystem.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  leegang.hrsystem.H.Lee kongleegang.hrsystem@163.com
 * @version  1.0
 */
public class LoginAction extends EmpBaseAction
{
	//����һ��������ΪԱ����¼�ɹ���Result��
	private final String EMP_RESULT = "emp";
	//����һ��������Ϊ�����¼�ɹ���Result��
	private final String MGR_RESULT = "mgr";
	//��¼���û���
	private String username;
	//��¼������
	private String password;
	//��¼����֤��
	private String vercode;
	//�����¼�����ʾ��Ϣ
	private String tip;
	//username���Ե�setter��getter����
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}

	//password���Ե�setter��getter����
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return this.password;
	}

	//vercode���Ե�setter��getter����
	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}
	public String getVercode()
	{
		return this.vercode;
	}

	//tip���Ե�setter��getter����
	public void setTip(String tip)
	{
		this.tip = tip;
	}
	public String getTip()
	{
		return this.tip;
	}
	//�����û�����
	public String execute()
		throws Exception
	{
		//����ActionContextʵ��
		ActionContext ctx = ActionContext.getContext();
		//��ȡHttpSession�е�rand����
		String ver2 = (String)ctx.getSession().get("rand");
		if (vercode.equals(ver2))
		{
			//����ҵ���߼������������¼����
			int result = mgr.validLogin(getUsername() , 
				getPassword());
			//��¼���Ϊ��ͨԱ��
			if (result == LOGIN_EMP)
			{
				ctx.getSession().put(WebConstant.USER 
					, username);
				ctx.getSession().put(WebConstant.LEVEL
					, WebConstant.EMP_LEVEL);
				setTip("���Ѿ��ɹ���½ϵͳ");
				return EMP_RESULT;
			}
			//��¼���Ϊ����
			else if (result == LOGIN_MGR)
			{
				ctx.getSession().put(WebConstant.USER 
					, username);
				ctx.getSession().put(WebConstant.LEVEL
					, WebConstant.MGR_LEVEL);
				setTip("���Ѿ��ɹ���½ϵͳ");
				return MGR_RESULT;
			}
			//�û��������벻ƥ��
			else
			{
				setTip("�û���/���벻ƥ��");
				return ERROR;
			}
		}
		//��֤�벻ƥ��
		else
		{
			setTip("��֤�벻ƥ��,����������");
			return ERROR;
		}
	}
}