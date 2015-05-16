package cn.edu.nwsuaf.videoCapture.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于获取系统当前时间
 * @author Qzhong
 *
 */
public class CurrentTime {
	/**
	 * 返回系统当前时间字符串格式
	 * @return:currentTime
	 */
	public static String getCurrentTime(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String currentTime = sdf.format(date);
		return currentTime;
	}

}
