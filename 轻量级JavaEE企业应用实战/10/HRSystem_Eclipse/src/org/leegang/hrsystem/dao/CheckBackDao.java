package org.leegang.hrsystem.dao;

import java.util.*; 

import org.leegang.hrsystem.model.*;

/**
 * Description:
 * <br/>Copyright (C), 2001-2010, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public interface CheckBackDao
{
	/**
	 * ���ݱ�ʶ����������CheckBackʵ��
	 * @param id ��Ҫ���ص�CheckBackʵ���ı�ʶ����ֵ
	 * @return ָ����ʶ���Զ�Ӧ��CheckBackʵ��
	 */
	CheckBack get(Integer id);

	/**
	 * �־û�ָ����CheckBackʵ��
	 * @param checkBack ��Ҫ���־û���CheckBackʵ��
	 * @return CheckBackʵ�����־û���ı�ʶ����ֵ
	 */
	Integer save(CheckBack checkBack);

	/**
	 * �޸�ָ����CheckBackʵ��
	 * @param checkBack ��Ҫ���޸ĵ�CheckBackʵ��
	 */
	void update(CheckBack checkBack);

	/**
	 * ɾ��ָ����CheckBackʵ��
	 * @param checkBack ��Ҫ��ɾ����CheckBackʵ��
	 */
	void delete(CheckBack checkBack);

	/**
	 * ���ݱ�ʶ����ɾ��CheckBackʵ��
	 * @param id ��Ҫ��ɾ����CheckBackʵ���ı�ʶ����ֵ
	 */
	void delete(Integer id);

	/**
	 * ��ѯȫ����CheckBackʵ��
	 * @return ���ݿ���ȫ����CheckBackʵ��
	 */
	List<CheckBack> findAll();
}
