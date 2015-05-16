package org.leegang.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;

import org.leegang.hrsystem.exception.HrException;
import org.leegang.hrsystem.action.base.MgrBaseAction;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, leegang.hrsystem.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  leegang.hrsystem.H.Lee kongleegang.hrsystem@163.com
 * @version  1.0
 */
public class AddEmpAction extends MgrBaseAction
{
	//����Ա��������
	private String empName;
	//����Ա��������
	private String empPass;
	//����Ա���Ĺ���
	private double empSal;
	//��װ��ʾ��Ϣ��tip����
	private String tip;
	//empName���Ե�setter��getter����
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getEmpName()
	{
		return this.empName;
	}

	//empPass���Ե�setter��getter����
	public void setEmpPass(String empPass)
	{
		this.empPass = empPass;
	}
	public String getEmpPass()
	{
		return this.empPass;
	}

	//empSal���Ե�setter��getter����
	public void setEmpSal(double empSal)
	{
		this.empSal = empSal;
	}
	public double getEmpSal()
	{
		return this.empSal;
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

    public String execute()
        throws Exception
    {
		//����ActionContextʵ��
		ActionContext ctx = ActionContext.getContext();
		//��ȡHttpSession�е�user����
		String mgrName = (String)ctx.getSession()
			.get(WebConstant.USER);
		//������û�
		mgr.addEmp(empName , empPass , empSal , mgrName);
		setTip("����Ա���ɹ�");
		return SUCCESS;
    }

}