package cn.edu.nwsuaf.videoCapture.operate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.media.Buffer;
import javax.media.Player;
import javax.media.Processor;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.JFrame;

import cn.edu.nwsuaf.videoCapture.utils.ChangeWindowIcon;
import cn.edu.nwsuaf.videoCapture.utils.CurrentTime;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class MediaCamera {
	private Player player;

	public MediaCamera(Player player) {
		this.player = player;
		imgpanel = new ImagePanel();
		imgpanel.addMouseMotionListener(imgpanel);
	}

	private Buffer buf = null;
	private Image img = null;
	private BufferToImage btoi = null;
	private ImagePanel imgpanel=null;
	/**
	 * 选取框的x,y,width,height参数的默认值
	 */
	private int rectX;
	private int rectY;
	private int rectWidth = 640;
	private int rectHeight = 480;
	private int imgWidth = 640;
	private int imgHeight = 480;
	/**
	 * 默认保存文件名
	 */
	private String fname = "test";

	/**
	 * 点击拍照
	 */
	public void take(String path) {

		FrameGrabbingControl fgc = (FrameGrabbingControl) player
				.getControl("javax.media.control.FrameGrabbingControl");
		buf = fgc.grabFrame(); // Convert it to an image
		btoi = new BufferToImage((VideoFormat) buf.getFormat());
		img = btoi.createImage(buf); // show the image
		imgpanel.setImage(img); // save image
		
		JFrame jframe = new JFrame();
		jframe.add(imgpanel);
		jframe.setSize(img.getWidth(null),img.getHeight(null));
		ChangeWindowIcon cwi = new ChangeWindowIcon(jframe);
		cwi.changWindowIcon();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		this.saveJPG(img,path);
	}

	/**
	 * 拍照完成后显示照片的组件，可以拖动范围框，选择要截取的部分
	 */
	class ImagePanel extends Panel implements MouseMotionListener {
		private Image myimg = null;

		public ImagePanel() {
			setLayout(null);
			setSize(imgWidth, imgHeight);
		}

		public void setImage(Image img) {
			this.myimg = img;
			repaint();
		}

		public void update(Graphics g) {
			g.clearRect(0, 0, getWidth(), getHeight());
			if (myimg != null) {
				g.drawImage(myimg, 0, 0, this);
				g.setColor(Color.RED);
				g.drawRect(rectX, rectY, rectWidth, rectHeight);
			}
		}

		public void paint(Graphics g) {
			update(g);
		}

		public void mouseDragged(MouseEvent e) {
			rectX = e.getX() - 50;
			rectY = e.getY() - 50;
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	/**
	 * 保存图像
	 * 
	 * @param img
	 * @param s
	 */
	public void saveJPG(Image img, String path) {
		path = path + "./" + CurrentTime.getCurrentTime() + ".jpg";
		
		
		// BufferedImage bi = (BufferedImage)createImage(imgWidth, imgHeight);

		BufferedImage bi = new BufferedImage(img.getWidth(null), img
				.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bi.createGraphics();
		g2.clipRect(rectX, rectY, rectWidth, rectHeight);
		g2.drawImage(img, null, null);
		int moveX = rectX > 0 ? rectX : 0;
		int moveY = rectY > 0 ? rectY : 0;
		int cutWidth = rectX + rectWidth > imgWidth ? rectWidth
				- ((rectX + rectWidth) - imgWidth) : rectWidth;
		int cutHeight = rectY + rectHeight > imgHeight ? rectHeight
				- ((rectY + rectHeight) - imgHeight) : rectHeight;
		bi = bi.getSubimage(moveX, moveY, cutWidth, cutHeight);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
		} catch (java.io.FileNotFoundException io) {
			System.out.println("File Not Found");
		}
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1f, false);
		encoder.setJPEGEncodeParam(param);
		try {
			encoder.encode(bi);
			out.close();
		} catch (java.io.IOException io) {
			System.out.println("IOException");
		}
	}
}
