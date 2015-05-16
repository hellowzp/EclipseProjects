package musicClient.downLoadMusic;
/**
 * ���������ļ�����������������ڵ��ļ���
 * ���ڣ�2013-1-5
 */
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadFromNet {
	
	//�������ӷ�������socket
	public Socket s;

	//������ʾ �����ļ��������Ϣ
	StringBuffer info= new StringBuffer();
	private String urlStr;
	//�õ���Դ��url
	public DownLoadFromNet(String urlStr) {
		this.urlStr = urlStr;
	}
	
	// ������ص���Դ������
	private String getResourceName() {
		return urlStr.substring(urlStr.lastIndexOf("/") + 1);

	}

	/**
	 * �����ĸ��߳̽�������
	 */
	public void doWork() {
		System.out.println(getResourceName());
		try {
			
			//�õ����ӣ���ʼ����
			URL url = new URL(urlStr);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			System.out.println(conn.getResponseCode()+"fff");
			System.out.println(HttpURLConnection.HTTP_OK+"ddd");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		
				System.out.println(urlStr);
				int size = conn.getContentLength();
				//�ļ���С
				System.out.println(size);
				
				//�ڱ��������ļ����ļ�λ���������Ӧ���ļ�����
				File file = new File("./" + getResourceName());
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.setLength(size);
				accessFile.close();
				
				
				// ��������ļ��������Ϣ
				info.append("����: " + url.getHost() + "\n");
				info.append("�˿�: " + url.getDefaultPort() + "\n");
				info.append("�����ļ�������: " + conn.getContentType() + "\n");
				info.append("����: " + url.getHost()+ "\n");
				info.append("��������..."+"\n");
				info.append("\r\n");
					System.out.println("����: " + url.getHost()+"����: " + url.getHost());
	
				int block = (size % 4 == 0) ? (size / 4) : (size / 4 + 1);
				//ÿ���߳���Ҫ���ص��ļ���С
				System.out.println(block);
				//���ĸ��߳��������ļ�
				for (int i = 0; i < 4; i++) {
					new DownLoadThread(url, file, block, i).start();
					info.append(" Thread id:" + i + " start"+"\n");	
				}
				info.append("laoding..."+"\n");
				if(file.length()>=conn.getContentLength()-2){
					info.append("�������...");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		DownLoadFromNet downLoadFromNet = new DownLoadFromNet(
				//"http://www.5ilrc.com/downlrc.asp");
				//"http://downmini.kugou.com/Kugou2012.exe");
				//"http://idc218a.newhua.com:82/down/exe4j_windows_4_5_1.zip"
				"http://222.20.73.200:3456/F:/KuGou/����Ӣ - ����һֻСС��.mp3"
				);
		  
				downLoadFromNet.doWork();
	}

}




// �����߳���
class DownLoadThread extends Thread {
	private URL url; // url��ַ
	private File file; // �����ļ�
	private int block; // ������Ϣ����
	private int threadPid; // �߳�id

	// ���캯��
	//�õ��ļ����ص������Ϣ
	public DownLoadThread(URL url, File file, int block, int threadPid) {
		this.url = url;
		this.file = file;
		this.block = block;
		this.threadPid = threadPid;
	}

	/**
	 * ʵ������
	 */
	public void run() {
		// ������߳�Ҫ������Ϣ�ķ�Χ
		int startPosition = threadPid * block; 
		int endPosition = (threadPid + 1) * block - 1;

		try {
			//�ڱ��ش���Ҫ���ص��ļ�
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			//�ļ���ʼ���ص�λ��
			accessFile.seek(startPosition);
			//�õ���������url
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			//���ó�ʱʱ��
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty("Range", "bytes=" + startPosition + "-"
					+ endPosition);
			//д���ļ�
			InputStream in = conn.getInputStream();

			//�ֽڻ�����
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				//д��
				accessFile.write(buf, 0, len); 
			}
			//�ر���Դ
			in.close();
			accessFile.close();
			System.out.println("Thread id:" + threadPid + " finished");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
