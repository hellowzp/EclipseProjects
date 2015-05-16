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
	//新增员工的名字
	private String empName;
	//新增员工的密码
	private String empPass;
	//新增员工的工资
	private double empSal;
	//封装提示信息的tip属性
	private String tip;
	//empName属性的setter和getter方法
	public void setEmpName(String empName)
	{
		this.empName = empName;
	}
	public String getEmpName()
	{
		return this.empName;
	}

	//empPass属性的setter和getter方法
	public void setEmpPass(String empPass)
	{
		this.empPass = empPass;
	}
	public String getEmpPass()
	{
		return this.empPass;
	}

	//empSal属性的setter和getter方法
	public void setEmpSal(double empSal)
	{
		this.empSal = empSal;
	}
	public double getEmpSal()
	{
		return this.empSal;
	}

	//tip属性的setter和getter方法
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
		//创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		//获取HttpSession中的user属性
		String mgrName = (String)ctx.getSession()
			.get(WebConstant.USER);
		//添加新用户
		mgr.addEmp(empName , empPass , empSal , mgrName);
		setTip("新增员工成功");
		return SUCCESS;
    }

}