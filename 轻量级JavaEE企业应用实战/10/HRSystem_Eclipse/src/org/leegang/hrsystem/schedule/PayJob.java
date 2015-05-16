package org.leegang.hrsystem.schedule;

import java.util.Date;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
public class PayJob extends QuartzJobBean 
{
	private EmpManager empMgr;
	private boolean isRunning;

	public void setEmpMgr(EmpManager empMgr)
	{
		this.empMgr = empMgr;
	}

	public void executeInternal(JobExecutionContext ctx) 
		throws JobExecutionException 
	{
		if (!isRunning)
		{
			System.out.println("开始调度自动结算工资");
			isRunning = true;
			empMgr.autoPay();
			isRunning = false;
		}
	}
}