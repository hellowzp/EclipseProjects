package org.leegang.hrsystem.action.base;

import com.opensymphony.xwork2.ActionSupport;

import org.leegang.hrsystem.service.MgrManager;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */

public class MgrBaseAction extends ActionSupport
{
    protected MgrManager mgr;

    public void setMgrManager(MgrManager mgr)
    {
        this.mgr = mgr;
    }
}