/**
 * 功能：添加歌曲
 */
package musicClient.operateToDb;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import musicClient.model.MusicModel;
public class AddMusic extends JFrame implements ActionListener{

	//定义所需要的组件
	JPanel jp_add,jp1;
	JLabel jlb_title,jlb_songname,jlb_songaddress,jlb_singer,jlb_songlist,jlb_songlrc;
	public JTextField jtf_songname,jtf_songaddress, jtf_singername, jtf_songlrc;
	public JComboBox jcb_songList;
	JButton jb_scane,jb_brower,jb_sure,jb_quit;
	//定义复选框得到的播放列表
	String listName;
	
	public AddMusic()
	{
		//设置组件属性
		//jlb_pic=new JLabel(new ImageIcon("images/welcome.jpg"));
		jlb_title=new JLabel("歌曲信息");
		jlb_title.setForeground(Color.orange);
		jlb_title.setFont(new Font("华文正楷",Font.BOLD,24));
		jlb_title.setBounds(170, 30, 200, 50);
		jlb_songname=new JLabel("歌   名:",JLabel.CENTER);
		jlb_songaddress=new JLabel("位   置:",JLabel.CENTER);
		jlb_singer=new JLabel("歌   手:",JLabel.CENTER);
		jlb_songlist=new JLabel("列   表:",JLabel.CENTER);
		jlb_songlrc=new JLabel("歌  词:",JLabel.CENTER);
		jlb_songname.setBounds(100, 100, 60, 25);
		jlb_songaddress.setBounds(100, 140, 60, 25);
		jlb_singer.setBounds(100, 180, 60, 25);
		jlb_songlist.setBounds(100, 220, 60, 25);
		jlb_songlrc.setBounds(100, 260, 60, 25);
		
		jtf_songname=new JTextField(40);
		jtf_songname.setText("选择歌曲");
		jtf_songname.setForeground(Color.magenta);
		jtf_songname.getFocusTraversalKeysEnabled();
		jtf_songaddress=new JTextField(100);
		jtf_singername=new JTextField(40);
		jtf_singername.setText("歌手名~");
		jtf_singername.setForeground(Color.darkGray);
		jtf_singername.getFocusListeners();
		String chooseList[]={"默认列表","好听的歌","喜欢的歌"}; 
		jcb_songList=new JComboBox(chooseList);
		jcb_songList.addActionListener(this);
		jcb_songList.setActionCommand("list");
		jtf_songlrc=new JTextField(100);
		//设置组件位置
		jtf_songname.setBounds( 200, 100, 150, 28);
		jtf_songaddress.setBounds( 200, 140, 150, 28);
		jtf_singername.setBounds( 200, 180, 150, 28);
		jcb_songList.setBounds( 200, 220, 150, 28);
		jtf_songlrc.setBounds(200, 260, 150, 28);
		
		
		jb_scane=new JButton("浏览");
		jb_scane.addActionListener(this);
		jb_brower=new JButton("选择");
		jb_brower.addActionListener(this);
		jb_sure=new JButton("确认");
		jb_sure.setForeground(Color.blue);
		jb_sure.addActionListener(this);
		jb_quit=new JButton("取消");
		jb_quit.setForeground(Color.gray);
		jb_quit.addActionListener(this);
		//设置组件位置
		jb_scane.setBounds( 355, 100, 60, 25);
		jb_brower.setBounds(355, 260, 60, 25);
		jb_sure.setBounds(180, 300, 60, 30);
		jb_quit.setBounds( 280, 300, 60, 30);
		
	
		
		//添加组件到面板
		jp_add=new JPanel(null);
		jp_add.setOpaque(false);
		
		jp_add.setBounds(0, 0, 500,400);
		jp_add.add(jlb_title);
		jp_add.add(jlb_songname);
		jp_add.add(jtf_songname);
		jp_add.add(jb_scane);
		jp_add.add(jlb_songaddress);
		jp_add.add(jtf_songaddress);
		jp_add.add(jlb_singer);
		jp_add.add(jtf_singername);
		jp_add.add(jlb_songlist);
		jp_add.add(jcb_songList);
		jp_add.add(jlb_songlrc);
		jp_add.add(jtf_songlrc);
		jp_add.add(jb_brower);
		jp_add.add(jb_sure);
		jp_add.add(jb_quit);
		
			
		this.add(jp_add);
	
		//设置背景图片
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background7.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//使内容窗格透明化
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		 try {
	            // 将LookAndFeel设置成Windows样式
	            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	        } catch (Exception ex) {	
	            ex.printStackTrace();
	        }
	        
		//设置窗体属性
	    this.setTitle("添加歌曲...");
		this.setSize(500,400);
		this.setLocation(300, 150);
		this.setResizable(false);
		this.setVisible(true);
		//AddMusic是子窗口，关闭窗口时不能退出程序
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//对复选框事件处理
		if(e.getActionCommand().equals("list")){
			JComboBox jcb=(JComboBox)e.getSource();
			setListName((String)jcb.getSelectedItem());
			
		}
		//处理事件
		if(e.getSource()==jb_scane)
		{
			//浏览文件
			//文件选择组件JFileChooser
			JFileChooser jfc=new JFileChooser();
			//设置名字
			jfc.setDialogTitle("请选择音乐文件... ");
			jfc.setFileFilter(new JAVAFiler(
			"*.mp3,*.AU、*.AVI、*.MIDI、*.MPEG、*.QuickTime,*.wav"));
			//默认方式
			jfc.showOpenDialog(null);
			//显示窗口
			jfc.setVisible(true);
			
			//得到用户选择文件的全路径
			String filepath=jfc.getSelectedFile().getAbsolutePath();
			String filename=jfc.getSelectedFile().getName();
			jtf_songname.setText("");
			jtf_songname.setText(filename);
			System.out.println(filepath);
			jtf_songaddress.setText("");
			jtf_songaddress.setText(filepath);	
		}
		if(e.getSource()==jb_brower)
		{
			//浏览文件
			//文件选择组件JFileChooser
			JFileChooser jfc=new JFileChooser();
			//设置名字
			jfc.setDialogTitle("请选择歌词文件... ");
			//默认方式
			jfc.showOpenDialog(null);
			//显示窗口
			jfc.setVisible(true);
			
			//得到用户选择文件的全路径
			String lrcpath=jfc.getSelectedFile().getAbsolutePath();
			jtf_songlrc.setText("");
			jtf_songlrc.setText(lrcpath);	
		}
		if(e.getSource()==jb_sure)
		{
			//连接数据库进行操作
			/*
			 * 确认提交,添加歌曲信息 获得会话框中文本框中的信息
			 */
			String songname = this.getJtf_songname().getText();
			String songaddress = this.getJtf_songaddress().getText();
			String singername= this.getJtf_singername().getText();
			String songlist=listName;
			String songlrc = this.getJtf_songlrc().getText();
			String mess = null;// 用来存储警告的信息
			if (songname == null || " ".equals(songname)) {
				mess = "歌曲名为空！";
				JOptionPane.showMessageDialog(this, mess);
				
			}else {
				//	连接数据库，将得到的歌曲信息发送到数据库并保存
				MusicModel mm=new MusicModel();
				mm.AddM(this);
				
			}
			
		}
		if(e.getSource()==jb_quit)
			{
				this.dispose();
			}
	}
	
