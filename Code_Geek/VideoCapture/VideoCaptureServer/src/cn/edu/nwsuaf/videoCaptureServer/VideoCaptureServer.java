package cn.edu.nwsuaf.videoCaptureServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VideoCaptureServer {
	
	
	/**
	 * ����ͨ���׽���
	 */
    private DatagramSocket ds;
    /**
     * ������ŷ�����Ϣ�����ݰ�
     */
    private byte[] buf = new byte[1024];

    /**
     * ���ݱ�
     */
    DatagramPacket rec = new DatagramPacket(buf, buf.length);
    /**
     * ������ͨ�Ŷ˿�
     */
    private int port;

    String newAddress = "";
    String addressList = "";
    String sendAddressList = "";
	
	
	
	
	/**
	 * �����ڷ�������̨�����ʾ��Ϣ
	 * @param message
	 */
	public void printInfo(String message) {
        Date nowTime = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa", Locale.US);
        System.out.println(fmt.format(nowTime) + "----" + message);
        
    }

	
		



}
