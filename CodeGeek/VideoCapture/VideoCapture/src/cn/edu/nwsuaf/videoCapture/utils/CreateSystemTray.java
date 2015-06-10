package cn.edu.nwsuaf.videoCapture.utils;

import java.awt.AWTException;
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

import javax.swing.JFrame;

/**
 * 用于生成系统托盘
 * @author Qzhong
 *
 */
public class CreateSystemTray {
	private JFrame jframe;
	private String imgURL ="img/DVD_tray.png";

	public CreateSystemTray() {

	}

	public CreateSystemTray(JFrame jframe) {
		this.jframe = jframe;

	}
	public CreateSystemTray(JFrame jframe,String imgURL) {
		this.jframe = jframe;
		this.imgURL = imgURL;

	}

	/**
	 * 生成托盘
	 * 
	 */
	private void initSystemTray() {
		System.out.println("正在生成托盘...");
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage(imgURL); // getClass.getResource("");
		jframe.addWindowListener(new WindowAdapter() {
			public void windowIconified(WindowEvent e) {
				jframe.dispose();
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
		popupMenu.add(exitItem_exit);

		TrayIcon trayIcon = new TrayIcon(image, "视频捕获与存储系统", popupMenu);
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					jframe.setExtendedState(JFrame.NORMAL);
					jframe.setVisible(true);
				}
			}
		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.err.println(e);
		}

	}
}
