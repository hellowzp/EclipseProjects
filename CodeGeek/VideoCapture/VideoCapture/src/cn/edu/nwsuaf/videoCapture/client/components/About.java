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
	 * �����ı�¼��
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
		this.setSize(400, 300); // ���ô�С
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // �رմ���
        initUI();
        changWindowIcon();
		
	}
	
	/**
	 * ���ڳ�ʼ��About���档
	 */
	private void initUI(){
		this.getContentPane().setLayout(null);
		jTextField1.setText("��Ƶ������洢ϵͳ"); // �����ı�����
		jTextField1.setEditable(false);
		jTextField1.setBounds(new Rectangle(1, 29, 390, 41)); // ���ô�С

		jTextField2.setText("�汾��1.0  ���ߣ�Qzhong"); // �����ı�����
		jTextField2.setBounds(new Rectangle(0, 69, 390, 35)); // ���ô�С
		jTextField2.setEditable(false);
		jTextField3.setMinimumSize(new Dimension(10, 20));
		jTextField3.setEditable(false);
		jTextField3.setText("�������: 2010-05-31");
		jTextField3.setBounds(new Rectangle(0, 102, 390, 37)); // ���ô�С
		jTextField4.setText("QQ : 625064709"); // �����ı�����
		jTextField4.setBounds(new Rectangle(0, 139, 390, 30)); // ���ô�С
		jTextField4.setEditable(false);
		jTextField5.setText("E_mail:qzhong@foxmail.com"); // �����ı�����
		jTextField5.setBounds(new Rectangle(1, 168, 390, 38)); // ���ô�С
		jTextField5.setEditable(false);
		this.getContentPane().add(jTextField1); // ���ı�������
		this.getContentPane().add(jTextField2); // ���ı�������
		this.getContentPane().add(jTextField3); // ���ı�������
		this.getContentPane().add(jTextField4); // ���ı�������
		this.getContentPane().add(jTextField5); // ���ı�������
		
	}
	/**����������ͼ��
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


