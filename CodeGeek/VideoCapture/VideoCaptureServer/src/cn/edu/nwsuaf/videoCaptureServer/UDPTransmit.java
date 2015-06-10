package cn.edu.nwsuaf.videoCaptureServer;

/**
 * 用来发送数据
 */

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class UDPTransmit extends Thread {
	DatagramSocket ds;
	DatagramPacket pack;
	String address;
	String data;
	int port;
	boolean cycle;

	public UDPTransmit(DatagramSocket ds, String data, String address,
			int port, boolean cycle) {
		this.ds = ds;
		this.data = data;
		this.address = address;
		this.port = port;
		this.cycle = cycle;

	}

	public void run() {

		try {

			byte[] bData = data.getBytes();
			pack = new DatagramPacket(bData, bData.length,
					new InetSocketAddress(address, port));
		} catch (SocketException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		if (cycle) {
			for (;;) {
				try {
					ds.send(pack);
					sleep(20000); // 20秒
				} catch (IOException ex) {
				} catch (InterruptedException ex1) {
				}
			}
		} else {
			try {
				ds.send(pack);
			} catch (IOException ex2) {
			}
		}
	}
}
