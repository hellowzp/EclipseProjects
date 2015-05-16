package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.CannotRealizeException;
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
import javax.media.protocol.DataSource;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.edu.nwsuaf.videoCapture.operate.MediaCamera;

public class VideoPlay implements ControllerListener {

	/**
	 * 这些东西将加入的容器
	 */
	private JPanel jpanel;
	/**
	 * 隶属的JFrame
	 */
	private JFrame jframe;
	private static Player videoPlayer = null;
	private static Player takePlayer;
	private Component visualPlayer; // 视频
	private Component controlPlayer = null; // 控制条

	private int videoWidth = 0; // 视频宽
	private int videoHeight = 0; // 视频高
	private int controlHeight = 30; // 控制条高
	private int insetWidth = 10; // 间隔
	private int insetHeight = 30;

	private VideoPlay(URL url) throws NoPlayerException,
			CannotRealizeException, IOException {

		videoPlayer = Manager.createRealizedPlayer(url);
	}

	public VideoPlay(File file, JFrame jframe, JPanel jpanel)
			throws NoPlayerException, CannotRealizeException,
			MalformedURLException, IOException {

		this(file.toURI().toURL());
		this.jframe = jframe;
		this.jpanel = jpanel;
		play();

	}

	// 使用一个MediaLocator

	public VideoPlay(MediaLocator locator, JFrame jframe, JPanel jpanel)
			throws NoPlayerException, CannotRealizeException, IOException {
		this.jframe = jframe;
		this.jpanel = jpanel;
		videoPlayer = Manager.createRealizedPlayer(locator);
		takePlayer = videoPlayer;
		play();
	}

	public VideoPlay(DataSource dataSource, JFrame jframe, JPanel jpanel)
			throws NoPlayerException, CannotRealizeException, IOException {
		this.jframe = jframe;
		this.jpanel = jpanel;
		videoPlayer = Manager.createRealizedPlayer(dataSource);
		takePlayer = videoPlayer;
		play();
	}

	public void play() {
		// 窗口关闭后，关闭播放器
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (videoPlayer != null) {
					videoPlayer.stop();
					videoPlayer.close();
				}
				System.exit(0);
			}
		});
		// 让player对象进行相关的资源分配
		videoPlayer.realize();
		// 对player对象注册监听器，能使其在相关事件发生的时候执行相关的动作
		videoPlayer.addControllerListener(this);
	}

	public static void stop() {

		videoPlayer.stop();
		videoPlayer.close();
	}
	public void stopCapture() {

		videoPlayer.stop();
		videoPlayer.close();
	}

	/**
	 * 视频拍照
	 */
	public static void take() {
		MediaCamera mc = new MediaCamera(takePlayer);
		mc.take("");
	}

	@Override
	public void controllerUpdate(ControllerEvent ce) {
		if (ce instanceof RealizeCompleteEvent) {
			// player实例化完成后进行player播放前预处理
			videoPlayer.prefetch();
		} else if (ce instanceof PrefetchCompleteEvent) {
			if (visualPlayer != null)
				return;

			// 取得player中的播放视频的组件，并得到视频窗口的大小
			// 然后把视频窗口的组件添加到Frame窗口中，
			if ((visualPlayer = videoPlayer.getVisualComponent()) != null) {
				Dimension size = visualPlayer.getPreferredSize();
				videoWidth = size.width;
				videoHeight = size.height;
				jpanel.add(visualPlayer, BorderLayout.CENTER);
			} else {
				videoWidth = 320;
			}

			// 取得player中的视频播放控制条组件，并把该组件添加到Frame窗口中
			if ((controlPlayer = videoPlayer.getControlPanelComponent()) != null) {
				controlHeight = controlPlayer.getPreferredSize().height;
				jpanel.add(controlPlayer, BorderLayout.SOUTH);

			}
			jpanel.updateUI();

			// 启动视频播放组件开始播放
			videoPlayer.start();
		} else if (ce instanceof EndOfMediaEvent) {
			// 当播放视频完成后，把时间进度条恢复到开始，并再次重新开始播放
			videoPlayer.setMediaTime(new Time(0));
			videoPlayer.start();
		}

	}
}
