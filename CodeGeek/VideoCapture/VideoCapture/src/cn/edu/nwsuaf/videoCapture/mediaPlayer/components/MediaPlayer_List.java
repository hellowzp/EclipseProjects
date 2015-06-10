package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JList;

public class MediaPlayer_List extends JList {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;
	public MediaPlayer_List() {
		super();
	}

	public MediaPlayer_List(String [] args ) {
		super(args);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
	public MediaPlayer_List(Vector<?> listData) {
		
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
}
