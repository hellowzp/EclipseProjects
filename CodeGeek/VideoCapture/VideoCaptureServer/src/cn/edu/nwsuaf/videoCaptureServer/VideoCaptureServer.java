package cn.edu.nwsuaf.videoCaptureServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoCaptureServer {
	
	
	/**
	 * 数据通信套接字
	 */
    private DatagramSocket ds;
    /**
     * 用来存放发送信息的数据包
     */
    private byte[] buf = new byte[1024];

    /**
     * 数据报
     */
    DatagramPacket rec = new DatagramPacket(buf, buf.length);
    /**
     * 服务器通信端口
     */
    private int port;

    String newAddress = "";
    String addressList = "";
    String sendAddressList = "";
	
	
	
	
	/**
	 * 用于在服务器后台打出提示信息
	 * @param message
	 */
	public void printInfo(String message) {
        Date nowTime = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.US);
        System.out.println(fmt.format(nowTime) + "----" + message);
        
    }

	
		



}
