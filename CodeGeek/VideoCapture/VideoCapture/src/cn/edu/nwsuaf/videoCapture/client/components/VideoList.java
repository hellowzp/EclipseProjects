package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JList;

public class VideoList extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;
	public VideoList() {
		super();
	}

	public VideoList(String [] args ) {
		super(args);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
	public VideoList(Vector<?> listData) {
		
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
}
