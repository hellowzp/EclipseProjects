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
		onlineList.setListData(new String[]{"暂无在线用户"});
//		flushOnlineUser();
	}

	/**
	 * 选择用户管理、在线用户选项卡时出发查找当前在线用户。
	 */
	public void flushOnlineUser() {
		Object[] obj = ServicePane.addresses.toArray();
		onlineList.setListData(obj);
	}
}
