package musicServer.downLoadMusic;


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
	
	//��������Ҫ�����
	JPanel jp ;
	JLabel jlb;
	JTextField jtf;
	JButton jb ;
	JTextArea jta;
	JScrollPane jsp;

	// �����ļ�����
	DownLoadFromNet downLoadFromNet;
	
	// ���캯��
	public DownloadFrame(String title) {
		super(title);
		
		//��ʼ�����
		jp = new JPanel();
		jlb=new JLabel("URL:");
		jtf = new JTextField(20);
		jb = new JButton("����");
		// ����¼�������
		jb.addActionListener(this);
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		// ���ô��岼��
		jp.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp.add(jlb);
		jp.add(jtf);
		jp.add(jb);
		jp.setOpaque(false);
		
		this.add(jp, BorderLayout.NORTH);
		this.add(jsp, BorderLayout.CENTER);
		this.setVisible(true);
		this.setSize(400,300);
		this.setLocation(400,200);
		this.setResizable(false);
		
		//���ñ���ͼƬ
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background5.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//ʹ���ݴ���͸����
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		
	}

	// ��Ӧ������ť
	public void actionPerformed(ActionEvent e) {
		// ���������ļ����������
		downLoadFromNet = new DownLoadFromNet(jtf.getText());
		downLoadFromNet.doWork();
		jta.setText(downLoadFromNet.info.toString());
	
	}
}


