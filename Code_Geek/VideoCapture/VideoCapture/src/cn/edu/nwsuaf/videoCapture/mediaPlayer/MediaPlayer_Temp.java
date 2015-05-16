package cn.edu.nwsuaf.videoCapture.mediaPlayer;


import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * MediaPlayer媒体播放器
 * @author Qzhong
 *
 */
public class MediaPlayer_Temp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2158745732025741723L;
	private int w,h;
	MenuItem exitItem_playOrPause_SystemTray = new MenuItem("播放/暂停");

	MenuItem exitItem_stop_SystemTray = new MenuItem("停止");
	
	
	
	public MediaPlayer_Temp(){
		MediaPlayerPanel mpp = new MediaPlayerPanel();
		mpp.init();
		this.setW(mpp.getWidth());
		this.setH(mpp.getHeight());
		this.add(mpp);
		changWindowIcon();
		initSystemTray();
		showMediaPlayer();
	}
	public MediaPlayer_Temp(File file){
		MediaPlayerPanel mpp = new MediaPlayerPanel(this,file);
		mpp.init();
		this.setW(mpp.getWidth());
		this.setH(mpp.getHeight());
		this.add(mpp);
		initSystemTray();
		changWindowIcon();
		showMediaPlayer();
	}
	


/**
 * showMediaPlayer方法用来初始化MediaPlayer的参数并显示之。
 */
	public  void showMediaPlayer(){
		
		System.out.println("播放器正在初始化! 请稍等......");
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		this.setTitle("霖雨媒体播放器--by Qzhong");
		this.setMinimumSize(new Dimension(530, 120));
		this.setLocationRelativeTo(this);
		this.setSize(this.getW(),this.getH());
		//给JFrame注册监听，当窗口关闭时程序终止。
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
			
		});

		System.out.println("初始化窗口完成!正在显示……");
		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * 用来获取MediaPlayer的宽度
	 * @return 返回一个int型数值。
	 */
	public int getW() {
		return w;
	}
	/**
	 * 用来设置MediaPlayer的宽度
	 * @param 参数为一个int型数值。
	 */
	public void setW(int width) {
		this.w = width;
	}
	/**
	 * 用来获取MediaPlayer的高度值。
	 * @return 返回一个int型数值。
	 */
	public int getH() {
		return h;
	}
	/**
	 * 用来设置MediaPlayer的高度值。
	 * @param int型数值。
	 */
	public void setH(int height) {
		this.h = height;
	}
	/**
	 * @param args
	 */
	/**更换标题栏图标
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("正在更换标题图标......");
		try {
			File imagefile = new File("img/vmp3.png");
			URL url = imagefile.toURI().toURL();
			Image image = this.getToolkit().getImage(url);
			this.setIconImage(image);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}
	
	/**生成托盘
	 * 
	 */
	private void initSystemTray() {
		System.out.println("正在生成托盘...");
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("img/vmp3.png"); // getClass.getResource("");
		this.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				dispose();
			}
		});
		PopupMenu popupMenu = new PopupMenu();

		MenuItem exitItem_exit = new MenuItem("退出");

		exitItem_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					System.exit(0);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		popupMenu.add(exitItem_playOrPause_SystemTray);
		popupMenu.add(exitItem_stop_SystemTray);
		popupMenu.add(exitItem_exit);

		TrayIcon trayIcon = new TrayIcon(image, "java播放器", popupMenu);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println(e);
		}

	}
	
	/**
	 * 用來創建并運行MediaPlayer。
	 */
	public static void run(){
		new MediaPlayer_Temp();
	}
	

}
