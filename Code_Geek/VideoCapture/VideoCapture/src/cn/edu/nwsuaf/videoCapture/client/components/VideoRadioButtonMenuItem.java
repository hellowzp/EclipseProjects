package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Font;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButtonMenuItem;

public class VideoRadioButtonMenuItem extends JRadioButtonMenuItem{
	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public VideoRadioButtonMenuItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param a
	 */
	public VideoRadioButtonMenuItem(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param icon
	 * @param selected
	 */
	public VideoRadioButtonMenuItem(Icon icon, boolean selected) {
		super(icon, selected);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param icon
	 */
	public VideoRadioButtonMenuItem(Icon icon) {
		super(icon);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param text
	 * @param selected
	 */
	public VideoRadioButtonMenuItem(String text, boolean selected) {
		super(text, selected);
		this.setFont(new Font("宋体", 7, 13));
	}

	/**
	 * @param text
	 * @param icon
	 * @param selected
	 */
	public VideoRadioButtonMenuItem(String text, Icon icon,
			boolean selected) {
		super(text, icon, selected);
		this.setFont(new Font("宋体", 7, 13));
	}

	/**
	 * @param text
	 * @param icon
	 */
	public VideoRadioButtonMenuItem(String text, Icon icon) {
		super(text, icon);
		this.setFont(new Font("宋体", 7, 13));
	}

	/**
	 * @param text
	 */
	public VideoRadioButtonMenuItem(String text) {
		super(text);
		this.setFont(new Font("宋体", 7, 13));
		
	}
	public void MouseChangButtonFace(MouseListener e) {

		this.addMouseListener(e);
	}
	
}
