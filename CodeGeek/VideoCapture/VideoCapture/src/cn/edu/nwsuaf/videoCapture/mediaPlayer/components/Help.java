package cn.edu.nwsuaf.videoCapture.mediaPlayer.components;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JTextField;

public class Help extends JDialog  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -13474663067L;
	JTextField jTextField1 = new JTextField(); // 产生文本录入组件
	JTextField jTextField2 = new JTextField();
	JTextField jTextField3 = new JTextField(); // 产生文本录入组件
	JTextField jTextField4 = new JTextField();
	JTextField jTextField5 = new JTextField(); // 产生文本录入组件
	/**
	 * 
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public Help(Frame owner, String title, boolean modal) { 
		super(owner, title, modal); // 继承
		this.setLocationRelativeTo(this);
		this.setSize(240, 260); // 设置大小
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 关闭处理
		initUI(); 
		changWindowIcon();
		
	}
	private void initUI() {
		this.getContentPane().setLayout(null);
		jTextField1.setText("【常用快捷键】"); // 加入文本内容
		jTextField1.setBounds(new Rectangle(1, 29, 239, 41)); // 设置大小
		jTextField1.setEditable(false);
		jTextField2.setText("打开文件 CTRL+O"); // 加入文本内容
		jTextField2.setBounds(new Rectangle(0, 69, 240, 35)); // 设置大小
		jTextField3.setMinimumSize(new Dimension(10, 20));
		jTextField2.setEditable(false);
		jTextField3.setEditable(false);
		jTextField3.setText("全屏         ENTER    停止   CTRL+S");
		jTextField3.setBounds(new Rectangle(0, 102, 240, 37)); // 设置大小
		jTextField4.setText("帮助　     CTRL+H    暂停　 SPACE"); // 加入文本内容
		jTextField4.setBounds(new Rectangle(0, 139, 240, 30)); // 设置大小
		jTextField4.setEditable(false);
		jTextField5.setText("（在这里感谢给我帮助的导师和同学）"); // 加入文本内容
		jTextField5.setBounds(new Rectangle(1, 168, 240, 38)); // 设置大小
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
