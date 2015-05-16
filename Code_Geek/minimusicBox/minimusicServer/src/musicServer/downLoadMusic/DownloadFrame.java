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


// 界面窗体
public class DownloadFrame extends JFrame implements ActionListener {
	
	//定义所需要的组件
	JPanel jp ;
	JLabel jlb;
	JTextField jtf;
	JButton jb ;
	JTextArea jta;
	JScrollPane jsp;

	// 网络文件下载
	DownLoadFromNet downLoadFromNet;
	
	// 构造函数
	public DownloadFrame(String title) {
		super(title);
		
		//初始化组件
		jp = new JPanel();
		jlb=new JLabel("URL:");
		jtf = new JTextField(20);
		jb = new JButton("下载");
		// 添加事件监听器
		jb.addActionListener(this);
		jta = new JTextArea();
		jsp = new JScrollPane(jta);
		// 设置窗体布局
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
		
		//设置背景图片
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background5.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//使内容窗格透明化
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		
	}

	// 响应单击按钮
	public void actionPerformed(ActionEvent e) {
		// 创建网络文件下载类变量
		downLoadFromNet = new DownLoadFromNet(jtf.getText());
		downLoadFromNet.doWork();
		jta.setText(downLoadFromNet.info.toString());
	
	}
}


