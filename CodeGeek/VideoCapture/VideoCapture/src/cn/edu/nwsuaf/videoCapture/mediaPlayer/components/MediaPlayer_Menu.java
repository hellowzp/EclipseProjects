package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.JMenu;

public class MediaPlayer_Menu extends JMenu{
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public MediaPlayer_Menu() {
		super();

	}

	/**
	 * @param a
	 */
	public MediaPlayer_Menu(Action a) {
		super(a);
	}

	/**
	 * @param s
	 * @param b
	 */
	public MediaPlayer_Menu(String s, boolean b) {
		super(s, b);
	}

	/**
	 * @param s
	 */
	public MediaPlayer_Menu(String s) {
		super(s);
		this.setFont(new Font("ËÎÌו",7,13));
	}

	
}
