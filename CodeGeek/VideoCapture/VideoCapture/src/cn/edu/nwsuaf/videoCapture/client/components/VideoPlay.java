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
	 * ��Щ���������������
	 */
	private JPanel jpanel;
	/**
	 * ������JFrame
	 */
	private JFrame jframe;
	private static Player videoPlayer = null;
	private static Player takePlayer;
	private Component visualPlayer; // ��Ƶ
	private Component controlPlayer = null; // ������

	private int videoWidth = 0; // ��Ƶ��
	private int videoHeight = 0; // ��Ƶ��
	private int controlHeight = 30; // ��������
	private int insetWidth = 10; // ���
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

	// ʹ��һ��MediaLocator

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
		// ���ڹرպ󣬹رղ�����
		jframe.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (videoPlayer != null) {
					videoPlayer.stop();
					videoPlayer.close();
				}
				System.exit(0);
			}
		});
		// ��player���������ص���Դ����
		videoPlayer.realize();
		// ��player����ע�����������ʹ��������¼�������ʱ��ִ����صĶ���
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
	 * ��Ƶ����
	 */
	public static void take() {
		MediaCamera mc = new MediaCamera(takePlayer);
		mc.take("");
	}

	@Override
	public void controllerUpdate(ControllerEvent ce) {
		if (ce instanceof RealizeCompleteEvent) {
			// playerʵ������ɺ����player����ǰԤ����
			videoPlayer.prefetch();
		} else if (ce instanceof PrefetchCompleteEvent) {
			if (visualPlayer != null)
				return;

			// ȡ��player�еĲ�����Ƶ����������õ���Ƶ���ڵĴ�С
			// Ȼ�����Ƶ���ڵ������ӵ�Frame�����У�
			if ((visualPlayer = videoPlayer.getVisualComponent()) != null) {
				Dimension size = visualPlayer.getPreferredSize();
				videoWidth = size.width;
				videoHeight = size.height;
				jpanel.add(visualPlayer, BorderLayout.CENTER);
			} else {
				videoWidth = 320;
			}

			// ȡ��player�е���Ƶ���ſ�������������Ѹ������ӵ�Frame������
			if ((controlPlayer = videoPlayer.getControlPanelComponent()) != null) {
				controlHeight = controlPlayer.getPreferredSize().height;
				jpanel.add(controlPlayer, BorderLayout.SOUTH);

			}
			jpanel.updateUI();

			// ������Ƶ���������ʼ����
			videoPlayer.start();
		} else if (ce instanceof EndOfMediaEvent) {
			// ��������Ƶ��ɺ󣬰�ʱ��������ָ�����ʼ�����ٴ����¿�ʼ����
			videoPlayer.setMediaTime(new Time(0));
			videoPlayer.start();
		}

	}
}
