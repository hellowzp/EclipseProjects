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
	//定义一个常量作为员工登录成功的Result名
	private final String EMP_RESULT = "emp";
	//定义一个常量作为经理登录成功的Result名
	private final String MGR_RESULT = "mgr";
	//登录的用户名
	private String username;
	//登录的密码
	private String password;
	//登录的验证码
	private String vercode;
	//处理登录后的提示信息
	private String tip;
	//username属性的setter和getter方法
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getUsername()
	{
		return this.username;
	}

	//password属性的setter和getter方法
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getPassword()
	{
		return this.password;
	}

	//vercode属性的setter和getter方法
	public void setVercode(String vercode)
	{
		this.vercode = vercode;
	}
	public String getVercode()
	{
		return this.vercode;
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
	//处理用户请求
	public String execute()
		throws Exception
	{
		//创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		//获取HttpSession中的rand属性
		String ver2 = (String)ctx.getSession().get("rand");
		if (vercode.equals(ver2))
		{
			//调用业务逻辑方法来处理登录请求
			int result = mgr.validLogin(getUsername() , 
				getPassword());
			//登录结果为普通员工
			if (result == LOGIN_EMP)
			{
				ctx.getSession().put(WebConstant.USER 
					, username);
				ctx.getSession().put(WebConstant.LEVEL
					, WebConstant.EMP_LEVEL);
				setTip("您已经成功登陆系统");
				return EMP_RESULT;
			}
			//登录结果为经理
			else if (result == LOGIN_MGR)
			{
				ctx.getSession().put(WebConstant.USER 
					, username);
				ctx.getSession().put(WebConstant.LEVEL
					, WebConstant.MGR_LEVEL);
				setTip("您已经成功登陆系统");
				return MGR_RESULT;
			}
			//用户名和密码不匹配
			else
			{
				setTip("用户名/密码不匹配");
				return ERROR;
			}
		}
		//验证码不匹配
		else
		{
			setTip("验证码不匹配,请重新输入");
			return ERROR;
		}
	}
}