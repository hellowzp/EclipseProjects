package cn.edu.nwsuaf.videoCapture.client;

/**
 * 用来发送信息
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
			try { // 0表示新加入，1表示回复，2表示请求连接，3为请求包的回复，6表示离开,7为服务器对客户端的通信，发所有信息给新加入的客户
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
							+ " 请求连接\n是否接受该连接？", "消息",
							JOptionPane.YES_NO_OPTION);

					new UDPTransmit(ds, "3 " + result, address[0], Integer
							.parseInt(address[1]), false).start(); // 0表示连接，1表示拒绝。如“3
																	// 0”表示连接，“3
																	// 1”表示拒绝

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
