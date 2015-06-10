package musicClient.downLoadMusic;
/**
 * 下载网络文件，并保存在软件所在的文件夹
 * 日期：2013-1-5
 */
import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadFromNet {
	
	//用于连接服务器的socket
	public Socket s;

	//用于显示 网络文件的相关信息
	StringBuffer info= new StringBuffer();
	private String urlStr;
	//得到资源的url
	public DownLoadFromNet(String urlStr) {
		this.urlStr = urlStr;
	}
	
	// 获得下载的资源的名称
	private String getResourceName() {
		return urlStr.substring(urlStr.lastIndexOf("/") + 1);

	}

	/**
	 * 开启四个线程进行下载
	 */
	public void doWork() {
		System.out.println(getResourceName());
		try {
			
			//得到链接，开始下载
			URL url = new URL(urlStr);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			System.out.println(conn.getResponseCode()+"fff");
			System.out.println(HttpURLConnection.HTTP_OK+"ddd");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
		
				System.out.println(urlStr);
				int size = conn.getContentLength();
				//文件大小
				System.out.println(size);
				
				//在本地下载文件，文件位置在软件对应的文件夹下
				File file = new File("./" + getResourceName());
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.setLength(size);
				accessFile.close();
				
				
				// 添加网络文件的相关信息
				info.append("主机: " + url.getHost() + "\n");
				info.append("端口: " + url.getDefaultPort() + "\n");
				info.append("网络文件的类型: " + conn.getContentType() + "\n");
				info.append("主机: " + url.getHost()+ "\n");
				info.append("正在下载..."+"\n");
				info.append("\r\n");
					System.out.println("主机: " + url.getHost()+"主机: " + url.getHost());
	
				int block = (size % 4 == 0) ? (size / 4) : (size / 4 + 1);
				//每个线程需要下载的文件大小
				System.out.println(block);
				//用四个线程来下载文件
				for (int i = 0; i < 4; i++) {
					new DownLoadThread(url, file, block, i).start();
					info.append(" Thread id:" + i + " start"+"\n");	
				}
				info.append("laoding..."+"\n");
				if(file.length()>=conn.getContentLength()-2){
					info.append("下载完成...");
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
				"http://222.20.73.200:3456/F:/KuGou/刘若英 - 我是一只小小鸟.mp3"
				);
		  
				downLoadFromNet.doWork();
	}

}




// 下载线程类
class DownLoadThread extends Thread {
	private URL url; // url地址
	private File file; // 下载文件
	private int block; // 下载信息长度
	private int threadPid; // 线程id

	// 构造函数
	//得到文件下载的相关信息
	public DownLoadThread(URL url, File file, int block, int threadPid) {
		this.url = url;
		this.file = file;
		this.block = block;
		this.threadPid = threadPid;
	}

	/**
	 * 实现下载
	 */
	public void run() {
		// 计算该线程要下载信息的范围
		int startPosition = threadPid * block; 
		int endPosition = (threadPid + 1) * block - 1;

		try {
			//在本地创建要下载的文件
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			//文件开始下载的位置
			accessFile.seek(startPosition);
			//得到下载链接url
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			//设置超时时间
			conn.setReadTimeout(5 * 1000);
			conn.setRequestProperty("Range", "bytes=" + startPosition + "-"
					+ endPosition);
			//写入文件
			InputStream in = conn.getInputStream();

			//字节缓冲流
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = in.read(buf)) != -1) {
				//写入
				accessFile.write(buf, 0, len); 
			}
			//关闭资源
			in.close();
			accessFile.close();
			System.out.println("Thread id:" + threadPid + " finished");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
