package cn.edu.nwsuaf.videoCaptureServer.panes;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.JPanel;

public class OnlinePane extends JPanel {
	JList onlineList = new JList();
	

	public OnlinePane() {
		setLayout(new BorderLayout());
		add(new JList(new String[] { "  ", "  " }), BorderLayout.WEST);
		add(onlineList, BorderLayout.CENTER);
		onlineList.setListData(new String[]{"���������û�"});
//		flushOnlineUser();
	}

	/**
	 * ѡ���û����������û�ѡ�ʱ�������ҵ�ǰ�����û���
	 */
	public void flushOnlineUser() {
		Object[] obj = ServicePane.addresses.toArray();
		onlineList.setListData(obj);
	}
}
