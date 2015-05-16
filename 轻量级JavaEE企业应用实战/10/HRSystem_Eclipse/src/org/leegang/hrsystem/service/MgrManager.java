package org.leegang.hrsystem.service;

import org.leegang.hrsystem.vo.*;
import org.leegang.hrsystem.model.*;
import org.leegang.hrsystem.exception.*;

import java.util.*;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public interface MgrManager
{
	/**
	 * 新增员工
	 * @param user 新增的员工名
	 * @param pass 员工的初始密码
	 * @param salary 员工的薪水
	 */
	void addEmp(String user , String pass , double salary ,String mgr)
		throws HrException;


	/**
	 * 根据经理返回所有的部门上个月工资
	 * @param mgr 新增的员工名
	 * @return 部门上个月工资
	 */
	List<SalaryBean> getSalaryByMgr(String mgr)throws HrException;

	/**
	 * 根据经理返回该部门的全部员工
	 * @param mgr 经理名
	 * @return 经理的全部下属
	 */
	List<EmpBean> getEmpsByMgr(String mgr)throws HrException;

	/**
	 * 根据经理返回该部门的没有批复的申请
	 * @param mgr 经理名
	 * @return 该部门的全部申请
	 */
	List<AppBean> getAppsByMgr(String mgr)throws HrException;

	/**
	 * 处理申请
	 * @param appid 申请ID
	 * @param mgrName 经理名字
	 * @param result 是否通过
	 */
	void check(int appid, String mgrName, boolean result);
}