package cn.edu.nwsuaf.videoCapture.mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.PrefetchCompleteEvent;
import javax.media.RealizeCompleteEvent;
import javax.media.Time;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.About;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.Help;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_Button;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_List;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_Menu;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_MenuBar;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_MenuItem;
import cn.edu.nwsuaf.videoCapture.mediaPlayer.components.MediaPlayer_RadioButtonMenuItem;

public class MediaPlayerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;
	/**
	 * 存放MediaPlayerPanel的JFrame
	 */
	JFrame jframe;
	/**
	 * MediaPlayerPanel的高度
	 */
	private int height = 380;
	/**
	 * MediaPlayerPanel的宽度
	 */
	private int width = 530;

	static int x, y;// 窗口所在的坐标位置

	/**
	 * 时间参数
	 */
	static double newTime, fileTime;
	/**
	 * 控制播放进度的线程
	 */
	static Thread timeThread;
	/**
	 * 播放器的一些状态参数
	 */
	@SuppressWarnings("unused")
	private boolean firstPlayer = true, tempPause = true, playerOrPause = true,
			AllScreenOrOriginal = false;
	/**
	 * player播放声音对像
	 */
	private Player player;
	/**
	 * visual为视窗组件
	 */
	private Component visual;
	private File file;

	/**
	 * userInfro用于存放列表文件数据的文件
	 */
	File userInfro = new File("user.dat");

	/**
	 * 无参构造
	 */
	public MediaPlayerPanel() {
		super();
		jframe = new JFrame();
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// 设置面板布局

	}

	/**
	 * 
	 * @param jframe
	 */
	public MediaPlayerPanel(JFrame jframe) {
		super();
		this.jframe = jframe;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// 设置面板布局

	}

	/**
	 * 
	 * @param jframe
	 * @param file
	 */
	public MediaPlayerPanel(JFrame jframe, File file) {
		super();
		this.jframe = jframe;
		this.file = file;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// 设置面板布局

	}

	public MediaPlayerPanel(File file) {

		super();
		this.file = file;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// 设置面板布局

	}

	private JLabel lable_infor, label_view;
	private JSplitPane splitpane;
	/**
	 * panel_view为视频面板.panel_down为整体布局,把窗口分成上下两部分.panel_play为添加播放按钮
	 * panel_control布局为添加进度条,音量控制和其它按钮的面板.panel_prograBar为添加进度条的面板.
	 * panel_button为添加按钮的面板
	 */
	private JPanel panel_view, panel_down, panel_play, panel_control,
			panel_prograBar, panel_button, panel_AddButton, panel_Addsounds;

	/**
	 * 主菜单条
	 */
	private JMenuBar menubar = new MediaPlayer_MenuBar();
	/**
	 * 主菜单
	 */
	private MediaPlayer_Menu jmenu_file, jmenu_paly, jmenu_help;
	private JMenu menuItem_sound, jmenu_view, menu_control, menu_playerMode,
			menuItem_controlPlayer;// 复选框菜单
	/**
	 * 主菜单条
	 */
	private MediaPlayer_MenuItem menuItem_open, menuItem_exit,
			menuItem_palyOrPause, menuItem_stop, menuItem_help,
			menuItem_volumeIncrease, menuItem_volumeDecrease, menuItem_about,
			menuItem_rewind, menuItem_skinForwind;
	/**
	 * 复选框菜单条
	 */
	private MediaPlayer_RadioButtonMenuItem menuItem_onlyOneMode,
			menuItem_forOneMode, menuItem_turnMode, menuItem_randomMode,
			menuItem_onTop, menuItem_AutoChange, menuItem_noSound;

	/**
	 * but_last-上一曲,but_pla-播放,but_inte-暂停,but_stop停止,but_next下一曲,but_speed快进,
	 * but_recede快退
	 * 
	 */
	private MediaPlayer_Button but_rewind, but_play, but_stop, but_fastForward,
			but_sounds, but_skipBackward, but_skipForward, but_pause,
			but_music;
	private ButtonGroup group__playerMode = new ButtonGroup();

	private ButtonGroup group__playerControl = new ButtonGroup();
	private Vector<String> fileName = new Vector<String>();// musicList-用于装列表中的元素

	private Vector<File> fileDirection = new Vector<File>();

	private JSlider slider_time = new JSlider();// slider_time时间进度条
	private JPopupMenu popupMenuAll, popupMenuList;// 右键弹出菜单
	private JSlider jSlider_sound;// 音量控制杆

	/**
	 * 右键菜单条
	 */
	private MediaPlayer_MenuItem menuItem_paly_Popup, menuItem_stop_Popup,
			menuItem_skinForwind_Popup, menuItem_rewind_Popup,
			menuItem_Addfile, menuItem_allScreen, menuItem_delete,
			menuItem_deleteAll, menuItem_playSelect;
	/**
	 * 存放用户选择的等待播放的文件
	 */
	private File[] files;

	private JPopupMenu popup_menu = new JPopupMenu();

	private File fileDir;

	private JScrollPane scrollPopup;

	private Time pauseTime;
	/**
	 * 播放列表
	 */
	private MediaPlayer_List list, popupList;

	MenuItem exitItem_playOrPause_SystemTray = new MenuItem("播放/暂停");

	MenuItem exitItem_stop_SystemTray = new MenuItem("停止");

	/**
	 * 私有方法initPanel用来初始化各个用来布局的面板
	 */
	private void initPanel() {
		System.out.println("开始构建各个布局面板......");
		panel_down = new JPanel();

		panel_play = new JPanel();

		panel_control = new JPanel();

		panel_prograBar = new JPanel();

		panel_button = new JPanel();

		panel_AddButton = new JPanel();

		panel_Addsounds = new JPanel();
		System.out.println("布局面板构建完成。");

	}

	/**
	 * 初始化菜单
	 */
	private void initMenu() {
		System.out.println("播放器菜单初始化中......");
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		jmenu_file = new MediaPlayer_Menu("文件");
		jmenu_paly = new MediaPlayer_Menu("播放");
		jmenu_view = new MediaPlayer_Menu("显示");
		jmenu_help = new MediaPlayer_Menu("帮助");

		menuItem_onTop = new MediaPlayer_RadioButtonMenuItem("前端显示");

		menuItem_sound = new MediaPlayer_Menu("音量");

		menuItem_open = new MediaPlayer_MenuItem("打开文件");
		menuItem_exit = new MediaPlayer_MenuItem("退出");

		menuItem_palyOrPause = new MediaPlayer_MenuItem("播放/暂停");
		menuItem_stop = new MediaPlayer_MenuItem("停止");

		menu_control = new MediaPlayer_Menu("播放控制");
		menu_playerMode = new MediaPlayer_Menu("播放列表");

		menuItem_skinForwind = new MediaPlayer_MenuItem("快进");
		menuItem_rewind = new MediaPlayer_MenuItem("快退");
		menuItem_skinForwind_Popup = new MediaPlayer_MenuItem("快进");
		menuItem_rewind_Popup = new MediaPlayer_MenuItem("快退");

		menuItem_AutoChange = new MediaPlayer_RadioButtonMenuItem("自动切换", true);
		menuItem_about = new MediaPlayer_MenuItem("关于");
		menuItem_help = new MediaPlayer_MenuItem("帮助");

		menuItem_volumeIncrease = new MediaPlayer_MenuItem("增大");
		menuItem_volumeDecrease = new MediaPlayer_MenuItem("减小");
		menuItem_noSound = new MediaPlayer_RadioButtonMenuItem("静音");

		menuItem_onlyOneMode = new MediaPlayer_RadioButtonMenuItem("单曲播放");
		menuItem_forOneMode = new MediaPlayer_RadioButtonMenuItem("单曲循环");
		menuItem_turnMode = new MediaPlayer_RadioButtonMenuItem("循环播放");
		menuItem_randomMode = new MediaPlayer_RadioButtonMenuItem("随机播放", true);

		System.out.println("菜单初始化完成。");
	}

	/**
	 * 对菜单组件进行装配
	 */
	private void initAddMenu() {
		System.out.println("菜单装配开始......");
		// 添加主菜单到菜单栏
		menubar.add(jmenu_file);
		menubar.add(jmenu_paly);
		menubar.add(jmenu_view);
		menubar.add(jmenu_help);
		// 添加到主菜单条
		jmenu_file.add(menuItem_open);
		jmenu_file.add(menuItem_exit);
		jmenu_paly.add(menuItem_palyOrPause);
		jmenu_paly.add(menuItem_stop);
		jmenu_paly.add(menu_control);
		menu_control.add(menuItem_skinForwind);
		menu_control.add(menuItem_rewind);

		jmenu_paly.add(menu_playerMode);
		jmenu_paly.add(menuItem_sound);

		jmenu_view.add(menuItem_onTop);
		jmenu_view.add(menuItem_AutoChange);

		jmenu_help.add(menuItem_about);
		jmenu_help.add(menuItem_help);

		menu_playerMode.add(menuItem_onlyOneMode);
		menu_playerMode.add(menuItem_forOneMode);
		menu_playerMode.add(menuItem_turnMode);
		menu_playerMode.add(menuItem_randomMode);

		menuItem_sound.add(menuItem_volumeIncrease);
		menuItem_sound.add(menuItem_volumeDecrease);
		menuItem_sound.add(menuItem_noSound);

		// 添加到选择菜单框组建上
		group__playerMode.add(menuItem_onlyOneMode);
		group__playerMode.add(menuItem_forOneMode);
		group__playerMode.add(menuItem_turnMode);
		group__playerMode.add(menuItem_randomMode);

		group__playerControl.add(menuItem_AutoChange);
		group__playerControl.add(menuItem_onTop);

		System.out.println("装配菜单结束。");

	}

	/**
	 * Button 设计
	 */
	private void initButton() {

		but_play = new MediaPlayer_Button(new ImageIcon("img/play.png"));
		but_pause = new MediaPlayer_Button(new ImageIcon("img/pause.png"));
		but_stop = new MediaPlayer_Button(new ImageIcon("img/stop.png"));
		but_rewind = new MediaPlayer_Button(new ImageIcon("img/rewind.png"));

		but_fastForward = new MediaPlayer_Button(new ImageIcon(
				"img/fastForward.png"));
		but_sounds = new MediaPlayer_Button(new ImageIcon("img/sound.png"));
		but_skipBackward = new MediaPlayer_Button(new ImageIcon(
				"img/skipBackward.png"));
		but_skipForward = new MediaPlayer_Button(new ImageIcon(
				"img/skipForward.png"));
		but_music = new MediaPlayer_Button(new ImageIcon("img/music.png"));

	}

	/**
	 * 设置操作提示信息
	 */
	private void initSetButton() {
		but_play.setToolTipText("播放");
		but_pause.setToolTipText("暂停");
		but_stop.setToolTipText("停止");
		but_skipBackward.setToolTipText("上一曲");
		but_rewind.setToolTipText("后退");
		but_fastForward.setToolTipText("快进");
		but_skipForward.setToolTipText("下一曲");
		but_sounds.setToolTipText("音量");
		but_music.setToolTipText("切换播放模式");
		but_music.setActionCommand("true");
	}

	/**
	 * 私有内部类用于将各个组件添加到对应面板中
	 */
	private void initSetComponentInPanel() {

		JPopupMenu.setDefaultLightWeightPopupEnabled(false);

		panel_AddButton.addMouseListener(new RightMouseListener());

		panel_down.setLayout(new BorderLayout());
		panel_control.setLayout(new BorderLayout());

		panel_prograBar.setLayout(new BorderLayout());

		slider_time.setValue(0);
		slider_time.setPreferredSize(new Dimension(this.getX(), 15));

		panel_button.setLayout(new BorderLayout());
		panel_AddButton.setLayout(new BoxLayout(panel_AddButton,
				BoxLayout.X_AXIS));

		lable_infor = new JLabel("音量:100 进度:0:0:0");
		TitledBorder titleBorder = new TitledBorder("播放信息");
		titleBorder.setTitleColor(new Color(51, 105, 186));
		titleBorder.setTitleFont(new Font("宋体", 6, 11));
		lable_infor.setBorder(titleBorder);
		lable_infor.setForeground(new Color(51, 105, 186));
		lable_infor.setFont(new Font("宋体", 6, 11));
		lable_infor.setPreferredSize(new Dimension(140, 35));
		panel_Addsounds.add(lable_infor, BorderLayout.CENTER);
		panel_Addsounds.add(but_music);

	}

	private void getSplitPane() {
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				getTabbedPane_player(), getTabbedPane());
		splitpane.setOneTouchExpandable(true);
		splitpane.setDividerLocation(360);
		splitpane.setDividerSize(6);
		splitpane.setResizeWeight(0.8);

	}

	/**
	 * 返回一个视频显示面板的实例。
	 * 
	 * @return 返回一个JPanel
	 */
	private JPanel getView() {
		panel_view = new JPanel();
		panel_view.setBackground(Color.black);
		panel_view.setLayout(new BorderLayout());
		panel_view.addMouseListener(new RightMouseListener());
		label_view = new JLabel(new ImageIcon("img/view.gif"), JLabel.CENTER);
		label_view.setFont(new Font("行书", Font.BOLD, 32));
		panel_view.add(label_view, BorderLayout.CENTER);
		return panel_view;
	}

	/**
	 * 用于获取视窗组件
	 */
	private void getVisualComponent() {
		System.out.println("--------------->获取视窗组件......");
		visual = player.getVisualComponent();
		if (visual != null) {
			visual.addMouseListener(new RightMouseListener());// 注册监听
			visual.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置鼠标显示样式
			panel_view.add(visual, BorderLayout.CENTER);
			panel_view.updateUI();
		}
	}

	/**
	 * 获取一个带视频显示面板的JTabbedPane
	 * 
	 * @return
	 */
	private JTabbedPane getTabbedPane_player() {
		JTabbedPane tab_player = new JTabbedPane(JTabbedPane.TOP);
		tab_player.addTab("正在播放", getView());
		return tab_player;

	}

	/**
	 * 获取一个带播放列表的JTabbedPane
	 * 
	 * @return
	 */
	private JTabbedPane getTabbedPane() {
		JTabbedPane tab_list = new JTabbedPane(JTabbedPane.TOP);
		tab_list.addTab("播放列表", getPlayerList());

		return tab_list;
	}

	/**
	 * 获取播放列表滚动面板
	 * 
	 * @return JScrollPane
	 */
	private JScrollPane getPlayerList() {

		list = new MediaPlayer_List(fileName);
		popupList = new MediaPlayer_List(fileName);
		list.setBackground(new Color(255, 237, 255));
		list.addMouseListener(new RightMouseListener());
		popupList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && (!fileName.isEmpty())) { // 判断是否双击
					list.setSelectedIndex(popupList.getSelectedIndex());
					playMedia(popupList.getSelectedIndex());
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && (!fileName.isEmpty())) { // 判断是否双击
					playMedia(list.getSelectedIndex());
				}
				if (e.getClickCount() == 1 && (!list.isSelectionEmpty())) {
					System.out.println("用户选中文件"
							+ list.getSelectedValue().toString());
					list.setSelectedIndex(list.getSelectedIndex());
				}
			}
		});
		scrollPopup = new JScrollPane();
		scrollPopup
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPopup.getViewport().add(popupList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getViewport().add(list);
		return scrollPane;
	}

	/**
	 * 装配各个面板
	 */
	private void initAddAllCompents() {

		this.add(splitpane, BorderLayout.CENTER);
		this.add(panel_down, BorderLayout.SOUTH);
		panel_down.setLayout(new BorderLayout());
		panel_down.add(panel_play, BorderLayout.WEST);
		panel_down.add(panel_control, BorderLayout.CENTER);
		panel_control.setLayout(new BorderLayout());
		panel_control.add(panel_prograBar, BorderLayout.NORTH);
		panel_prograBar.add(slider_time);
		panel_control.add(panel_button, BorderLayout.SOUTH);
		panel_button.setLayout(new BorderLayout());
		panel_button.add(panel_AddButton, BorderLayout.WEST);
		panel_button.add(panel_Addsounds, BorderLayout.EAST);
	}

	/**
	 * 初始化弹出菜单
	 */
	private void initPopupMenu() {
		System.out.println("---------------初始化右键弹出菜单---------------");
		popupMenuAll = new JPopupMenu();
		popupMenuList = new JPopupMenu();

		menuItem_paly_Popup = new MediaPlayer_MenuItem("播放/暂停");
		menuItem_stop_Popup = new MediaPlayer_MenuItem("停止");
		menuItem_controlPlayer = new MediaPlayer_Menu("播放控制");
		menuItem_allScreen = new MediaPlayer_MenuItem("全屏");

		menuItem_playSelect = new MediaPlayer_MenuItem("播放选中文件");
		menuItem_delete = new MediaPlayer_MenuItem("从列表中删除");
		menuItem_Addfile = new MediaPlayer_MenuItem("向列表中添加");
		menuItem_deleteAll = new MediaPlayer_MenuItem("清除列表文件");
		System.out.println("---------------右键弹出菜单初始化完成---------------");
	}

	/**
	 * 返回一个音量控制组件 JSlider
	 * 
	 * @return JSlider（一个音量控制组件）
	 */
	private JSlider getSoundControl() {
		jSlider_sound = new JSlider(0, 100, 100);
		jSlider_sound.setToolTipText("音量控制");
		jSlider_sound.addChangeListener(new SoundChangeListener());// 对其进行注册监听
		jSlider_sound.setPreferredSize(new Dimension(100, 15));
		return jSlider_sound;
	}

	/**
	 * 装配各类button
	 */
	private void initAddButton() {
		System.out.println("---------------正在装配各类button---------------");
		panel_play.add(but_play);
		panel_AddButton.add(but_stop);
		panel_AddButton.add(but_skipBackward);
		panel_AddButton.add(but_rewind);
		panel_AddButton.add(but_fastForward);
		panel_AddButton.add(but_skipForward);
		panel_AddButton.add(but_sounds);
		panel_AddButton.add(getSoundControl());
		System.out.println("---------------Button装配完成---------------");
	}

	/**
	 * 关闭播放对象
	 */
	public void closePlayerStream() {
		System.out.println(timeThread);
		if (timeThread != null) {
			((ControlProgrecess) timeThread).stopThread();
		}
		if (player != null) {
			System.out.println("停止上次播放文件!");
			player.stop();
			System.out.println("停止上次播放文件流!");
			player.close();
		}
		if (visual != null) {
			panel_view.remove(visual);
		}
	}

	/**
	 * playMedia用于播放媒体对象
	 * 
	 * @param fileIndex
	 *            参数fileIndex为对象序列索引
	 */
	public void playMedia(int fileIndex) {
		System.out.println("当前播放器为" + player);
		if (player != null) {
			closePlayerStream();
		}
		readyToPlayer();
		try {
			System.out.println("获取成功:  开始创建播放其对象player.................");
			if (file != null) {
				player = Manager.createPlayer(new MediaLocator(file.toURI()
						.toURL()));
			} else {
				player = Manager.createPlayer(new MediaLocator(getFileToplayer(
						fileIndex).toURI().toURL()));
			}

			player.addControllerListener(new ControlListener());
			System.out.println("添加成功!正在对文件进行预读..............................");
			player.prefetch();

		} catch (NoPlayerException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		jframe.setTitle(fileName.elementAt(fileIndex).substring(
				fileName.elementAt(fileIndex).indexOf('.') + 1));
		this.validate();
	}

	/**
	 * 一个线程类处理进度条
	 */
	private class ControlProgrecess extends Thread {

		private boolean temp = true;

		public void run() {
			while (newTime <= fileTime && temp) {
				try {
					Thread.sleep(1000);
					slider_time.setValue((int) (newTime++));
					lable_infor.setText("音量:" + jSlider_sound.getValue() + " "
							+ "进度:" + (int) newTime / 3600 + ":"
							+ (int) (newTime % 3600) / 60 + ":"
							+ (int) (newTime % 60));

					System.out.println("音量:" + jSlider_sound.getValue() + " "
							+ "进度:" + (int) newTime / 3600 + ":"
							+ (int) (newTime % 3600) / 60 + ":"
							+ (int) (newTime % 60));

					System.out.println("======================");
				} catch (InterruptedException interruptedexception) {

				}
			}
			temp = true;
		}

		public void stopThread() {
			temp = false;
		}
	}

	/**
	 * 
	 * 对播放的整个过程进行控制。
	 * 
	 * @author Qzhong
	 * 
	 */
	private class ControlListener implements ControllerListener {

		public void controllerUpdate(ControllerEvent e) {

			if (e instanceof RealizeCompleteEvent) {
				timeThread = new ControlProgrecess();
				System.out.println("获得文件播放时间: "
						+ player.getDuration().getSeconds());
				fileTime = player.getDuration().getSeconds();
				slider_time.setMaximum((int) fileTime);
				getVisualComponent();

				return;
			}
			if (e instanceof PrefetchCompleteEvent) {
				slider_time.setValue(0);
				mediaMute(but_sounds.getActionCommand());
				player.getGainControl().setLevel(
						(float) jSlider_sound.getValue() / 100);
				System.out.println("获取音量: "
						+ (float) player.getGainControl().getLevel());
				System.out.println("准备完毕!开始播放...................");
				timeThread.start();
				player.start();
				return;
			}
			if (e instanceof ControllerClosedEvent) {

				return;
			}
			if (e instanceof EndOfMediaEvent) {
				firstPlayer = true;
				if (AllScreenOrOriginal = true) {
					originalView();
					AllScreenOrOriginal = false;
				}
				judgePlayerMode();
				newTime = 0;
				slider_time.setValue(0);
				return;
			}

		}
		

		// 自动判断文件格式并把窗口调成相应模式
		private void judgePlayerMode() {
			System.out.println("正在核对播放模式.......");
			if (menuItem_onlyOneMode.isSelected()) {
				stopMedia();
			} else if (menuItem_forOneMode.isSelected()) {
				pauseToPlayer();
			} else if (menuItem_turnMode.isSelected()) {
				musicChooser(1);
			} else if (menuItem_randomMode.isSelected()) {
				playMedia(randomPodePlayer(list.getLastVisibleIndex()));
			}
		}
	}
	/**RightMouseListener类用于处理右键事件
	 * 
	 * @author Qzhong
	 *
	 */
	class RightMouseListener extends MouseAdapter {
		boolean flag = true;

		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			int mods = e.getModifiers();
			if ((mods & 4) != 0 && (e.getSource() == visual)
					|| (e.getSource() == panel_view)) {
				popupMenuList.add(menuItem_paly_Popup);
				popupMenuList.add(menuItem_stop_Popup);
				popupMenuList.add(menuItem_controlPlayer);
				popupMenuList.add(menuItem_allScreen);
				menuItem_controlPlayer.add(menuItem_skinForwind_Popup);
				menuItem_controlPlayer.add(menuItem_rewind_Popup);
				if (e.getSource() == visual) {
					popupMenuList.show(visual, e.getX(), e.getY());
				}
				if ((mods & 4) != 0 && e.getSource() == panel_view) {
					popupMenuList.show(panel_view, e.getX(), e.getY());
				}
			} else if ((mods & 4) != 0 && e.getSource() == list) {
				popupMenuAll.add(menuItem_playSelect);
				popupMenuAll.add(menuItem_delete);
				popupMenuAll.add(menuItem_Addfile);
				popupMenuAll.add(menuItem_deleteAll);
				popupMenuAll.show(list, e.getX(), e.getY());
			} else if (e.getClickCount() == 2 && visual != null
					&& e.getSource() == visual) {
				if (flag) {
					fullScreenView();
					flag = false;
				} else {
					originalView();
					flag = true;
				}

			} else if (e.getClickCount() == 1 && visual != null
					&& e.getSource() == visual) {
				if (player != null)
					if (tempPause && e.getClickCount() <= 1) {
						pauseMedia();
						tempPause = false;
					} else {
						if (pauseTime != null) {
							pauseToPlayer();
							tempPause = true;
						}
					}
			}
		}
	}

	/**
	 * 
	 * @param int temp
	 */
	protected void musicChooser(int temp) {
		System.out.println("用户选择上一首");
		int index = list.getSelectedIndex() + temp;
		list.setSelectedIndex(index);
		if (temp < 0) {
			if (0 <= index) {
				playMedia(index);
			} else if (index == -1) {
				index = list.getLastVisibleIndex();
				list.setSelectedIndex(index);
				playMedia(index);
			}
		}
		if (temp > 0) {
			list.setSelectedIndex(index);
			if (index <= list.getLastVisibleIndex()) {
				playMedia(index);
			} else if (index > list.getLastVisibleIndex()) {
				index = 0;
				list.setSelectedIndex(index);
				playMedia(index);
			}
		}
	}

	/**
	 * 停止播放让窗口还原初始状态
	 * 
	 */
	private void stopMedia() {
		System.out.println(">>>用户停止播放.............");
		if (timeThread != null) {
			((ControlProgrecess) timeThread).stopThread();
		}
		panel_play.remove(but_pause);
		panel_play.add(but_play);
		panel_play.updateUI();
		newTime = 0;
		slider_time.setValue(0);
		firstPlayer = true;
		closePlayerStream();
		jframe.setTitle("霖雨媒体播放器--by Qzhong");
	}

	/**
	 * 从暂停位置开始播放
	 * 
	 */
	private void pauseToPlayer() {

		timeThread = new ControlProgrecess();
		playerOrPause = true;
		timeThread.start();
		player.start();
		panel_play.remove(but_play);
		panel_play.add(but_pause);
		panel_play.updateUI();
		System.out.println(">>>继续开始播放...........");
	}

	/**
	 * 为播放做准备(还原一些变量和状态)
	 */
	public void readyToPlayer() {
		playerOrPause = true;
		panel_play.remove(but_play);
		panel_play.add(but_pause);
		panel_play.updateUI();
		firstPlayer = false;
		newTime = 0;
		fileTime = 0;
	}

	/**
	 *mediaMute处理静音事件
	 */
	public void mediaMute(String mute) {
		if (mute == "false" && player != null) {
			System.out.println(">>>>用户还原播放器声音!");
			menuItem_noSound.setSelected(false);
			player.getGainControl().setMute(false);
		} else if (mute == "true" && player != null) {
			System.out.println("<<<<用户要求静音！");
			menuItem_noSound.setSelected(true);
			player.getGainControl().setMute(true);
		}
	}

	/**
	 * 随机产生一个播放对象
	 * 
	 * @param listLastIndex
	 * @return
	 */
	public int randomPodePlayer(int listLastIndex) {
		int index = (int) (Math.random() * listLastIndex);
		list.setSelectedIndex(index);
		System.out.println("现在播放第: " + (index + 1) + " 首歌");
		return index;
	}

	/**
	 * 设置还原全屏时保存的大小和位置
	 * 
	 */
	public void setFrameOrignal() {
		jframe.setSize(width, height);
		jframe.setLocation(x, y);
	}

	/**
	 * 退出全屏前的状态
	 * 
	 */
	private void originalView() {
		setFrameOrignal();
		jframe.getContentPane().remove(visual);
		getRootPane().setWindowDecorationStyle(1);
		add(menubar, BorderLayout.NORTH);
		jframe.setAlwaysOnTop(false);
		this.add(panel_down, BorderLayout.SOUTH);
		this.add(splitpane, BorderLayout.CENTER);
		panel_view.add(visual, BorderLayout.CENTER);
		validate();
		System.out.println("用户退出全屏!");
	}

	/**
	 * 打开文件后对文件格式进行判断
	 * 
	 * @param int file
	 * @return File
	 */

	private File getFileToplayer(int file) {

		System.out.println("验证播放文件是否存在");

		fileTime = 0;
		if (file != -1) {
			System.out.println("验证文件成功!文件路径 " + fileDirection.get(file));
			fileDir = fileDirection.get(file);

			System.out.println("播放的文件格式: "
					+ fileName.elementAt(file).substring(
							fileName.elementAt(file).lastIndexOf('.') + 1));

			String fileMode = fileName.elementAt(file).substring(
					fileName.elementAt(file).lastIndexOf('.') + 1);
			int index = list.getSelectedIndex();
			if (menuItem_AutoChange.isSelected() == true) {
				if (fileMode.equals("mp3") || fileMode.equals("MP3")
						|| fileMode.equals("WAV") || fileMode.equals("wav")) {

					changToMusicMode();
				} else if (fileMode.equals("mpg") || fileMode.equals("mpeg")
						|| fileMode.equals("MPG") || fileMode.equals("MPEG")
						|| fileMode.equals("AVI") || fileMode.equals("avi")) {

					changToMoveMode(index);
				}
			}
			but_stop.setFocusable(true);
			System.out.println("验证是否有正在播放文件........");
			if (player != null) {
				closePlayerStream();
			}
		}
		return fileDir;
	}

	/**
	 * 切换到音频模式
	 * 
	 */
	private void changToMusicMode() {
		System.out.println("切换到音频模式.....");
		but_music.setActionCommand("false");
		jframe.setSize(new Dimension(260, 20));
		jframe.setResizable(false);
		jframe.remove(menubar);
		jframe.remove(splitpane);
		jframe.pack();
	}

	/**
	 * 切换到视频模式
	 * 
	 * @param index
	 */
	public void changToMoveMode(int index) {

		System.out.println("切换到视频模式.....");
		but_music.setActionCommand("true");
		jframe.setResizable(true);
		jframe.setSize(500, 400);
		add(menubar, BorderLayout.NORTH);
		jframe.add(splitpane, BorderLayout.CENTER);
		popupList.setListData(fileName);
		list.setListData(fileName);
		list.setSelectedIndex(index);
	}

	/**
	 * 给操作按钮及菜单注册监听
	 */
	private void initAddListenerInMenu() {
		System.out.println(">>>给操作按钮及菜单注册监听.....");
		menuItem_palyOrPause.addActionListener(new CommendActionListener());
		menuItem_paly_Popup.addActionListener(new CommendActionListener());

		menuItem_stop.addActionListener(new CommendActionListener());
		menuItem_stop_Popup.addActionListener(new CommendActionListener());

		menuItem_open.addActionListener(new CommendActionListener());
		menuItem_exit.addActionListener(new CommendActionListener());

		menuItem_stop.addActionListener(new CommendActionListener());

		menuItem_volumeIncrease.addActionListener(new CommendActionListener());
		menuItem_volumeDecrease.addActionListener(new CommendActionListener());
		menuItem_noSound.addActionListener(new CommendActionListener());

		menuItem_skinForwind_Popup
				.addActionListener(new CommendActionListener());
		menuItem_rewind_Popup.addActionListener(new CommendActionListener());

	}

	private class CommendActionListener implements ActionListener {
		@SuppressWarnings("unused")
		boolean tenp = true, temp = true, ifAllScreen = true;

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == menuItem_open) {
				open();
			} else if (source == menuItem_exit) {
				System.exit(0);
			} else if (source == menuItem_palyOrPause
					|| source == menuItem_paly_Popup
					|| source == exitItem_playOrPause_SystemTray) {
				if (tenp && firstPlayer) {
					playMedia(list.getSelectedIndex());
					tenp = false;
				} else {
					if (player != null) {
						if (tempPause && playerOrPause) {
							System.out.println("pause");
							pauseMedia();
							tempPause = false;
						} else if (!playerOrPause) {
							System.out.println("player");
							pauseToPlayer();
							tempPause = true;
						} else {
							pauseMedia();
						}
					}
				}
			} else if (source == menuItem_stop || source == menuItem_stop_Popup
					|| source == exitItem_stop_SystemTray) {
				stopMedia();
				tenp = true;
			} else if (source == menuItem_skinForwind_Popup
					|| source == menuItem_skinForwind) {

				proControl(5);
			} else if (source == menuItem_rewind
					|| source == menuItem_rewind_Popup) {
				proControl(-5);
			} else if (source == menuItem_onTop) {
				if (menuItem_onTop.isSelected()) {
					jframe.setAlwaysOnTop(true);
				} else {
					jframe.setAlwaysOnTop(false);
				}
			} else if (source == menuItem_allScreen) {
				System.out.println("allFull");
				if (visual != null) {
					if (ifAllScreen) {
						fullScreenView();
						menuItem_allScreen.setText("退出全屏");
						ifAllScreen = false;
					} else {
						originalView();
						menuItem_allScreen.setText("全屏");
						ifAllScreen = true;
					}
				}
			} else if (source == menuItem_volumeIncrease) {
				changeSounds(10);
			} else if (source == menuItem_volumeDecrease) {
				changeSounds(-10);
			} else if (source == menuItem_noSound) {
				if (menuItem_noSound.isSelected()) {
					but_sounds.setActionCommand("true");
					but_sounds.setIcon(new ImageIcon("img/nosound_.png"));
					mediaMute(but_sounds.getActionCommand());
				} else {
					but_sounds.setActionCommand("false");
					but_sounds.setIcon(new ImageIcon("img/sound.png"));
					mediaMute(but_sounds.getActionCommand());
				}
			} else if (source == menuItem_help) {
				Help help = new Help(null, "帮助中心", true);
				help.setLocationRelativeTo(null);
				help.setVisible(true);
			} else if (source == menuItem_about) {
				About about = new About(null, "关于我们", true);
				about.setLocationRelativeTo(null);
				about.setVisible(true);

			}
		}
	}

	/**
	 * 暂停播放
	 * 
	 */
	public void pauseMedia() {
		pauseTime = player.getMediaTime();
		playerOrPause = false;
		((ControlProgrecess) timeThread).stopThread();
		player.stop();
		panel_play.remove(but_pause);
		panel_play.add(but_play);
		panel_play.updateUI();
		System.out.println("暂停播放...........");

	}

	/**
	 * 用于改变音量
	 * 
	 * @param temp
	 */
	private void changeSounds(int temp) {

		int newSounds = jSlider_sound.getValue() + temp;
		if (newSounds < 0 || newSounds > 100) {
			newSounds = jSlider_sound.getValue();
		}
		but_sounds.setActionCommand("false");
		but_sounds.setIcon(new ImageIcon("img/sound.png"));
		mediaMute(but_sounds.getActionCommand());
		menuItem_noSound.setSelected(false);
		jSlider_sound.setValue(newSounds);
	}

	/**
	 * 时间参数newTime的增量设置（用于快进或者快退）
	 * 
	 * @param temp
	 */
	public void proControl(int temp) {
		newTime = slider_time.getValue() + temp;
		if (newTime >= fileTime && temp >= 0) {
			newTime = slider_time.getValue();
		} else if (newTime <= 0 && temp <= 0) {
			newTime = 0;
		}
		if (player != null) {
			player.setMediaTime(new Time(newTime));
		}
	}

	/**
	 * 窗口总是在最前端
	 * 
	 */
