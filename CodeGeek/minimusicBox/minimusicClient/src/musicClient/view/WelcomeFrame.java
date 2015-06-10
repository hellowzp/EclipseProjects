/**
 * ���ܣ����ǽ������ʱ�Ļ�ӭ����
 * ���ڣ�2012-10-23
 */
package musicClient.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.sql.*;

import musicClient.model.*;
public class WelcomeFrame extends JFrame implements ActionListener{

	//��������Ҫ�����
	JPanel jp1,jp2;
	JLabel jlb1,jlb2,jlb3,jlb4;
	JTextField jtf;
	JPasswordField jpf;
	JButton jb1,jb2,jb3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WelcomeFrame wf=new WelcomeFrame();
		

	}
	//���캯������ʼ�����
	public WelcomeFrame(){
		//�������
		jlb1=new JLabel(new ImageIcon("images/welcome.jpg"));
		//�м�
		jlb2=new JLabel("�û���:",JLabel.CENTER);
		jlb3=new  JLabel("��   ��:",JLabel.CENTER);
		jtf=new JTextField(30);
		jtf.setSize(40, 20);
		jpf=new JPasswordField(30);
		jpf.setSize(40, 20);
		jb1=new JButton("ע��");
		jb1.addActionListener(this);
		jb1.setActionCommand("reg");
		jlb4=new JLabel("��������",JLabel.CENTER);
		//���������С����ɫ
		jlb4.setFont(new Font("������κ",Font.PLAIN,16));
		jlb4.setForeground(Color.BLUE);
		//�ԡ��������롱ʵ�ּ���
		jlb4.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==2){
					JOptionPane.showMessageDialog(null, "������ֱ�ӵ�¼��");
					
				}
				}
			
		});
		
		//�ϲ����
		jb2=new JButton("��½����");
		jb2.addActionListener(this);
		jb2.setActionCommand("login");
		jb3=new JButton("ֱ�ӽ���");
		jb3.addActionListener(this);
		jb3.setActionCommand("enter");
		
		//����jp1Ϊ���񲼾�
		jp1=new JPanel();
		jp1.setLayout(new GridLayout(2,3,10,4));
		jp1.add(jlb2);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jlb3);
		jp1.add(jpf);
		jp1.add(jlb4);
		//����jp2Ϊ������
		jp2=new JPanel();
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp2.add(jb2);
		jp2.add(jb3);
		//��ӵ����
		this.add(jlb1,BorderLayout.NORTH);
		this.add(jp1,BorderLayout.CENTER);
		this.add(jp2,BorderLayout.SOUTH);
		
		//���ô�������
		setSize(324,230);
		setTitle("MiniMusicBox");
		setLocation(500, 200);
		//��ֹ�ı䴰���С
		setResizable(false);
		//�رմ���ʱ�˳�����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			//ע���û�
			Register userReg=new Register(); 
			
		}
		if(e.getSource()==jb2)
		{
			//������֤��Ϣ��������
			UserModel u=new UserModel();	
			//�õ��û��������룿����
			u.setUserName(jtf.getText().trim());
			u.setPassword(new String(jpf.getPassword()));
			
			//���ӵ�½�����õ����û���Ϣ�����ݿ��еõ���֤	
			//�����������ݿ����Ӻ�Ĳ���
			Connection ct=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			//�������ݿ⣬�õ��û���Ϣ
			String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
			String url="jdbc:microsoft:sqlserver://222.20.73.200:1433;databaseName=miniMusicBox";
			String user="sa";
			String password="wen";
			
				
			try {
				//��ʼ������
				//1����������
				Class.forName(driver);
				//2���õ�����
				ct=DriverManager.getConnection(url,user,password);
				
				//�õ��û���Ϣ
				ps=ct.prepareStatement("select * from userInfo");		
				rs=ps.executeQuery();
				boolean b=false;
				while(rs.next()){
					if(u.getUserName().equals(rs.getString(1)))				
					{
						if(u.getPassword().equals(rs.getString(2)))
						{
							b=true;
							break;
						}		
					}	   
				}
				if(b){
					
					//�û���Ϣ��ȷ����½�ɹ�
					u.setOnline("��");
					System.out.println("�û����� "+u.getUserName()+"   ����: "+u.getPassword());
					rs.getString(3).equals(u.getOnline());
					//��½
					MainView vl=new MainView(u.getUserName()+",��ӭ��~");
					
					Thread t=new Thread(vl);
					t.start();
					this.dispose();	
					
				}else{
							JOptionPane.showMessageDialog(this,"�û���������������������룡");
						}
				
					
					
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}finally{
					//�ر���Դ
					try{
						if(rs!=null) rs.close();
						if(ps!=null) ps.close();
						if(ct!=null) ct.close();
					}catch(Exception e3){
						e3.printStackTrace();
						}
				}
						
		}else if(e.getSource()==jb3)
		{
			//ֱ�ӵ�¼
			System.out.println("ֱ�ӵ�¼����");
			MainView vl=new MainView("�������ֺ�");
			Thread t=new Thread(vl);
			t.start();
			this.dispose();
			
		}
	}

}
