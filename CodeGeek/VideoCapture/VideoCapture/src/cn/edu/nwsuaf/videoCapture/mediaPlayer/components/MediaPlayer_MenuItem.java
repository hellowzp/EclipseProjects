package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Font;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.event.ListSelectionListener;

public class MediaPlayer_MenuItem extends JMenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;

	/**
	 * 
	 */
	public MediaPlayer_MenuItem() {
		super();
	}

	/**
	 * @param a
	 */
	public MediaPlayer_MenuItem(Action a) {
		super(a);
	}

	/**
	 * @param icon
	 */
	public MediaPlayer_MenuItem(Icon icon) {
		super(icon);
	}

	/**
	 * @param text
	 * @param icon
	 */
	public MediaPlayer_MenuItem(String text, Icon icon) {
		super(text, icon);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 * @param mnemonic
	 */
	public MediaPlayer_MenuItem(String text, int mnemonic) {
		super(text, mnemonic);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}

	/**
	 * @param text
	 */
	public MediaPlayer_MenuItem(String text) {
		super(text);
		this.setFont(new Font("ËÎÌו", 7, 13));
	}
	
	public void addListSelectionListener(ListSelectionListener e) {

		this.addListSelectionListener(e);
	}

}
