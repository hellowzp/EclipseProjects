/**
 * 功能：这是播放器主界面窗口
 * 日期：2012-10-23
 * 
 *？？？ 需将播放列表置于面板上，可选中相应的歌曲，能执行删除，修改的操作。
 *？？？播放列表的代码过于冗余！！！
 */
package musicClient.view;

import java.awt.*;

import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.RealizeCompleteEvent;
import javax.swing.*;

import java.awt.event.*;
import java.util.*;
import java.util.Map.Entry;

import musicClient.model.MusicModel;
import musicClient.operateToDb.*;
import musicClient.player.LrcParser;
import musicClient.player.PlayerWork;
import musicClient.downLoadMusic.*;


public class MainView extends JFrame implements 
ActionListener,MouseListener,Runnable{
	/**
	 * 1、左边的歌曲信息及管理
	 *   1.1  系统小图、当前播放、时间条、三个按钮，音量控制
	 *   1.2 （Borderlayout）添加删除、搜索框和按钮，播放列表（左）歌曲显示（右），三个操作按钮
	 *   注意：列表不能为空，否则无法进行判断，出现空指针
	 * 2、右边的歌词面板
	 *   2.1 横幅栏
	 *   2.2 歌词显示，（无歌词则显示自带图片加文字）
	 */

	//函数的入口
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		MainView vl=new MainView("miniMusicBox");
//		Thread t=new Thread(vl);
//		t.start();
//		
//	}
	
	//创建播放器
	PlayerWork playerWork=new PlayerWork();
	//创建歌词类,添加歌词信息
	LrcParser lrcParser;
	//定义用于与数据库交互的model类
	MusicModel musicM=new MusicModel();
	
	
	/**定义一个用于存放所有歌曲信息的hashmap
	*？？？由于在本项目中，频繁的对数据库进行操作，导致代码不够稳定，运行速度慢
	*！！！拓展：第一次与数据库交互时，可以直接得到数据库的所有信息，
	*	并保存在hashmap中，以后只需对hashmap操作即可
	**/
	//两个String类型分别为歌曲名和歌曲地址
	HashMap hashMap=new HashMap<Integer , String>();
	int key=1;//k用于表示hashMap的主键,k=[1,length];
	int totalSong=0;
//	//定义一个迭代器，用于对hashMap的遍历
//	Iterator<String> iterator = hashMap.keySet().iterator(); 
	
	//定义字符串，用于存放用户单击时得到的歌曲、歌曲地址和歌词set，get方法
	String songChoose;
	String songAddress;
	String lrcAddress;
	//定义用于下载时传递的字符串
	
	String songAdd;
	
	//定义改变播放时间的变量
	protected int totalTime=0;
	int totalMinute=0, totalSecond=0;
	int minute1=0,second1=0,minute2=0,second2=0;
	protected int playTime=0;
	
	/**
	 * 界面分割：1、左边，》歌曲信息显示，列表显示，歌曲操作按钮显示
	 *        2、右边，》系统时间显示（实时） ，搜索框，歌词显示    		
	 */				
	
	JLabel jlb_background;//背景图片
	
//1、左边，》歌曲信息显示，列表显示，歌曲操作按钮显示
	//list部分（北部的歌曲信息，中部的列表，南部的操作）
	JPanel jp_songinfo,jp_showlist,jp_operation;
	//JPanel jp_slider=new JPanel(null);//用于放置系统滚动条
	//北部
	JLabel jlb_pic,jlb_song,jlb_voice,jlb_no_voice;
	JLabel jlb_time1,jlb_time2;
	JSlider js_time,js_voice;
	//JProgressBar jpb_time,jpb_voice;
	JButton jb_play_pause,jb_next,jb_last;
	JMenuBar jmb_style;
	JMenu jm_style;
	JMenuItem jmi_shunxu,jmi_xunhuan,jmi_danqu;
	//南部的按钮
	JButton jb_add,jb_delete,jb_change;
	
	//中间的列表显示（卡片布局）
	CardLayout cl;
	JTable jtb_show;
	//定义第一个卡片
	JPanel  jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3;
	JScrollPane jsp1;
	//定义第二个卡片
	JPanel  jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	//定义第三个卡片
	JPanel  jphmd1,jphmd2,jphmd3;
	JButton jphmd_jb1,jphmd_jb2,jphmd_jb3;
	JScrollPane jsp3;
	
//2、右边，》系统时间显示（实时） ，搜索框，歌词显示    	
	JPanel jp_lrc,jp_ku,jp_background,jp_so,jp_showCard;
	CardLayout cl_right;
	JLabel jlb_system_time,jlb_lrc;
	JTextField jtf_so;
	JButton jb_so,jb_download,jb_lrc,jb_ku,jb_background,jb_find;
	//card 中的组件
	JTable jtb_so;
	JTextArea jta_lrc;
	JScrollPane jsp_lrc,jsp_song;
	JLabel jlb_singer1,jlb_singer2,jlb_singer3,jlb_singer4;
	JLabel jlb_back1,jlb_back2,jlb_back3,jlb_back4;
	JLabel jlb_songs[];
	//没有歌词是显示的内容
	String  no_lrc[]={"-----#$@%^#$%$-----",
					  "喜欢一个人在热闹的地方孤独地读书",
						"戴上耳机听一首单曲循环的音乐",
						"好像整个世界与我无关",
						"那天午夜，在地铁里",
					"与一群刚刚下班的人群乘着不知开往哪里的列车",
					   "-----*&%$#@%^#@&----"};
	
	//左边的歌曲信息显示，列表显示，歌曲操作按钮显示
	public void ViewLeft() {
		//北部
		jp_songinfo=new JPanel();
		jp_songinfo.setLayout(null);
		jlb_pic=new JLabel(new ImageIcon("images/pic.jpg"), JLabel.LEFT);
		jlb_song=new JLabel("正在播放···");
		jlb_time1=new JLabel("00:00");
		jlb_time1.setForeground(Color.black);
		js_time=new JSlider(JSlider.HORIZONTAL,0,100,0);
		js_time.setForeground(Color.blue);
		jlb_time2=new JLabel();
		jlb_time2.setForeground(Color.black);

		jb_play_pause=new JButton();
		jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
		jb_play_pause.setToolTipText("播放/暂停");
		jb_play_pause.addActionListener(this);
		jb_play_pause.setActionCommand("jb_play_pause");
		
		jb_next=new JButton(new ImageIcon("images/next.jpg"));
		jb_next.setToolTipText("下一曲");
		jb_next.addActionListener(this);
		jb_next.setActionCommand("jb_next");
		
		jb_last=new JButton(new ImageIcon("images/last.jpg"));
		jb_last.setToolTipText("上一曲");
		jb_last.addActionListener(this);
		jb_last.setActionCommand("jb_last");
		
		jlb_voice=new JLabel(new ImageIcon("images/voice.jpg"));
		jlb_no_voice=new JLabel(new ImageIcon("images/no_voice.jpg"));
		jlb_voice.setToolTipText("音量");
		js_voice=new JSlider(JSlider.HORIZONTAL,0,100,40);
		js_voice.setBackground(Color.BLUE);
		
		//设置组件的位置并添加到面板上
		jp_songinfo.setBounds(5, 5, 248, 92);
		jlb_pic.setBounds(0,0, 70, 38);
		jlb_song.setBounds(72,12,170,20);
		//定义用于存放时间条的面板
		jlb_time1.setBounds(3,42,30,20);
		js_time.setBounds(37,48,170,8);
		jlb_time2.setBounds(212,42,30,20);
		
		jb_last.setBounds(10,62,24,23);
		jb_play_pause.setBounds(37,62,28,23);
		jb_next.setBounds(69,62,24,23);
		jlb_voice.setBounds(120,62,22,20);
		js_voice.setBounds(144,72,50,6);
		
		//在声音后边添加模式按钮
		jmb_style=new JMenuBar();
		jmb_style.setBounds(210, 64, 30, 20);
		jm_style=new JMenu("模式");
		jmi_shunxu=new JMenuItem(new ImageIcon("images/listloop.jpg"));
		jmi_shunxu.setToolTipText("顺序播放");
		jmi_xunhuan=new JMenuItem(new ImageIcon("images/random.jpg"));
		jmi_xunhuan.setToolTipText("随机播放");
		jmi_danqu=new JMenuItem(new ImageIcon("images/single.jpg"));
		jmi_danqu.setToolTipText("单曲循环");		
		//添加事件监听
		jmi_shunxu.addActionListener(this);
		jmi_shunxu.setActionCommand("shunxu");
		jmi_xunhuan.addActionListener(this);
		jmi_xunhuan.setActionCommand("suiji");
		jmi_danqu.addActionListener(this);
		jmi_danqu.setActionCommand("danqu");
		//将子菜单添加到菜单栏
		jm_style.add(jmi_shunxu);
		jm_style.add(jmi_xunhuan);
		jm_style.add(jmi_danqu);
		
		jmb_style.add(jm_style);
		
		
		//jp_songinfo.add(jlb_pic1);
		jp_songinfo.add(jlb_pic);
		jp_songinfo.add(jlb_song);
		jp_songinfo.add(jlb_time1);
		jp_songinfo.add(js_time);
		jp_songinfo.add(jlb_time2);

		jp_songinfo.add(jb_last);
		jp_songinfo.add(jb_play_pause);
		jp_songinfo.add(jb_next);
		//jp_songinfo.add(jb_pause);
		jp_songinfo.add(jlb_voice);
		jp_songinfo.add(js_voice);
		jp_songinfo.add(jmb_style);
		jp_songinfo.setOpaque(false);
		
		
		//南部的组件
		jp_operation=new JPanel();
		jp_operation.setLayout(new FlowLayout());
		//jp_operation.setLayout(null);
		jp_operation.setSize(240, 35);
		jp_operation.setLocation(5, 443);
		//jp_operation.setBounds(5, 568, 300, 35);
		//jp_operation.setBackground(Color.blue);
		jb_add=new JButton("添加");
		jb_add.addActionListener(this);
		jb_add.setActionCommand("add");
		jb_delete=new JButton("删除");
		jb_delete.addActionListener(this);
		jb_delete.setActionCommand("delete");
		jb_change=new JButton("修改");
		jb_change.addActionListener(this);
		jb_change.setActionCommand("change");

		//将南部的按钮添加到面板中
		jp_operation.add(jb_add);
		jp_operation.add(jb_delete);
		jp_operation.add(jb_change);
		jp_operation.setOpaque(false);
		
		
		
		//中间的组件（播放列表）

 		//默认列表
 		jphy_jb1=new JButton("默认列表");	
 		jphy_jb2=new JButton("好听的歌");
 		//jphy_jb2.addActionListener(this);
 		jphy_jb3=new JButton("喜欢的歌");
 		//jphy_jb3.addActionListener(this);
 		
 		jphy1=new JPanel(new BorderLayout());
 		//初始化默认列表
 		MusicModel musicM=new MusicModel();
 		//歌曲总数
 		int length=musicM.getRowCount();
 		System.out.println("共有歌曲："+length);
 		
 		int length1=length;
 		jphy2=new JPanel(new GridLayout(length1,1,4,4));
 		JLabel []jlbs1=new JLabel[length1];
 		
 		for(int i=0;i<jlbs1.length;i++)
 		{
 			String listName1=(String)musicM.getValueAt(i, 3);
 			
 			//得到默认列表歌曲 根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName1==null||listName1.equals("默认列表")){
	 			String moren=(String)musicM.getValueAt(i, 0);
	 			jlbs1[i]=new JLabel(moren,new ImageIcon("images/mouse.jpg"),JLabel.LEFT);
	 			//jlbs[i]=new JLabel("my music no."+i,new ImageIcon("images/mouse.jpg"),JLabel.LEFT);
	 			jlbs1[i].addMouseListener(this);
	 			jphy2.add(jlbs1[i]);
 			}
 		}
 		
 		//两行一列的网格布局
 		jphy3=new JPanel(new GridLayout(2,1));
 		jphy3.add(jphy_jb2);
 		jphy3.add(jphy_jb3);
 		
 		jsp1=new JScrollPane(jphy2);
 			
 		//对jphy1初始化
 		jphy1.add(jphy_jb1,BorderLayout.NORTH);
 		jphy1.add(jsp1,BorderLayout.CENTER);
 		jphy1.add(jphy3,BorderLayout.SOUTH);
 		
 		
 		//好听的歌
 		jpmsr_jb1=new JButton("默认列表");	
 		//jpmsr_jb1.addActionListener(this);
 		jpmsr_jb2=new JButton("好听的歌");
 		jpmsr_jb3=new JButton("喜欢的歌");
 		//jpmsr_jb3.addActionListener(this);
 	
 		jpmsr1=new JPanel(new BorderLayout());
 	 	
 		jpmsr2=new JPanel(new GridLayout(2,1));
 		jpmsr2.add(jpmsr_jb1);
 		jpmsr2.add(jpmsr_jb2);
 			
 		//初始化好听的歌列表
 		int length2=musicM.getRowCount();
 		jpmsr3=new JPanel(new GridLayout(length2,1,4,4));
 		JLabel []jlbs2=new JLabel[length2];
 		for(int i=0;i<jlbs2.length;i++)
 		{
 			String listName2=(String)musicM.getValueAt(i, 3);
 			//得到歌曲列表  根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName2!=null&&listName2.equals("好听的歌")){
 				String niceMusic=(String)musicM.getValueAt(i, 0);
	 			jlbs2[i]=new JLabel(niceMusic,new ImageIcon("images/miqi.jpg"),JLabel.LEFT);
	 			jlbs2[i].addMouseListener(this);
	 			jpmsr3.add(jlbs2[i]);
	 			
 			}
 		}
 				
 		jsp2=new JScrollPane(jpmsr3);
 				
 		//对jpmsr1初始化
 		jpmsr1.add(jpmsr2,BorderLayout.NORTH);
 		jpmsr1.add(jsp2,BorderLayout.CENTER);
 		jpmsr1.add(jpmsr_jb3,BorderLayout.SOUTH);
 		
 		
 		//喜欢的歌
 		jphmd_jb1=new JButton("默认列表");	
 		//jphmd_jb1.addActionListener(this);
 		jphmd_jb2=new JButton("好听的歌");
 		//jphmd_jb2.addActionListener(this);
 		jphmd_jb3=new JButton("喜欢的歌");
 		
 		jphmd1=new JPanel(new BorderLayout());
 		//初始化默认列表
 		int length3=musicM.getRowCount();
 		jphmd2=new JPanel(new GridLayout(length3,1,4,4));
 		JLabel []jlbs3=new JLabel[length3];
 		for(int i=0;i<jlbs3.length;i++)
 		{
 			String listName3=(String)musicM.getValueAt(i, 3);
 			//得到默认列表歌曲 根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName3!=null&&listName3.equals("喜欢的歌")){
 				String loveMusic=(String)musicM.getValueAt(i, 0);
	 			jlbs3[i]=new JLabel(loveMusic,new ImageIcon("images/meng.jpg"),JLabel.LEFT);
	 			jlbs3[i].addMouseListener(this);
	 			jphmd2.add(jlbs3[i]);
 		
 			}
 		}
 		
 		jphmd3=new JPanel(new GridLayout(3,1));
 		jphmd3.add(jphmd_jb1);
 		jphmd3.add(jphmd_jb2);
 		jphmd3.add(jphmd_jb3);
 		
 		jsp3=new JScrollPane(jphmd2);
 			
 		//对jphmd1初始化
 		jphmd1.add(jphmd3,BorderLayout.NORTH);	
 		jphmd1.add(jsp3,BorderLayout.CENTER);
 		
 		
 		//初始化卡片
 		cl=new CardLayout();
 		jp_showlist=new JPanel(cl);
 		jp_showlist.setSize(248, 346);
 		jp_showlist.setLocation(5, 98);
 		//jp_showlist.setOpaque(false);
 	   
 		jp_showlist.add(jphy1,"1");
 		jp_showlist.add(jpmsr1,"2");
 		jp_showlist.add(jphmd1,"3");
		
 		//切换到面板1
 		jpmsr_jb1.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
               cl.show(jp_showlist, "1");
            }
        });
 		jphmd_jb1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               cl.show(jp_showlist, "1");
            }
        });
 		
 		//切换到面板2
 		jphy_jb2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl.show(jp_showlist, "2");
            }
        });
 		jphmd_jb2.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl.show(jp_showlist, "2");
            }
        });

 		//切换到面板3
 		jphy_jb3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl.show(jp_showlist, "3");
            }	
        });
 		jpmsr_jb3.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl.show(jp_showlist, "3");
            }	
        });
 		
 		 //JSplitPane 用于分隔两个（只能两个）Component
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		//HORIZONTAL_SPLIT表示水平排列两个面板,VERTICAL_SPLIT表示上下排列两个面板设置分隔条的位置，x=200
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(2);
	
		
		
		
 		//将panel放置在主panel上并显示
		this.setLayout(null);
		this.add(splitPane);
		
		this.getContentPane().add(jp_songinfo);
		this.getContentPane().add(jp_showlist);
		this.getContentPane().add(jp_operation);
	
		
	}
	
	//右边的歌词面板显示，乐库，换肤
	public void ViewRight(){
		
		//初始化卡片
		cl_right=new CardLayout();
		jp_showCard=new JPanel(cl_right);
		jp_showCard.setBounds(254, 96, 378, 342);
		
		//歌词
		jp_lrc=new JPanel(null);
		jp_lrc.setBounds(254, 96, 378, 342);
		jp_lrc.setOpaque(true);
		jta_lrc=new JTextArea();
		jta_lrc.setLineWrap(true);
		jsp_lrc=new JScrollPane(jta_lrc);
		jsp_lrc.setHorizontalScrollBar(null);
		jta_lrc.setBackground(Color.lightGray);
		jsp_lrc.setBounds(2, 2, 378, 342);
		jsp_lrc.setOpaque(true);	
		jp_lrc.add(jsp_lrc);
		
		//默认现实加入歌词面板
		jta_lrc.append("\r\n"+"\r\n");
		for(int i=0;i<no_lrc.length;i++){
			jta_lrc.append("\r\t"+no_lrc[i]+"\r\n");
		}
		
		//乐库
		jp_ku=new JPanel(new GridLayout(2,2,10,10));
		jp_ku.setBounds(270, 100, 350, 320);
		jp_ku.setOpaque(true);
		jlb_singer1=new JLabel(new ImageIcon("images/刘若英.jpg"));
		jlb_singer1.setToolTipText("点击搜索刘若英新歌");
		jlb_singer2=new JLabel(new ImageIcon("images/周杰伦.jpg")); 
		jlb_singer2.setToolTipText("点击搜索周杰伦新歌");
		jlb_singer3=new JLabel(new ImageIcon("images/陈奕迅.jpg"));
		jlb_singer3.setToolTipText("点击搜索陈奕迅新歌");
		jlb_singer4=new JLabel(new ImageIcon("images/梁静茹.jpg")); 
		jlb_singer4.setToolTipText("点击搜索梁静茹新歌");
		jp_ku.add(jlb_singer1);
		jp_ku.add(jlb_singer2);
		jp_ku.add(jlb_singer3);
		jp_ku.add(jlb_singer4);
		
		//对4个label进行监听，点击搜索相应歌手的歌曲
		jlb_singer1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					//将之前的搜索结果清空
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//用于模糊搜索
					String searchMusic="%"+"刘若英"+"%";
					//更新数据库
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "没有找到歌曲！");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("双击下载");
							songAdd=(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//下载，创建线程
											 ClientReciveFromServer crfs=new ClientReciveFromServer(songAdd);
									    	 Thread t=new Thread(crfs);
									    	 t.start();
										}
										
									}
									@Override
									public void mouseEntered(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.blue);
									}
									@Override
									public void mouseExited(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.black);
									}
									
							});	
						}		

					  }//for(num)
					}//(num!=0)
				}			
			}
		});
		jlb_singer2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					//将之前的搜索结果清空
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//用于模糊搜索
					String searchMusic="%"+"周杰伦"+"%";
					//更新数据库
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "没有找到歌曲！");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("双击下载");
							songAdd="歌曲地址："+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//下载，创建线程
											 ClientReciveFromServer crfs=new ClientReciveFromServer(songAdd);
									    	 Thread t=new Thread(crfs);
									    	 t.start();
										}
										
									}
									@Override
									public void mouseEntered(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.blue);
									}
									@Override
									public void mouseExited(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.black);
									}
									
							});	
						}		

					  }//for(num)
					}//(num!=0)
				}
			}
		});
		jlb_singer3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					//将之前的搜索结果清空
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//用于模糊搜索
					String searchMusic="%"+"陈奕迅"+"%";
					//更新数据库
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "没有找到歌曲！");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("双击下载");
							songAdd="歌曲地址："+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//下载，创建线程
											 ClientReciveFromServer crfs=new ClientReciveFromServer(songAdd);
									    	 Thread t=new Thread(crfs);
									    	 t.start();
										}
										
									}
									@Override
									public void mouseEntered(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.blue);
									}
									@Override
									public void mouseExited(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.black);
									}
									
							});	
						}		

					  }//for(num)
					}//(num!=0)
				}
				
			}
		});
		jlb_singer4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					//将之前的搜索结果清空
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//用于模糊搜索
					String searchMusic="%"+"梁静茹"+"%";
					//更新数据库
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "没有找到歌曲！");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("双击下载");
							songAdd="歌曲地址："+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//下载，创建线程
											//下载，创建线程
											 ClientReciveFromServer crfs=new ClientReciveFromServer(songAdd);
									    	 Thread t=new Thread(crfs);
									    	 t.start();
										}
										
									}
									@Override
									public void mouseEntered(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.blue);
									}
									@Override
									public void mouseExited(MouseEvent arg0) {
										// TODO Auto-generated method stub
										JLabel jlb=(JLabel)arg0.getSource();
										jlb.setForeground(Color.black);
									}
									
							});	
						}		

					  }//for(num)
					}//(num!=0)
				}
			}
		});
		
		//皮肤
		jp_background=new JPanel(new GridLayout(2,2,10,10));
		jp_background.setBounds(270, 100, 350, 320);
		jp_background.setOpaque(true);
		jlb_back1=new JLabel(new ImageIcon("images/back01.jpg"));
		jlb_back1.setToolTipText("点击切换背景");
		jlb_back2=new JLabel(new ImageIcon("images/back02.jpg")); 
		jlb_back2.setToolTipText("点击切换背景");
		jlb_back3=new JLabel(new ImageIcon("images/back03.jpg"));
		jlb_back3.setToolTipText("点击切换背景");
		jlb_back4=new JLabel(new ImageIcon("images/back04.jpg")); 
		jlb_back4.setToolTipText("点击切换背景");
		jp_background.add(jlb_back1);
		jp_background.add(jlb_back2);
		jp_background.add(jlb_back3);
		jp_background.add(jlb_back4);
		
		//监听4个label，点击切换背景
		jlb_back1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					jlb_background.setIcon(new ImageIcon("images/back1.jpg"));
				}
				
			}
		});
		jlb_back2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					jlb_background.setIcon(new ImageIcon("images/back2.jpg"));
				}
				
			}
		});
		jlb_back3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					jlb_background.setIcon(new ImageIcon("images/back3.jpg"));
				}
				
			}
		});
		jlb_back4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					jlb_background.setIcon(new ImageIcon("images/back4.jpg"));
				}
				
			}
		});
		
		//搜索的歌曲
		jp_so=new JPanel(null);
		jp_so.setBounds(254, 96, 378, 342);
		jlb_songs=new JLabel[totalSong];
		for(int k=0;k<totalSong;k++){
			jlb_songs[k]=new JLabel();
			jlb_songs[k].setBounds(10,30*k, 300, 30);
			jp_so.add(jlb_songs[k]);
		}
		
		//设置card布局
		jp_showCard.setOpaque(true);
		jp_showCard.add(jp_ku,"2");
		jp_showCard.add(jp_lrc,"1");
		jp_showCard.add(jp_background,"3");
		jp_showCard.add(jp_so,"4");
		
		
		//初始化4个用于切换的button
		jb_lrc=new JButton(new ImageIcon("images/lrc.jpg"));
		jb_ku=new JButton(new ImageIcon("images/ku.jpg"));
		jb_background=new JButton(new ImageIcon("images/pifu.jpg"));
		jb_find=new JButton(new ImageIcon("images/find.jpg"));
		jb_lrc.setBounds(254,68,50,27);
		jb_ku.setBounds(304,68,50,27);
		jb_background.setBounds(354,68,50,27);
		jb_find.setBounds(404,68,50,27);
		
		this.add(jb_lrc);
		this.add(jb_ku);
		this.add(jb_background);
		this.add(jb_find);
		this.getContentPane().add(jp_showCard);
		
		//4个button的监听，实现面板的切换
		jb_lrc.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl_right.show(jp_showCard, "1");
            }	
        });
		jb_ku.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl_right.show(jp_showCard, "2");
            }	
        });
		jb_background.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl_right.show(jp_showCard, "3");
            }	
        });
		jb_find.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                cl_right.show(jp_showCard, "4");
            }	
        });
		
		
		
	}
	//构造函数，对组件初始化
	public MainView(String title)
	{
		/*2、右边
		 * 2.1 系统时间显示（实时） ，搜索框，
		 * 2.2  歌词显示,乐库，换肤 
		*/   
		
		//2.1系统时间显示（实时） ，搜索框，
		jlb_system_time=new JLabel();
		jlb_system_time.setForeground(Color.magenta);
		
		jtf_so=new JTextField(50);
		jb_so=new JButton(new ImageIcon("images/search.jpg"));
		jb_so.setToolTipText("搜歌··");
		jb_so.addActionListener(this);
		jb_so.setActionCommand("search");
		jb_download=new JButton(new ImageIcon("images/download.jpg"));
		jb_download.setToolTipText("网络歌曲下载");
		jb_download.addActionListener(this);
		jb_download.setActionCommand("download");

		
		//最底部的歌词实时显示
		jlb_lrc=new JLabel();
		jlb_lrc.setText("歌 词:");
		jlb_lrc.setForeground(Color.blue);
		jlb_lrc.setFont(new Font("华文正楷",Font.BOLD,18));
			
		
		this.setLayout(null);
		jlb_system_time.setBounds(400,5,240,30);
		jtf_so.setBounds(370, 40, 120, 28);
		jb_so.setBounds(489, 40, 36, 28);
		jb_download.setBounds(550, 40, 40, 28);
		
		//最下面的歌词
		jlb_lrc.setBounds(250, 430, 50, 50);
	
		this.add(jlb_system_time);
		this.add(jlb_lrc);
		this.add(jtf_so);
		this.add(jb_so);
		this.add(jb_download);
		
		
		
		try {
            // 将LookAndFeel设置成Windows样式
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {	
            ex.printStackTrace();
        }
        
		//设置背景图片
        jlb_background=new JLabel();
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background3.jpg");
		jlb_background.setIcon(background);
		jlb_background.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//使内容窗格透明化
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(jlb_background,new Integer(Integer.MIN_VALUE));
		
		
		//显示左右两边的面板		
		this.ViewLeft();
		this.ViewRight();
		
		this.setSize(640,500);
		this.setResizable(false);
		this.setLocation(200,100);
		this.setTitle(title);
		this.setIconImage(new ImageIcon("images/get23.jpg").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	
	
	//将歌曲名以及歌曲地址放在hashMap中
	{
		MusicModel mm=new MusicModel();
		int length=mm.getRowCount();
		for(int i=0;i<length;i++){
			String listName1=(String)mm.getValueAt(i, 3);	
	 			//得到默认列表歌曲 根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
	 			if(listName1==null||listName1.equals("默认列表")){
				hashMap.put(key,(String)mm.getValueAt(i, 1) );
				key=key+1;
				System.out.println("歌曲 "+(key-1)+" : "+hashMap.get(key-1));
 			}
 		}
		for(int i=0;i<length;i++){
			String listName2=(String)mm.getValueAt(i, 3);	
 			//得到默认列表歌曲 根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName2!=null&&listName2.equals("好听的歌")){
			hashMap.put(key,(String)mm.getValueAt(i, 1) );
			key=key+1;
			System.out.println("歌曲 "+(key-1)+" : "+hashMap.get(key-1));
			}
		}
		for(int i=0;i<length;i++){
			String listName3=(String)mm.getValueAt(i, 3);	
 			//得到默认列表歌曲 根据列表名判断|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName3!=null&&listName3.equals("喜欢的歌")){
			hashMap.put(key,(String)mm.getValueAt(i, 1) );
			key=key+1;
			System.out.println("歌曲 "+(key-1)+" : "+hashMap.get(key-1));
			}
		}
		
		totalSong=key-1;
	}
	
	//计算总时间并按照正确格式显示
	 public String total(int t_seconds) {
		   minute2 = (int) t_seconds / 60;
		   second2 = (int) t_seconds % 60;
		   //jlb_time2的设置
		   String time2;
			if(minute2<10&&second2<10){
				time2="0"+minute2+":0"+second2;
			}else if(minute2<10&&second2>9){
				time2="0"+minute2+":"+second2;
			}else if(minute2>9&&second2<10){
				time2=minute2+":0"+second2;
			}else{
				time2=minute2+":"+second2;
			}
		 
		   return time2;
		 }
	
	 
	//设置播放器的初始状态,b_paly=false 表示当前播放器没有启动
	//b_paly 还用于顺序播放和循环播放的判断条件。
	boolean b_play=false;
	//默认情况下为顺序播放，（三种播放模式）
	boolean b_loopPlay=true;
	boolean b_singlePlay=false;
	boolean b_randomPlay=false;
	//用于添加同步歌词时
	int k=0;
	
	 
	 //线程，用于实时显示播放时间
	 @Override
		public void run() {
			// TODO Auto-generated method stub
		 	while(true){
		 		//得到当前系统时间
		 		Date nowTime=new Date();
		 		String now="TIME:"+nowTime.toString();
		 		jlb_system_time.setText(now);
		 		
		 		//每隔1s计时一次
		 		try{
		 			Thread.sleep(1000);
		 		}catch(Exception e){
		 			e.printStackTrace();
		 		}
		 		
		 		//若正在播放歌曲
		 		if(b_play){
		 			//得到正在播放的时间（以秒为单位）
		 			playTime=(int)(playerWork.getPlay().getMediaTime().getSeconds());
		 		
		 			second1=playTime%60;
		 			if(playTime!=0){
		 				if(playTime%60==0){
		 					minute1++;
		 				}
		 			}
		 			//jlb_time1的设置
		 			if(minute1<10&&second1<10){
		 				jlb_time1.setText("0"+minute1+":0"+second1);
		 			}else if(minute1<10&&second1>9){
		 				jlb_time1.setText("0"+minute1+":"+second1);
		 			}else if(minute1>9&&second1<10){
		 				jlb_time1.setText(minute1+":0"+second1);
		 			}else{
		 				jlb_time1.setText(minute1+":"+second1);
		 			}
		 			
		 			js_time.setMaximum(totalTime);
		 			if(playTime!=totalTime){
		 				js_time.setValue(playTime);
		 			}else{
		 				js_time.setValue(0);
		 				second1=0;
		 				minute1=0;
		 				jp_songinfo.updateUI();
		 			}
		 			
		
		 				//歌词同步
		 			if(getLrcAddress()==null||getLrcAddress().equals("")
		 					||getLrcAddress().equals(" ")){
		 				this.paint(getGraphics(),"   没有歌词... ");
		 					
		 				}else{
		 					//System.out.println("heihei "+getLrcAddress());
		 					if(k<lrcParser.length-1){
					 			if(playTime*1000>=lrcParser.hashMap1.get(k)
					 					&&playTime*1000<lrcParser.hashMap1.get(k+1)){
					 				this.paint(getGraphics(), lrcParser.hashMap2.get(k));
					 				
					 			}
					 			if(playTime*1000>=lrcParser.hashMap1.get(k+1)-500){
					 				k++;	
					 				this.repaint();
					 			}
		 					}
		 				}
		 			
		 		}//b_play判断
		 		
		 		
		 		//以下是三种播放模式（顺序，单曲，随机）的判断
		 		
		 		//1、顺序播放（默认情况下是顺序播放）
		 		if(b_loopPlay){
					
					//如果有播放
					if(b_play){
						
						//如果当前歌曲播放完，则得到下一曲地址继续播放
						boolean b_shunxu=false;
						int s_play=0;
						if(playTime>=totalTime-1){
							//hashMap中共用key-1首歌
							for(int k=1;k<=totalSong;k++){
								if(((String)hashMap.get(k)).equals(getSongAddress())){
									
									b_shunxu=true;
									s_play=k;
									
								}
							}
							//判断顺序播放是否能进行
							if(b_shunxu){	
								if(s_play!=key-1){
									setSongAddress((String)hashMap.get(s_play+1));
								}else{
									setSongAddress((String)hashMap.get(1));
								}
								System.out.println("（顺序播放）歌曲地址（第"+s_play+"首歌"+"）：  "+getSongAddress());
								this.begin();
							}else{
								JOptionPane.showMessageDialog(this, "歌曲："+getSongAddress()+"不存在，建议删除歌曲！");
								b_play=false;
							}
						}
						
						
					}//if(b_play)判断
					
		 			
		 		}
		 		
		 		//2、单曲循环
		 		if(b_singlePlay){
		 			
		 			jp_songinfo.updateUI();
					if(b_play){
						
						if(playTime>=totalTime-1){
							System.out.println("单曲循环开始！！！");
							this.begin();
						}
					}else{
						JOptionPane.showMessageDialog(this, "当前没有歌曲播放");
					}
		 		}
		 		
		 		//3、随机播放
				if(b_randomPlay){
					
					//定义随机函数
					Random random=new Random();	
					if(b_play){
						//如果当前歌曲播放完，则得到下一曲地址继续播放
						boolean b_suiji=false;
						if(playTime>=totalTime-1){
							//hashMap中共用key-1首歌
							b_suiji=true;
							int playNow2=random.nextInt(totalSong)+1;
							System.out.println("（随机播放） 第"+playNow2+"首歌曲");
							//如果重复则重新生成随机数
							if(hashMap.get(playNow2).equals(getSongAddress())){
								playNow2=random.nextInt(totalSong)+1;
							}
							
							//判断随机播放是否能进行
							if(b_suiji){	
								setSongAddress(hashMap.get(playNow2).toString());
								System.out.println("（随机）歌曲地址"+getSongAddress());
								this.begin();
							}else{
								JOptionPane.showMessageDialog(this, "歌曲"+getSongAddress()+"地址不存在，建议删除歌曲！");
								b_play=false;
							}		
							
						}//if(playTime==totalTime)
							
					
					}//if(b_play)
				}//随机播放
		 		
		 		
		 	}//while(true)
		 		
		}
	
	
	
	//开始
	//start按钮监听后调用该类。begin类调用player
	protected void begin() {
		if(getSongAddress()==null){	
			JOptionPane.showMessageDialog(null, "请先选中歌曲");
		}else{
			//判断当前是否有歌曲在播放
			if(b_play){
				//每次重新开始之前将k置蕶
				k=0;
				
				minute1=0;
				second1=0;
				//先关闭播放器
				playerWork.playerStop();
				//歌词内容清空
				jta_lrc.setText("");
			
			}
				
				//当前没有歌曲播放，直接打开播放器
				playerWork.PlayerWorking(getSongAddress());
				//((String) (songlist.getSelectedItem()));
				playerWork.playerStart();
				b_play=true;
				
				//将之前的歌词清空
				jta_lrc.setText("");
				//添加歌词
				lrcParser=new LrcParser();
				if(getLrcAddress().equals(null)||
				   getLrcAddress().equals("")||getLrcAddress().equals(" ")){
					this.repaint();
					this.paint(getGraphics(),"   没有歌词... ");
					System.out.println("歌词为空!!!");
				}else{
					//如果歌词非空，则调用歌词类
					System.out.println("歌词地址:"+getLrcAddress()+"aa");
					try{
						lrcParser.parser(getLrcAddress());
					
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//添加新的歌词
					jta_lrc.append("\r\t"+"-------歌词信息------"+"\r\n");
					jta_lrc.append("\r\t"+" 歌  曲:"+lrcParser.lrcinfo.getTitle()+"\r\n");
					jta_lrc.append("\r\t"+" 歌  手 :"+lrcParser.lrcinfo.getSinger()+"\r\n");
					jta_lrc.append("\r\t"+" 专  辑 :"+lrcParser.lrcinfo.getAlbum()+"\r\n");
					jta_lrc.append("\r\n");
					for(int i=0;i<lrcParser.length;i++){
						//歌词全部添加
						jta_lrc.append("\r\t"+lrcParser.hashMap2.get(i)+"\r\n");
						
					}
					
				}
			 		
						
//					//定义迭代器，用于遍历hashMap
//					Set set = lrcParser.maps.entrySet() ;
//					java.util.Iterator iterator = lrcParser.maps.entrySet().iterator();
//					java.util.Map.Entry entry ;
//					//遍历hashm
//					while(iterator.hasNext()){
//						entry= (java.util.Map.Entry)iterator.next();
//						//返回与此项对应的键      ？？ 问题：得到的歌词无序
//						jta_lrc.append("\r\t"+entry.getValue()+"\r\n");
//						// entry.getValue() 返回与此项对应的值
//						System.out.println(entry.getKey()+"geci"+entry.getValue());
//					
//				}  

				
				
				
				
				//设置播放时间显示
				try{
					//每隔0.5s计时一次
					Thread.sleep(500);
				}catch(Exception e){
					e.printStackTrace();
				}
//				totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());
//				if(totalTime>10){
//					try {
//						//歌曲时间是否大于10s
//				         Thread.sleep(500);
//				        } catch (InterruptedException ew) {
//				        	ew.printStackTrace();
//				        }
//				    totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());	
//				}
				totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());
				//设置歌曲总时间
				String time2=total(totalTime);
				jlb_time2.setText("");
				jlb_time2.setText(time2);
			
				//设置当前播放显示
				String songname=getSongChoose();
				jlb_song.setText("");
				jlb_song.setText(songname);
				//将播放按钮设置成play.jpg
				jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
				jp_songinfo.updateUI();
			
				
		}
	}
	
	//结束播放
	private void end() {
		//当前有歌曲播放则关闭
		if(b_play){
			playerWork.playerStop();		
			//将播放器切换到初始状态
			b_play=false;
			//将播放按钮设置成play.jpg（待播放状态）
			jb_play_pause.setIcon(new ImageIcon("images/pause.jpg"));
			jp_songinfo.updateUI();
		}
		
	}

	
	//按钮事件的监听（播放按钮，操作按钮、播放模式按钮,搜索下载）
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		//1、处理操作按钮
		//2、处理播放按钮
		//3、处理播放模式
		//4、搜索与下载
		
		//1、处理操作按钮
		//添加
		if(e.getActionCommand().equals("add"))
		{
			AddMusic am=new AddMusic();
		}//删除
		if(e.getActionCommand().equals("delete")){
			String delMusic=getSongChoose();
			if(delMusic==null){
				JOptionPane.showMessageDialog(this, "请先单击选中要删除的歌曲一行！","DEL",JOptionPane.WARNING_MESSAGE);
			}else{
				int res=JOptionPane.showConfirmDialog(this, "确定要删除歌曲: "+delMusic+"?","删除",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				//对用户的选择进行判断 Y-删除 N—取消
				if(res==JOptionPane.YES_OPTION){
					musicM.DeleteM(delMusic);
				}
				
			}
		}//修改
		if(e.getActionCommand().equals("change")){
			String chgMusic=getSongChoose();
			if(chgMusic==null){
				JOptionPane.showMessageDialog(this, "请先单击选中要修改的歌曲一行！","CHG",JOptionPane.WARNING_MESSAGE);
			}else{
				Boolean b=true;
				
				//遍历数据库，得到歌曲所在位置
				//遍历数据库时，从0开始
				System.out.println(musicM.getRowCount());
				for(int i=0;i<musicM.getRowCount();i++)
				{
					if(musicM.getValueAt(i, 0).equals(chgMusic)){
						UpdateMusic upm=new UpdateMusic(musicM, i);
						b=false;
						System.out.println(i);
					}
				}if(b){
					JOptionPane.showMessageDialog(this, "歌曲不存在！");
				}
					
			}
		}
	
		
		//2、处理播放按钮
		//播放或暂停
		if(e.getActionCommand().equals("jb_play_pause")){
			//如果当前有歌曲播放
			if(b_play){		
				this.end();		
				
			}else{
				if(getSongAddress()==null){
					JOptionPane.showMessageDialog(this, "请双击要播放的歌曲！");
					
				}else{
				//当前没有歌曲播放，直接打开播放器
				playerWork.PlayerWorking(getSongAddress());
				playerWork.playerStart();
				b_play=true;
				
				//将播放按钮设置成play.jpg
				jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
				jp_songinfo.updateUI();
				
				//从数据库中得到歌曲的地址
				for(int i=0;i<musicM.getRowCount();i++){
					if(musicM.getValueAt(i, 1).equals(getSongAddress())){
						//set歌曲名字
						setSongChoose((String)musicM.getValueAt(i, 0));
					}
				}
				
				String songname=getSongChoose();
				jlb_song.setText(songname);	
				}
				
			}
			
		}
		//下一曲
		if(e.getActionCommand().equals("jb_next")){
			//判断当前歌曲
			if(b_play){//当前有歌曲在播放
				//得到当前歌曲的地址
				String songadd=getSongAddress();
				//判断下一曲是否能播放
				boolean b_last=false;
				for(int k=1;k<key;k++){
					System.out.println("hashmap :"+hashMap.get(k));
					
					if(hashMap.get(k).equals(songadd)){
						b_last=true;
						if(k!=key-1){
							//重新设置播放地址
							setSongAddress((String)hashMap.get(k+1));
						}else{
							//如果已经是最后一曲，则从头开始播放
							setSongAddress((String)hashMap.get(1));
						}
					}
					
				}//for循环
				if(b_last){
					for(int i=0;i<musicM.getRowCount();i++)
					{
						//从数据库中得到歌曲的地址
						if(musicM.getValueAt(i, 1).equals(getSongAddress())){
							//set歌曲名字
							setSongChoose((String)musicM.getValueAt(i, 0));
							//开始播放
							this.begin();
						}
					}
						
				}else{
					
					JOptionPane.showMessageDialog(this, "歌曲地址不存在！","！！！",JOptionPane.WARNING_MESSAGE);
				}
				
			
			}else{
			
				JOptionPane.showMessageDialog(this, "当前无播放！","！！！",JOptionPane.WARNING_MESSAGE);
						
			}
			
			
		}
		//上一曲
		if(e.getActionCommand().equals("jb_last")){
			//判断当前歌曲
			if(b_play){//当前有歌曲在播放
				//得到当前歌曲的地址
				String songadd=getSongAddress();
				//判断上一曲是否能播放
				boolean b_next=false;
				for(int k=1;k<key;k++){
					if(hashMap.get(k).equals(songadd)){
						b_next=true;
						if(k!=1){
							//重新设置播放地址
							setSongAddress((String)hashMap.get(k-1));
						}else{
							//如果已经是第一曲，则从最后一首开始播放
							setSongAddress((String)hashMap.get(key-1));
						}
					}
					
				}//for循环
				if(b_next){
					for(int i=0;i<musicM.getRowCount();i++)
					{
						//从数据库中得到歌曲的地址
						if(musicM.getValueAt(i, 1).equals(getSongAddress())){
							//set歌曲名字
							setSongChoose((String)musicM.getValueAt(i, 0));
							//开始播放
							this.begin();
						}
					}
						
				}else{
					JOptionPane.showMessageDialog(this, "歌曲地址不存在！","！！！",JOptionPane.WARNING_MESSAGE);
				}
			}else{
			
				JOptionPane.showMessageDialog(this, "当前无播放！","！！！",JOptionPane.WARNING_MESSAGE);
						
			}
		}
		
		
		//3、处理播放模式
		//顺序播放
		if(e.getActionCommand().equals("shunxu")){		
			b_loopPlay=true;
			b_singlePlay=false;
			b_randomPlay=false;
			jm_style.setText("顺序");
			System.out.println("顺序播放！！！");
			
		}
		//单曲循环
		if(e.getActionCommand().equals("danqu")){
			b_loopPlay=false;
			b_singlePlay=true;
			b_randomPlay=false;
			jm_style.setText("单曲");
			System.out.println("单曲循环！！！");
		}
		//随机播放
		if(e.getActionCommand().equals("suiji")){
			
			b_loopPlay=false;
			b_singlePlay=false;
			b_randomPlay=true;
			jm_style.setText("随机");
			System.out.println("随机播放！！！");
			
		}
		
		
		//4、搜索与下载
		if(e.getActionCommand().equals("search")){
			//将之前的搜索结果清空
			for(int k=0;k<totalSong;k++){
				jlb_songs[k].setText("");
			}
			//用于模糊搜索
			String searchMusic="%"+jtf_so.getText()+"%";
			//更新数据库
			String sql="select * from musicInfo where musicName" +
					" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
			musicM=new MusicModel(sql);
			int num=musicM.getRowCount();
			
			if(jtf_so.getText().equals("")){
				JOptionPane.showMessageDialog(null, "请输入要查询的歌曲！");
				
			}else{	
				if(num==0){
				    JOptionPane.showMessageDialog(null, "没有找到歌曲！");
				}else{		
				//显示搜索后的面板
				cl_right.show(jp_showCard, "4");
				
				for(int i=0;i<num;i++){	
					if(musicM.getValueAt(i, 0)!=null){
						String songName=(String)musicM.getValueAt(i, 0);
						jlb_songs[i].setText(songName);
						jlb_songs[i].setToolTipText("双击下载");
						songAdd="歌曲地址："+(String)musicM.getValueAt(i, 1);
						jlb_songs[i].addMouseListener(new MouseAdapter(){
								public void mouseClicked(MouseEvent arg0) {
									// TODO Auto-generated method stub
									if(arg0.getClickCount()==2){
										//下载，创建线程
										 ClientReciveFromServer crfs=new ClientReciveFromServer(songAdd);
								    	 Thread t=new Thread(crfs);
								    	 t.start();
									}
									
								}
								@Override
								public void mouseEntered(MouseEvent arg0) {
									// TODO Auto-generated method stub
									JLabel jlb=(JLabel)arg0.getSource();
									jlb.setForeground(Color.blue);
								}
								@Override
								public void mouseExited(MouseEvent arg0) {
									// TODO Auto-generated method stub
									JLabel jlb=(JLabel)arg0.getSource();
									jlb.setForeground(Color.black);
								}
			
						});	
					}		

				  }//for(num)
				}//(num!=0)
			}	
		}
		//下载
		if(e.getActionCommand().equals("download")){
			
			DownloadFrame dw = new DownloadFrame("网络文件下载");
		}
		
	}
	
	//鼠标事件监听（单击，双击）
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MusicModel mm=new MusicModel();
		//1、响应用户的单击事件
		if(arg0.getClickCount()==1){
			setSongChoose(((JLabel)arg0.getSource()).getText());
			
			for(int i=0;i<mm.getRowCount();i++)
			{
				//从数据库中得到歌曲的地址
				if(mm.getValueAt(i, 0).equals(getSongChoose())){
					//set歌曲地址
					//setSongAddress((String)mm.getValueAt(i, 1));			
				}
			}
		}
		//2、响应用户的双击事件，得到默认编号
		//双击歌曲，播放
		if(arg0.getClickCount()==2)
		{	
			
			for(int i=0;i<mm.getRowCount();i++)
			{
				//从数据库中得到歌曲的地址
				if(mm.getValueAt(i, 0).equals(getSongChoose())){
					//set歌曲地址
					setSongAddress((String)mm.getValueAt(i, 1));
					
				}
			}
			//双击播放
			this.begin();
			
		}
		if(arg0.getClickCount()==5){
			JOptionPane.showMessageDialog(this, "はるかに、ああ、あなたを愛して~");
		}
		if(arg0.getClickCount()==10){
			JOptionPane.showMessageDialog(this, "コックバーストは、人類はあなたを止めることはできませんしています！");
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jlb=(JLabel)arg0.getSource();
		jlb.setForeground(Color.blue);
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		JLabel jlb=(JLabel)arg0.getSource();
		jlb.setForeground(Color.black);
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	private JLabel getJlb_song() {
		return jlb_song;
	}
	private void setJlb_song(JLabel jlb_song) {
		this.jlb_song = jlb_song;
	}	
	public String getSongAddress() {
		return songAddress;
	}
	public void setSongAddress(String songAddress) {
		this.songAddress = songAddress;
		MusicModel m1=new MusicModel();
		for(int i=0;i<m1.getRowCount();i++)
		{
			//从数据库中得到歌曲的地址
			if(m1.getValueAt(i, 1).equals(songAddress)){
				setSongChoose((String)m1.getValueAt(i, 0));
				setLrcAddress((String)m1.getValueAt(i, 4));
			}
		}
	}
	public String getSongChoose() {
		return songChoose;
	}
	public void setSongChoose(String songChoose) {
		this.songChoose = songChoose;
		
	}
	public String getLrcAddress() {
		return lrcAddress;
	}
	public void setLrcAddress(String lrcAddress) {
		this.lrcAddress = lrcAddress;
	}
	
	//实时同步歌词
	public void paint(Graphics g,String paint)  
    {   
		
		char ch2[]=paint.toCharArray();
		int length=paint.length();
        for(int i=0;i<length;i++)  
        {  
           int  r1=50+(int)(Math.random()*50);    //通过调用Math类的random产生随机数  
           int g1=50+(int)(Math.random()*30);    //再通过随机数分别设置三原色，红绿蓝  
           int b1=150+(int)(Math.random()*100);  
            Color color=new Color(r1,g1,b1);    //创建一个颜色对象  
            g.setColor(color);                  //设置颜色  
            int size=14;  
            Font f=new Font("",1,size);     //设置字体  
            g.setFont(f);  
            g.drawChars(ch2,i,1,310+i*16,485); //显示指定大小颜色的字符串  
        }  
    }

}
