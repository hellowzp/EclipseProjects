package cn.edu.nwsuaf.videoCapture.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���ڻ�ȡϵͳ��ǰʱ��
 * @author Qzhong
 *
 */
public class CurrentTime {
	/**
	 * ����ϵͳ��ǰʱ���ַ�����ʽ
	 * @return:currentTime
	 */
	public static String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(date);
		return currentTime;
	}

}
