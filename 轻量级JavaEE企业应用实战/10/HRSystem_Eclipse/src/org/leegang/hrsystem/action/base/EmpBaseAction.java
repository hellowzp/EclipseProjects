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
    protected EmpManager mgr;

    public void setEmpManager(EmpManager mgr)
    {
        this.mgr = mgr;
    }
}