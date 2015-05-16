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
 * ��Ƶ����ͻ��˽���
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
		this.setTitle("��Ƶ������洢ϵͳ");
		init();
		this.validate();
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	/**
	 * ��JFrame��contentPane����
	 */
	Component contentPane = this.getContentPane();

	/**
	 * topPanel,centerPanel,bottomPanel ,leftPanel,rightPanel�ֱ�Ϊ�����еĶ���������
	 * �в��������ײ�����,�Լ��Ӵ��ָ������������
	 */
	private JPanel topPanel, centerPanel, bottomPanel, leftPanel, rightPanel;
	/**
	 * �����������
	 */
	private JPanel rightPanelDesign;
	/**
	 * view_split,text_split�ֱ�Ϊ�Ӵ��ָ������ı���Ϣ�ָ���塣
	 */
	private JSplitPane view_split, text_split;
	/**
	 * onlinePanel ��ʾ�����б���� messagePanel ��Ϣ��ʾ��� buttonMsgPanel ���ÿ��ư�ť�����
	 */
	private JPanel onlinePanel, messagePanel, buttonMsgPanel;

	/**
	 * ѡ����
	 */
	private JTabbedPane jTabbedPanel;
	/**
	 * inputTextArea,showTextArea�ֱ�Ϊ������Ϣ�򣬺���ʾ��Ϣ��
	 */
	public static JTextArea inputTextArea, showTextArea;

	private JScrollPane inputTextScrollPane, showTextScrollPane;
	/**
	 * �˵�������
	 */
	private JToolBar topToolBar;
	/**
	 * ���˵���
	 */
	private JMenuBar menuBar = new VideoMenuBar();

	/**
	 * ���˵�
	 */
	private VideoMenu jmenu_server, jmenu_system, jmenu_appearance, jmenu_tool,
			jmenu_help;
	/**
	 * �˵���
	 */
	private VideoMenuItem menuItem_connect, menuItem_disconnect, menuItem_send,
			menuItem_player, menuItem_dvdplay, menuItem_osk, menuItem_help,
			menuItem_about;
	/**
	 * ��ѡ��˵���
	 */
	private VideoRadioButtonMenuItem default_apperance, system_apperance,
			apperance3, apperance4, apperance5, apperance6;
	private ButtonGroup gruop_apperance = new ButtonGroup();

	/**
	 * sendButton,cancelButon ��Ϣ���Button
	 */
	private JButton sendButton = new JButton("����");
	/**
	 * �����б�VideoList
	 */
	public static VideoList onlineList = new VideoList();

	// ********** UDPͨ��
	// *********************************************************************//

	/**
	 * List<String>Ϊ�洢������Ա�ļ���
	 */
	private List<String> aList = new ArrayList<String>();
	/**
	 * ���ڴ�ż���ת��Ϊ�����ֵ��
	 */
	Object[] obj = aList.toArray();

	String message = "";
	String user = "ѩ������";
	String name = "ëë��";
	DatagramSocket clientSocket = null;
	String serverAddress = "192.168.12.234";
	int serverPort = 2010;
	int port = 2011;
	int personNum = 100001;

	// **************************************************************************************//
	/**
	 * ��ʼ���������ֵĸ����������
	 */
	private void initPanel() {
		System.out.println("��ʼ��������塭��");
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
		topToolBar = new JToolBar("�˵���", JToolBar.HORIZONTAL);

		bottomPanel.add(new JButton("    "));// �ײ�ռλ
	}

	/**
	 * ��panel��������
	 */
	private void initAddPanel() {
		System.out.println("����������������......");
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
	 * ��ȡview_split
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
	 * ��ȡtext_split
	 */
	private JSplitPane getText_splitPane() {

		inputTextScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		inputTextScrollPane.getViewport().add(inputTextArea);
		inputTextArea.setLineWrap(true);
		inputTextArea.setFont(new Font("����", 7, 14));

		showTextScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		showTextScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		showTextScrollPane.getViewport().add(showTextArea);
		showTextArea.setLineWrap(true);
		showTextArea.setFont(new Font("����", 7, 14));

		text_split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				showTextScrollPane, inputTextScrollPane);
		text_split.setDividerSize(20);
		text_split.setResizeWeight(0.8);

		return text_split;
	}

	/**
	 * ��ȡjTabbedPanelѡ�
	 * 
	 * @return
	 */
	private JTabbedPane getJtabbedPanel() {
		JTabbedPane tab_player = new JTabbedPane(JTabbedPane.BOTTOM);
		tab_player.addTab("��Ϣ����", getMessagePanel());
		onlinePanel.setLayout(new BorderLayout());
		onlinePanel.add(new JList(new String[] { "  ", "  " }),
				BorderLayout.WEST);
		onlinePanel.add(onlineList, BorderLayout.CENTER);
		onlineList.setListData(new String[] { "001��ѩ�����꡿", "002���޻��ǡ�" });
		tab_player.addTab("�����б�", onlinePanel);
		return tab_player;
	}

	/**
	 * ��ȡ��Ϣ���
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
	 * ���ô�����ʾ���
	 * 
	 * @param bool
	 *            (ֵΪtrue/false)
	 */
	public static void setAppearance(boolean bool) {
		System.out.println("�û�������ۡ�����������");
		JFrame.setDefaultLookAndFeelDecorated(bool);
		JDialog.setDefaultLookAndFeelDecorated(bool);
	}

	/**
	 * ����������ͼ��
	 * 
	 */
	public void changWindowIcon() {
		System.out.println("���ڸ�������ͼ��......");
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
	 * ��������
	 * 
	 */
	private void initSystemTray() {
		System.out.println("������������...");
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().getImage("img/DVD_tray.png"); // getClass.getResource("");
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
		popupMenu.add(exitItem_exit);

		TrayIcon trayIcon = new TrayIcon(image, "��Ƶ������洢ϵͳ", popupMenu);
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
	 * ��ʼ�����˵���
	 */
	public void initMenuBar() {
		System.out.println("ϵͳ���ڳ�ʼ���˵���������������������������������");
		// ��ʼ�����˵�
		jmenu_server = new VideoMenu("����");
		jmenu_system = new VideoMenu("ϵͳ");
		jmenu_appearance = new VideoMenu("���");
		jmenu_tool = new VideoMenu("����");
		jmenu_help = new VideoMenu("����");
		// ��ʼ���˵���

		default_apperance = new VideoRadioButtonMenuItem("FieldOfWheat");
		system_apperance = new VideoRadioButtonMenuItem("OfficeBlue2007");
		apperance3 = new VideoRadioButtonMenuItem("Business");
		apperance4 = new VideoRadioButtonMenuItem("ChallengerDeep");
		apperance5 = new VideoRadioButtonMenuItem("Creme");
		apperance6 = new VideoRadioButtonMenuItem("EmeraldDusk");
		menuItem_help = new VideoMenuItem("����");
		menuItem_about = new VideoMenuItem("����");
		menuItem_player = new VideoMenuItem("�����Զ���߼�������");
		menuItem_dvdplay = new VideoMenuItem("��Windows Media Player");
		menuItem_osk = new VideoMenuItem("������Ļ����");
		menuItem_connect = new VideoMenuItem("���ӷ�����");
		menuItem_disconnect = new VideoMenuItem("�Ͽ�����");
		menuItem_send = new VideoMenuItem("����");

		// ע�����
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

		// ��װ�˵�
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
	 * �����ע�����
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
			Help help = new Help(null, "---������---", true);
			help.setLocationRelativeTo(null);
			help.setVisible(true);
		} else if (e.getSource() == menuItem_about) {
			About about = new About(null, "������������>>����", true);
			about.setLocationRelativeTo(null);
			about.setVisible(true);

		} else if (e.getSource() == menuItem_player) {

			new Thread() {
				public void run() {
					try {
						Process process = Runtime.getRuntime().exec(
								"java -jar player.jar");
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "������Դ�����ã�");
						System.out.print(e);
					}

				}

			}.start();

		} else if (e.getSource() == menuItem_dvdplay) {
			try {
				Process process = Runtime.getRuntime().exec("dvdplay");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "��ǰϵͳ��֧��������Դ��");
				System.out.print(e);
			}

		} else if (e.getSource() == menuItem_osk) {
			try {
				Process process = Runtime.getRuntime().exec("osk");
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "��ǰϵͳ��֧��������Դ��");
				System.out.print(e);
			}

		} else if ("����".equals(e.getActionCommand())) {

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
	 * ��ʾ��ˢ�������б�onlineList
	 */
	private void flushOnlineUser() {// ��ʾ��ˢ�������б�
		System.out.println("ˢ���û������б�--------------------");
		// onlineList

	}

	/**
	 * ������Ϣ
	 * 
	 */
	public void sendMessage() {
		System.out.println("������Ϣ......");
		message = inputTextArea.getText();
		if(clientSocket ==null || clientSocket.isClosed()){
			showTextArea.append("\n��Ϣ����������������Ͽ����ӣ���Ϣ���ܷ��ͣ�\n");
		}else{
			if (message == null || message.trim().equals("")) {
				showTextArea.append("\n���ѡ����������ܷ��Ϳ���Ϣ��\n");
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
	 * ���ӷ�����
	 */
	public void startServer() {
		System.out.println("���ӷ�����......");
		try {
			clientSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.out.println(e.getMessage());
			showTextArea.append("���󡪡�����ַ����ʹ�ã��޷��󶨡�\n");
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
						aList.add(personNum + " " + "��" + name + "��");
						personNum++;
						obj = aList.toArray();
						onlineList.setListData(obj);
						showTextArea.append("��������Ϣ������" + msgReceive + "\n");
						
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
	 * ��������Ͽ�����
	 */
	public void stopServer() {
		System.out.println("�Ͽ�����......");
		if (clientSocket != null && !clientSocket.isClosed()) {
			new UDPTransmit(clientSocket, "7#�û��뿪", serverAddress, serverPort,
					false).start();
			clientSocket.close();
			showTextArea.append("ϵͳ��Ϣ�������û��˳�ϵͳ����������Ͽ����ӡ�\n");
		}

	}

	/**
	 * ����������Ϣ
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
		 * ��������Э��******************** 1��ʾ����UDP 2��ʾ��ƵUDP 3��ʾ��ƵRTCP UDP 4��ʾ��ƵUDP
		 * 5��ʾ��ƵRTCP UDP 6��ʾ��Ϣ�Ự 7��ʾ�뿪 8��ʾ��������
		 ********************************************/
		public void run() {
			for (;;) {
				try { // 0��ʾ�¼��룬1��ʾ�ظ���2��ʾ�������ӣ�3Ϊ������Ļظ���6��ʾ�뿪,7Ϊ�������Կͻ��˵�ͨ�ţ���������Ϣ���¼���Ŀͻ�
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
								address[0] + " ��������\n�Ƿ���ܸ����ӣ�", "��Ϣ",
								JOptionPane.YES_NO_OPTION);

						new UDPTransmit(ds, "3 " + result, address[0], Integer
								.parseInt(address[1]), false).start(); // 0��ʾ���ӣ�1��ʾ�ܾ����硰3
						// 0����ʾ���ӣ���3
						// 1����ʾ�ܾ�
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
									+ " �ܾ�����");
						} else {
							// cf.rtpConnect(pack.getAddress().getHostAddress());
						}
					} else if (Num.equals("6")) { // �յ�Bye��ɾ����
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
		changWindowIcon();// ����������ͼ��
		initSystemTray();// ��������
		initPanel();// ��ʼ���������
		initMenuBar();// ��ʼ���˵���
		initAddPanel();// ����������������
		initAddActionListener();// ����¼�

	}

}
