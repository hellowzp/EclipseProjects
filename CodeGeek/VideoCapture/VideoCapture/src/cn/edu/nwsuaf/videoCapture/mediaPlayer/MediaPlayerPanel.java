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
	 * ���MediaPlayerPanel��JFrame
	 */
	JFrame jframe;
	/**
	 * MediaPlayerPanel�ĸ߶�
	 */
	private int height = 380;
	/**
	 * MediaPlayerPanel�Ŀ��
	 */
	private int width = 530;

	static int x, y;// �������ڵ�����λ��

	/**
	 * ʱ�����
	 */
	static double newTime, fileTime;
	/**
	 * ���Ʋ��Ž��ȵ��߳�
	 */
	static Thread timeThread;
	/**
	 * ��������һЩ״̬����
	 */
	@SuppressWarnings("unused")
	private boolean firstPlayer = true, tempPause = true, playerOrPause = true,
			AllScreenOrOriginal = false;
	/**
	 * player������������
	 */
	private Player player;
	/**
	 * visualΪ�Ӵ����
	 */
	private Component visual;
	private File file;

	/**
	 * userInfro���ڴ���б��ļ����ݵ��ļ�
	 */
	File userInfro = new File("user.dat");

	/**
	 * �޲ι���
	 */
	public MediaPlayerPanel() {
		super();
		jframe = new JFrame();
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// ������岼��

	}

	/**
	 * 
	 * @param jframe
	 */
	public MediaPlayerPanel(JFrame jframe) {
		super();
		this.jframe = jframe;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// ������岼��

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
		this.setLayout(new BorderLayout());// ������岼��

	}

	public MediaPlayerPanel(File file) {

		super();
		this.file = file;
		this.setSize(width, height);
		this.setLayout(new BorderLayout());// ������岼��

	}

	private JLabel lable_infor, label_view;
	private JSplitPane splitpane;
	/**
	 * panel_viewΪ��Ƶ���.panel_downΪ���岼��,�Ѵ��ڷֳ�����������.panel_playΪ��Ӳ��Ű�ť
	 * panel_control����Ϊ��ӽ�����,�������ƺ�������ť�����.panel_prograBarΪ��ӽ����������.
	 * panel_buttonΪ��Ӱ�ť�����
	 */
	private JPanel panel_view, panel_down, panel_play, panel_control,
			panel_prograBar, panel_button, panel_AddButton, panel_Addsounds;

	/**
	 * ���˵���
	 */
	private JMenuBar menubar = new MediaPlayer_MenuBar();
	/**
	 * ���˵�
	 */
	private MediaPlayer_Menu jmenu_file, jmenu_paly, jmenu_help;
	private JMenu menuItem_sound, jmenu_view, menu_control, menu_playerMode,
			menuItem_controlPlayer;// ��ѡ��˵�
	/**
	 * ���˵���
	 */
	private MediaPlayer_MenuItem menuItem_open, menuItem_exit,
			menuItem_palyOrPause, menuItem_stop, menuItem_help,
			menuItem_volumeIncrease, menuItem_volumeDecrease, menuItem_about,
			menuItem_rewind, menuItem_skinForwind;
	/**
	 * ��ѡ��˵���
	 */
	private MediaPlayer_RadioButtonMenuItem menuItem_onlyOneMode,
			menuItem_forOneMode, menuItem_turnMode, menuItem_randomMode,
			menuItem_onTop, menuItem_AutoChange, menuItem_noSound;

	/**
	 * but_last-��һ��,but_pla-����,but_inte-��ͣ,but_stopֹͣ,but_next��һ��,but_speed���,
	 * but_recede����
	 * 
	 */
	private MediaPlayer_Button but_rewind, but_play, but_stop, but_fastForward,
			but_sounds, but_skipBackward, but_skipForward, but_pause,
			but_music;
	private ButtonGroup group__playerMode = new ButtonGroup();

	private ButtonGroup group__playerControl = new ButtonGroup();
	private Vector<String> fileName = new Vector<String>();// musicList-����װ�б��е�Ԫ��

	private Vector<File> fileDirection = new Vector<File>();

	private JSlider slider_time = new JSlider();// slider_timeʱ�������
	private JPopupMenu popupMenuAll, popupMenuList;// �Ҽ������˵�
	private JSlider jSlider_sound;// �������Ƹ�

	/**
	 * �Ҽ��˵���
	 */
	private MediaPlayer_MenuItem menuItem_paly_Popup, menuItem_stop_Popup,
			menuItem_skinForwind_Popup, menuItem_rewind_Popup,
			menuItem_Addfile, menuItem_allScreen, menuItem_delete,
			menuItem_deleteAll, menuItem_playSelect;
	/**
	 * ����û�ѡ��ĵȴ����ŵ��ļ�
	 */
	private File[] files;

	private JPopupMenu popup_menu = new JPopupMenu();

	private File fileDir;

	private JScrollPane scrollPopup;

	private Time pauseTime;
	/**
	 * �����б�
	 */
	private MediaPlayer_List list, popupList;

	MenuItem exitItem_playOrPause_SystemTray = new MenuItem("����/��ͣ");

	MenuItem exitItem_stop_SystemTray = new MenuItem("ֹͣ");

	/**
	 * ˽�з���initPanel������ʼ�������������ֵ����
	 */
	private void initPanel() {
		System.out.println("��ʼ���������������......");
		panel_down = new JPanel();

		panel_play = new JPanel();

		panel_control = new JPanel();

		panel_prograBar = new JPanel();

		panel_button = new JPanel();

		panel_AddButton = new JPanel();

		panel_Addsounds = new JPanel();
		System.out.println("������幹����ɡ�");

	}

	/**
	 * ��ʼ���˵�
	 */
	private void initMenu() {
		System.out.println("�������˵���ʼ����......");
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		jmenu_file = new MediaPlayer_Menu("�ļ�");
		jmenu_paly = new MediaPlayer_Menu("����");
		jmenu_view = new MediaPlayer_Menu("��ʾ");
		jmenu_help = new MediaPlayer_Menu("����");

		menuItem_onTop = new MediaPlayer_RadioButtonMenuItem("ǰ����ʾ");

		menuItem_sound = new MediaPlayer_Menu("����");

		menuItem_open = new MediaPlayer_MenuItem("���ļ�");
		menuItem_exit = new MediaPlayer_MenuItem("�˳�");

		menuItem_palyOrPause = new MediaPlayer_MenuItem("����/��ͣ");
		menuItem_stop = new MediaPlayer_MenuItem("ֹͣ");

		menu_control = new MediaPlayer_Menu("���ſ���");
		menu_playerMode = new MediaPlayer_Menu("�����б�");

		menuItem_skinForwind = new MediaPlayer_MenuItem("���");
		menuItem_rewind = new MediaPlayer_MenuItem("����");
		menuItem_skinForwind_Popup = new MediaPlayer_MenuItem("���");
		menuItem_rewind_Popup = new MediaPlayer_MenuItem("����");

		menuItem_AutoChange = new MediaPlayer_RadioButtonMenuItem("�Զ��л�", true);
		menuItem_about = new MediaPlayer_MenuItem("����");
		menuItem_help = new MediaPlayer_MenuItem("����");

		menuItem_volumeIncrease = new MediaPlayer_MenuItem("����");
		menuItem_volumeDecrease = new MediaPlayer_MenuItem("��С");
		menuItem_noSound = new MediaPlayer_RadioButtonMenuItem("����");

		menuItem_onlyOneMode = new MediaPlayer_RadioButtonMenuItem("��������");
		menuItem_forOneMode = new MediaPlayer_RadioButtonMenuItem("����ѭ��");
		menuItem_turnMode = new MediaPlayer_RadioButtonMenuItem("ѭ������");
		menuItem_randomMode = new MediaPlayer_RadioButtonMenuItem("�������", true);

		System.out.println("�˵���ʼ����ɡ�");
	}

	/**
	 * �Բ˵��������װ��
	 */
	private void initAddMenu() {
		System.out.println("�˵�װ�俪ʼ......");
		// ������˵����˵���
		menubar.add(jmenu_file);
		menubar.add(jmenu_paly);
		menubar.add(jmenu_view);
		menubar.add(jmenu_help);
		// ��ӵ����˵���
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

		// ��ӵ�ѡ��˵����齨��
		group__playerMode.add(menuItem_onlyOneMode);
		group__playerMode.add(menuItem_forOneMode);
		group__playerMode.add(menuItem_turnMode);
		group__playerMode.add(menuItem_randomMode);

		group__playerControl.add(menuItem_AutoChange);
		group__playerControl.add(menuItem_onTop);

		System.out.println("װ��˵�������");

	}

	/**
	 * Button ���
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
	 * ���ò�����ʾ��Ϣ
	 */
	private void initSetButton() {
		but_play.setToolTipText("����");
		but_pause.setToolTipText("��ͣ");
		but_stop.setToolTipText("ֹͣ");
		but_skipBackward.setToolTipText("��һ��");
		but_rewind.setToolTipText("����");
		but_fastForward.setToolTipText("���");
		but_skipForward.setToolTipText("��һ��");
		but_sounds.setToolTipText("����");
		but_music.setToolTipText("�л�����ģʽ");
		but_music.setActionCommand("true");
	}

	/**
	 * ˽���ڲ������ڽ����������ӵ���Ӧ�����
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

		lable_infor = new JLabel("����:100 ����:0:0:0");
		TitledBorder titleBorder = new TitledBorder("������Ϣ");
		titleBorder.setTitleColor(new Color(51, 105, 186));
		titleBorder.setTitleFont(new Font("����", 6, 11));
		lable_infor.setBorder(titleBorder);
		lable_infor.setForeground(new Color(51, 105, 186));
		lable_infor.setFont(new Font("����", 6, 11));
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
	 * ����һ����Ƶ��ʾ����ʵ����
	 * 
	 * @return ����һ��JPanel
	 */
	private JPanel getView() {
		panel_view = new JPanel();
		panel_view.setBackground(Color.black);
		panel_view.setLayout(new BorderLayout());
		panel_view.addMouseListener(new RightMouseListener());
		label_view = new JLabel(new ImageIcon("img/view.gif"), JLabel.CENTER);
		label_view.setFont(new Font("����", Font.BOLD, 32));
		panel_view.add(label_view, BorderLayout.CENTER);
		return panel_view;
	}

	/**
	 * ���ڻ�ȡ�Ӵ����
	 */
	private void getVisualComponent() {
		System.out.println("--------------->��ȡ�Ӵ����......");
		visual = player.getVisualComponent();
		if (visual != null) {
			visual.addMouseListener(new RightMouseListener());// ע�����
			visual.setCursor(new Cursor(Cursor.HAND_CURSOR));// ���������ʾ��ʽ
			panel_view.add(visual, BorderLayout.CENTER);
			panel_view.updateUI();
		}
	}

	/**
	 * ��ȡһ������Ƶ��ʾ����JTabbedPane
	 * 
	 * @return
	 */
	private JTabbedPane getTabbedPane_player() {
		JTabbedPane tab_player = new JTabbedPane(JTabbedPane.TOP);
		tab_player.addTab("���ڲ���", getView());
		return tab_player;

	}

	/**
	 * ��ȡһ���������б��JTabbedPane
	 * 
	 * @return
	 */
	private JTabbedPane getTabbedPane() {
		JTabbedPane tab_list = new JTabbedPane(JTabbedPane.TOP);
		tab_list.addTab("�����б�", getPlayerList());

		return tab_list;
	}

	/**
	 * ��ȡ�����б�������
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
				if (e.getClickCount() == 2 && (!fileName.isEmpty())) { // �ж��Ƿ�˫��
					list.setSelectedIndex(popupList.getSelectedIndex());
					playMedia(popupList.getSelectedIndex());
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && (!fileName.isEmpty())) { // �ж��Ƿ�˫��
					playMedia(list.getSelectedIndex());
				}
				if (e.getClickCount() == 1 && (!list.isSelectionEmpty())) {
					System.out.println("�û�ѡ���ļ�"
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
	 * װ��������
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
	 * ��ʼ�������˵�
	 */
	private void initPopupMenu() {
		System.out.println("---------------��ʼ���Ҽ������˵�---------------");
		popupMenuAll = new JPopupMenu();
		popupMenuList = new JPopupMenu();

		menuItem_paly_Popup = new MediaPlayer_MenuItem("����/��ͣ");
		menuItem_stop_Popup = new MediaPlayer_MenuItem("ֹͣ");
		menuItem_controlPlayer = new MediaPlayer_Menu("���ſ���");
		menuItem_allScreen = new MediaPlayer_MenuItem("ȫ��");

		menuItem_playSelect = new MediaPlayer_MenuItem("����ѡ���ļ�");
		menuItem_delete = new MediaPlayer_MenuItem("���б���ɾ��");
		menuItem_Addfile = new MediaPlayer_MenuItem("���б������");
		menuItem_deleteAll = new MediaPlayer_MenuItem("����б��ļ�");
		System.out.println("---------------�Ҽ������˵���ʼ�����---------------");
	}

	/**
	 * ����һ������������� JSlider
	 * 
	 * @return JSlider��һ���������������
	 */
	private JSlider getSoundControl() {
		jSlider_sound = new JSlider(0, 100, 100);
		jSlider_sound.setToolTipText("��������");
		jSlider_sound.addChangeListener(new SoundChangeListener());// �������ע�����
		jSlider_sound.setPreferredSize(new Dimension(100, 15));
		return jSlider_sound;
	}

	/**
	 * װ�����button
	 */
	private void initAddButton() {
		System.out.println("---------------����װ�����button---------------");
		panel_play.add(but_play);
		panel_AddButton.add(but_stop);
		panel_AddButton.add(but_skipBackward);
		panel_AddButton.add(but_rewind);
		panel_AddButton.add(but_fastForward);
		panel_AddButton.add(but_skipForward);
		panel_AddButton.add(but_sounds);
		panel_AddButton.add(getSoundControl());
		System.out.println("---------------Buttonװ�����---------------");
	}

	/**
	 * �رղ��Ŷ���
	 */
	public void closePlayerStream() {
		System.out.println(timeThread);
		if (timeThread != null) {
			((ControlProgrecess) timeThread).stopThread();
		}
		if (player != null) {
			System.out.println("ֹͣ�ϴβ����ļ�!");
			player.stop();
			System.out.println("ֹͣ�ϴβ����ļ���!");
			player.close();
		}
		if (visual != null) {
			panel_view.remove(visual);
		}
	}

	/**
	 * playMedia���ڲ���ý�����
	 * 
	 * @param fileIndex
	 *            ����fileIndexΪ������������
	 */
	public void playMedia(int fileIndex) {
		System.out.println("��ǰ������Ϊ" + player);
		if (player != null) {
			closePlayerStream();
		}
		readyToPlayer();
		try {
			System.out.println("��ȡ�ɹ�:  ��ʼ�������������player.................");
			if (file != null) {
				player = Manager.createPlayer(new MediaLocator(file.toURI()
						.toURL()));
			} else {
				player = Manager.createPlayer(new MediaLocator(getFileToplayer(
						fileIndex).toURI().toURL()));
			}

			player.addControllerListener(new ControlListener());
			System.out.println("��ӳɹ�!���ڶ��ļ�����Ԥ��..............................");
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
	 * һ���߳��ദ�������
	 */
	private class ControlProgrecess extends Thread {

		private boolean temp = true;

		public void run() {
			while (newTime <= fileTime && temp) {
				try {
					Thread.sleep(1000);
					slider_time.setValue((int) (newTime++));
					lable_infor.setText("����:" + jSlider_sound.getValue() + " "
							+ "����:" + (int) newTime / 3600 + ":"
							+ (int) (newTime % 3600) / 60 + ":"
							+ (int) (newTime % 60));

					System.out.println("����:" + jSlider_sound.getValue() + " "
							+ "����:" + (int) newTime / 3600 + ":"
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
	 * �Բ��ŵ��������̽��п��ơ�
	 * 
	 * @author Qzhong
	 * 
	 */
	private class ControlListener implements ControllerListener {

		public void controllerUpdate(ControllerEvent e) {

			if (e instanceof RealizeCompleteEvent) {
				timeThread = new ControlProgrecess();
				System.out.println("����ļ�����ʱ��: "
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
				System.out.println("��ȡ����: "
						+ (float) player.getGainControl().getLevel());
				System.out.println("׼�����!��ʼ����...................");
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
		

		// �Զ��ж��ļ���ʽ���Ѵ��ڵ�����Ӧģʽ
		private void judgePlayerMode() {
			System.out.println("���ں˶Բ���ģʽ.......");
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
	/**RightMouseListener�����ڴ����Ҽ��¼�
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
		System.out.println("�û�ѡ����һ��");
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
	 * ֹͣ�����ô��ڻ�ԭ��ʼ״̬
	 * 
	 */
	private void stopMedia() {
		System.out.println(">>>�û�ֹͣ����.............");
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
		jframe.setTitle("����ý�岥����--by Qzhong");
	}

	/**
	 * ����ͣλ�ÿ�ʼ����
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
		System.out.println(">>>������ʼ����...........");
	}

	/**
	 * Ϊ������׼��(��ԭһЩ������״̬)
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
	 *mediaMute�������¼�
	 */
	public void mediaMute(String mute) {
		if (mute == "false" && player != null) {
			System.out.println(">>>>�û���ԭ����������!");
			menuItem_noSound.setSelected(false);
			player.getGainControl().setMute(false);
		} else if (mute == "true" && player != null) {
			System.out.println("<<<<�û�Ҫ������");
			menuItem_noSound.setSelected(true);
			player.getGainControl().setMute(true);
		}
	}

	/**
	 * �������һ�����Ŷ���
	 * 
	 * @param listLastIndex
	 * @return
	 */
	public int randomPodePlayer(int listLastIndex) {
		int index = (int) (Math.random() * listLastIndex);
		list.setSelectedIndex(index);
		System.out.println("���ڲ��ŵ�: " + (index + 1) + " �׸�");
		return index;
	}

	/**
	 * ���û�ԭȫ��ʱ����Ĵ�С��λ��
	 * 
	 */
	public void setFrameOrignal() {
		jframe.setSize(width, height);
		jframe.setLocation(x, y);
	}

	/**
	 * �˳�ȫ��ǰ��״̬
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
		System.out.println("�û��˳�ȫ��!");
	}

	/**
	 * ���ļ�����ļ���ʽ�����ж�
	 * 
	 * @param int file
	 * @return File
	 */

	private File getFileToplayer(int file) {

		System.out.println("��֤�����ļ��Ƿ����");

		fileTime = 0;
		if (file != -1) {
			System.out.println("��֤�ļ��ɹ�!�ļ�·�� " + fileDirection.get(file));
			fileDir = fileDirection.get(file);

			System.out.println("���ŵ��ļ���ʽ: "
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
			System.out.println("��֤�Ƿ������ڲ����ļ�........");
			if (player != null) {
				closePlayerStream();
			}
		}
		return fileDir;
	}

	/**
	 * �л�����Ƶģʽ
	 * 
	 */
	private void changToMusicMode() {
		System.out.println("�л�����Ƶģʽ.....");
		but_music.setActionCommand("false");
		jframe.setSize(new Dimension(260, 20));
		jframe.setResizable(false);
		jframe.remove(menubar);
		jframe.remove(splitpane);
		jframe.pack();
	}

	/**
	 * �л�����Ƶģʽ
	 * 
	 * @param index
	 */
	public void changToMoveMode(int index) {

		System.out.println("�л�����Ƶģʽ.....");
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
	 * ��������ť���˵�ע�����
	 */
	private void initAddListenerInMenu() {
		System.out.println(">>>��������ť���˵�ע�����.....");
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
						menuItem_allScreen.setText("�˳�ȫ��");
						ifAllScreen = false;
					} else {
						originalView();
						menuItem_allScreen.setText("ȫ��");
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
				Help help = new Help(null, "��������", true);
				help.setLocationRelativeTo(null);
				help.setVisible(true);
			} else if (source == menuItem_about) {
				About about = new About(null, "��������", true);
				about.setLocationRelativeTo(null);
				about.setVisible(true);

			}
		}
	}

	/**
	 * ��ͣ����
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
		System.out.println("��ͣ����...........");

	}

	/**
	 * ���ڸı�����
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
	 * ʱ�����newTime���������ã����ڿ�����߿��ˣ�
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
	 * ������������ǰ��
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
	 * ���ļ���ӵ��б��Զ�����
	 * 
	 * @return
	 */
	public File[] open() {
		System.out.println("׼�����ļ�ѡ����....");
		JFileChooser fileChooser = new JFileChooser();

		// �ļ�������
		FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3",
				"MP3", "wav", "mpg", "mpeg", "wav", "avi", "gif", "GIF", "PNG",
				"png", "jpg", "JPG");
		fileChooser.setFileFilter(filter);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);// ������ʾ�ļ�
		fileChooser.setMultiSelectionEnabled(true);// �����ѡ

		System.out.println("�򿪳ɹ�:  ��ʾ�ļ�ѡ����");

		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("�û�ȡ��ѡ���ļ�");
			return null;
		} else {
			System.out.println("�û�ѡ���ļ�" + fileChooser.getSelectedFile());
			files = fileChooser.getSelectedFiles();
			System.out.println("���ڰ��ļ���ӵ��б�");
			addList(files);
			saveListToFile(files);
			if (player == null) {
				playMedia(randomPodePlayer(list.getLastVisibleIndex()));
			}
			return files;
		}
	}

	/**
	 * ����ȫ��״̬
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
		System.out.println("�û�ѡ��ȫ��!");
	}

	/**
	 * ��ȡȫ��ǰ�Ĵ��ڵĴ�С,λ��
	 * 
	 */
	public void getFrameInfor() {
		width = jframe.getWidth();
		height = jframe.getHeight();
		x = jframe.getX();
		y = jframe.getY();
	}

	/**
	 * �Զ�����ļ����б�
	 */
	public void AutoAddFileToList() {
		list.setListData(fileName);
		popupList.setListData(fileName);
		randomPodePlayer(fileName.size());
	}

	/**
	 * ���ļ���ӵ��б�
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
	 * ���б���ɾ���ļ�
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
		System.out.println("�û�ɾ�����±�Ϊ" + fileIndex);
		// saveListToFile((File[]) fileDirection.toArray());
		list.updateUI();
	}

	/**
	 * ���б������ݱ��浽�ļ�
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
	 * ��ťע�����
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
	 * �������˵�ע�����
	 */
	private void initAddListenerInPopupMenu() {
		menuItem_delete.addActionListener(new PupupSeletedListener());
		menuItem_deleteAll.addActionListener(new PupupSeletedListener());

		menuItem_playSelect.addActionListener(new PupupSeletedListener());
		menuItem_Addfile.addActionListener(new PupupSeletedListener());
	}

	/**
	 * �����˵�������
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
	 * pupupSeletedActionΪ�����˵�����Դѡ��
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
	 * controlSliderTime�ṩΪ�û����Ľ����϶�
	 * 
	 */
	public void controlSliderTime() {

		slider_time.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				if (player != null) {
					newTime = slider_time.getValue();
					player.setMediaTime(new Time(newTime));
					System.out.println("�����ļ���:��" + newTime);
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

	/**�����ṩΪ�û���������
	 * 
	 * @author Qzhong
	 *
	 */
	private class SoundChangeListener implements ChangeListener {

		public void stateChanged(ChangeEvent e) {
			but_sounds.setActionCommand("false");
			but_sounds.setIcon(new ImageIcon("img/sound.png"));
			menuItem_noSound.setSelected(false);
			System.out.println("����������СΪ: " + (float) jSlider_sound.getValue()
					/ 100);
			if (player != null) {
				player.getGainControl().setLevel(
						(float) jSlider_sound.getValue() / 100);
				System.out.println("������СΪ"
						+ (float) player.getGainControl().getLevel());
				mediaMute(but_sounds.getActionCommand());
			}
			lable_infor.setText("����:" + jSlider_sound.getValue() + " " + "����:"
					+ (int) newTime / 3600 + ":" + (int) (newTime % 3600) / 60
					+ ":" + (int) (newTime % 60));
			System.out.println("-----------------------");
			jSlider_sound.setToolTipText(jSlider_sound.getValue() + "");
		}
	}
	/**
	 * ����ģʽ�µİ�ť����
	 * 
	 */
	public void initMusicModeControl() {
		but_music.addMouseListener(new MouseAdapter() {

			MediaPlayer_Menu popup_menuList = new MediaPlayer_Menu("������Ŀ");

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
	 * ���ļ��ж�ȡ�б����
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
				System.out.println("��д���................");
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
	 * ���˵����ÿ�ݼ�
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
	/**Ϊ�û��ṩ��ť�Ŀ���
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

		// but_skipBackward�͸���һ��
		but_skipBackward.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				if (list.getSelectedIndex() != -1) {
					System.out.println("�û�ѡ����һ������");
					musicChooser(-1);
				} else {
					System.out.println("�б���û������");
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
					System.out.println("�û�ѡ����һ������");
					musicChooser(1);
				} else {
					System.out.println("�б���û������");
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
	 * ��MediaPlayerPanel���г�ʼ����
	 */
	public void init() {
		this.add(menubar, BorderLayout.NORTH);
//		changWindowIcon();
		initPanel();// ��ʼ���������
		initButton();// ��ʼ����ť
		initSetComponentInPanel();// �����������ӵ���Ӧ�����
		initMenu();// ��ʼ���˵�
		initPopupMenu();// ��ʼ���Ҽ������˵�
		initAddMenu();// װ��˵�
		getSplitPane();// װ��SplitPane
		initAddListenerInMenu();// �������˵�ע�����
		initAddButton();// װ�����button
		initAddListenerInButton();// ��ťע�����
		initAddListenerInPopupMenu();// ���˵�ע�����
		initButtonChangFaceListener();
		initAddAllCompents();// װ��������
		initSetButton();// ���ð�ť��ʾ��Ϣ��
		controlSliderTime();// �ṩΪ�û����Ľ����϶�
		initMusicModeControl();// ����ģʽ�µİ�ť����
		setAcceleratorInMenuItem();// ���˵����ÿ�ݼ�
//		initSystemTray();
		readFileToList();// ���ļ��ж�ȡ�б����
		AutoAddFileToList();// �Զ�����ļ����б�
		
		

	}

}
