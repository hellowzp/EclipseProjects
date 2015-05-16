package org.leegang.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;

import org.leegang.hrsystem.exception.HrException;
import org.leegang.hrsystem.action.base.EmpBaseAction;
import org.leegang.hrsystem.vo.*;

import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, leegang.hrsystem.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  leegang.hrsystem.H.Lee kongleegang.hrsystem@163.com
 * @version  1.0
 */
public class AppChangeAction extends EmpBaseAction
{
	//��װ�����춯���б�
	private List types;
	//types���Ե�setter��getter����
	public void setTypes(List types)
	{
		this.types = types;
	}
	public List getTypes()
	{
		return this.types;
	}
	//�����û�����
	public String execute()
		throws Exception
	{
		setTypes(mgr.getAllType());
		return SUCCESS;
	}
}