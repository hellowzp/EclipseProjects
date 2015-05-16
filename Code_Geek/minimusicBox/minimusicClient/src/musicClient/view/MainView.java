/**
 * ���ܣ����ǲ����������洰��
 * ���ڣ�2012-10-23
 * 
 *������ �轫�����б���������ϣ���ѡ����Ӧ�ĸ�������ִ��ɾ�����޸ĵĲ�����
 *�����������б�Ĵ���������࣡����
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
	 * 1����ߵĸ�����Ϣ������
	 *   1.1  ϵͳСͼ����ǰ���š�ʱ������������ť����������
	 *   1.2 ��Borderlayout�����ɾ����������Ͱ�ť�������б��󣩸�����ʾ���ң�������������ť
	 *   ע�⣺�б���Ϊ�գ������޷������жϣ����ֿ�ָ��
	 * 2���ұߵĸ�����
	 *   2.1 �����
	 *   2.2 �����ʾ�����޸������ʾ�Դ�ͼƬ�����֣�
	 */

	//���������
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		MainView vl=new MainView("miniMusicBox");
//		Thread t=new Thread(vl);
//		t.start();
//		
//	}
	
	//����������
	PlayerWork playerWork=new PlayerWork();
	//���������,��Ӹ����Ϣ
	LrcParser lrcParser;
	//�������������ݿ⽻����model��
	MusicModel musicM=new MusicModel();
	
	
	/**����һ�����ڴ�����и�����Ϣ��hashmap
	*�����������ڱ���Ŀ�У�Ƶ���Ķ����ݿ���в��������´��벻���ȶ��������ٶ���
	*��������չ����һ�������ݿ⽻��ʱ������ֱ�ӵõ����ݿ��������Ϣ��
	*	��������hashmap�У��Ժ�ֻ���hashmap��������
	**/
	//����String���ͷֱ�Ϊ�������͸�����ַ
	HashMap hashMap=new HashMap<Integer , String>();
	int key=1;//k���ڱ�ʾhashMap������,k=[1,length];
	int totalSong=0;
//	//����һ�������������ڶ�hashMap�ı���
//	Iterator<String> iterator = hashMap.keySet().iterator(); 
	
	//�����ַ��������ڴ���û�����ʱ�õ��ĸ�����������ַ�͸��set��get����
	String songChoose;
	String songAddress;
	String lrcAddress;
	//������������ʱ���ݵ��ַ���
	
	String songAdd;
	
	//����ı䲥��ʱ��ı���
	protected int totalTime=0;
	int totalMinute=0, totalSecond=0;
	int minute1=0,second1=0,minute2=0,second2=0;
	protected int playTime=0;
	
	/**
	 * ����ָ1����ߣ���������Ϣ��ʾ���б���ʾ������������ť��ʾ
	 *        2���ұߣ���ϵͳʱ����ʾ��ʵʱ�� �������򣬸����ʾ    		
	 */				
	
	JLabel jlb_background;//����ͼƬ
	
//1����ߣ���������Ϣ��ʾ���б���ʾ������������ť��ʾ
	//list���֣������ĸ�����Ϣ���в����б��ϲ��Ĳ�����
	JPanel jp_songinfo,jp_showlist,jp_operation;
	//JPanel jp_slider=new JPanel(null);//���ڷ���ϵͳ������
	//����
	JLabel jlb_pic,jlb_song,jlb_voice,jlb_no_voice;
	JLabel jlb_time1,jlb_time2;
	JSlider js_time,js_voice;
	//JProgressBar jpb_time,jpb_voice;
	JButton jb_play_pause,jb_next,jb_last;
	JMenuBar jmb_style;
	JMenu jm_style;
	JMenuItem jmi_shunxu,jmi_xunhuan,jmi_danqu;
	//�ϲ��İ�ť
	JButton jb_add,jb_delete,jb_change;
	
	//�м���б���ʾ����Ƭ���֣�
	CardLayout cl;
	JTable jtb_show;
	//�����һ����Ƭ
	JPanel  jphy1,jphy2,jphy3;
	JButton jphy_jb1,jphy_jb2,jphy_jb3;
	JScrollPane jsp1;
	//����ڶ�����Ƭ
	JPanel  jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	//�����������Ƭ
	JPanel  jphmd1,jphmd2,jphmd3;
	JButton jphmd_jb1,jphmd_jb2,jphmd_jb3;
	JScrollPane jsp3;
	
