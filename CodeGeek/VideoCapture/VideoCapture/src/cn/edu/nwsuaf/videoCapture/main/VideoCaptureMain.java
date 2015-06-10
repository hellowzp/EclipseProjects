package cn.edu.nwsuaf.videoCapture.main;

/**
 * 系统运行入口
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
