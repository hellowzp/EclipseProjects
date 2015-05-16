package org.leegang.hrsystem.action.authority;

import com.opensymphony.xwork2.*;

import com.opensymphony.xwork2.interceptor.*;

import org.leegang.hrsystem.action.WebConstant;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, leegang.hrsystem.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  leegang.hrsystem.H.Lee kongleegang.hrsystem@163.com
 * @version  1.0
 */
public class EmpAuthorityInterceptor
	extends AbstractInterceptor
{
	public String intercept(ActionInvocation invocation) 
		throws Exception
	{
		//创建ActionContext实例
		ActionContext ctx = ActionContext.getContext();
		//获取HttpSession中的level属性
		String level = (String)ctx.getSession()
			.get(WebConstant.LEVEL);
		//如果level不为null，且level为emp或mgr
		if (level != null 
			&& (level.equals(WebConstant.EMP_LEVEL) 
			|| level.equals(WebConstant.MGR_LEVEL)))
		{
			return invocation.invoke();
		}
		else
		{
			return Action.LOGIN;
		}
	}
}