//2���ұߣ���ϵͳʱ����ʾ��ʵʱ�� �������򣬸����ʾ    	
	JPanel jp_lrc,jp_ku,jp_background,jp_so,jp_showCard;
	CardLayout cl_right;
	JLabel jlb_system_time,jlb_lrc;
	JTextField jtf_so;
	JButton jb_so,jb_download,jb_lrc,jb_ku,jb_background,jb_find;
	//card �е����
	JTable jtb_so;
	JTextArea jta_lrc;
	JScrollPane jsp_lrc,jsp_song;
	JLabel jlb_singer1,jlb_singer2,jlb_singer3,jlb_singer4;
	JLabel jlb_back1,jlb_back2,jlb_back3,jlb_back4;
	JLabel jlb_songs[];
	//û�и������ʾ������
	String  no_lrc[]={"-----#$@%^#$%$-----",
					  "ϲ��һ���������ֵĵط��¶��ض���",
						"���϶�����һ�׵���ѭ��������",
						"�����������������޹�",
						"������ҹ���ڵ�����",
					"��һȺ�ո��°����Ⱥ���Ų�֪����������г�",
					   "-----*&%$#@%^#@&----"};
	
	//��ߵĸ�����Ϣ��ʾ���б���ʾ������������ť��ʾ
	public void ViewLeft() {
		//����
		jp_songinfo=new JPanel();
		jp_songinfo.setLayout(null);
		jlb_pic=new JLabel(new ImageIcon("images/pic.jpg"), JLabel.LEFT);
		jlb_song=new JLabel("���ڲ��š�����");
		jlb_time1=new JLabel("00:00");
		jlb_time1.setForeground(Color.black);
		js_time=new JSlider(JSlider.HORIZONTAL,0,100,0);
		js_time.setForeground(Color.blue);
		jlb_time2=new JLabel();
		jlb_time2.setForeground(Color.black);

		jb_play_pause=new JButton();
		jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
		jb_play_pause.setToolTipText("����/��ͣ");
		jb_play_pause.addActionListener(this);
		jb_play_pause.setActionCommand("jb_play_pause");
		
		jb_next=new JButton(new ImageIcon("images/next.jpg"));
		jb_next.setToolTipText("��һ��");
		jb_next.addActionListener(this);
		jb_next.setActionCommand("jb_next");
		
		jb_last=new JButton(new ImageIcon("images/last.jpg"));
		jb_last.setToolTipText("��һ��");
		jb_last.addActionListener(this);
		jb_last.setActionCommand("jb_last");
		
		jlb_voice=new JLabel(new ImageIcon("images/voice.jpg"));
		jlb_no_voice=new JLabel(new ImageIcon("images/no_voice.jpg"));
		jlb_voice.setToolTipText("����");
		js_voice=new JSlider(JSlider.HORIZONTAL,0,100,40);
		js_voice.setBackground(Color.BLUE);
		
		//���������λ�ò���ӵ������
		jp_songinfo.setBounds(5, 5, 248, 92);
		jlb_pic.setBounds(0,0, 70, 38);
		jlb_song.setBounds(72,12,170,20);
		//�������ڴ��ʱ���������
		jlb_time1.setBounds(3,42,30,20);
		js_time.setBounds(37,48,170,8);
		jlb_time2.setBounds(212,42,30,20);
		
		jb_last.setBounds(10,62,24,23);
		jb_play_pause.setBounds(37,62,28,23);
		jb_next.setBounds(69,62,24,23);
		jlb_voice.setBounds(120,62,22,20);
		js_voice.setBounds(144,72,50,6);
		
		//������������ģʽ��ť
		jmb_style=new JMenuBar();
		jmb_style.setBounds(210, 64, 30, 20);
		jm_style=new JMenu("ģʽ");
		jmi_shunxu=new JMenuItem(new ImageIcon("images/listloop.jpg"));
		jmi_shunxu.setToolTipText("˳�򲥷�");
		jmi_xunhuan=new JMenuItem(new ImageIcon("images/random.jpg"));
		jmi_xunhuan.setToolTipText("�������");
		jmi_danqu=new JMenuItem(new ImageIcon("images/single.jpg"));
		jmi_danqu.setToolTipText("����ѭ��");		
		//����¼�����
		jmi_shunxu.addActionListener(this);
		jmi_shunxu.setActionCommand("shunxu");
		jmi_xunhuan.addActionListener(this);
		jmi_xunhuan.setActionCommand("suiji");
		jmi_danqu.addActionListener(this);
		jmi_danqu.setActionCommand("danqu");
		//���Ӳ˵���ӵ��˵���
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
		
		
		//�ϲ������
		jp_operation=new JPanel();
		jp_operation.setLayout(new FlowLayout());
		//jp_operation.setLayout(null);
		jp_operation.setSize(240, 35);
		jp_operation.setLocation(5, 443);
		//jp_operation.setBounds(5, 568, 300, 35);
		//jp_operation.setBackground(Color.blue);
		jb_add=new JButton("���");
		jb_add.addActionListener(this);
		jb_add.setActionCommand("add");
		jb_delete=new JButton("ɾ��");
		jb_delete.addActionListener(this);
		jb_delete.setActionCommand("delete");
		jb_change=new JButton("�޸�");
		jb_change.addActionListener(this);
		jb_change.setActionCommand("change");

		//���ϲ��İ�ť��ӵ������
		jp_operation.add(jb_add);
		jp_operation.add(jb_delete);
		jp_operation.add(jb_change);
		jp_operation.setOpaque(false);
		
		
		
		//�м������������б�

 		//Ĭ���б�
 		jphy_jb1=new JButton("Ĭ���б�");	
 		jphy_jb2=new JButton("�����ĸ�");
 		//jphy_jb2.addActionListener(this);
 		jphy_jb3=new JButton("ϲ���ĸ�");
 		//jphy_jb3.addActionListener(this);
 		
 		jphy1=new JPanel(new BorderLayout());
 		//��ʼ��Ĭ���б�
 		MusicModel musicM=new MusicModel();
 		//��������
 		int length=musicM.getRowCount();
 		System.out.println("���и�����"+length);
 		
 		int length1=length;
 		jphy2=new JPanel(new GridLayout(length1,1,4,4));
 		JLabel []jlbs1=new JLabel[length1];
 		
 		for(int i=0;i<jlbs1.length;i++)
 		{
 			String listName1=(String)musicM.getValueAt(i, 3);
 			
 			//�õ�Ĭ���б���� �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName1==null||listName1.equals("Ĭ���б�")){
	 			String moren=(String)musicM.getValueAt(i, 0);
	 			jlbs1[i]=new JLabel(moren,new ImageIcon("images/mouse.jpg"),JLabel.LEFT);
	 			//jlbs[i]=new JLabel("my music no."+i,new ImageIcon("images/mouse.jpg"),JLabel.LEFT);
	 			jlbs1[i].addMouseListener(this);
	 			jphy2.add(jlbs1[i]);
 			}
 		}
 		
 		//����һ�е����񲼾�
 		jphy3=new JPanel(new GridLayout(2,1));
 		jphy3.add(jphy_jb2);
 		jphy3.add(jphy_jb3);
 		
 		jsp1=new JScrollPane(jphy2);
 			
 		//��jphy1��ʼ��
 		jphy1.add(jphy_jb1,BorderLayout.NORTH);
 		jphy1.add(jsp1,BorderLayout.CENTER);
 		jphy1.add(jphy3,BorderLayout.SOUTH);
 		
 		
 		//�����ĸ�
 		jpmsr_jb1=new JButton("Ĭ���б�");	
 		//jpmsr_jb1.addActionListener(this);
 		jpmsr_jb2=new JButton("�����ĸ�");
 		jpmsr_jb3=new JButton("ϲ���ĸ�");
 		//jpmsr_jb3.addActionListener(this);
 	
 		jpmsr1=new JPanel(new BorderLayout());
 	 	
 		jpmsr2=new JPanel(new GridLayout(2,1));
 		jpmsr2.add(jpmsr_jb1);
 		jpmsr2.add(jpmsr_jb2);
 			
 		//��ʼ�������ĸ��б�
 		int length2=musicM.getRowCount();
 		jpmsr3=new JPanel(new GridLayout(length2,1,4,4));
 		JLabel []jlbs2=new JLabel[length2];
 		for(int i=0;i<jlbs2.length;i++)
 		{
 			String listName2=(String)musicM.getValueAt(i, 3);
 			//�õ������б�  �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName2!=null&&listName2.equals("�����ĸ�")){
 				String niceMusic=(String)musicM.getValueAt(i, 0);
	 			jlbs2[i]=new JLabel(niceMusic,new ImageIcon("images/miqi.jpg"),JLabel.LEFT);
	 			jlbs2[i].addMouseListener(this);
	 			jpmsr3.add(jlbs2[i]);
	 			
 			}
 		}
 				
 		jsp2=new JScrollPane(jpmsr3);
 				
 		//��jpmsr1��ʼ��
 		jpmsr1.add(jpmsr2,BorderLayout.NORTH);
 		jpmsr1.add(jsp2,BorderLayout.CENTER);
 		jpmsr1.add(jpmsr_jb3,BorderLayout.SOUTH);
 		
 		
 		//ϲ���ĸ�
 		jphmd_jb1=new JButton("Ĭ���б�");	
 		//jphmd_jb1.addActionListener(this);
 		jphmd_jb2=new JButton("�����ĸ�");
 		//jphmd_jb2.addActionListener(this);
 		jphmd_jb3=new JButton("ϲ���ĸ�");
 		
 		jphmd1=new JPanel(new BorderLayout());
 		//��ʼ��Ĭ���б�
 		int length3=musicM.getRowCount();
 		jphmd2=new JPanel(new GridLayout(length3,1,4,4));
 		JLabel []jlbs3=new JLabel[length3];
 		for(int i=0;i<jlbs3.length;i++)
 		{
 			String listName3=(String)musicM.getValueAt(i, 3);
 			//�õ�Ĭ���б���� �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName3!=null&&listName3.equals("ϲ���ĸ�")){
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
 			
 		//��jphmd1��ʼ��
 		jphmd1.add(jphmd3,BorderLayout.NORTH);	
 		jphmd1.add(jsp3,BorderLayout.CENTER);
 		
 		
 		//��ʼ����Ƭ
 		cl=new CardLayout();
 		jp_showlist=new JPanel(cl);
 		jp_showlist.setSize(248, 346);
 		jp_showlist.setLocation(5, 98);
 		//jp_showlist.setOpaque(false);
 	   
 		jp_showlist.add(jphy1,"1");
 		jp_showlist.add(jpmsr1,"2");
 		jp_showlist.add(jphmd1,"3");
		
 		//�л������1
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
 		
 		//�л������2
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

 		//�л������3
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
 		
 		 //JSplitPane ���ڷָ�������ֻ��������Component
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		//HORIZONTAL_SPLIT��ʾˮƽ�����������,VERTICAL_SPLIT��ʾ������������������÷ָ�����λ�ã�x=200
		splitPane.setDividerLocation(300);
		splitPane.setDividerSize(2);
	
		
		
		
 		//��panel��������panel�ϲ���ʾ
		this.setLayout(null);
		this.add(splitPane);
		
		this.getContentPane().add(jp_songinfo);
		this.getContentPane().add(jp_showlist);
		this.getContentPane().add(jp_operation);
	
		
	}
	
	//�ұߵĸ�������ʾ���ֿ⣬����
	public void ViewRight(){
		
		//��ʼ����Ƭ
		cl_right=new CardLayout();
		jp_showCard=new JPanel(cl_right);
		jp_showCard.setBounds(254, 96, 378, 342);
		
		//���
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
		
		//Ĭ����ʵ���������
		jta_lrc.append("\r\n"+"\r\n");
		for(int i=0;i<no_lrc.length;i++){
			jta_lrc.append("\r\t"+no_lrc[i]+"\r\n");
		}
		
		//�ֿ�
		jp_ku=new JPanel(new GridLayout(2,2,10,10));
		jp_ku.setBounds(270, 100, 350, 320);
		jp_ku.setOpaque(true);
		jlb_singer1=new JLabel(new ImageIcon("images/����Ӣ.jpg"));
		jlb_singer1.setToolTipText("�����������Ӣ�¸�");
		jlb_singer2=new JLabel(new ImageIcon("images/�ܽ���.jpg")); 
		jlb_singer2.setToolTipText("��������ܽ����¸�");
		jlb_singer3=new JLabel(new ImageIcon("images/����Ѹ.jpg"));
		jlb_singer3.setToolTipText("�����������Ѹ�¸�");
		jlb_singer4=new JLabel(new ImageIcon("images/������.jpg")); 
		jlb_singer4.setToolTipText("��������������¸�");
		jp_ku.add(jlb_singer1);
		jp_ku.add(jlb_singer2);
		jp_ku.add(jlb_singer3);
		jp_ku.add(jlb_singer4);
		
		//��4��label���м��������������Ӧ���ֵĸ���
		jlb_singer1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==1){
					//��֮ǰ������������
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//����ģ������
					String searchMusic="%"+"����Ӣ"+"%";
					//�������ݿ�
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "û���ҵ�������");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("˫������");
							songAdd=(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//���أ������߳�
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
					//��֮ǰ������������
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//����ģ������
					String searchMusic="%"+"�ܽ���"+"%";
					//�������ݿ�
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "û���ҵ�������");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("˫������");
							songAdd="������ַ��"+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//���أ������߳�
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
					//��֮ǰ������������
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//����ģ������
					String searchMusic="%"+"����Ѹ"+"%";
					//�������ݿ�
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "û���ҵ�������");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("˫������");
							songAdd="������ַ��"+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//���أ������߳�
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
					//��֮ǰ������������
					for(int k=0;k<totalSong;k++){
						jlb_songs[k].setText("");
					}
					//����ģ������
					String searchMusic="%"+"������"+"%";
					//�������ݿ�
					String sql="select * from musicInfo where musicName" +
							" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
					musicM=new MusicModel(sql);
					int num=musicM.getRowCount();
					
					cl_right.show(jp_showCard, "4");
					if(num==0){
					    JOptionPane.showMessageDialog(null, "û���ҵ�������");				
					}else{
					   for(int i=0;i<num;i++){	
						if(musicM.getValueAt(i, 0)!=null){
							String songName=(String)musicM.getValueAt(i, 0);
							jlb_songs[i].setText(songName);
							jlb_songs[i].setToolTipText("˫������");
							songAdd="������ַ��"+(String)musicM.getValueAt(i, 1);
							jlb_songs[i].addMouseListener(new MouseAdapter(){
									public void mouseClicked(MouseEvent arg0) {
										// TODO Auto-generated method stub
										if(arg0.getClickCount()==2){
											//���أ������߳�
											//���أ������߳�
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
		
		//Ƥ��
		jp_background=new JPanel(new GridLayout(2,2,10,10));
		jp_background.setBounds(270, 100, 350, 320);
		jp_background.setOpaque(true);
		jlb_back1=new JLabel(new ImageIcon("images/back01.jpg"));
		jlb_back1.setToolTipText("����л�����");
		jlb_back2=new JLabel(new ImageIcon("images/back02.jpg")); 
		jlb_back2.setToolTipText("����л�����");
		jlb_back3=new JLabel(new ImageIcon("images/back03.jpg"));
		jlb_back3.setToolTipText("����л�����");
		jlb_back4=new JLabel(new ImageIcon("images/back04.jpg")); 
		jlb_back4.setToolTipText("����л�����");
		jp_background.add(jlb_back1);
		jp_background.add(jlb_back2);
		jp_background.add(jlb_back3);
		jp_background.add(jlb_back4);
		
		//����4��label������л�����
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
		
		//�����ĸ���
		jp_so=new JPanel(null);
		jp_so.setBounds(254, 96, 378, 342);
		jlb_songs=new JLabel[totalSong];
		for(int k=0;k<totalSong;k++){
			jlb_songs[k]=new JLabel();
			jlb_songs[k].setBounds(10,30*k, 300, 30);
			jp_so.add(jlb_songs[k]);
		}
		
		//����card����
		jp_showCard.setOpaque(true);
		jp_showCard.add(jp_ku,"2");
		jp_showCard.add(jp_lrc,"1");
		jp_showCard.add(jp_background,"3");
		jp_showCard.add(jp_so,"4");
		
		
		//��ʼ��4�������л���button
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
		
		//4��button�ļ�����ʵ�������л�
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
	//���캯�����������ʼ��
	public MainView(String title)
	{
		/*2���ұ�
		 * 2.1 ϵͳʱ����ʾ��ʵʱ�� ��������
		 * 2.2  �����ʾ,�ֿ⣬���� 
		*/   
		
		//2.1ϵͳʱ����ʾ��ʵʱ�� ��������
		jlb_system_time=new JLabel();
		jlb_system_time.setForeground(Color.magenta);
		
		jtf_so=new JTextField(50);
		jb_so=new JButton(new ImageIcon("images/search.jpg"));
		jb_so.setToolTipText("�Ѹ衤��");
		jb_so.addActionListener(this);
		jb_so.setActionCommand("search");
		jb_download=new JButton(new ImageIcon("images/download.jpg"));
		jb_download.setToolTipText("�����������");
		jb_download.addActionListener(this);
		jb_download.setActionCommand("download");

		
		//��ײ��ĸ��ʵʱ��ʾ
		jlb_lrc=new JLabel();
		jlb_lrc.setText("�� ��:");
		jlb_lrc.setForeground(Color.blue);
		jlb_lrc.setFont(new Font("��������",Font.BOLD,18));
			
		
		this.setLayout(null);
		jlb_system_time.setBounds(400,5,240,30);
		jtf_so.setBounds(370, 40, 120, 28);
		jb_so.setBounds(489, 40, 36, 28);
		jb_download.setBounds(550, 40, 40, 28);
		
		//������ĸ��
		jlb_lrc.setBounds(250, 430, 50, 50);
	
		this.add(jlb_system_time);
		this.add(jlb_lrc);
		this.add(jtf_so);
		this.add(jb_so);
		this.add(jb_download);
		
		
		
		try {
            // ��LookAndFeel���ó�Windows��ʽ
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {	
            ex.printStackTrace();
        }
        
		//���ñ���ͼƬ
        jlb_background=new JLabel();
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background3.jpg");
		jlb_background.setIcon(background);
		jlb_background.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//ʹ���ݴ���͸����
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(jlb_background,new Integer(Integer.MIN_VALUE));
		
		
		//��ʾ�������ߵ����		
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
	
	
	//���������Լ�������ַ����hashMap��
	{
		MusicModel mm=new MusicModel();
		int length=mm.getRowCount();
		for(int i=0;i<length;i++){
			String listName1=(String)mm.getValueAt(i, 3);	
	 			//�õ�Ĭ���б���� �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
	 			if(listName1==null||listName1.equals("Ĭ���б�")){
				hashMap.put(key,(String)mm.getValueAt(i, 1) );
				key=key+1;
				System.out.println("���� "+(key-1)+" : "+hashMap.get(key-1));
 			}
 		}
		for(int i=0;i<length;i++){
			String listName2=(String)mm.getValueAt(i, 3);	
 			//�õ�Ĭ���б���� �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName2!=null&&listName2.equals("�����ĸ�")){
			hashMap.put(key,(String)mm.getValueAt(i, 1) );
			key=key+1;
			System.out.println("���� "+(key-1)+" : "+hashMap.get(key-1));
			}
		}
		for(int i=0;i<length;i++){
			String listName3=(String)mm.getValueAt(i, 3);	
 			//�õ�Ĭ���б���� �����б����ж�|((String)linkDb.getValueAt(i, 3)).equals("")
 			if(listName3!=null&&listName3.equals("ϲ���ĸ�")){
			hashMap.put(key,(String)mm.getValueAt(i, 1) );
			key=key+1;
			System.out.println("���� "+(key-1)+" : "+hashMap.get(key-1));
			}
		}
		
		totalSong=key-1;
	}
	
	//������ʱ�䲢������ȷ��ʽ��ʾ
	 public String total(int t_seconds) {
		   minute2 = (int) t_seconds / 60;
		   second2 = (int) t_seconds % 60;
		   //jlb_time2������
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
	
	 
	//���ò������ĳ�ʼ״̬,b_paly=false ��ʾ��ǰ������û������
	//b_paly ������˳�򲥷ź�ѭ�����ŵ��ж�������
	boolean b_play=false;
	//Ĭ�������Ϊ˳�򲥷ţ������ֲ���ģʽ��
	boolean b_loopPlay=true;
	boolean b_singlePlay=false;
	boolean b_randomPlay=false;
	//�������ͬ�����ʱ
	int k=0;
	
	 
	 //�̣߳�����ʵʱ��ʾ����ʱ��
	 @Override
		public void run() {
			// TODO Auto-generated method stub
		 	while(true){
		 		//�õ���ǰϵͳʱ��
		 		Date nowTime=new Date();
		 		String now="TIME:"+nowTime.toString();
		 		jlb_system_time.setText(now);
		 		
		 		//ÿ��1s��ʱһ��
		 		try{
		 			Thread.sleep(1000);
		 		}catch(Exception e){
		 			e.printStackTrace();
		 		}
		 		
		 		//�����ڲ��Ÿ���
		 		if(b_play){
		 			//�õ����ڲ��ŵ�ʱ�䣨����Ϊ��λ��
		 			playTime=(int)(playerWork.getPlay().getMediaTime().getSeconds());
		 		
		 			second1=playTime%60;
		 			if(playTime!=0){
		 				if(playTime%60==0){
		 					minute1++;
		 				}
		 			}
		 			//jlb_time1������
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
		 			
		
		 				//���ͬ��
		 			if(getLrcAddress()==null||getLrcAddress().equals("")
		 					||getLrcAddress().equals(" ")){
		 				this.paint(getGraphics(),"   û�и��... ");
		 					
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
		 			
		 		}//b_play�ж�
		 		
		 		
		 		//���������ֲ���ģʽ��˳�򣬵�������������ж�
		 		
		 		//1��˳�򲥷ţ�Ĭ���������˳�򲥷ţ�
		 		if(b_loopPlay){
					
					//����в���
					if(b_play){
						
						//�����ǰ���������꣬��õ���һ����ַ��������
						boolean b_shunxu=false;
						int s_play=0;
						if(playTime>=totalTime-1){
							//hashMap�й���key-1�׸�
							for(int k=1;k<=totalSong;k++){
								if(((String)hashMap.get(k)).equals(getSongAddress())){
									
									b_shunxu=true;
									s_play=k;
									
								}
							}
							//�ж�˳�򲥷��Ƿ��ܽ���
							if(b_shunxu){	
								if(s_play!=key-1){
									setSongAddress((String)hashMap.get(s_play+1));
								}else{
									setSongAddress((String)hashMap.get(1));
								}
								System.out.println("��˳�򲥷ţ�������ַ����"+s_play+"�׸�"+"����  "+getSongAddress());
								this.begin();
							}else{
								JOptionPane.showMessageDialog(this, "������"+getSongAddress()+"�����ڣ�����ɾ��������");
								b_play=false;
							}
						}
						
						
					}//if(b_play)�ж�
					
		 			
		 		}
		 		
		 		//2������ѭ��
		 		if(b_singlePlay){
		 			
		 			jp_songinfo.updateUI();
					if(b_play){
						
						if(playTime>=totalTime-1){
							System.out.println("����ѭ����ʼ������");
							this.begin();
						}
					}else{
						JOptionPane.showMessageDialog(this, "��ǰû�и�������");
					}
		 		}
		 		
		 		//3���������
				if(b_randomPlay){
					
					//�����������
					Random random=new Random();	
					if(b_play){
						//�����ǰ���������꣬��õ���һ����ַ��������
						boolean b_suiji=false;
						if(playTime>=totalTime-1){
							//hashMap�й���key-1�׸�
							b_suiji=true;
							int playNow2=random.nextInt(totalSong)+1;
							System.out.println("��������ţ� ��"+playNow2+"�׸���");
							//����ظ����������������
							if(hashMap.get(playNow2).equals(getSongAddress())){
								playNow2=random.nextInt(totalSong)+1;
							}
							
							//�ж���������Ƿ��ܽ���
							if(b_suiji){	
								setSongAddress(hashMap.get(playNow2).toString());
								System.out.println("�������������ַ"+getSongAddress());
								this.begin();
							}else{
								JOptionPane.showMessageDialog(this, "����"+getSongAddress()+"��ַ�����ڣ�����ɾ��������");
								b_play=false;
							}		
							
						}//if(playTime==totalTime)
							
					
					}//if(b_play)
				}//�������
		 		
		 		
		 	}//while(true)
		 		
		}
	
	
	
	//��ʼ
	//start��ť��������ø��ࡣbegin�����player
	protected void begin() {
		if(getSongAddress()==null){	
			JOptionPane.showMessageDialog(null, "����ѡ�и���");
		}else{
			//�жϵ�ǰ�Ƿ��и����ڲ���
			if(b_play){
				//ÿ�����¿�ʼ֮ǰ��k��ʙ
				k=0;
				
				minute1=0;
				second1=0;
				//�ȹرղ�����
				playerWork.playerStop();
				//����������
				jta_lrc.setText("");
			
			}
				
				//��ǰû�и������ţ�ֱ�Ӵ򿪲�����
				playerWork.PlayerWorking(getSongAddress());
				//((String) (songlist.getSelectedItem()));
				playerWork.playerStart();
				b_play=true;
				
				//��֮ǰ�ĸ�����
				jta_lrc.setText("");
				//��Ӹ��
				lrcParser=new LrcParser();
				if(getLrcAddress().equals(null)||
				   getLrcAddress().equals("")||getLrcAddress().equals(" ")){
					this.repaint();
					this.paint(getGraphics(),"   û�и��... ");
					System.out.println("���Ϊ��!!!");
				}else{
					//�����ʷǿգ�����ø����
					System.out.println("��ʵ�ַ:"+getLrcAddress()+"aa");
					try{
						lrcParser.parser(getLrcAddress());
					
					}catch(Exception e){
						e.printStackTrace();
					}
					
					//����µĸ��
					jta_lrc.append("\r\t"+"-------�����Ϣ------"+"\r\n");
					jta_lrc.append("\r\t"+" ��  ��:"+lrcParser.lrcinfo.getTitle()+"\r\n");
					jta_lrc.append("\r\t"+" ��  �� :"+lrcParser.lrcinfo.getSinger()+"\r\n");
					jta_lrc.append("\r\t"+" ר  �� :"+lrcParser.lrcinfo.getAlbum()+"\r\n");
					jta_lrc.append("\r\n");
					for(int i=0;i<lrcParser.length;i++){
						//���ȫ�����
						jta_lrc.append("\r\t"+lrcParser.hashMap2.get(i)+"\r\n");
						
					}
					
				}
			 		
						
//					//��������������ڱ���hashMap
//					Set set = lrcParser.maps.entrySet() ;
//					java.util.Iterator iterator = lrcParser.maps.entrySet().iterator();
//					java.util.Map.Entry entry ;
//					//����hashm
//					while(iterator.hasNext()){
//						entry= (java.util.Map.Entry)iterator.next();
//						//����������Ӧ�ļ�      ���� ���⣺�õ��ĸ������
//						jta_lrc.append("\r\t"+entry.getValue()+"\r\n");
//						// entry.getValue() ����������Ӧ��ֵ
//						System.out.println(entry.getKey()+"geci"+entry.getValue());
//					
//				}  

				
				
				
				
				//���ò���ʱ����ʾ
				try{
					//ÿ��0.5s��ʱһ��
					Thread.sleep(500);
				}catch(Exception e){
					e.printStackTrace();
				}
//				totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());
//				if(totalTime>10){
//					try {
//						//����ʱ���Ƿ����10s
//				         Thread.sleep(500);
//				        } catch (InterruptedException ew) {
//				        	ew.printStackTrace();
//				        }
//				    totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());	
//				}
				totalTime=(int)(playerWork.getPlay().getDuration().getSeconds());
				//���ø�����ʱ��
				String time2=total(totalTime);
				jlb_time2.setText("");
				jlb_time2.setText(time2);
			
				//���õ�ǰ������ʾ
				String songname=getSongChoose();
				jlb_song.setText("");
				jlb_song.setText(songname);
				//�����Ű�ť���ó�play.jpg
				jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
				jp_songinfo.updateUI();
			
				
		}
	}
	
	//��������
	private void end() {
		//��ǰ�и���������ر�
		if(b_play){
			playerWork.playerStop();		
			//���������л�����ʼ״̬
			b_play=false;
			//�����Ű�ť���ó�play.jpg��������״̬��
			jb_play_pause.setIcon(new ImageIcon("images/pause.jpg"));
			jp_songinfo.updateUI();
		}
		
	}

	
	//��ť�¼��ļ��������Ű�ť��������ť������ģʽ��ť,�������أ�
	@Override
	public void actionPerformed(ActionEvent e) {		
		
		//1�����������ť
		//2�������Ű�ť
		//3��������ģʽ
		//4������������
		
		//1�����������ť
		//���
		if(e.getActionCommand().equals("add"))
		{
			AddMusic am=new AddMusic();
		}//ɾ��
		if(e.getActionCommand().equals("delete")){
			String delMusic=getSongChoose();
			if(delMusic==null){
				JOptionPane.showMessageDialog(this, "���ȵ���ѡ��Ҫɾ���ĸ���һ�У�","DEL",JOptionPane.WARNING_MESSAGE);
			}else{
				int res=JOptionPane.showConfirmDialog(this, "ȷ��Ҫɾ������: "+delMusic+"?","ɾ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				//���û���ѡ������ж� Y-ɾ�� N��ȡ��
				if(res==JOptionPane.YES_OPTION){
					musicM.DeleteM(delMusic);
				}
				
			}
		}//�޸�
		if(e.getActionCommand().equals("change")){
			String chgMusic=getSongChoose();
			if(chgMusic==null){
				JOptionPane.showMessageDialog(this, "���ȵ���ѡ��Ҫ�޸ĵĸ���һ�У�","CHG",JOptionPane.WARNING_MESSAGE);
			}else{
				Boolean b=true;
				
				//�������ݿ⣬�õ���������λ��
				//�������ݿ�ʱ����0��ʼ
				System.out.println(musicM.getRowCount());
				for(int i=0;i<musicM.getRowCount();i++)
				{
					if(musicM.getValueAt(i, 0).equals(chgMusic)){
						UpdateMusic upm=new UpdateMusic(musicM, i);
						b=false;
						System.out.println(i);
					}
				}if(b){
					JOptionPane.showMessageDialog(this, "���������ڣ�");
				}
					
			}
		}
	
		
		//2�������Ű�ť
		//���Ż���ͣ
		if(e.getActionCommand().equals("jb_play_pause")){
			//�����ǰ�и�������
			if(b_play){		
				this.end();		
				
			}else{
				if(getSongAddress()==null){
					JOptionPane.showMessageDialog(this, "��˫��Ҫ���ŵĸ�����");
					
				}else{
				//��ǰû�и������ţ�ֱ�Ӵ򿪲�����
				playerWork.PlayerWorking(getSongAddress());
				playerWork.playerStart();
				b_play=true;
				
				//�����Ű�ť���ó�play.jpg
				jb_play_pause.setIcon(new ImageIcon("images/play.jpg"));
				jp_songinfo.updateUI();
				
				//�����ݿ��еõ������ĵ�ַ
				for(int i=0;i<musicM.getRowCount();i++){
					if(musicM.getValueAt(i, 1).equals(getSongAddress())){
						//set��������
						setSongChoose((String)musicM.getValueAt(i, 0));
					}
				}
				
				String songname=getSongChoose();
				jlb_song.setText(songname);	
				}
				
			}
			
		}
		//��һ��
		if(e.getActionCommand().equals("jb_next")){
			//�жϵ�ǰ����
			if(b_play){//��ǰ�и����ڲ���
				//�õ���ǰ�����ĵ�ַ
				String songadd=getSongAddress();
				//�ж���һ���Ƿ��ܲ���
				boolean b_last=false;
				for(int k=1;k<key;k++){
					System.out.println("hashmap :"+hashMap.get(k));
					
					if(hashMap.get(k).equals(songadd)){
						b_last=true;
						if(k!=key-1){
							//�������ò��ŵ�ַ
							setSongAddress((String)hashMap.get(k+1));
						}else{
							//����Ѿ������һ�������ͷ��ʼ����
							setSongAddress((String)hashMap.get(1));
						}
					}
					
				}//forѭ��
				if(b_last){
					for(int i=0;i<musicM.getRowCount();i++)
					{
						//�����ݿ��еõ������ĵ�ַ
						if(musicM.getValueAt(i, 1).equals(getSongAddress())){
							//set��������
							setSongChoose((String)musicM.getValueAt(i, 0));
							//��ʼ����
							this.begin();
						}
					}
						
				}else{
					
					JOptionPane.showMessageDialog(this, "������ַ�����ڣ�","������",JOptionPane.WARNING_MESSAGE);
				}
				
			
			}else{
			
				JOptionPane.showMessageDialog(this, "��ǰ�޲��ţ�","������",JOptionPane.WARNING_MESSAGE);
						
			}
			
			
		}
		//��һ��
		if(e.getActionCommand().equals("jb_last")){
			//�жϵ�ǰ����
			if(b_play){//��ǰ�и����ڲ���
				//�õ���ǰ�����ĵ�ַ
				String songadd=getSongAddress();
				//�ж���һ���Ƿ��ܲ���
				boolean b_next=false;
				for(int k=1;k<key;k++){
					if(hashMap.get(k).equals(songadd)){
						b_next=true;
						if(k!=1){
							//�������ò��ŵ�ַ
							setSongAddress((String)hashMap.get(k-1));
						}else{
							//����Ѿ��ǵ�һ����������һ�׿�ʼ����
							setSongAddress((String)hashMap.get(key-1));
						}
					}
					
				}//forѭ��
				if(b_next){
					for(int i=0;i<musicM.getRowCount();i++)
					{
						//�����ݿ��еõ������ĵ�ַ
						if(musicM.getValueAt(i, 1).equals(getSongAddress())){
							//set��������
							setSongChoose((String)musicM.getValueAt(i, 0));
							//��ʼ����
							this.begin();
						}
					}
						
				}else{
					JOptionPane.showMessageDialog(this, "������ַ�����ڣ�","������",JOptionPane.WARNING_MESSAGE);
				}
			}else{
			
				JOptionPane.showMessageDialog(this, "��ǰ�޲��ţ�","������",JOptionPane.WARNING_MESSAGE);
						
			}
		}
		
		
		//3��������ģʽ
		//˳�򲥷�
		if(e.getActionCommand().equals("shunxu")){		
			b_loopPlay=true;
			b_singlePlay=false;
			b_randomPlay=false;
			jm_style.setText("˳��");
			System.out.println("˳�򲥷ţ�����");
			
		}
		//����ѭ��
		if(e.getActionCommand().equals("danqu")){
			b_loopPlay=false;
			b_singlePlay=true;
			b_randomPlay=false;
			jm_style.setText("����");
			System.out.println("����ѭ��������");
		}
		//�������
		if(e.getActionCommand().equals("suiji")){
			
			b_loopPlay=false;
			b_singlePlay=false;
			b_randomPlay=true;
			jm_style.setText("���");
			System.out.println("������ţ�����");
			
		}
		
		
		//4������������
		if(e.getActionCommand().equals("search")){
			//��֮ǰ������������
			for(int k=0;k<totalSong;k++){
				jlb_songs[k].setText("");
			}
			//����ģ������
			String searchMusic="%"+jtf_so.getText()+"%";
			//�������ݿ�
			String sql="select * from musicInfo where musicName" +
					" like '"+searchMusic+"' or singer like '"+searchMusic+"'";	
			musicM=new MusicModel(sql);
			int num=musicM.getRowCount();
			
			if(jtf_so.getText().equals("")){
				JOptionPane.showMessageDialog(null, "������Ҫ��ѯ�ĸ�����");
				
			}else{	
				if(num==0){
				    JOptionPane.showMessageDialog(null, "û���ҵ�������");
				}else{		
				//��ʾ����������
				cl_right.show(jp_showCard, "4");
				
				for(int i=0;i<num;i++){	
					if(musicM.getValueAt(i, 0)!=null){
						String songName=(String)musicM.getValueAt(i, 0);
						jlb_songs[i].setText(songName);
						jlb_songs[i].setToolTipText("˫������");
						songAdd="������ַ��"+(String)musicM.getValueAt(i, 1);
						jlb_songs[i].addMouseListener(new MouseAdapter(){
								public void mouseClicked(MouseEvent arg0) {
									// TODO Auto-generated method stub
									if(arg0.getClickCount()==2){
										//���أ������߳�
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
		//����
		if(e.getActionCommand().equals("download")){
			
			DownloadFrame dw = new DownloadFrame("�����ļ�����");
		}
		
	}
	
	//����¼�������������˫����
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		MusicModel mm=new MusicModel();
		//1����Ӧ�û��ĵ����¼�
		if(arg0.getClickCount()==1){
			setSongChoose(((JLabel)arg0.getSource()).getText());
			
			for(int i=0;i<mm.getRowCount();i++)
			{
				//�����ݿ��еõ������ĵ�ַ
				if(mm.getValueAt(i, 0).equals(getSongChoose())){
					//set������ַ
					//setSongAddress((String)mm.getValueAt(i, 1));			
				}
			}
		}
		//2����Ӧ�û���˫���¼����õ�Ĭ�ϱ��
		//˫������������
		if(arg0.getClickCount()==2)
		{	
			
			for(int i=0;i<mm.getRowCount();i++)
			{
				//�����ݿ��еõ������ĵ�ַ
				if(mm.getValueAt(i, 0).equals(getSongChoose())){
					//set������ַ
					setSongAddress((String)mm.getValueAt(i, 1));
					
				}
			}
			//˫������
			this.begin();
			
		}
		if(arg0.getClickCount()==5){
			JOptionPane.showMessageDialog(this, "�Ϥ뤫�ˡ����������ʤ���ۤ���~");
		}
		if(arg0.getClickCount()==10){
			JOptionPane.showMessageDialog(this, "���å��Щ`���Ȥϡ���Ϥ��ʤ���ֹ��뤳�ȤϤǤ��ޤ��󤷤Ƥ��ޤ���");
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
			//�����ݿ��еõ������ĵ�ַ
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
	
	//ʵʱͬ�����
	public void paint(Graphics g,String paint)  
    {   
		
		char ch2[]=paint.toCharArray();
		int length=paint.length();
        for(int i=0;i<length;i++)  
        {  
           int  r1=50+(int)(Math.random()*50);    //ͨ������Math���random���������  
           int g1=50+(int)(Math.random()*30);    //��ͨ��������ֱ�������ԭɫ��������  
           int b1=150+(int)(Math.random()*100);  
            Color color=new Color(r1,g1,b1);    //����һ����ɫ����  
            g.setColor(color);                  //������ɫ  
            int size=14;  
            Font f=new Font("",1,size);     //��������  
            g.setFont(f);  
            g.drawChars(ch2,i,1,310+i*16,485); //��ʾָ����С��ɫ���ַ���  
        }  
    }

}
