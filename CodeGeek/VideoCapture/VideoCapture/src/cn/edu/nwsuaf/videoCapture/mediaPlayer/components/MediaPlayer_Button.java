package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicButtonUI;

public class MediaPlayer_Button extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public MediaPlayer_Button() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param a
	 */
	public MediaPlayer_Button(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param icon
	 */
	public MediaPlayer_Button(Icon icon) {
		super(icon);
		this.setUI(new BasicButtonUI());
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	/**
	 * @param text
	 * @param icon
	 */
	public MediaPlayer_Button(String text, Icon icon) {
		super(text, icon);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 */
	public MediaPlayer_Button(String text) {
		super(text);
		this.setText(text);
		this.setFont(new Font("ËÎÌו", 7, 12));
	}
	public void setAccelerator(KeyStroke keyStroke) {
		this.setAccelerator(keyStroke);
		
		
	}
	

}