	//一堆set和get的方法，用于得到添加的歌曲信息
	public JTextField getJtf_songlrc() {
		return jtf_songlrc;
	}
	public void setJtf_songlrc(JTextField jtf_songlrc) {
		this.jtf_songlrc = jtf_songlrc;
	}
	public JTextField getJtf_songname() {
		return jtf_songname;
	}
	public void setJtf_songname(JTextField jtf_songname) {
		this.jtf_songname = jtf_songname;
	}
	public JTextField getJtf_songaddress() {
		return jtf_songaddress;
	}
	public void setJtf_songaddress(JTextField jtf_songaddress) {
		this.jtf_songaddress = jtf_songaddress;
	}
	public JTextField getJtf_singername() {
		return jtf_singername;
	}
	public void setJtf_singername(JTextField jtf_singername) {
		this.jtf_singername = jtf_singername;
	}

	public JComboBox getJcb_songList() {
		return jcb_songList;
	}
	public void setJcb_songList(JComboBox jcb_songList) {
		this.jcb_songList = jcb_songList;
	}
	
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	
}

//设置文件选择过滤器
class JAVAFiler extends FileFilter {

	private static final long serialVersionUID = 1L;
	String ext;

	public JAVAFiler(String ext) {
		this.ext = ext;
	}

	public boolean accept(File file) {
		if (file.isDirectory())
			return true;

		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');

		if (index > 0 && index < fileName.length() - 1) {
			String extension = fileName.substring(index + 1).toLowerCase();
			if (ext.toLowerCase().indexOf(extension.toLowerCase()) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "媒体(*.mp3,*.AU、*.AVI、*.MIDI、*.MPEG、,*.wav)";
	}
}
	
	