//	public void alwaysOnTop() {
//		if (menuItem_onTop.isSelected()) {
//			jframe.setAlwaysOnTop(true);
//		} else {
//			jframe.setAlwaysOnTop(false);
//		}
//	}

	/**
	 * 打开文件添加到列表并自动播放
	 * 
	 * @return
	 */
	public File[] open() {
		System.out.println("准备打开文件选择器....");
		JFileChooser fileChooser = new JFileChooser();

		// 文件过滤器
		FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3",
				"MP3", "wav", "mpg", "mpeg", "wav", "avi", "gif", "GIF", "PNG",
				"png", "jpg", "JPG");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// 仅限显示文件
		fileChooser.setMultiSelectionEnabled(true);// 允许多选

		System.out.println("打开成功:  显示文件选择器");

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("用户取消选择文件");
			return null;
		} else {
			System.out.println("用户选择文件" + fileChooser.getSelectedFile());
			files = fileChooser.getSelectedFiles();
			System.out.println("正在把文件添加到列表");
			addList(files);
			saveListToFile(files);
			if (player == null) {
				playMedia(randomPodePlayer(list.getLastVisibleIndex()));
			}
			return files;
		}
	}

	/**
	 * 进入全屏状态
	 * 
	 */
	private void fullScreenView() {
		AllScreenOrOriginal = true;
		getFrameInfor();
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension fullSize = tk.getScreenSize();
		getRootPane().setWindowDecorationStyle(0);
		setSize(fullSize);
		setLocation(0, 0);
		remove(menubar);
		remove(panel_down);
		jframe.getContentPane().setSize(fullSize);
		setSize(fullSize);
		this.remove(splitpane);
		jframe.setAlwaysOnTop(true);
		jframe.getContentPane().add(visual, BorderLayout.CENTER);
		jframe.validate();
		System.out.println("用户选择全屏!");
	}

	/**
	 * 获取全屏前的窗口的大小,位置
	 * 
	 */
	public void getFrameInfor() {
		width = jframe.getWidth();
		height = jframe.getHeight();
		x = jframe.getX();
		y = jframe.getY();
	}

	/**
	 * 自动添加文件到列表
	 */
	public void AutoAddFileToList() {
		list.setListData(fileName);
		popupList.setListData(fileName);
		randomPodePlayer(fileName.size());
	}

	/**
	 * 把文件添加到列表
	 * 
	 * @param file
	 */
	public void addList(File[] file) {

		System.out.println(file.length);
		System.out.println(fileName.size());
		for (int i = fileName.size(); i < file.length; i++) {
			fileName.add(i, i + 1 + ". " + file[i].getName());
			fileDirection.add(i, file[i]);
		}
		popupList.setListData(fileName);
		list.setListData(fileName);
		list.setSelectedIndex(0);
		fileName.trimToSize();
		fileDirection.trimToSize();

	}

	/**
	 * 从列表中删除文件
	 * 
	 * @param fileIndex
	 * @param ifAll
	 */
	public void removeFile(int fileIndex, boolean ifAll) {

		if ((fileIndex < fileName.size()) && !ifAll && fileIndex != -1) {
			fileName.removeElementAt(fileIndex);
			fileDirection.removeElementAt(fileIndex);

		} else if (fileIndex != -1) {
			fileName.clear();
			fileDirection.clear();
		}
		list.setListData(fileName);
		popupList.setListData(fileName);
		list.setSelectedIndex(fileIndex);
		System.out.println("用户删除了下标为" + fileIndex);
		// saveListToFile((File[]) fileDirection.toArray());
		list.updateUI();
	}

	/**
	 * 把列表中内容保存到文件
	 * 
	 * @param files
	 */
	private void saveListToFile(File[] files) {
		BufferedWriter bf = null;
		try {
			bf = new BufferedWriter(new FileWriter(userInfro));
			for (int i = 0; i < files.length; i++) {
				bf.write(files[i] + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bf.flush();
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向按钮注册监听
	 */
	private void initAddListenerInButton() {
		but_play.addActionListener(new CommendActionListener());
		but_play.addActionListener(new CommendActionListener());

		menuItem_onTop.addActionListener(new CommendActionListener());

		menuItem_allScreen.addActionListener(new CommendActionListener());

		menuItem_skinForwind.addActionListener(new CommendActionListener());
		menuItem_rewind.addActionListener(new CommendActionListener());
		menuItem_skinForwind_Popup
				.addActionListener(new CommendActionListener());
		menuItem_rewind_Popup.addActionListener(new CommendActionListener());

		menuItem_help.addActionListener(new CommendActionListener());
		menuItem_about.addActionListener(new CommendActionListener());

		exitItem_playOrPause_SystemTray
				.addActionListener(new CommendActionListener());
		exitItem_stop_SystemTray.addActionListener(new CommendActionListener());
	}

	/**
	 * 给弹出菜单注册监听
	 */
	private void initAddListenerInPopupMenu() {
		menuItem_delete.addActionListener(new PupupSeletedListener());
		menuItem_deleteAll.addActionListener(new PupupSeletedListener());

		menuItem_playSelect.addActionListener(new PupupSeletedListener());
		menuItem_Addfile.addActionListener(new PupupSeletedListener());
	}

	/**
	 * 弹出菜单监听器
	 * 
	 * @author Qzhong
	 * 
	 */
	private class PupupSeletedListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			pupupSeletedAction(source);
		}
	}

	/**
	 * pupupSeletedAction为弹出菜单命令源选择
	 * 
	 * @param source
	 */
	public void pupupSeletedAction(Object source) {
		if (source == menuItem_delete) {
			removeFile(list.getSelectedIndex(), false);
		} else if (source == menuItem_deleteAll) {
			removeFile(list.getSelectedIndex(), true);
		} else if (source == menuItem_Addfile) {
			open();
		} else if (source == menuItem_playSelect) {
			playMedia(list.getSelectedIndex());
		}
	}

	/**
	 * controlSliderTime提供为用户更改进度拖动
	 * 
	 */
	public void controlSliderTime() {

		slider_time.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				if (player != null) {
					newTime = slider_time.getValue();
					player.setMediaTime(new Time(newTime));
					System.out.println("调整文件到:　" + newTime);
					System.out.println("-----------------------");
					player.start();
				} else {
					slider_time.setValue(0);
					newTime = 0;
					fileTime = 0;
				}
			}
		});
	}

	/**此类提供为用户更改音量
	 * 
	 * @author Qzhong
	 *
	 */
	private class SoundChangeListener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			but_sounds.setActionCommand("false");
			but_sounds.setIcon(new ImageIcon("img/sound.png"));
			menuItem_noSound.setSelected(false);
			System.out.println("调节音量大小为: " + (float) jSlider_sound.getValue()
					/ 100);
			if (player != null) {
				player.getGainControl().setLevel(
						(float) jSlider_sound.getValue() / 100);
				System.out.println("音量大小为"
						+ (float) player.getGainControl().getLevel());
				mediaMute(but_sounds.getActionCommand());
			}
			lable_infor.setText("音量:" + jSlider_sound.getValue() + " " + "进度:"
					+ (int) newTime / 3600 + ":" + (int) (newTime % 3600) / 60
					+ ":" + (int) (newTime % 60));
			System.out.println("-----------------------");
			jSlider_sound.setToolTipText(jSlider_sound.getValue() + "");
		}
	}
	/**
	 * 音乐模式下的按钮处理
	 * 
	 */
	public void initMusicModeControl() {
		but_music.addMouseListener(new MouseAdapter() {

			MediaPlayer_Menu popup_menuList = new MediaPlayer_Menu("播放曲目");

			public void mouseClicked(MouseEvent e) {
				int mod = e.getModifiers() & 4;
				if (e.getButton() == MouseEvent.BUTTON1
						&& e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					if (but_music.getActionCommand() == "true") {
						changToMusicMode();
					} else {
						changToMoveMode(index);
					}
				} else if (mod != 0) {
					System.out.println("Ok");

					popup_menu.add(popup_menuList);
					popup_menu.show(but_music, e.getX(), e.getY());
					popupList.setSelectedIndex(list.getSelectedIndex());
					popup_menuList.add(popupList);
				}
			}
		});
	}

	/**
	 * 从文件中读取列表对象
	 * 
	 */
	private void readFileToList() {
		BufferedReader br = null;
		try {
			if (!userInfro.exists()) {
				userInfro.createNewFile();
			}
			if (userInfro.exists()) {
				br = new BufferedReader(new FileReader(userInfro));
				String filePath;
				for (int i = 1; (filePath = br.readLine()) != null; i++) {
					fileDirection.addElement(new File(filePath));
					fileName.addElement(i
							+ ". "
							+ filePath
									.substring(filePath.lastIndexOf('\\') + 1));
				}
				System.out.println("读写完成................");
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给菜单设置快捷键
	 */
	private void setAcceleratorInMenuItem() {
		menuItem_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));
		menuItem_allScreen.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_ENTER, InputEvent.CTRL_MASK));
		menuItem_stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		menuItem_about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_MASK));
		menuItem_help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				InputEvent.CTRL_MASK));
		menuItem_noSound.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_MASK));
		menuItem_palyOrPause.setAccelerator(KeyStroke.getKeyStroke("SPACE"));

	}
	/**为用户提供按钮的控制
	 * 
	 */
	private void initButtonChangFaceListener() {
		but_play.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (firstPlayer) {
					if (list.getSelectedIndex() != -1) {
						playMedia(list.getSelectedIndex());
					}
				} else {
					pauseToPlayer();
				}
			}

			public void mouseEntered(MouseEvent e) {
				but_play.setIcon(new ImageIcon("img/Play_.png"));
				panel_play.validate();
			}

			public void mouseExited(MouseEvent e) {
				but_play.setIcon(new ImageIcon("img/Play.png"));
				panel_play.validate();
			}
		});

		but_pause.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (player != null) {
					pauseMedia();
				}
			}

			public void mouseEntered(MouseEvent e) {
				but_pause.setIcon(new ImageIcon("img/pause_.png"));
				panel_play.validate();
			}

			public void mouseExited(MouseEvent e) {
				but_pause.setIcon(new ImageIcon("img/pause.png"));
				panel_play.validate();
			}
		});
		but_stop.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				stopMedia();
			}

			public void mouseEntered(MouseEvent e) {
				but_stop.setIcon(new ImageIcon("img/stop_.png"));

			}

			public void mouseExited(MouseEvent e) {
				but_stop.setIcon(new ImageIcon("img/stop.png"));

			}
		});

		// but_skipBackward和个上一曲
		but_skipBackward.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (list.getSelectedIndex() != -1) {
					System.out.println("用户选择上一曲播放");
					musicChooser(-1);
				} else {
					System.out.println("列表中没有内容");
					return;
				}
			}

			public void mouseEntered(MouseEvent e) {
				but_skipBackward
						.setIcon(new ImageIcon("img/skipBackward_.png"));

			}

			public void mouseExited(MouseEvent e) {
				but_skipBackward.setIcon(new ImageIcon("img/skipBackward.png"));

			}
		});
		but_skipForward.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				if (list.getSelectedIndex() != -1) {
					System.out.println("用户选择下一曲播放");
					musicChooser(1);
				} else {
					System.out.println("列表中没有内容");
					return;
				}
			}

			public void mouseEntered(MouseEvent e) {
				but_skipForward.setIcon(new ImageIcon("img/skipForward_.png"));

			}

			public void mouseExited(MouseEvent e) {
				but_skipForward.setIcon(new ImageIcon("img/skipForward.png"));

			}
		});
		but_rewind.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				proControl(-5);
			}

			public void mouseEntered(MouseEvent e) {

				but_rewind.setIcon(new ImageIcon("img/rewind_.png"));

			}

			public void mouseExited(MouseEvent e) {
				but_rewind.setIcon(new ImageIcon("img/rewind.png"));

			}
		});
		but_fastForward.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				proControl(5);
			}

			public void mouseEntered(MouseEvent e) {
				but_fastForward.setIcon(new ImageIcon("img/fastForward_.png"));

			}

			public void mouseExited(MouseEvent e) {
				but_fastForward.setIcon(new ImageIcon("img/fastForward.png"));

			}
		});

		but_sounds.addMouseListener(new MouseAdapter() {
			boolean temp = false;

			public void mouseClicked(MouseEvent e) {
				if (temp) {
					but_sounds.setActionCommand("false");
					but_sounds.setIcon(new ImageIcon("img/sound.png"));
					mediaMute(but_sounds.getActionCommand());
					menuItem_noSound.setSelected(false);
					temp = false;
				} else {
					but_sounds.setActionCommand("true");
					but_sounds.setIcon(new ImageIcon("img/nosound_.png"));
					mediaMute(but_sounds.getActionCommand());
					menuItem_noSound.setSelected(true);
					temp = true;
				}
			}
		});
	}

	/**
	 * 对MediaPlayerPanel进行初始化。
	 */
	public void init() {
		this.add(menubar, BorderLayout.NORTH);
//		changWindowIcon();
		initPanel();// 初始化各个面板
		initButton();// 初始化按钮
		initSetComponentInPanel();// 将各个组件添加到对应面板中
		initMenu();// 初始化菜单
		initPopupMenu();// 初始化右键弹出菜单
		initAddMenu();// 装配菜单
		getSplitPane();// 装配SplitPane
		initAddListenerInMenu();// 给操作菜单注册监听
		initAddButton();// 装配各类button
		initAddListenerInButton();// 向按钮注册监听
		initAddListenerInPopupMenu();// 给菜单注册监听
		initButtonChangFaceListener();
		initAddAllCompents();// 装配各个面板
		initSetButton();// 设置按钮提示信息。
		controlSliderTime();// 提供为用户更改进度拖动
		initMusicModeControl();// 音乐模式下的按钮处理
		setAcceleratorInMenuItem();// 给菜单设置快捷键
//		initSystemTray();
		readFileToList();// 从文件中读取列表对象
		AutoAddFileToList();// 自动添加文件到列表
		
		

	}

}
