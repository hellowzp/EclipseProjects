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


// ���洰��
public class DownloadFrame extends JFrame implements ActionListener {
	
	//���岢��ʼ������Ҫ�����
	JPanel jp = new JPanel();
	JLabel jlb=new JLabel("URL:");
	JTextField jtf = new JTextField(50);
	JButton jb = new JButton("����");
	JTextArea jta = new JTextArea();
	// �����ı����Ĺ�����
	int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
	int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
	JScrollPane jsp = new JScrollPane(jta, v, h);

	// �����ļ�����
	DownLoadFromNet downLoadFromNet;
	
	// ���캯��
	public DownloadFrame(String title) {
		super(title);
		// ���ô��岼��
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.add(jlb);
		jp.add(jtf);
		jp.add(jb);
		
		this.add(jp, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		this.setVisible(true);
		
		
		// ����¼�������
		jtf.addActionListener(this);
		jb.addActionListener(this);
		
		
		// ������Ŀ�Ⱥ͸߶ȷֱ�����Ϊ��Ļ��Ⱥ���Ļ�߶ȵ�1/3�����Ͻ�λ��Ҳ����Ϊ��Ļ��Ⱥ���Ļ�߶ȵ�1/3��
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 3, wndSize.height / 3, wndSize.width / 3,
				wndSize.height / 3);

		
	}

	// ��Ӧ������ť
	public void actionPerformed(ActionEvent e) {
		// ���������ļ����������
		downLoadFromNet = new DownLoadFromNet(jtf.getText());
		downLoadFromNet.doWork();
		jta.setText(downLoadFromNet.info.toString());
	
	}
}



//
//// �����ļ�������
//class Downloader implements Runnable {
//	// �����ļ���URL
//	String urlString;
//
//	// ��ʾ�����ļ���Ϣ���ı���
//	JTextArea jta;
//
//	// ���캯��
//	public Downloader(String urlString, JTextArea jta) {
//		// ��������
//		this.urlString = urlString;
//		this.jta = jta;
//	}
//
//	// ���������ļ����̷߳���
//	public void run() {
//		// �����ļ��������Ϣ
//		StringBuffer info = new StringBuffer();
//		try {
//			// �����ļ���URL
//			URL url = new URL(urlString);
//
//			// �򿪸������ļ���URL����
//			URLConnection urlConn = url.openConnection();
//
//			// ��������ļ��������Ϣ
//			info.append("����: " + url.getHost() + "\n");
//			info.append("�˿�: " + url.getDefaultPort() + "\n");
//			info.append("�����ļ�������: " + urlConn.getContentType() + "\n");
//			info.append("����: " + urlConn.getContentLength() + "\n");
//			info.append("��������...");
//			
//			// ��ʾ�����ļ��������Ϣ
//			jta.setText(info.toString());
//
//			// ���������ļ���������
//			InputStream is = urlConn.getInputStream();
//
//			// ��ȡ�����ļ����ļ�����
//			String localFileName = url.getFile().substring(
//					url.getFile().lastIndexOf("/") + 1);
//
//			// ���������ļ������
//			//����һ�������ļ������ڱ������ݣ��ļ����������½�
//			File f=new File("D:\\"+localFileName);
//			File myFile=new File(f,"myfile.xml");
//			FileOutputStream fos = new FileOutputStream(localFileName);
//
//			// ��ȡ�����ļ��������ļ�
//			int data;
//			while ((data = is.read()) != -1) {
//				fos.write(data);				
//			}
//
//			// �ر���
//			is.close();
//			fos.close();
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		jta.append("������ϣ�");
//	}
//}
