package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionListener;

public class VideoMenuItem extends JMenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public VideoMenuItem() {
		super();
	}

	/**
	 * @param a
	 */
	public VideoMenuItem(Action a) {
		super(a);
	}

	/**
	 * @param icon
	 */
	public VideoMenuItem(Icon icon) {
		super(icon);
	}

	/**
	 * @param text
	 * @param icon
	 */
	public VideoMenuItem(String text, Icon icon) {
		super(text, icon);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 * @param mnemonic
	 */
	public VideoMenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 */
	public VideoMenuItem(String text) {
		super(text);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
	
	public void addListSelectionListener(ListSelectionListener e) {

		this.addListSelectionListener(e);
	}

}
