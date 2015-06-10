package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;

public class MediaPlayer_Label extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public MediaPlayer_Label() {
		super();
	}

	/**
	 * @param image
	 * @param horizontalAlignment
	 */
	public MediaPlayer_Label(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	/**
	 * @param image
	 */
	public MediaPlayer_Label(Icon image) {
		super(image);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 */
	public MediaPlayer_Label(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		
	}

	/**
	 * @param text
	 * @param horizontalAlignment
	 */
	public MediaPlayer_Label(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		
	}

	/**
	 * @param text
	 */
	public MediaPlayer_Label(String text) {
		super(text);
		this.setFont(new Font("ËÎÌו", 7, 13));
		
	}

	
}
