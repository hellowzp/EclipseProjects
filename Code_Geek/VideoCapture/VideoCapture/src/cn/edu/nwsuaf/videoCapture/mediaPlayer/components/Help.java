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
	JTextField jTextField1 = new JTextField(); // �����ı�¼�����
	JTextField jTextField2 = new JTextField();
	JTextField jTextField3 = new JTextField(); // �����ı�¼�����
	JTextField jTextField4 = new JTextField();
	JTextField jTextField5 = new JTextField(); // �����ı�¼�����
	/**
	 * 
	 * @param owner
	 * @param title
	 * @param modal
	 */
	public Help(Frame owner, String title, boolean modal) { 
		super(owner, title, modal); // �̳�
		this.setLocationRelativeTo(this);
		this.setSize(240, 260); // ���ô�С
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // �رմ���
		initUI(); 
		changWindowIcon();
		
	}
	private void initUI() {
		this.getContentPane().setLayout(null);
		jTextField1.setText("�����ÿ�ݼ���"); // �����ı�����
		jTextField1.setBounds(new Rectangle(1, 29, 239, 41)); // ���ô�С
		jTextField1.setEditable(false);
		jTextField2.setText("���ļ� CTRL+O"); // �����ı�����
		jTextField2.setBounds(new Rectangle(0, 69, 240, 35)); // ���ô�С
		jTextField3.setMinimumSize(new Dimension(10, 20));
		jTextField2.setEditable(false);
		jTextField3.setEditable(false);
		jTextField3.setText("ȫ��         ENTER    ֹͣ   CTRL+S");
		jTextField3.setBounds(new Rectangle(0, 102, 240, 37)); // ���ô�С
		jTextField4.setText("������     CTRL+H    ��ͣ�� SPACE"); // �����ı�����
		jTextField4.setBounds(new Rectangle(0, 139, 240, 30)); // ���ô�С
		jTextField4.setEditable(false);
		jTextField5.setText("���������л���Ұ����ĵ�ʦ��ͬѧ��"); // �����ı�����
		jTextField5.setBounds(new Rectangle(1, 168, 240, 38)); // ���ô�С
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
