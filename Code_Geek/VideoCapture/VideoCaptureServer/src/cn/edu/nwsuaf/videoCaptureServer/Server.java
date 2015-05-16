
package cn.edu.nwsuaf.videoCaptureServer;

import java.awt.Toolkit;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.nwsuaf.videoCaptureServer.panes.ConfigPane;
import cn.edu.nwsuaf.videoCaptureServer.panes.LogPane;
import cn.edu.nwsuaf.videoCaptureServer.panes.OnlinePane;
import cn.edu.nwsuaf.videoCaptureServer.panes.ServicePane;
import cn.edu.nwsuaf.videoCaptureServer.utils.ChangeWindowIcon;

 /**
 * 视频捕获系统服务端类。
 * @author Qzhong
 * @version	1.0
 * @since   JDK1.6
 */
public class Server extends JFrame implements ChangeListener{

	/** 选项卡 */
	private JTabbedPane jTabbedPane = null;
	/** 系统服务面板 */
	private ServicePane servicePane = new ServicePane(); 
	/** 系统配置面板 */
	private ConfigPane configPane = new ConfigPane(this);
	/** 在线用户面板 */
	private OnlinePane onlinePane = new OnlinePane();
	/**日志面板*/
	private LogPane     logPane = new LogPane();
	/** 属性配置文件 */
	public static Properties prop = null;
	
	

	
	public Server() {
		
		setTitle("视频捕获与存储-系统服务端");
		setSize(600,520);
		setResizable(false);
		Toolkit tk=Toolkit.getDefaultToolkit();
		setLocation((tk.getScreenSize().width-getSize().width)/2,(tk.getScreenSize().height-getSize().height)/2);

		jTabbedPane = new JTabbedPane();
		jTabbedPane.add("系统服务",servicePane);
		jTabbedPane.add("系统配置",configPane);
		jTabbedPane.add("在线用户",onlinePane);
		jTabbedPane.add("系统日志",logPane);
		add(jTabbedPane);
		jTabbedPane.addChangeListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ChangeWindowIcon cwi = new ChangeWindowIcon(this);
		cwi.changWindowIcon();
		setVisible(true);
		
	}
	
	/**
	 * 选择用户管理、在线用户选项卡时出发查找当前在线用户。
	 */
	public void stateChanged(ChangeEvent e) {		
		if(jTabbedPane.getSelectedComponent()==onlinePane)
			onlinePane.flushOnlineUser();
	}
}
