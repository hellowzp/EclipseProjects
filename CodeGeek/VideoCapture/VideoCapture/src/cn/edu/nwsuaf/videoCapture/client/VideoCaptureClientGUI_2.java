package cn.edu.nwsuaf.videoCapture.client;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;
import org.jvnet.substance.skin.SubstanceChallengerDeepLookAndFeel;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;
import org.jvnet.substance.skin.SubstanceEmeraldDuskLookAndFeel;
import org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel;
import org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel;

import cn.edu.nwsuaf.videoCapture.client.components.About;
import cn.edu.nwsuaf.videoCapture.client.components.Help;
import cn.edu.nwsuaf.videoCapture.client.components.VideoList;
import cn.edu.nwsuaf.videoCapture.client.components.VideoMenu;
import cn.edu.nwsuaf.videoCapture.client.components.VideoMenuBar;
import cn.edu.nwsuaf.videoCapture.client.components.VideoMenuItem;
import cn.edu.nwsuaf.videoCapture.client.components.VideoRadioButtonMenuItem;
import cn.edu.nwsuaf.videoCapture.utils.DateDeal;

/**
 * 视频捕获客户端界面
 * 
 * @author Qzhong
 * 
 */
public class VideoCaptureClientGUI_2 extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws HeadlessException
	 */
	public VideoCaptureClientGUI_2() throws HeadlessException {
		super();
		this.setSize(800, 600);
		this.setLocationRelativeTo(this);
		// this.setResizable(false);
		this.setTitle("视频捕获与存储系统");
		init();
		this.validate();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * 本JFrame的contentPane对象。
	 */
	Component contentPane = this.getContentPane();

	/**
	 * topPanel,centerPanel,bottomPanel ,leftPanel,rightPanel分别为布局中的顶部容器，
	 * 中部容器，底部容器,以及视窗分割的左右容器。
	 */
	private JPanel topPanel, centerPanel, bottomPanel, leftPanel, rightPanel;
	/**
	 * 右面板设计面板
	 */
	private JPanel rightPanelDesign;
	/**
	 * view_split,text_split分别为视窗分割面板和文本信息分割面板。
	 */
	private JSplitPane view_split, text_split;
	/**
	 * onlinePanel 显示在线列表面板 messagePanel 信息显示面板 buttonMsgPanel 放置控制按钮的面板
	 */
	private JPanel onlinePanel, messagePanel, buttonMsgPanel;

	/**
	 * 选项卡面板
	 */
	private JTabbedPane jTabbedPanel;
	/**
	 * inputTextArea,showTextArea分别为输入信息域，和显示信息域。
	 */
	public static JTextArea inputTextArea, showTextArea;

	private JScrollPane inputTextScrollPane, showTextScrollPane;
	/**
	 * 菜单工具条
	 */
	private JToolBar topToolBar;
	/**
	 * 主菜单条
	 */
	private JMenuBar menuBar = new VideoMenuBar();

	/**
	 * 主菜单
	 */
	private VideoMenu jmenu_server, jmenu_system, jmenu_appearance, jmenu_tool,
			jmenu_help;
	/**
	 * 菜单条
	 */
	private VideoMenuItem menuItem_connect, menuItem_disconnect, menuItem_send,
			menuItem_player, menuItem_dvdplay, menuItem_osk, menuItem_help,
			menuItem_about;
	/**
	 * 复选框菜单条
	 */
	private VideoRadioButtonMenuItem default_apperance, system_apperance,
			apperance3, apperance4, apperance5, apperance6;
	private ButtonGroup gruop_apperance = new ButtonGroup();

	/**
	 * sendButton,cancelButon 信息面板Button
	 */
	private JButton sendButton = new JButton("发送");
	/**
	 * 在线列表VideoList
	 */
	public static VideoList onlineList = new VideoList();

	// ********** UDP通信
	// *********************************************************************//

	/**
	 * List<String>为存储在线人员的集合
	 */
	private List<String> aList = new ArrayList<String>();
	/**
	 * 用于存放几个转化为数组的值。
	 */
	Object[] obj = aList.toArray();

	String message = "";
	String user = "雪中霖雨";
	String name = "毛毛虫";
	DatagramSocket clientSocket = null;
	String serverAddress = "192.168.12.234";
	int serverPort = 2010;
	int port = 2011;
	int personNum = 100001;

	// **************************************************************************************//
	/**
	 * 初始换用来布局的各个面板容器
	 */
	private void initPanel() {
		System.out.println("初始化各个面板……");
		inputTextScrollPane = new JScrollPane();
		showTextScrollPane = new JScrollPane();
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		buttonMsgPanel = new JPanel();
		onlinePanel = new JPanel();

		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanelDesign = new RrightPanelDesign(this);

		inputTextArea = new JTextArea();
		showTextArea = new JTextArea();
		showTextArea.setEditable(false);
		view_split = getView_splitPane();
		jTabbedPanel = getJtabbedPanel();
		topToolBar = new JToolBar("菜单栏", JToolBar.HORIZONTAL);

		bottomPanel.add(new JButton("    "));// 底部占位
	}

	/**
	 * 将panel加入容器
	 */
	private void initAddPanel() {
		System.out.println("将各个面板加入容器......");
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
		centerPanel.add(view_split);
		leftPanel.add(jTabbedPanel);
		rightPanel.add(rightPanelDesign);
		topPanel.add(topToolBar);
		topToolBar.add(menuBar);//

	}

	/**
	 * 获取view_split
	 */
	private JSplitPane getView_splitPane() {
		view_split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
				rightPanel);
		view_split.setOneTouchExpandable(true);
		view_split.setDividerLocation(440);
		view_split.setDividerSize(12);
		// view_split.setResizeWeight(0.375);

		return view_split;
	}

	/**
	 * 获取text_split
	 */
	private JSplitPane getText_splitPane() {

		inputTextScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		inputTextScrollPane.getViewport().add(inputTextArea);
		inputTextArea.setLineWrap(true);
		inputTextArea.setFont(new Font("行书", 7, 14));

		showTextScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		showTextScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		showTextScrollPane.getViewport().add(showTextArea);
		showTextArea.setLineWrap(true);
		showTextArea.setFont(new Font("黑体", 7, 14));

		text_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				showTextScrollPane, inputTextScrollPane);
		text_split.setDividerSize(20);
		text_split.setResizeWeight(0.8);

		return text_split;
	}

	/**
	 * 获取jTabbedPanel选项卡
	 * 
	 * @return
	 */
	private JTabbedPane getJtabbedPanel() {
		JTabbedPane tab_player = new JTabbedPane(JTabbedPane.BOTTOM);
		tab_player.addTab("信息窗口", getMessagePanel());
		onlinePanel.setLayout(new BorderLayout());
		onlinePanel.add(new JList(new String[] { "  ", "  " }),
				BorderLayout.WEST);
		onlinePanel.add(onlineList, BorderLayout.CENTER);
		onlineList.setListData(new String[] { "001【雪中霖雨】", "002【棉花糖】" });
		tab_player.addTab("在线列表", onlinePanel);
		return tab_player;
	}

	/**
	 * 获取信息面板
	 * 
	 * @return
	 */
	private JPanel getMessagePanel() {
		messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add(getText_splitPane(), BorderLayout.CENTER);
		// buttonMsgPanel.setAlignmentX(LEFT_ALIGNMENT);
		buttonMsgPanel.setLayout(new BorderLayout());
		// buttonMsgPanel.add(cancelButton, BorderLayout.CENTER);
		buttonMsgPanel.add(sendButton, BorderLayout.EAST);

		messagePanel.add(buttonMsgPanel, BorderLayout.SOUTH);
		return messagePanel;

	}

	/**
	 * 设置窗口显示外观
	 * 
	 * @param bool
	 *            (值为true/false)
	 */
	public static void setAppearance(boolean bool) {
		System.out.println("用户更改外观。。。。。。");
		JFrame.setDefaultLookAndFeelDecorated(bool);
		JDialog.setDefaultLookAndFeelDecorated(bool);
	}

	/**
	 * 更换标题栏图标
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("正在更换标题图标......");
		try {
			File imagefile = new File("img/DVD.png");
			URL url = imagefile.toURI().toURL();
			Image image = this.getToolkit().getImage(url);
			this.setIconImage(image);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}

	/**
	 * 生成托盘
	 * 
	 */
	private void initSystemTray() {
		System.out.println("正在生成托盘...");
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("img/DVD_tray.png"); // getClass.getResource("");
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
		popupMenu.add(exitItem_exit);

		TrayIcon trayIcon = new TrayIcon(image, "视频捕获与存储系统", popupMenu);
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
	 * 初始化主菜单条
	 */
	public void initMenuBar() {
		System.out.println("系统正在初始化菜单条。。。。。。。。。。。。。。。");
		// 初始化主菜单
		jmenu_server = new VideoMenu("服务");
		jmenu_system = new VideoMenu("系统");
		jmenu_appearance = new VideoMenu("外观");
		jmenu_tool = new VideoMenu("工具");
		jmenu_help = new VideoMenu("帮助");
		// 初始化菜单项

		default_apperance = new VideoRadioButtonMenuItem("FieldOfWheat");
		system_apperance = new VideoRadioButtonMenuItem("OfficeBlue2007");
		apperance3 = new VideoRadioButtonMenuItem("Business");
		apperance4 = new VideoRadioButtonMenuItem("ChallengerDeep");
		apperance5 = new VideoRadioButtonMenuItem("Creme");
		apperance6 = new VideoRadioButtonMenuItem("EmeraldDusk");
		menuItem_help = new VideoMenuItem("帮助");
		menuItem_about = new VideoMenuItem("关于");
		menuItem_player = new VideoMenuItem("启动自定义高级播放器");
		menuItem_dvdplay = new VideoMenuItem("打开Windows Media Player");
		menuItem_osk = new VideoMenuItem("启动屏幕键盘");
		menuItem_connect = new VideoMenuItem("连接服务器");
		menuItem_disconnect = new VideoMenuItem("断开连接");
		menuItem_send = new VideoMenuItem("发送");

		// 注册监听
		default_apperance.addActionListener(this);
		system_apperance.addActionListener(this);
		apperance3.addActionListener(this);
		apperance4.addActionListener(this);
		apperance5.addActionListener(this);
		apperance6.addActionListener(this);
		menuItem_help.addActionListener(this);
		menuItem_about.addActionListener(this);
		menuItem_player.addActionListener(this);
		menuItem_dvdplay.addActionListener(this);
		menuItem_osk.addActionListener(this);
		menuItem_connect.addActionListener(this);
		menuItem_disconnect.addActionListener(this);
		menuItem_send.addActionListener(this);

		// 组装菜单
		menuBar.add(jmenu_server);
		menuBar.add(jmenu_system);
		menuBar.add(jmenu_appearance);
		menuBar.add(jmenu_tool);
		menuBar.add(jmenu_help);
		gruop_apperance.add(default_apperance);
		gruop_apperance.add(system_apperance);
		gruop_apperance.add(apperance3);
		gruop_apperance.add(apperance4);
		gruop_apperance.add(apperance5);
		gruop_apperance.add(apperance6);
		jmenu_appearance.add(default_apperance);
		jmenu_appearance.add(system_apperance);
		jmenu_appearance.add(apperance3);
		jmenu_appearance.add(apperance4);
		jmenu_appearance.add(apperance5);
		jmenu_appearance.add(apperance6);
		jmenu_help.add(menuItem_help);
		jmenu_help.add(menuItem_about);
		jmenu_tool.add(menuItem_player);
		jmenu_tool.add(menuItem_dvdplay);
		jmenu_tool.add(menuItem_osk);
		jmenu_server.add(menuItem_connect);
		jmenu_server.add(menuItem_disconnect);
		jmenu_system.add(menuItem_send);

	}

	/**
	 * 给组件注册监听
	 */
	private void initAddActionListener() {
		menuItem_send.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
				InputEvent.CTRL_MASK));
		sendButton.addActionListener(this);

		jTabbedPanel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				if (jTabbedPanel.getSelectedIndex() == 1) {

					flushOnlineUser();

				}

			}

		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		System.out.println("ActionCommand::" + cmd);
		if (default_apperance == e.getSource()) {

			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager
						.setLookAndFeel(new SubstanceFieldOfWheatLookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}

		} else if (system_apperance == e.getSource()) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager
						.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}
		} else if (apperance3 == e.getSource()) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}
		} else if (apperance4 == e.getSource()) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager
						.setLookAndFeel(new SubstanceChallengerDeepLookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}
		} else if (apperance5 == e.getSource()) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}
		} else if (apperance6 == e.getSource()) {
			JFrame.setDefaultLookAndFeelDecorated(true);
			try {
				UIManager.setLookAndFeel(new SubstanceEmeraldDuskLookAndFeel());
			} catch (Exception e1) {
				e1.printStackTrace();
				System.out
						.println("Substance Raven Graphite failed to initialize");
			}
		} else if (e.getSource() == menuItem_help) {
			Help help = new Help(null, "---帮助中---", true);
			help.setLocationRelativeTo(null);
			help.setVisible(true);
		} else if (e.getSource() == menuItem_about) {
			About about = new About(null, "······>>关于", true);
			about.setLocationRelativeTo(null);
			about.setVisible(true);

		} else if (e.getSource() == menuItem_player) {

			new Thread() {
				public void run() {
					try {
						Process process = Runtime.getRuntime().exec(
								"java -jar player.jar");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "请求资源不可用！");
						System.out.print(e);
					}

				}

			}.start();

		} else if (e.getSource() == menuItem_dvdplay) {
			try {
				Process process = Runtime.getRuntime().exec("dvdplay");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "当前系统不支持请求资源！");
				System.out.print(e);
			}

		} else if (e.getSource() == menuItem_osk) {
			try {
				Process process = Runtime.getRuntime().exec("osk");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "当前系统不支持请求资源！");
				System.out.print(e);
			}

		} else if ("发送".equals(e.getActionCommand())) {

			sendMessage();
			// inputTextArea, showTextArea

		} else if (e.getSource() == menuItem_connect) {

			startServer();

		} else if (e.getSource() == menuItem_disconnect) {

			stopServer();

		}

	}

	public void writeTextArea(String name, String msg) {
		showTextArea.append(name + " " + DateDeal.getCurrentTime() + "\n   "
				+ msg + "\n");
	}

	// //////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 显示及刷新在线列表onlineList
	 */
	private void flushOnlineUser() {// 显示及刷新在线列表
		System.out.println("刷新用户在线列表--------------------");
		// onlineList

	}

	/**
	 * 发送信息
	 * 
	 */
	public void sendMessage() {
		System.out.println("发送信息......");
		message = inputTextArea.getText();
		if(clientSocket ==null || clientSocket.isClosed()){
			showTextArea.append("\n消息——：已与服务器断开连接，信息不能发送！\n");
		}else{
			if (message == null || message.trim().equals("")) {
				showTextArea.append("\n提醒——：您不能发送空信息！\n");
			} else {
				writeTextArea(name, message);
				inputTextArea.setText("");
				new UDPTransmit(clientSocket, "6#" +message, serverAddress, serverPort,
						false).start();
				
				
				
			}
		}
		

		// inputTextArea, showTextArea
	}

	/**
	 * 连接服务器
	 */
	public void startServer() {
		System.out.println("连接服务器......");
		try {
			clientSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println(e.getMessage());
			showTextArea.append("错误——：地址已在使用，无法绑定。\n");
		}
		new UDPTransmit(clientSocket, "1#" + name, serverAddress, serverPort,
				false).start();
		new Thread() {
			@Override
			public void run() {
				byte[] buffer = new byte[1024];
				DatagramPacket dp = null;
				try {
					dp = new DatagramPacket(buffer, buffer.length,
							new InetSocketAddress(serverAddress, serverPort));
				} catch (SocketException e) {

				}
				while (true) {

					try {
						clientSocket.receive(dp);
					} catch (IOException e) {
					}
					String msgReceive = new String(dp.getData(),
							dp.getOffset(), dp.getLength());
					String Num = msgReceive.substring(0, 1);
					msgReceive = msgReceive.substring(2);

					System.out.println(Num + ":" + msgReceive);
					if ("0".equals(Num)) {
						aList.add(personNum + " " + "【" + name + "】");
						personNum++;
						obj = aList.toArray();
						onlineList.setListData(obj);
						showTextArea.append("服务器消息——：" + msgReceive + "\n");
						
					}else if("6".equals(Num)){
						if(message.equals(msgReceive)){
							
						}else{
							writeTextArea(user,msgReceive);
						}
						
						
						
					}
				}

			}
		}.start();

	}

	/**
	 * 与服务器断开连接
	 */
	public void stopServer() {
		System.out.println("断开连接......");
		if (clientSocket != null && !clientSocket.isClosed()) {
			new UDPTransmit(clientSocket, "7#用户离开", serverAddress, serverPort,
					false).start();
			clientSocket.close();
			showTextArea.append("系统消息——：用户退出系统，与服务器断开连接。\n");
		}

	}

	/**
	 * 用来发送信息
	 * 
	 * @author Qzhong
	 * 
	 */

	class UDPReceive extends Thread {
		String msgReceive;
		String msgSend;
		byte[] recbuf = new byte[1024];
		DatagramSocket ds;
		DatagramPacket pack = new DatagramPacket(recbuf, recbuf.length);

		public UDPReceive(DatagramSocket ds) {
			this(ds, null);
		}

		public UDPReceive(DatagramSocket ds, String msgSend) {
			this.ds = ds;
			this.msgSend = msgSend;
		}

		/***************
		 * 服务器端协议******************** 1表示管理UDP 2表示音频UDP 3表示音频RTCP UDP 4表示视频UDP
		 * 5表示视频RTCP UDP 6表示消息会话 7表示离开 8表示保持连接
		 ********************************************/
		public void run() {
			for (;;) {
				try { // 0表示新加入，1表示回复，2表示请求连接，3为请求包的回复，6表示离开,7为服务器对客户端的通信，发所有信息给新加入的客户
					ds.receive(pack);
					msgReceive = new String(pack.getData(), pack.getOffset(),
							pack.getLength());
					String Num = msgReceive.substring(0, 1);
					msgReceive = msgReceive.substring(2);

					String address[] = msgReceive.split(":");

					if (Num.equals("0") || Num.equals("1")) {
						aList.add(pack.getAddress().getHostName());
						obj = aList.toArray();
						onlineList.setListData(obj);
						if (Num.equals("0")
								&& !pack.getAddress().getHostAddress().equals(
										getLocalAddress())) {
							new UDPTransmit(ds, "1#" + msgSend, pack
									.getAddress().getHostAddress(), pack
									.getPort(), false).start();
						}
					} else if (Num.equals("2")) {
						int result = JOptionPane.showConfirmDialog(null,
								address[0] + " 请求连接\n是否接受该连接？", "消息",
								JOptionPane.YES_NO_OPTION);

						new UDPTransmit(ds, "3 " + result, address[0], Integer
								.parseInt(address[1]), false).start(); // 0表示连接，1表示拒绝。如“3
						// 0”表示连接，“3
						// 1”表示拒绝
						if (result == 0) {
							// cf.tar.setDestAddressAndPort(address[0], Integer
							// .parseInt(address[1]), Integer
							// .parseInt(address[2]), Integer
							// .parseInt(address[3]), Integer
							// .parseInt(address[4]), Integer
							// .parseInt(address[5]));
							// cf.rtpConnect(address[0]);
						}
					} else if (Num.equals("3")) {
						if (msgReceive.equals("1")) {
							JOptionPane.showMessageDialog(null, pack
									.getAddress().getHostAddress()
									+ " 拒绝连接");
						} else {
							// cf.rtpConnect(pack.getAddress().getHostAddress());
						}
					} else if (Num.equals("6")) { // 收到Bye，删除行
						// cf.findTableRow(address[0], address[1]);
						// cf.defaultModel.removeRow(cf.findTableRow(address[0],
						// address[1]));
						// delTableRow(address[0], address[1]);
					} else {
						String addressList[] = msgReceive.split(" ");
						for (int i = 0; i < addressList.length; i++) {
							address = addressList[i].split(":");
							// cf.defaultModel.addRow(address);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void delTableRow(String name, String address) {

		}

		public String getLocalAddress() {
			InetAddress addr = null;
			try {
				addr = InetAddress.getLocalHost();
			} catch (UnknownHostException ex) {
			}
			return addr.getHostAddress();
		}

	}

	public void init() {
		changWindowIcon();// 更换标题栏图标
		initSystemTray();// 生成托盘
		initPanel();// 初始化各个面板
		initMenuBar();// 初始化菜单条
		initAddPanel();// 将各个面板加入容器
		initAddActionListener();// 添加事件

	}

}
