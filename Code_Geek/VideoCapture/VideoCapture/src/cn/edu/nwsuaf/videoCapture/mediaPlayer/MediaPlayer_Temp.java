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
 * MediaPlayerý�岥����
 * @author Qzhong
 *
 */
public class MediaPlayer_Temp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2158745732025741723L;
	private int w,h;
	MenuItem exitItem_playOrPause_SystemTray = new MenuItem("����/��ͣ");

	MenuItem exitItem_stop_SystemTray = new MenuItem("ֹͣ");
	
	
	
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
 * showMediaPlayer����������ʼ��MediaPlayer�Ĳ�������ʾ֮��
 */
	public  void showMediaPlayer(){
		
		System.out.println("���������ڳ�ʼ��! ���Ե�......");
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		this.setTitle("����ý�岥����--by Qzhong");
		this.setMinimumSize(new Dimension(530, 120));
		this.setLocationRelativeTo(this);
		this.setSize(this.getW(),this.getH());
		//��JFrameע������������ڹر�ʱ������ֹ��
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
			
		});

		System.out.println("��ʼ���������!������ʾ����");
		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * ������ȡMediaPlayer�Ŀ��
	 * @return ����һ��int����ֵ��
	 */
	public int getW() {
		return w;
	}
	/**
	 * ��������MediaPlayer�Ŀ��
	 * @param ����Ϊһ��int����ֵ��
	 */
	public void setW(int width) {
		this.w = width;
	}
	/**
	 * ������ȡMediaPlayer�ĸ߶�ֵ��
	 * @return ����һ��int����ֵ��
	 */
	public int getH() {
		return h;
	}
	/**
	 * ��������MediaPlayer�ĸ߶�ֵ��
	 * @param int����ֵ��
	 */
	public void setH(int height) {
		this.h = height;
	}
	/**
	 * @param args
	 */
	/**����������ͼ��
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("���ڸ�������ͼ��......");
		try {
			File imagefile = new File("img/vmp3.png");
			URL url = imagefile.toURI().toURL();
			Image image = this.getToolkit().getImage(url);
			this.setIconImage(image);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}
	
	/**��������
	 * 
	 */
	private void initSystemTray() {
		System.out.println("������������...");
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("img/vmp3.png"); // getClass.getResource("");
		this.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				dispose();
			}
		});
		PopupMenu popupMenu = new PopupMenu();

		MenuItem exitItem_exit = new MenuItem("�˳�");

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

		TrayIcon trayIcon = new TrayIcon(image, "java������", popupMenu);
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
	 * �Á턓�����\��MediaPlayer��
	 */
	public static void run(){
		new MediaPlayer_Temp();
	}
	

}
