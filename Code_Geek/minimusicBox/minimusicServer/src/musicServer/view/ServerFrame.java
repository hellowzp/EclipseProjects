/**
 * 功能：这是服务器的主界面
 */
package musicServer.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

import musicServer.downLoadMusic.*;
import musicServer.model.*;
import musicServer.operateToDb.*;

public class ServerFrame extends JFrame implements ActionListener,java.io.Serializable{

	//定义中间部分所需要的组件cardlayout
	JPanel jp_north,jp_south,jp_showInfo,jp_song,jp_user;
	JButton jb_song1,jb_user1,jb_song2,jb_user2;
	JTable jtb_song,jtb_user;
	JScrollPane jsp_song,jsp_user;
	CardLayout card=new CardLayout();
	//定义菜单栏组件
	JMenuBar jmb;
	JMenu jm_file,jm_set,jm_help;//3个菜单项
	JMenuItem jmi_user,jmi_music,jmi_add,jmi_download,jmi_exit,jmi_help,jmi_start,jmi_stop;
	//定义对数据库进行操作的组件
	JButton jb_add,jb_delete,jb_change,jb_find1,jb_find2,jb_back1,jb_back2;
	JTextField jtf_find1,jtf_find2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerFrame sf=new ServerFrame();
	}
	public ServerFrame()
	{
		//北部
		//初始化菜单栏
		jmb=new JMenuBar();
		jm_file=new JMenu(" 文件 ");
		jm_set=new JMenu(" 设置  ");
		jm_help=new JMenu(" 帮助 ");
		//二级菜单
		jmi_user=new JMenuItem("用户信息");
		jmi_user.addActionListener(this);
		jmi_music=new JMenuItem("歌曲信息");
		jmi_music.addActionListener(this);
		jmi_add=new JMenuItem("添加歌曲");
		jmi_add.addActionListener(this);
		jmi_download=new JMenuItem("下载歌曲");
		jmi_download.addActionListener(this);
		jmi_download.setActionCommand("download");
		jmi_exit=new JMenuItem("退出");
		jmi_exit.addActionListener(this);
		jmi_help=new JMenuItem("帮助文档");
		jmi_help.addActionListener(this);
		jmi_start=new JMenuItem("启动服务器");
		jmi_start.addActionListener(this);
		jmi_stop=new JMenuItem("关闭服务器");
		jmi_stop.addActionListener(this);
		
		//添加菜单项
		jm_file.add(jmi_user);
		jm_file.add(jmi_music);
		jm_file.addSeparator();//添加分割线
		jm_file.add(jmi_add);
		jm_file.add(jmi_download);
		jm_file.addSeparator();//添加分割线
		jm_file.add(jmi_exit);
		jm_set.add(jmi_start);
		jm_set.add(jmi_stop);
		jm_help.add(jmi_help);
		//添加菜单项到菜单栏
		jmb.add(jm_file);
		jmb.add(jm_set);
		jmb.add(jm_help);
		jmb.setOpaque(false);
		//将菜单栏添加在面板上
		jp_north=new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_north.setBounds(0,0,560,40);
		jp_north.add(jmb);
		jp_north.setOpaque(false);
		
		//南部
		jb_add=new JButton("添加");
		jb_add.addActionListener(this);
		jb_delete=new JButton("删除");
		jb_delete.addActionListener(this);
		jb_change=new JButton("修改");
		jb_change.addActionListener(this);
		jp_south=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp_south.add(jb_add);
		jp_south.add(jb_delete);
		jp_south.add(jb_change);
		jp_south.setOpaque(false);
		jp_south.setBounds(20,380, 560,40);
		
		//中间
		//cardlayout 歌曲信息显示和用户信息显示
		//card1
		jb_song1=new JButton("歌曲信息");
		jb_user1=new JButton("用户信息");
		jtf_find1=new JTextField(12);
		
		jb_find1=new JButton("查询");
		jb_find1.addActionListener(this);
		jb_back1=new JButton("刷新");
		jb_back1.addActionListener(this);
		JPanel jp2=new JPanel();
		jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp2.setOpaque(false);
		jp2.add(jb_song1);
		jp2.add(jb_user1);
		jp2.add(jtf_find1);
		jp2.add(jb_find1);
		jp2.add(jb_back1);
		jp2.setBounds(40, 18, 500, 40);
		//连接数据库，得到歌曲信息
		String sql="select * from musicInfo";
		String tableName="musicInfo";
		LinkDBModel ldm=new LinkDBModel(sql, tableName);
		jtb_song=new JTable(ldm);
		jsp_song=new JScrollPane(jtb_song);
		jsp_song.setAutoscrolls(true);
		jsp_song.setBounds(45,55 ,500,290 );
		
		//jsp_song.setLayout(new BorderLayout());
		//将得到的歌曲信息添加到面板上
		jp_song=new JPanel(null);
		jp_song.setBounds(20, 45, 560, 300);
		//jp_song.setEnabled(true);
		jp_song.setOpaque(false);
		jp_song.add(jp2);
		jp_song.add(jsp_song);
		
		//card2
		jb_song2=new JButton("歌曲信息");
		jb_user2=new JButton("用户信息");
		jtf_find2=new JTextField(12);
		jb_find2=new JButton("查询");
		jb_find2.addActionListener(this);
		jb_back2=new JButton("刷新");
		jb_back2.addActionListener(this);
		JPanel jp3=new JPanel();
		jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp3.setOpaque(false);
		jp3.add(jb_song2);
		jp3.add(jb_user2);
		jp3.add(jtf_find2);
		jp3.add(jb_find2);
		jp3.add(jb_back2);
		jp3.setBounds(40, 18, 500, 40);
		//连接数据库，得到用户信息
		String sql2="select * from userInfo";
		String tableName2="userInfo";
		LinkDBModel ldm2=new LinkDBModel(sql2, tableName2);
		jtb_user=new JTable(ldm2);		
		JScrollPane jsp_user;
		jsp_user=new JScrollPane(jtb_user);
		jsp_user.setBounds(45,55 ,500,290 );
		//将得到的用户信息添加到面板2
		jp_user=new JPanel(null);
		jp_user.setBounds(20, 45, 560, 300);
		
		jp_user.setOpaque(false);
		jp_user.add(jp3);
		jp_user.add(jsp_user);
		
		//设置卡片布局
		jp_showInfo=new JPanel(card) ;
		jp_showInfo.setBounds(20,22,560,360);
		jp_showInfo.setOpaque(false);
		jp_showInfo.add(jp_song,"1");
		jp_showInfo.add(jp_user,"2");
		
		//卡片跳转设置
		jb_song2.addActionListener(new ActionListener(){ 
	           public void actionPerformed(ActionEvent e) {
	               card.show(jp_showInfo,"1");
	           }	
	       });
	
	    jb_user1.addActionListener(new ActionListener(){ 
	          public void actionPerformed(ActionEvent e) {
	              card.show(jp_showInfo,"2");
		        }	
	      });
		
		
		//将组件及面板添加到主面板上		
	    this.setLayout(null);
	    this.getContentPane().add(jp_north);
		this.getContentPane().add(jp_showInfo);
		//this.add(jp_showInfo,BorderLayout.CENTER);
		this.getContentPane().add(jp_south);
		
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
		
		
		//设置窗口属性
		this.setVisible(true);
		this.setSize(600,450);
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setTitle("迷你音乐播放器-服务器");
		this.setIconImage(new ImageIcon("images/server.jpg").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
	//添加事件的监听
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//预定义处理函数
		AddMusic addMusic;
		UpdateMusic updateMusic;
		LinkDBModel linkDb;
		ServerSendToClient sstc=new ServerSendToClient();;
		Thread th=new Thread(sstc);
		
		//对菜单项的监听
		//信息面板的切换
		if(e.getSource()==jmi_user){
			 card.show(jp_showInfo,"2");
		}if(e.getSource()==jmi_music){
			 card.show(jp_showInfo,"1");
		}if(e.getSource()==jmi_add){
			//添加歌曲
			addMusic=new AddMusic();
			//更新数据库
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);		
		}if(e.getActionCommand().equals("download")){
			//下载歌曲
			DownloadFrame dw = new DownloadFrame("网络文件下载");
	
		}if(e.getSource()==jmi_exit){
			//退出
			System.exit(0);
			this.dispose();
		
			
		}
		//启动服务器
		if(e.getSource()==jmi_start){		
			th.start();
			JOptionPane.showMessageDialog(this, "服务器正在运行,在34566端口监听...");	
		}//关闭服务器	
		if(e.getSource()==jmi_stop){
		
			int res=JOptionPane.showConfirmDialog(this, "温馨提示：关闭服务器将无法响应用户的相关操作！"+"\n"+"是否继续？","确定",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			//对用户的选择进行判断 Y-删除 N—取消
			if(res==JOptionPane.YES_OPTION){    
				//JOptionPane.showMessageDialog(this, "服务器已关闭...");
				th.stop();
				sstc.stop();
				System.out.println("服务器已关闭！");
			}
			
		}if(e.getSource()==jmi_help){
			//打开帮助文档
			JOptionPane.showMessageDialog(this, "这是一款简单的音乐播放软件的服务器端\r\n"+
					"希望您使用愉快！\r\n");
		}
		
		//对button的监听
		//查询
		if(e.getSource()==jb_find1){
			//搜索歌曲
			String song="%"+jtf_find1.getText()+"%";
			//更新数据库
			String sql="select * from musicInfo where musicName" +
					" like '"+song+"' or singer like '"+song+"'";
			
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);	
		}if(e.getSource()==jb_find2){
			//搜索用户
			String user="%"+jtf_find2.getText()+"%";
			//更新数据库
			String sql="select * from userInfo where userName like '"+user+"'";
			String tableName="userInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_user.setModel(linkDb);	
		}
		//刷新列表
		if(e.getSource()==jb_back1){
			String tableName="musicInfo";
			linkDb=new LinkDBModel(tableName);
			card.show(jp_showInfo, "1");
			jtf_find1.setText("");
			jtb_song.setModel(linkDb);
			
		}
		if(e.getSource()==jb_back2){
			//更新数据库
			String sql="select * from userInfo ";
			String tableName="userInfo";
			linkDb=new LinkDBModel(sql, tableName);
			card.show(jp_showInfo, "2");
			jtf_find2.setText("");
			jtb_user.setModel(linkDb);	
			
		}
		//添加
		if(e.getSource()==jb_add){
			addMusic=new AddMusic();
			//更新数据库
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);
		}
			
		//删除
		if(e.getSource()==jb_delete){
			int rowNum=this.jtb_song.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "请选中一行！");
				return;
			}else{
				 linkDb=new LinkDBModel("musicInfo");
				String musicName=(String)(linkDb.getValueAt(rowNum, 0));		
				
				linkDb.DeleteM(musicName);
				//构建新的数据库并更新
				 linkDb=new LinkDBModel("musicInfo");
				 jtb_song.setModel(linkDb);
			}
		}
		//修改
		if(e.getSource()==jb_change){
			int rowNum=this.jtb_song.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "请选中一行！");
				return;
			}else{
				 linkDb=new LinkDBModel("musicInfo");
				updateMusic=new UpdateMusic( linkDb, rowNum);
			}
			//更新数据库
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);
		
		}
		
		
		
	}
	

}
