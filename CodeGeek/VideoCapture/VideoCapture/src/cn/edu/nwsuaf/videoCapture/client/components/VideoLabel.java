package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JLabel;

public class VideoLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public VideoLabel() {
		super();
	}

	/**
	 * @param image
	 * @param horizontalAlignment
	 */
	public VideoLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	/**
	 * @param image
	 */
	public VideoLabel(Icon image) {
		super(image);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 * @param icon
	 * @param horizontalAlignment
	 */
	public VideoLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		
	}

	/**
	 * @param text
	 * @param horizontalAlignment
	 */
	public VideoLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		
	}

	/**
	 * @param text
	 */
	public VideoLabel(String text) {
		super(text);
		this.setFont(new Font("ËÎÌו", 7, 13));
		
	}

	
}
