package cn.edu.nwsuaf.videoCapture.utils;

import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.swing.JFrame;

/**
 * ��������������ͼ��
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
	 * ����������ͼ��
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("���ڸ�������ͼ��......");
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
