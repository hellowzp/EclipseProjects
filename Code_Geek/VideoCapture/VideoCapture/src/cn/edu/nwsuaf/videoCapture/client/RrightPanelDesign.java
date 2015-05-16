package cn.edu.nwsuaf.videoCapture.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.DataSink;
import javax.media.IncompatibleSourceException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;
import javax.media.Processor;
import javax.media.control.StreamWriterControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.media.protocol.SourceCloneable;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import jmapps.util.StateHelper;
import cn.edu.nwsuaf.videoCapture.client.VideoRecorder.CamDataSource;
import cn.edu.nwsuaf.videoCapture.client.VideoRecorder.VideoRecorder;
import cn.edu.nwsuaf.videoCapture.client.components.VideoButton;
import cn.edu.nwsuaf.videoCapture.client.components.VideoPlay;
import cn.edu.nwsuaf.videoCapture.utils.CurrentTime;

/**
 * @author Qzhong
 * 
 */
public class RrightPanelDesign extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1590066329692636829L;
	private JFrame jframe;
	/**
	 * 视频显示面板
	 */
	private JPanel videoPanel = new JPanel();
	/**
	 * 视频捕获面板
	 */
	private JPanel capturePanel = new JPanel();
	/**
	 * 控制操作面板
	 */
	private JPanel controlPanel = new JPanel();
	private JToolBar toolBar = new JToolBar("工具栏",JToolBar.VERTICAL);

	/**
	 * 包含视频显示面板的面板
	 */
	private JPanel videoPanelContain = new JPanel();
	/**
	 * 视频显示面板下部的视频控制面板
	 */
	private JPanel videoControlPanel = new JPanel();

	private JSplitPane videoSplitPanel, videoControlSplitPanel;
	/**
	 * controlPanel上的控制按钮
	 */
	private JButton jb_capture, jb_take, jb_play, jb_stroe, jb_exchange,jb_exit;
	private boolean change = true;
	private MediaLocator videoLocator, audioLocator;
	private Processor mProcessor;
	private DataSource dataSource, videoDataSource, cameraCloneDataSource,videoCloneableDataSource;

	private DataSink filewriter = null;
	private VideoPlay capturePlay,videoPlay;

	/**
	 * 
	 * @param jframe
	 */
	public RrightPanelDesign(JFrame jframe) {// 本类自定义构造
		super();
		this.jframe = jframe;
		videoPanel.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		initPanel();

	}

	private void initPanel() {
		System.out.println("正在初始化视频面板…………");
		videoPanelContain.setLayout(new BorderLayout());
		videoPanelContain.add(videoPanel);
		this.add(getVideoSplitPanel(), BorderLayout.CENTER);
		videoControlPanel.setLayout(new BorderLayout());
		videoControlPanel.add(getVideoControlSplitPanel(), BorderLayout.CENTER);

		videoPanel.setBackground(Color.black);
		capturePanel.setBackground(getForeground());
		capturePanel.setLayout(new BorderLayout());
		controlPanel.setBackground(Color.black);
		initAndAddButton();

		System.out.println("-----------------视频面板初始化成功。");

		this.updateUI();
		jframe.validate();

	}

	/**
	 * 初始化Buttons
	 */
	public void initAndAddButton() {
		jb_capture = new VideoButton("捕获");
		jb_take = new VideoButton("拍照");
		jb_play = new VideoButton("播放");
		jb_stroe = new VideoButton("录制");
		jb_exchange = new VideoButton("切换");
		jb_exit=new VideoButton("退出");
		jb_capture.addActionListener(this);
		jb_take.addActionListener(this);
		jb_play.addActionListener(this);
		jb_stroe.addActionListener(this);
		jb_exchange.addActionListener(this);
		jb_exit.addActionListener(this);// 注册监听
		
		controlPanel.setLayout(new GridLayout(1,1));
		controlPanel.add(toolBar);
		
		toolBar.add(jb_capture);
		toolBar.add(jb_take);
		toolBar.add(jb_play);
		toolBar.add(jb_exchange);
		toolBar.add(jb_exit);

	}

	/**
	 * 获取videoControlPanel
	 */
	private JSplitPane getVideoSplitPanel() {
		videoSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				videoPanelContain, videoControlPanel);
		videoSplitPanel.setOneTouchExpandable(true);
		videoSplitPanel.setDividerLocation(300);
		videoSplitPanel.setDividerSize(12);
		// videoSplitPanel.setResizeWeight(0.6);

		return videoSplitPanel;
	}

	/**
	 * 获取videoControlSplitPanel
	 * 
	 * @return
	 */
	private JSplitPane getVideoControlSplitPanel() {
		videoControlSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				controlPanel, capturePanel);
		// videoControlSplitPanel.setOneTouchExpandable(true);
		// videoSplitPanel.setDividerLocation(100);
		videoControlSplitPanel.setDividerSize(4);
		videoControlSplitPanel.setResizeWeight(0.0);

		return videoControlSplitPanel;
	}

	/**
	 * 初始化音频设备
	 */
	public void initAudioCapDevLoc() {
		// 填写其它的音频编码格式，AudioFormat类
		Vector audioCapDevList = CaptureDeviceManager
				.getDeviceList(new AudioFormat(AudioFormat.LINEAR));

		if ((audioCapDevList.size() > 0)) {
			// 或许有几个CaptureDevice,这里取第一个
			CaptureDeviceInfo audioCapDevInfo = (CaptureDeviceInfo) audioCapDevList
					.elementAt(0);
			audioLocator = audioCapDevInfo.getLocator();
		} else {
			System.out.println("找不到音频采集设备");

		}

	}

	/**
	 * 初始化视频设备
	 * 
	 * @throws IOException
	 * @throws NoDataSourceException
	 */
	public void initVideoCapDevLoc() throws NoDataSourceException, IOException {
		// 填写其它的编码视频格式，VideoFormat类
		Vector videoCapDevList = CaptureDeviceManager
				.getDeviceList(new VideoFormat(VideoFormat.RGB));

		if ((videoCapDevList.size() > 0)) {
			// 或许有几个CaptureDevice,这里取第一个
			CaptureDeviceInfo videoCapDevInfo = (CaptureDeviceInfo) videoCapDevList
					.elementAt(0);
			videoLocator = videoCapDevInfo.getLocator();
			videoDataSource = Manager.createDataSource(videoLocator);
			videoDataSource = Manager.createCloneableDataSource(videoDataSource);

		} else {
			System.out.println("找不到视频采集设备");

		}
	}
	/**
	 * 克隆视频数据源
	 */
    public DataSource cloneCamSource() throws NoDataSourceException, IOException{
    	initVideoCapDevLoc();
        return ((SourceCloneable)videoDataSource).createClone();
    }

	


	/**
	 * 视频音频数据源合并后一起播放
	 * 
	 * @throws NoDataSourceException
	 * @throws IOException
	 * @throws IncompatibleSourceException
	 */
	public void dataSource() throws NoDataSourceException, IOException,
			IncompatibleSourceException {
		DataSource[] dataSources = new DataSource[2];
		
		dataSources[0] = cloneCamSource();
		dataSources[1] = Manager.createDataSource(audioLocator);
		dataSource = Manager.createMergingDataSource(dataSources);

	}

	/**
	 * 视频捕获
	 * 
	 * @throws IOException
	 * @throws CannotRealizeException
	 * @throws NoPlayerException
	 * @throws NoDataSourceException
	 */
	public void videoCapture(JPanel panel) throws NoPlayerException,
			CannotRealizeException, IOException, NoDataSourceException {
		// Vector v = CaptureDeviceManager.getDeviceList(new VideoFormat(
		// VideoFormat.RGB));
		// CaptureDeviceInfo cdi = (CaptureDeviceInfo) v.elementAt(0);
		// videoLocator = cdi.getLocator();
//		initVideoCapDevLoc();
		cameraCloneDataSource = cloneCamSource();
		capturePlay = new VideoPlay(cameraCloneDataSource, jframe, panel);

	}

	/**
	 * 视频拍照
	 */
	public void videoTake() {

		VideoPlay.take();
	}

	/**
	 * 视频播放
	 * 
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws CannotRealizeException
	 * @throws NoPlayerException
	 */
	public void videoPlay(JPanel panel, File file) throws NoPlayerException,
			CannotRealizeException, MalformedURLException, IOException {

		videoPlay = new VideoPlay(file, jframe, panel);
	}

	/**
	 * 视频监控
	 */
	public void videoAlarms() {

	}

	/**
	 * 视频存储
	 * 
	 * @throws IOException
	 * @throws IncompatibleSourceException
	 * @throws NoDataSourceException
	 * @throws NoDataSinkException
	 * @throws CannotRealizeException
	 * @throws NoPlayerException
	 */
	public void videoStore(String fileName) throws NoDataSourceException,
			IncompatibleSourceException, IOException, NoDataSinkException,
			NoPlayerException, CannotRealizeException {

		initAudioCapDevLoc();
	
		dataSource();
		// 创建Processor
		mProcessor = Manager.createProcessor(dataSource);

		StateHelper sh = new StateHelper(mProcessor);

		sh.configure(10000);

		// 设置Processor,输出类型为MPEG格式
		mProcessor.setContentDescriptor(new FileTypeDescriptor(
				FileTypeDescriptor.QUICKTIME));

		sh.realize(10000);
		// 取得Processor的输出
		DataSource source = mProcessor.getDataOutput();
		// 建立一个保存文件位置的MediaLocator
		MediaLocator dest = new MediaLocator("file://" + fileName + ".MOV");

		mProcessor.start();

		// 建立一个DataSink

		filewriter = Manager.createDataSink(source, dest);
		filewriter.open();

		// 设置Processor控制生成文件的大小，只要调用Processor的StreamWriterControl
		StreamWriterControl swc = (StreamWriterControl) mProcessor
				.getControl("javax.media.control.StreamWriterControl");
		// 设置生成文件大小最大2M
		if (swc != null) {
			swc.setStreamSizeLimit(2000000);
		}

		// 设置完毕，可以启动Processor和DataSink来保存捕获数据了。
		filewriter.start();

	}

	public void stopStore() {
		try {
			filewriter.stop();
			filewriter.close();
			mProcessor.stop();
			mProcessor.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("ActionCommand:" + e.getActionCommand());

		if (e.getSource() == jb_capture) {
			System.out.println("--------------------【视频捕获】");
			new Thread() {
				@Override
				public void run() {
					try {
//						videoCapture(capturePanel);
						
					    CamDataSource dataSource = new CamDataSource(null);
					    dataSource.setMainSource();
					    dataSource.makeDataSourceCloneable();
					    dataSource.startProcessing();
					    videoControlSplitPanel.remove(capturePanel);
						
					    capturePanel = new VideoRecorder(dataSource,jframe);
					    videoControlSplitPanel.setBottomComponent(capturePanel);
					    videoPanelContain.updateUI();
					    
					} catch (Exception e1) {
						e1.printStackTrace();
						System.out.println("没有找到可用视频设备，请检查设备连接或未被占用！"
								+ e1.getMessage());
						JOptionPane.showMessageDialog(null,
								"未创建视频预览，请检查设备连接或未被占用！");

					}
				}
			}.start();
		} else if (e.getSource() == jb_take) {
			System.out.println("--------------------【媒体拍照】");

//			videoTake();
			VideoRecorder.take("");

		} else if (e.getSource() == jb_play) {
			if ("播放".equals(e.getActionCommand())) {
				System.out.println("--------------------【媒体播放】");
//				new Thread() {// 利用匿名内部类来启动一个线程
//					@Override
//					public void run() {
						System.out
								.println("开始视频播放-----------------------------");
						String filePath = "64.mpg";
						JFileChooser jfc = new JFileChooser();
						jfc.showOpenDialog(jframe);
						try {
							filePath = jfc.getSelectedFile().getAbsolutePath();
						} catch (Exception e2) {}
						System.out.println("将要播放文件：filePath="+filePath);
						
						File file = new File(filePath);
						
						try {
							videoPlay(videoPanel, file);
						} catch (NoPlayerException e1) {
							System.out.println(e1.getMessage());
							JOptionPane
									.showMessageDialog(null, "暂不支持此格式媒体的播放！");
						} catch (CannotRealizeException e1) {
							System.out.println(e1.getMessage());
							JOptionPane.showMessageDialog(null,
									"媒体初始化失败，检查后重新打开。");
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							System.out.println(e1.getMessage());
							JOptionPane.showMessageDialog(null, "资源路径出错！");

						}
//					}
//				}.start();
				jb_play.setText("停止");
			} else if ("停止".equals(e.getActionCommand())) {
				System.out.println("停止视频播放-----------------------------");
				VideoPlay.stop();
				videoPanel.removeAll();
				jb_play.setText("播放");
			}
		} else if (e.getSource() == jb_stroe) {
			
				System.out.println("--------------------【录制媒体】");

				new Thread() {// 利用匿名内部类来启动一个线程
					@Override
					public void run() {
						
						
//						capturePlay.stop();
//						capturePanel.remove(capturePanel.getComponent(0));
//						capturePanel.updateUI();
						
//						 CamDataSource dataSource = new CamDataSource(null);
//						    dataSource.setMainSource();
//						    dataSource.makeDataSourceCloneable();
//						    dataSource.startProcessing();
//						    
//						    VideoRecorder frame = new VideoRecorder(dataSource);
//						    frame.setSize(400, 400);
//						    frame.setLocationRelativeTo(null);
//						    frame.setVisible(true);
						try {
							
							videoStore(CurrentTime.getCurrentTime());
							
						} catch (NoDataSourceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IncompatibleSourceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoDataSinkException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NoPlayerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (CannotRealizeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
					}
				}.start();
			

		} else if (e.getSource() == jb_exchange) {
			System.out.println("--------------------【窗口交换】");
			if (change) {
				videoPanelContain.remove(videoPanel);
				videoPanelContain.add(capturePanel);
				videoControlSplitPanel.remove(capturePanel);
				videoControlSplitPanel.setBottomComponent(videoPanel);
				change = false;
			} else {
				change = true;
				videoPanelContain.remove(capturePanel);
				videoPanelContain.add(videoPanel);
				videoControlSplitPanel.remove(videoPanel);
				videoControlSplitPanel.setBottomComponent(capturePanel);

			}
			this.validate();
			this.updateUI();
			jframe.validate();

		}else if(e.getSource() == jb_exit){
			System.out.println("--------------------【退出系统】");
			System.exit(0);
		}
	}

}
