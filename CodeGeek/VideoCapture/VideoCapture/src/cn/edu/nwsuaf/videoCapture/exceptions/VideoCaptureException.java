/**
 * 
 */
package cn.edu.nwsuaf.videoCapture.exceptions;

/**
 * VideoCaptureException自定义异常
 * @author Qzhong
 *
 */
public class VideoCaptureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6537724771526812077L;

	/**
	 * 
	 */
	public VideoCaptureException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public VideoCaptureException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public VideoCaptureException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public VideoCaptureException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
