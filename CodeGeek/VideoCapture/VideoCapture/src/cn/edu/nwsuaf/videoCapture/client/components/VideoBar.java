package cn.edu.nwsuaf.videoCapture.client.components;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class VideoBar {
	/**
	 * 主菜单条
	 */
	private JMenuBar menuBar = new VideoMenuBar();
	
	/**
	 * 主菜单
	 */
	private VideoMenu jmenu_server, jmenu_system,jmenu_appearance, jmenu_help;
	/**
	 * 菜单条
	 */
	private VideoMenuItem   menuItem_help,menuItem_about;
	/**
	 * 复选框菜单条
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
		//初始化主菜单
		jmenu_server = new VideoMenu("服务");
		jmenu_system = new VideoMenu("系统");
		jmenu_appearance = new VideoMenu("外观");
		jmenu_help = new VideoMenu("帮助");
		//初始化菜单项
		
		default_apperance = new VideoRadioButtonMenuItem("默认外观");
		system_apperance = new VideoRadioButtonMenuItem("系统外观");
		menuItem_help = new VideoMenuItem("帮助");
		menuItem_about = new VideoMenuItem("关于");
		
		
		//注册监听
		
		
		//组装菜单
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
