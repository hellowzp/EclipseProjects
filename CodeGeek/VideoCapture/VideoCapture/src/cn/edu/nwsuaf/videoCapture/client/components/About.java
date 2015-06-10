package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class About extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;
	/**
	 * 用于文本录入
	 */
	JTextField jTextField1 = new JTextField(); 
	JTextField jTextField2 = new JTextField();
	JTextField jTextField3 = new JTextField();
	JTextField jTextField4 = new JTextField();
	JTextField jTextField5 = new JTextField();
	/**
	 * 
	 * @param owner
	 * @param title
	 * @param modal
	 */
	
	public About(Frame owner, String title, boolean modal) { 
		super(owner, title, modal); 
		
		this.setLocationRelativeTo(this);
		this.setSize(400, 300); // 设置大小
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 关闭处理
        initUI();
        changWindowIcon();
		
	}
	
	/**
	 * 用于初始化About界面。
	 */
	private void initUI(){
		this.getContentPane().setLayout(null);
		jTextField1.setText("视频捕获与存储系统"); // 加入文本内容
		jTextField1.setEditable(false);
		jTextField1.setBounds(new Rectangle(1, 29, 390, 41)); // 设置大小

		jTextField2.setText("版本：1.0  作者：Qzhong"); // 加入文本内容
		jTextField2.setBounds(new Rectangle(0, 69, 390, 35)); // 设置大小
		jTextField2.setEditable(false);
		jTextField3.setMinimumSize(new Dimension(10, 20));
		jTextField3.setEditable(false);
		jTextField3.setText("完成日期: 2010-05-31");
		jTextField3.setBounds(new Rectangle(0, 102, 390, 37)); // 设置大小
		jTextField4.setText("QQ : 625064709"); // 加入文本内容
		jTextField4.setBounds(new Rectangle(0, 139, 390, 30)); // 设置大小
		jTextField4.setEditable(false);
		jTextField5.setText("E_mail:qzhong@foxmail.com"); // 加入文本内容
		jTextField5.setBounds(new Rectangle(1, 168, 390, 38)); // 设置大小
		jTextField5.setEditable(false);
		this.getContentPane().add(jTextField1); // 加文本框到容器
		this.getContentPane().add(jTextField2); // 加文本框到容器
		this.getContentPane().add(jTextField3); // 加文本框到容器
		this.getContentPane().add(jTextField4); // 加文本框到容器
		this.getContentPane().add(jTextField5); // 加文本框到容器
		
	}
	/**更换标题栏图标
	 * 
	 */
	public void changWindowIcon() {
		try {
			File imagefile = new File("img/vmp3.png");
			URL url = imagefile.toURI().toURL();
			Image image = this.getToolkit().getImage(url);
			this.setIconImage(image);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}
}


