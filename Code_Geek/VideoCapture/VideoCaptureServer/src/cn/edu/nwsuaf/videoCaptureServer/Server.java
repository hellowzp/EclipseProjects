
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
 * ��Ƶ����ϵͳ������ࡣ
 * @author Qzhong
 * @version	1.0
 * @since   JDK1.6
 */
public class Server extends JFrame implements ChangeListener{

	/** ѡ� */
	private JTabbedPane jTabbedPane = null;
	/** ϵͳ������� */
	private ServicePane servicePane = new ServicePane(); 
	/** ϵͳ������� */
	private ConfigPane configPane = new ConfigPane(this);
	/** �����û���� */
	private OnlinePane onlinePane = new OnlinePane();
	/**��־���*/
	private LogPane     logPane = new LogPane();
	/** ���������ļ� */
	public static Properties prop = null;
	
	

	
	public Server() {
		
		setTitle("��Ƶ������洢-ϵͳ�����");
		setSize(600,520);
		setResizable(false);
		Toolkit tk=Toolkit.getDefaultToolkit();
		setLocation((tk.getScreenSize().width-getSize().width)/2,(tk.getScreenSize().height-getSize().height)/2);

		jTabbedPane = new JTabbedPane();
		jTabbedPane.add("ϵͳ����",servicePane);
		jTabbedPane.add("ϵͳ����",configPane);
		jTabbedPane.add("�����û�",onlinePane);
		jTabbedPane.add("ϵͳ��־",logPane);
		add(jTabbedPane);
		jTabbedPane.addChangeListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ChangeWindowIcon cwi = new ChangeWindowIcon(this);
		cwi.changWindowIcon();
		setVisible(true);
		
	}
	
	/**
	 * ѡ���û����������û�ѡ�ʱ�������ҵ�ǰ�����û���
	 */
	public void stateChanged(ChangeEvent e) {		
		if(jTabbedPane.getSelectedComponent()==onlinePane)
			onlinePane.flushOnlineUser();
	}
}
