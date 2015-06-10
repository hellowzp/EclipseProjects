package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class VideoBar {
	/**
	 * ���˵���
	 */
	private JMenuBar menuBar = new VideoMenuBar();
	
	/**
	 * ���˵�
	 */
	private VideoMenu jmenu_server, jmenu_system,jmenu_appearance, jmenu_help;
	/**
	 * �˵���
	 */
	private VideoMenuItem   menuItem_help,menuItem_about;
	/**
	 * ��ѡ��˵���
	 */
	private VideoRadioButtonMenuItem  default_apperance,system_apperance;
	private ButtonGroup gruop_apperance = new ButtonGroup();
	
	public VideoBar(){
		
		initMenuBar();
		JFrame jframe = new JFrame();
		jframe.add(menuBar,BorderLayout.NORTH);
		jframe.setSize(600, 300);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
	
		
	}
	
	public void initMenuBar(){
		//��ʼ�����˵�
		jmenu_server = new VideoMenu("����");
		jmenu_system = new VideoMenu("ϵͳ");
		jmenu_appearance = new VideoMenu("���");
		jmenu_help = new VideoMenu("����");
		//��ʼ���˵���
		
		default_apperance = new VideoRadioButtonMenuItem("Ĭ�����");
		system_apperance = new VideoRadioButtonMenuItem("ϵͳ���");
		menuItem_help = new VideoMenuItem("����");
		menuItem_about = new VideoMenuItem("����");
		
		
		//ע�����
		
		
		//��װ�˵�
		menuBar.add(jmenu_server);
		menuBar.add(jmenu_system);
		menuBar.add(jmenu_appearance);
		menuBar.add(jmenu_help);
		gruop_apperance.add(default_apperance);
		gruop_apperance.add(system_apperance);
		jmenu_appearance.add(default_apperance);
		jmenu_appearance.add(system_apperance);
		jmenu_help.add(menuItem_help);
		jmenu_help.add(menuItem_about);
		
		
		
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new VideoBar();

	}

}
