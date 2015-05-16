package cn.edu.nwsuaf.videoCapture.utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;

/**
 * 用来更换标题栏图标
 * @author Qzhong
 *
 */
public class ChangeWindowIcon {
	private JFrame jframe;
	private String imgURL = "img/DVD.png";
	public ChangeWindowIcon() {

	}

	public ChangeWindowIcon(JFrame jframe) {
		this.jframe = jframe;
	}
	public ChangeWindowIcon(JFrame jframe,String imgURL) {
		this.jframe = jframe;
		this.imgURL = imgURL;
	}

	/**
	 * 更换标题栏图标
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("正在更换标题图标......");
		try {
			File imagefile = new File(imgURL);
			URL url = imagefile.toURI().toURL();
			Image image = jframe.getToolkit().getImage(url);
			jframe.setIconImage(image);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}
}
