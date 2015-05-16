package org.leegang.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;

import org.leegang.hrsystem.service.EmpManager;
import org.leegang.hrsystem.exception.HrException;
import static org.leegang.hrsystem.service.EmpManager.*;

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
public class ProcessPunchAction extends ActionSupport
{
	//��Action��������ҵ���߼����
	private EmpManager empMgr;
	//��װ��������tip����
	private String tip;
	//����ע��ҵ���߼������setter����
	public void setEmpManager(EmpManager empMgr)
	{
		this.empMgr = empMgr;
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
	//�����ϰ�򿨵ķ���
	public String come()
		throws Exception
	{
		return process(true);
	}
	public String leave()
		throws Exception
	{
		return process(false);
	}
	private String process(boolean isCome)
		throws Exception
	{
		//����ActionContextʵ��
		ActionContext ctx = ActionContext.getContext();
		//��ȡHttpSession�е�user����
		String user = (String)ctx.getSession()
			.get(WebConstant.USER);
		System.out.println("-----��----" + user);
		String dutyDay = new java.sql.Date(
			System.currentTimeMillis()).toString();
		//����ҵ���߼��������������
		int result = empMgr.punch(user ,dutyDay , isCome);
		switch(result)
		{
			case PUNCH_FAIL:
				setTip("��ʧ��");
				break;
			case PUNCHED:
				setTip("���Ѿ�������ˣ���Ҫ�ظ���");
				break;
			case PUNCH_SUCC:
				setTip("�򿨳ɹ�");
				break;
		}
		return SUCCESS;
	}
}