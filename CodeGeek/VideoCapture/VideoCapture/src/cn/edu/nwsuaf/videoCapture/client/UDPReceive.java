package cn.edu.nwsuaf.videoCapture.client;

/**
 * ����������Ϣ
 */

import java.net.*;
import javax.swing.JOptionPane;

public class UDPReceive extends Thread {
	String msgReceive;
	String msgSend;
	byte[] recbuf = new byte[1024];
	DatagramSocket ds;
	DatagramPacket pack = new DatagramPacket(recbuf, recbuf.length);

	public UDPReceive(DatagramSocket ds) {
		this(ds, null);
	}

	public UDPReceive(DatagramSocket ds, String msgSend) {
		this.ds = ds;
		this.msgSend = msgSend;
	}

	public void run() {
		for (;;) {
			try { // 0��ʾ�¼��룬1��ʾ�ظ���2��ʾ�������ӣ�3Ϊ������Ļظ���6��ʾ�뿪,7Ϊ�������Կͻ��˵�ͨ�ţ���������Ϣ���¼���Ŀͻ�
				ds.receive(pack);
				msgReceive = new String(pack.getData(), pack.getOffset(), pack
						.getLength());
				String Num = msgReceive.substring(0, 1);
				msgReceive = msgReceive.substring(2);

				String address[] = msgReceive.split(":");

				if (Num.equals("0") || Num.equals("1")) {

					if (Num.equals("0")
							&& !pack.getAddress().getHostAddress().equals(
									getLocalAddress())) {
						new UDPTransmit(ds, "1 " + msgSend, pack.getAddress()
								.getHostAddress(), pack.getPort(), false)
								.start();
					}
				} else if (Num.equals("2")) {
					int result = JOptionPane.showConfirmDialog(null, address[0]
							+ " ��������\n�Ƿ���ܸ����ӣ�", "��Ϣ",
							JOptionPane.YES_NO_OPTION);

					new UDPTransmit(ds, "3 " + result, address[0], Integer
							.parseInt(address[1]), false).start(); // 0��ʾ���ӣ�1��ʾ�ܾ����硰3
																	// 0����ʾ���ӣ���3
																	// 1����ʾ�ܾ�

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getLocalAddress() {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException ex) {
		}
		return addr.getHostAddress();
	}

}
