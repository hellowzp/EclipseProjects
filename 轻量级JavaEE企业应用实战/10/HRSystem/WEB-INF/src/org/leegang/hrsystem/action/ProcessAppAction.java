package org.leegang.hrsystem.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;

import org.leegang.hrsystem.service.EmpManager;
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
public class ProcessAppAction extends EmpBaseAction
{
	//�����춯�ĳ���ID
	private int attId;
	//ϣ���ı䵽��������
	private int typeId;
	//��������
	private String reason;
	//������
	private String tip;
	//addId���Ե�setter��getter����
	public void setAttId(int attId)
	{
		this.attId = attId;
	}
	public int getAttId()
	{
		return this.attId;
	}

	//typeId���Ե�setter��getter����
	public void setTypeId(int typeId)
	{
		this.typeId = typeId;
	}
	public int getTypeId()
	{
		return this.typeId;
	}

	//reason���Ե�setter��getter����
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public String getReason()
	{
		return this.reason;
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
		//�����춯����
		boolean result = mgr.addApplication(attId , 
			typeId , reason);
		//�������ɹ�
		if (result)
		{
			setTip("���Ѿ�����ɹ����ȴ���������");
		}
		else
		{
			setTip("����ʧ�ܣ���ע�ⲻҪ�ظ�����");
		}
		return SUCCESS;
	}
}