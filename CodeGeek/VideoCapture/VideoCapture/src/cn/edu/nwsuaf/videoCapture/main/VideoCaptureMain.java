package cn.edu.nwsuaf.videoCapture.main;

/**
 * ϵͳ�������
 * @author Qzhong
 *
 */
public class VideoCaptureMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Thread(new StartGUI()).start();
		

	}

}
