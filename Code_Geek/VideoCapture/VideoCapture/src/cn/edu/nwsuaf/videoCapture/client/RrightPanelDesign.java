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
	 * ��Ƶ��ʾ���
	 */
	private JPanel videoPanel = new JPanel();
	/**
	 * ��Ƶ�������
	 */
	private JPanel capturePanel = new JPanel();
	/**
	 * ���Ʋ������
	 */
	private JPanel controlPanel = new JPanel();
	private JToolBar toolBar = new JToolBar("������",JToolBar.VERTICAL);

	/**
	 * ������Ƶ��ʾ�������
	 */
	private JPanel videoPanelContain = new JPanel();
	/**
	 * ��Ƶ��ʾ����²�����Ƶ�������
	 */
	private JPanel videoControlPanel = new JPanel();

	private JSplitPane videoSplitPanel, videoControlSplitPanel;
	/**
	 * controlPanel�ϵĿ��ư�ť
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
	public RrightPanelDesign(JFrame jframe) {// �����Զ��幹��
		super();
		this.jframe = jframe;
		videoPanel.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		initPanel();

	}

	private void initPanel() {
		System.out.println("���ڳ�ʼ����Ƶ��塭������");
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

		System.out.println("-----------------��Ƶ����ʼ���ɹ���");

		this.updateUI();
		jframe.validate();

	}

	/**
	 * ��ʼ��Buttons
	 */
	public void initAndAddButton() {
		jb_capture = new VideoButton("����");
		jb_take = new VideoButton("����");
		jb_play = new VideoButton("����");
		jb_stroe = new VideoButton("¼��");
		jb_exchange = new VideoButton("�л�");
		jb_exit=new VideoButton("�˳�");
		jb_capture.addActionListener(this);
		jb_take.addActionListener(this);
		jb_play.addActionListener(this);
		jb_stroe.addActionListener(this);
		jb_exchange.addActionListener(this);
		jb_exit.addActionListener(this);// ע�����
		
		controlPanel.setLayout(new GridLayout(1,1));
		controlPanel.add(toolBar);
		
		toolBar.add(jb_capture);
		toolBar.add(jb_take);
		toolBar.add(jb_play);
		toolBar.add(jb_exchange);
		toolBar.add(jb_exit);

	}

	/**
	 * ��ȡvideoControlPanel
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
	 * ��ȡvideoControlSplitPanel
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
	 * ��ʼ����Ƶ�豸
	 */
	public void initAudioCapDevLoc() {
		// ��д��������Ƶ�����ʽ��AudioFormat��
		Vector audioCapDevList = CaptureDeviceManager
				.getDeviceList(new AudioFormat(AudioFormat.LINEAR));

		if ((audioCapDevList.size() > 0)) {
			// �����м���CaptureDevice,����ȡ��һ��
			CaptureDeviceInfo audioCapDevInfo = (CaptureDeviceInfo) audioCapDevList
					.elementAt(0);
			audioLocator = audioCapDevInfo.getLocator();
		} else {
			System.out.println("�Ҳ�����Ƶ�ɼ��豸");

		}

	}

	/**
	 * ��ʼ����Ƶ�豸
	 * 
	 * @throws IOException
	 * @throws NoDataSourceException
	 */
	public void initVideoCapDevLoc() throws NoDataSourceException, IOException {
		// ��д�����ı�����Ƶ��ʽ��VideoFormat��
		Vector videoCapDevList = CaptureDeviceManager
				.getDeviceList(new VideoFormat(VideoFormat.RGB));

		if ((videoCapDevList.size() > 0)) {
			// �����м���CaptureDevice,����ȡ��һ��
			CaptureDeviceInfo videoCapDevInfo = (CaptureDeviceInfo) videoCapDevList
					.elementAt(0);
			videoLocator = videoCapDevInfo.getLocator();
			videoDataSource = Manager.createDataSource(videoLocator);
			videoDataSource = Manager.createCloneableDataSource(videoDataSource);

		} else {
			System.out.println("�Ҳ�����Ƶ�ɼ��豸");

		}
	}
	/**
	 * ��¡��Ƶ����Դ
	 */
    public DataSource cloneCamSource() throws NoDataSourceException, IOException{
    	initVideoCapDevLoc();
        return ((SourceCloneable)videoDataSource).createClone();
    }

	


	/**
	 * ��Ƶ��Ƶ����Դ�ϲ���һ�𲥷�
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
	 * ��Ƶ����
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
	 * ��Ƶ����
	 */
	public void videoTake() {

		VideoPlay.take();
	}

	/**
	 * ��Ƶ����
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
	 * ��Ƶ���
	 */
	public void videoAlarms() {

	}

	/**
	 * ��Ƶ�洢
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
		// ����Processor
		mProcessor = Manager.createProcessor(dataSource);

		StateHelper sh = new StateHelper(mProcessor);

		sh.configure(10000);

		// ����Processor,�������ΪMPEG��ʽ
		mProcessor.setContentDescriptor(new FileTypeDescriptor(
				FileTypeDescriptor.QUICKTIME));

		sh.realize(10000);
		// ȡ��Processor�����
		DataSource source = mProcessor.getDataOutput();
		// ����һ�������ļ�λ�õ�MediaLocator
		MediaLocator dest = new MediaLocator("file://" + fileName + ".MOV");

		mProcessor.start();

		// ����һ��DataSink

		filewriter = Manager.createDataSink(source, dest);
		filewriter.open();

		// ����Processor���������ļ��Ĵ�С��ֻҪ����Processor��StreamWriterControl
		StreamWriterControl swc = (StreamWriterControl) mProcessor
				.getControl("javax.media.control.StreamWriterControl");
		// ���������ļ���С���2M
		if (swc != null) {
			swc.setStreamSizeLimit(2000000);
		}

		// ������ϣ���������Processor��DataSink�����沶�������ˡ�
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
			System.out.println("--------------------����Ƶ����");
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
						System.out.println("û���ҵ�������Ƶ�豸�������豸���ӻ�δ��ռ�ã�"
								+ e1.getMessage());
						JOptionPane.showMessageDialog(null,
								"δ������ƵԤ���������豸���ӻ�δ��ռ�ã�");

					}
				}
			}.start();
		} else if (e.getSource() == jb_take) {
			System.out.println("--------------------��ý�����ա�");

//			videoTake();
			VideoRecorder.take("");

		} else if (e.getSource() == jb_play) {
			if ("����".equals(e.getActionCommand())) {
				System.out.println("--------------------��ý�岥�š�");
//				new Thread() {// ���������ڲ���������һ���߳�
//					@Override
//					public void run() {
						System.out
								.println("��ʼ��Ƶ����-----------------------------");
						String filePath = "64.mpg";
						JFileChooser jfc = new JFileChooser();
						jfc.showOpenDialog(jframe);
						try {
							filePath = jfc.getSelectedFile().getAbsolutePath();
						} catch (Exception e2) {}
						System.out.println("��Ҫ�����ļ���filePath="+filePath);
						
						File file = new File(filePath);
						
						try {
							videoPlay(videoPanel, file);
						} catch (NoPlayerException e1) {
							System.out.println(e1.getMessage());
							JOptionPane
									.showMessageDialog(null, "�ݲ�֧�ִ˸�ʽý��Ĳ��ţ�");
						} catch (CannotRealizeException e1) {
							System.out.println(e1.getMessage());
							JOptionPane.showMessageDialog(null,
									"ý���ʼ��ʧ�ܣ��������´򿪡�");
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							System.out.println(e1.getMessage());
							JOptionPane.showMessageDialog(null, "��Դ·������");

						}
//					}
//				}.start();
				jb_play.setText("ֹͣ");
			} else if ("ֹͣ".equals(e.getActionCommand())) {
				System.out.println("ֹͣ��Ƶ����-----------------------------");
				VideoPlay.stop();
				videoPanel.removeAll();
				jb_play.setText("����");
			}
		} else if (e.getSource() == jb_stroe) {
			
				System.out.println("--------------------��¼��ý�塿");

				new Thread() {// ���������ڲ���������һ���߳�
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
			System.out.println("--------------------�����ڽ�����");
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
			System.out.println("--------------------���˳�ϵͳ��");
			System.exit(0);
		}
	}

}
