package cn.edu.nwsuaf.videoCapture.client.VideoRecorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Codec;
import javax.media.Control;
import javax.media.DataSink;
import javax.media.Format;
import javax.media.IncompatibleSourceException;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoProcessorException;
import javax.media.Owned;
import javax.media.Player;
import javax.media.Processor;
import javax.media.control.QualityControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.FileTypeDescriptor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import cn.edu.nwsuaf.videoCapture.client.components.VideoButton;
import cn.edu.nwsuaf.videoCapture.operate.MediaCamera;

public class VideoRecorder extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel centerPanel;
	private static JLabel fileLabel;
	private JToolBar mainToolBar;
	private JLabel messageLabel;
	private JPanel northPanel;
	private JButton recordButton;
	private JPanel southPanel;

	private CamDataSource dataSource;
	private DataSource camSource;
	private DataSource recordCamSource;
	private DataSink dataSink;
	private static Processor processor;
	private Processor recordProcessor;
	private CamStateHelper playhelper;
	private JFrame jframe;
	private MediaLocator audioLocator;

	private JFileChooser movieChooser;

	public VideoRecorder(CamDataSource dataSource,JFrame jframe) {
		this.setLayout(new BorderLayout());
		this.dataSource = dataSource;
		this.dataSource.setParent(this);
		this.jframe = jframe;
		camSource = dataSource.cloneCamSource();

		initComponents();
		try {
			processor = Manager.createProcessor(camSource);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Exception creating processor: " + e.getMessage(), "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		} catch (NoProcessorException e) {
			JOptionPane.showMessageDialog(this,
					"Exception creating processor: " + e.getMessage(), "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		playhelper = new CamStateHelper(processor);
		if (!playhelper.configure(10000)) {
			JOptionPane.showMessageDialog(this, "cannot configure processor",
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		checkIncoding(processor.getTrackControls());
		processor.setContentDescriptor(null);
		if (!playhelper.realize(10000)) {
			JOptionPane.showMessageDialog(this, "cannot realize processor",
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		setJPEGQuality(processor, 1.0f);
		processor.start();

		processor.getVisualComponent().setBackground(Color.gray);
		centerPanel.add(processor.getVisualComponent(), BorderLayout.CENTER);// 视频面板
		centerPanel.add(processor.getControlPanelComponent(),
				BorderLayout.SOUTH);// 视频控制面板
	}

	// 界面初始化
	private void initComponents() {
		northPanel = new JPanel();
		messageLabel = new JLabel();
		southPanel = new JPanel();
		mainToolBar = new JToolBar();
		recordButton = new VideoButton("录制");
		fileLabel = new JLabel();
		centerPanel = new JPanel();

		jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jframe.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				formWindowClosing(evt);
			}
		});

		northPanel.setLayout(new BorderLayout());

		messageLabel.setText("状态：");
		northPanel.add(messageLabel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);

		southPanel.setLayout(new BorderLayout());
		recordButton.setText("录制");
		recordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				recordButtonActionPerformed(evt);
			}
		});

		mainToolBar.add(recordButton);

		fileLabel.setText("文件:");
		mainToolBar.add(fileLabel);
		southPanel.add(mainToolBar, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		centerPanel.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
	}

	private void formWindowClosing(java.awt.event.WindowEvent evt) {

		processor.close();
	}

	// 录制当前视频
	private void recordButtonActionPerformed(java.awt.event.ActionEvent evt) {

		if (recordButton.getText().equals("录制")) {
			System.out.println("--------------------【视频录制中...】");
			fileLabel.setText("文件:");
			if (movieChooser == null)
				movieChooser = new JFileChooser();
			movieChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			movieChooser.addChoosableFileFilter(new MOVFilter());
			movieChooser.setAcceptAllFileFilterUsed(false);
			movieChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = movieChooser.showDialog(this, "录制");
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = movieChooser.getSelectedFile();
				if (!file.getName().endsWith(".mov")
						&& !file.getName().endsWith(".MOV"))
					file = new File(file.toString() + ".mov");
				recordToFile(file);
				fileLabel.setText("文件:" + file.toString());
				recordButton.setText("停止");
			}
		} else {
			stopRecording();
			recordButton.setText("录制");
		}
	}

	void setJPEGQuality(Player p, float val) {
		Control cs[] = p.getControls();
		QualityControl qc = null;
		VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);

		// Loop through the controls to find the Quality control for
		// the JPEG encoder.
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] instanceof QualityControl && cs[i] instanceof Owned) {
				Object owner = ((Owned) cs[i]).getOwner();
				// Check to see if the owner is a Codec.
				// Then check for the output format.
				if (owner instanceof Codec) {
					Format fmts[] = ((Codec) owner)
							.getSupportedOutputFormats(null);
					for (int j = 0; j < fmts.length; j++) {
						if (fmts[j].matches(jpegFmt)) {
							qc = (QualityControl) cs[i];
							qc.setQuality(val);
							break;
						}
					}
				}
				if (qc != null)
					break;
			}
		}
	}

	public void checkIncoding(TrackControl track[]) {
		for (int i = 0; i < track.length; i++) {
			Format format = track[i].getFormat();
			if (track[i].isEnabled() && format instanceof VideoFormat) {
				Dimension size = ((VideoFormat) format).getSize();
				float frameRate = ((VideoFormat) format).getFrameRate();
				int w = (size.width % 8 == 0 ? size.width
						: (int) (size.width / 8) * 8);
				int h = (size.height % 8 == 0 ? size.height
						: (int) (size.height / 8) * 8);
				VideoFormat jpegFormat = new VideoFormat(VideoFormat.JPEG_RTP,
						new Dimension(w, h), Format.NOT_SPECIFIED,
						Format.byteArray, frameRate);
				messageLabel.setText("状态: 视频正以" + jpegFormat.toString() + "播放");
			}
		}
	}

	public void recordToFile(File file) {
		URL movieUrl = null;
		MediaLocator dest = null;
		try {
			movieUrl = file.toURI().toURL();
			dest = new MediaLocator(movieUrl);
		} catch (MalformedURLException e) {

		}

		recordCamSource = dataSource.cloneCamSource();
		initAudioCapDevLoc();

		 
        DataSource[] dataSources = new DataSource[2];
        DataSource dataSource=null;
		try {
			dataSources[0] = recordCamSource;
			dataSources[1] = Manager.createDataSource(audioLocator);
			dataSource= Manager.createMergingDataSource(dataSources);
		} catch (NoDataSourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IncompatibleSourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		try {
//			recordProcessor = Manager.createProcessor(recordCamSource);
			recordProcessor = Manager.createProcessor(dataSource);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Exception creating record processor: " + e.getMessage(),
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		} catch (NoProcessorException e) {
			JOptionPane.showMessageDialog(this,
					"Exception creating record processor: " + e.getMessage(),
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		playhelper = new CamStateHelper(recordProcessor);
		if (!playhelper.configure(10000)) {
			JOptionPane.showMessageDialog(this,
					"cannot configure record processor", "Error",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		VideoFormat vfmt = new VideoFormat(VideoFormat.CINEPAK);
		(recordProcessor.getTrackControls())[0].setFormat(vfmt);
		(recordProcessor.getTrackControls())[0].setEnabled(true);
		recordProcessor.setContentDescriptor(new FileTypeDescriptor(
				FileTypeDescriptor.QUICKTIME));
		Control control = recordProcessor
				.getControl("javax.media.control.FrameRateControl");
		if (control != null
				&& control instanceof javax.media.control.FrameRateControl)
			((javax.media.control.FrameRateControl) control)
					.setFrameRate(15.0f);
		if (!playhelper.realize(10000)) {
			JOptionPane.showMessageDialog(this, "cannot realize processor",
					"Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {
			if (recordProcessor.getDataOutput() == null) {
				JOptionPane.showMessageDialog(this, "No Data Output", "Error",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			dataSink = Manager.createDataSink(recordProcessor.getDataOutput(),
					dest);
			recordProcessor.start();
			dataSink.open();
			dataSink.start();
		} catch (NoDataSinkException ex) {
			JOptionPane.showMessageDialog(this, "No DataSink "
					+ ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "IOException "
					+ ex.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
	}

	public void stopRecording() {
		try {
			recordProcessor.close();
			dataSink.stop();
			dataSink.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "cannot stop recording "
					+ e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 视频拍照
	 */
	public static void take(String path) {
		MediaCamera mc = new MediaCamera(processor);
		mc.take(path);
		fileLabel.setText(" 拍照成功，照片已保存在当前目录。");
	}
	/**
	 * 初始化音频设备
	 */
	public void initAudioCapDevLoc() {
		Vector audioCapDevList = CaptureDeviceManager
				.getDeviceList(new AudioFormat(AudioFormat.LINEAR));

		if ((audioCapDevList.size() > 0)) {
			CaptureDeviceInfo audioCapDevInfo = (CaptureDeviceInfo) audioCapDevList
					.elementAt(0);
			audioLocator = audioCapDevInfo.getLocator();
		} else {
			System.out.println("找不到音频采集设备");

		}}

}
