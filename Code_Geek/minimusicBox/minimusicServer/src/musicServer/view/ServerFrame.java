/**
 * ���ܣ����Ƿ�������������
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

	//�����м䲿������Ҫ�����cardlayout
	JPanel jp_north,jp_south,jp_showInfo,jp_song,jp_user;
	JButton jb_song1,jb_user1,jb_song2,jb_user2;
	JTable jtb_song,jtb_user;
	JScrollPane jsp_song,jsp_user;
	CardLayout card=new CardLayout();
	//����˵������
	JMenuBar jmb;
	JMenu jm_file,jm_set,jm_help;//3���˵���
	JMenuItem jmi_user,jmi_music,jmi_add,jmi_download,jmi_exit,jmi_help,jmi_start,jmi_stop;
	//��������ݿ���в��������
	JButton jb_add,jb_delete,jb_change,jb_find1,jb_find2,jb_back1,jb_back2;
	JTextField jtf_find1,jtf_find2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerFrame sf=new ServerFrame();
	}
	public ServerFrame()
	{
		//����
		//��ʼ���˵���
		jmb=new JMenuBar();
		jm_file=new JMenu(" �ļ� ");
		jm_set=new JMenu(" ����  ");
		jm_help=new JMenu(" ���� ");
		//�����˵�
		jmi_user=new JMenuItem("�û���Ϣ");
		jmi_user.addActionListener(this);
		jmi_music=new JMenuItem("������Ϣ");
		jmi_music.addActionListener(this);
		jmi_add=new JMenuItem("��Ӹ���");
		jmi_add.addActionListener(this);
		jmi_download=new JMenuItem("���ظ���");
		jmi_download.addActionListener(this);
		jmi_download.setActionCommand("download");
		jmi_exit=new JMenuItem("�˳�");
		jmi_exit.addActionListener(this);
		jmi_help=new JMenuItem("�����ĵ�");
		jmi_help.addActionListener(this);
		jmi_start=new JMenuItem("����������");
		jmi_start.addActionListener(this);
		jmi_stop=new JMenuItem("�رշ�����");
		jmi_stop.addActionListener(this);
		
		//��Ӳ˵���
		jm_file.add(jmi_user);
		jm_file.add(jmi_music);
		jm_file.addSeparator();//��ӷָ���
		jm_file.add(jmi_add);
		jm_file.add(jmi_download);
		jm_file.addSeparator();//��ӷָ���
		jm_file.add(jmi_exit);
		jm_set.add(jmi_start);
		jm_set.add(jmi_stop);
		jm_help.add(jmi_help);
		//��Ӳ˵���˵���
		jmb.add(jm_file);
		jmb.add(jm_set);
		jmb.add(jm_help);
		jmb.setOpaque(false);
		//���˵�������������
		jp_north=new JPanel(new FlowLayout(FlowLayout.LEFT));
		jp_north.setBounds(0,0,560,40);
		jp_north.add(jmb);
		jp_north.setOpaque(false);
		
		//�ϲ�
		jb_add=new JButton("���");
		jb_add.addActionListener(this);
		jb_delete=new JButton("ɾ��");
		jb_delete.addActionListener(this);
		jb_change=new JButton("�޸�");
		jb_change.addActionListener(this);
		jp_south=new JPanel(new FlowLayout(FlowLayout.CENTER));
		jp_south.add(jb_add);
		jp_south.add(jb_delete);
		jp_south.add(jb_change);
		jp_south.setOpaque(false);
		jp_south.setBounds(20,380, 560,40);
		
		//�м�
		//cardlayout ������Ϣ��ʾ���û���Ϣ��ʾ
		//card1
		jb_song1=new JButton("������Ϣ");
		jb_user1=new JButton("�û���Ϣ");
		jtf_find1=new JTextField(12);
		
		jb_find1=new JButton("��ѯ");
		jb_find1.addActionListener(this);
		jb_back1=new JButton("ˢ��");
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
		//�������ݿ⣬�õ�������Ϣ
		String sql="select * from musicInfo";
		String tableName="musicInfo";
		LinkDBModel ldm=new LinkDBModel(sql, tableName);
		jtb_song=new JTable(ldm);
		jsp_song=new JScrollPane(jtb_song);
		jsp_song.setAutoscrolls(true);
		jsp_song.setBounds(45,55 ,500,290 );
		
		//jsp_song.setLayout(new BorderLayout());
		//���õ��ĸ�����Ϣ��ӵ������
		jp_song=new JPanel(null);
		jp_song.setBounds(20, 45, 560, 300);
		//jp_song.setEnabled(true);
		jp_song.setOpaque(false);
		jp_song.add(jp2);
		jp_song.add(jsp_song);
		
		//card2
		jb_song2=new JButton("������Ϣ");
		jb_user2=new JButton("�û���Ϣ");
		jtf_find2=new JTextField(12);
		jb_find2=new JButton("��ѯ");
		jb_find2.addActionListener(this);
		jb_back2=new JButton("ˢ��");
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
		//�������ݿ⣬�õ��û���Ϣ
		String sql2="select * from userInfo";
		String tableName2="userInfo";
		LinkDBModel ldm2=new LinkDBModel(sql2, tableName2);
		jtb_user=new JTable(ldm2);		
		JScrollPane jsp_user;
		jsp_user=new JScrollPane(jtb_user);
		jsp_user.setBounds(45,55 ,500,290 );
		//���õ����û���Ϣ��ӵ����2
		jp_user=new JPanel(null);
		jp_user.setBounds(20, 45, 560, 300);
		
		jp_user.setOpaque(false);
		jp_user.add(jp3);
		jp_user.add(jsp_user);
		
		//���ÿ�Ƭ����
		jp_showInfo=new JPanel(card) ;
		jp_showInfo.setBounds(20,22,560,360);
		jp_showInfo.setOpaque(false);
		jp_showInfo.add(jp_song,"1");
		jp_showInfo.add(jp_user,"2");
		
		//��Ƭ��ת����
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
		
		
		//������������ӵ��������		
	    this.setLayout(null);
	    this.getContentPane().add(jp_north);
		this.getContentPane().add(jp_showInfo);
		//this.add(jp_showInfo,BorderLayout.CENTER);
		this.getContentPane().add(jp_south);
		
		//���ñ���ͼƬ
		JPanel imagePanel;
		ImageIcon background=new ImageIcon("images/background5.jpg");
		JLabel label=new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		imagePanel=(JPanel)this.getContentPane();
		//ʹ���ݴ���͸����
		imagePanel.setOpaque(false);
		this.getLayeredPane().setLayout(null);
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		
		//���ô�������
		this.setVisible(true);
		this.setSize(600,450);
		this.setLocation(300, 100);
		this.setResizable(false);
		this.setTitle("�������ֲ�����-������");
		this.setIconImage(new ImageIcon("images/server.jpg").getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
	//����¼��ļ���
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//Ԥ���崦����
		AddMusic addMusic;
		UpdateMusic updateMusic;
		LinkDBModel linkDb;
		ServerSendToClient sstc=new ServerSendToClient();;
		Thread th=new Thread(sstc);
		
		//�Բ˵���ļ���
		//��Ϣ�����л�
		if(e.getSource()==jmi_user){
			 card.show(jp_showInfo,"2");
		}if(e.getSource()==jmi_music){
			 card.show(jp_showInfo,"1");
		}if(e.getSource()==jmi_add){
			//��Ӹ���
			addMusic=new AddMusic();
			//�������ݿ�
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);		
		}if(e.getActionCommand().equals("download")){
			//���ظ���
			DownloadFrame dw = new DownloadFrame("�����ļ�����");
	
		}if(e.getSource()==jmi_exit){
			//�˳�
			System.exit(0);
			this.dispose();
		
			
		}
		//����������
		if(e.getSource()==jmi_start){		
			th.start();
			JOptionPane.showMessageDialog(this, "��������������,��34566�˿ڼ���...");	
		}//�رշ�����	
		if(e.getSource()==jmi_stop){
		
			int res=JOptionPane.showConfirmDialog(this, "��ܰ��ʾ���رշ��������޷���Ӧ�û�����ز�����"+"\n"+"�Ƿ������","ȷ��",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			//���û���ѡ������ж� Y-ɾ�� N��ȡ��
			if(res==JOptionPane.YES_OPTION){    
				//JOptionPane.showMessageDialog(this, "�������ѹر�...");
				th.stop();
				sstc.stop();
				System.out.println("�������ѹرգ�");
			}
			
		}if(e.getSource()==jmi_help){
			//�򿪰����ĵ�
			JOptionPane.showMessageDialog(this, "����һ��򵥵����ֲ�������ķ�������\r\n"+
					"ϣ����ʹ����죡\r\n");
		}
		
		//��button�ļ���
		//��ѯ
		if(e.getSource()==jb_find1){
			//��������
			String song="%"+jtf_find1.getText()+"%";
			//�������ݿ�
			String sql="select * from musicInfo where musicName" +
					" like '"+song+"' or singer like '"+song+"'";
			
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);	
		}if(e.getSource()==jb_find2){
			//�����û�
			String user="%"+jtf_find2.getText()+"%";
			//�������ݿ�
			String sql="select * from userInfo where userName like '"+user+"'";
			String tableName="userInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_user.setModel(linkDb);	
		}
		//ˢ���б�
		if(e.getSource()==jb_back1){
			String tableName="musicInfo";
			linkDb=new LinkDBModel(tableName);
			card.show(jp_showInfo, "1");
			jtf_find1.setText("");
			jtb_song.setModel(linkDb);
			
		}
		if(e.getSource()==jb_back2){
			//�������ݿ�
			String sql="select * from userInfo ";
			String tableName="userInfo";
			linkDb=new LinkDBModel(sql, tableName);
			card.show(jp_showInfo, "2");
			jtf_find2.setText("");
			jtb_user.setModel(linkDb);	
			
		}
		//���
		if(e.getSource()==jb_add){
			addMusic=new AddMusic();
			//�������ݿ�
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);
		}
			
		//ɾ��
		if(e.getSource()==jb_delete){
			int rowNum=this.jtb_song.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��һ�У�");
				return;
			}else{
				 linkDb=new LinkDBModel("musicInfo");
				String musicName=(String)(linkDb.getValueAt(rowNum, 0));		
				
				linkDb.DeleteM(musicName);
				//�����µ����ݿⲢ����
				 linkDb=new LinkDBModel("musicInfo");
				 jtb_song.setModel(linkDb);
			}
		}
		//�޸�
		if(e.getSource()==jb_change){
			int rowNum=this.jtb_song.getSelectedRow();
			if(rowNum==-1)
			{
				JOptionPane.showMessageDialog(this, "��ѡ��һ�У�");
				return;
			}else{
				 linkDb=new LinkDBModel("musicInfo");
				updateMusic=new UpdateMusic( linkDb, rowNum);
			}
			//�������ݿ�
			String sql="select * from musicInfo";
			String tableName="musicInfo";
			linkDb=new LinkDBModel(sql, tableName);
			jtb_song.setModel(linkDb);
		
		}
		
		
		
	}
	

}
