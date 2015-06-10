package musicClient.downLoadMusic;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.*;


// 界面窗体
public class DownloadFrame extends JFrame implements ActionListener {
	
	//定义并初始化所需要的组件
	JPanel jp = new JPanel();
	JLabel jlb=new JLabel("URL:");
	JTextField jtf = new JTextField(50);
	JButton jb = new JButton("下载");
	JTextArea jta = new JTextArea();
	// 设置文本区的滚动条
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	JScrollPane jsp = new JScrollPane(jta, v, h);

	// 网络文件下载
	DownLoadFromNet downLoadFromNet;
	
	// 构造函数
	public DownloadFrame(String title) {
		super(title);
		// 设置窗体布局
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.add(jlb);
		jp.add(jtf);
		jp.add(jb);
		
		this.add(jp, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		this.setVisible(true);
		
		
		// 添加事件监听器
		jtf.addActionListener(this);
		jb.addActionListener(this);
		
		
		// 将窗体的宽度和高度分别设置为屏幕宽度和屏幕高度的1/3，左上角位置也设置为屏幕宽度和屏幕高度的1/3处
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 3, wndSize.height / 3, wndSize.width / 3,
				wndSize.height / 3);

		
	}

	// 响应单击按钮
	public void actionPerformed(ActionEvent e) {
		// 创建网络文件下载类变量
		downLoadFromNet = new DownLoadFromNet(jtf.getText());
		downLoadFromNet.doWork();
		jta.setText(downLoadFromNet.info.toString());
	
	}
}



//
//// 网络文件下载类
//class Downloader implements Runnable {
//	// 网络文件的URL
//	String urlString;
//
//	// 显示网络文件信息的文本区
//	JTextArea jta;
//
//	// 构造函数
//	public Downloader(String urlString, JTextArea jta) {
//		// 设置属性
//		this.urlString = urlString;
//		this.jta = jta;
//	}
//
//	// 下载网络文件的线程方法
//	public void run() {
//		// 网络文件的相关信息
//		StringBuffer info = new StringBuffer();
//		try {
//			// 网络文件的URL
//			URL url = new URL(urlString);
//
//			// 打开该网络文件的URL连接
//			URLConnection urlConn = url.openConnection();
//
//			// 添加网络文件的相关信息
//			info.append("主机: " + url.getHost() + "\n");
//			info.append("端口: " + url.getDefaultPort() + "\n");
//			info.append("网络文件的类型: " + urlConn.getContentType() + "\n");
//			info.append("长度: " + urlConn.getContentLength() + "\n");
//			info.append("正在下载...");
//			
//			// 显示网络文件的相关信息
//			jta.setText(info.toString());
//
//			// 创建网络文件的输入流
//			InputStream is = urlConn.getInputStream();
//
//			// 获取网络文件的文件名称
//			String localFileName = url.getFile().substring(
//					url.getFile().lastIndexOf("/") + 1);
//
//			// 创建本地文件输出流
//			//创建一个本地文件，用于保存数据，文件不存在则新建
//			File f=new File("D:\\"+localFileName);
//			File myFile=new File(f,"myfile.xml");
//			FileOutputStream fos = new FileOutputStream(localFileName);
//
//			// 读取网络文件到本地文件
//			int data;
//			while ((data = is.read()) != -1) {
//				fos.write(data);				
//			}
//
//			// 关闭流
//			is.close();
//			fos.close();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		jta.append("下载完毕！");
//	}
//}
