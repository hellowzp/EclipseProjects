package org.leegang.hrsystem.action.base;

import com.opensymphony.xwork2.ActionSupport;

import org.leegang.hrsystem.service.EmpManager;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class EmpBaseAction extends ActionSupport
{
	//依赖的业务逻辑组件
    protected EmpManager mgr;
	//依赖注入业务逻辑组件所必须的setter方法
    public void setEmpManager(EmpManager mgr)
    {
        this.mgr = mgr;
    }
}