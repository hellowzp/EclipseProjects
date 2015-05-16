package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.JMenu;

public class VideoMenu extends JMenu{
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public VideoMenu() {
		super();

	}

	/**
	 * @param a
	 */
	public VideoMenu(Action a) {
		super(a);
	}

	/**
	 * @param s
	 * @param b
	 */
	public VideoMenu(String s, boolean b) {
		super(s, b);
	}

	/**
	 * @param s
	 */
	public VideoMenu(String s) {
		super(s);
		this.setFont(new Font("ËÎÌו",7,13));
	}

	
}
