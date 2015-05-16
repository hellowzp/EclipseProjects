package cn.edu.nwsuaf.videoCaptureServer.panes;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.edu.nwsuaf.videoCaptureServer.about.About;

public class ConfigPane extends JPanel{
	JFrame jframe;
	JPanel southPanel;

	JButton aboutBtn = new JButton("关于本服务器");
	public ConfigPane(JFrame jframe){
		this.jframe = jframe;
		this.setLayout(new BorderLayout());
		init();
	}
	
	public void init(){
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(aboutBtn,BorderLayout.EAST);
		this.add(southPanel,BorderLayout.NORTH);
		aboutBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				new About(jframe,"关于本软件",true);
				
			}
			
		});
	}
}
