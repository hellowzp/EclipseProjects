/**
 * 功能：这是进入软件时的欢迎界面
 * 日期：2012-10-23
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

	//定义所需要的组件
	JPanel jp1,jp2;
	JLabel jlb1,jlb2,jlb3,jlb4;
	JTextField jtf;
	JPasswordField jpf;
	JButton jb1,jb2,jb3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WelcomeFrame wf=new WelcomeFrame();
		

	}
	//构造函数，初始化组件
	public WelcomeFrame(){
		//北部组件
		jlb1=new JLabel(new ImageIcon("images/welcome.jpg"));
		//中间
		jlb2=new JLabel("用户名:",JLabel.CENTER);
		jlb3=new  JLabel("密   码:",JLabel.CENTER);
		jtf=new JTextField(30);
		jtf.setSize(40, 20);
		jpf=new JPasswordField(30);
		jpf.setSize(40, 20);
		jb1=new JButton("注册");
		jb1.addActionListener(this);
		jb1.setActionCommand("reg");
		jlb4=new JLabel("忘记密码",JLabel.CENTER);
		//设置字体大小和颜色
		jlb4.setFont(new Font("华文新魏",Font.PLAIN,16));
		jlb4.setForeground(Color.BLUE);
		//对“忘记密码”实现监听
		jlb4.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==2){
					JOptionPane.showMessageDialog(null, "您可以直接登录！");
					
				}
				}
			
		});
		
		//南部组件
		jb2=new JButton("登陆进入");
		jb2.addActionListener(this);
		jb2.setActionCommand("login");
		jb3=new JButton("直接进入");
		jb3.addActionListener(this);
		jb3.setActionCommand("enter");
		
		//设置jp1为网格布局
		jp1=new JPanel();
		jp1.setLayout(new GridLayout(2,3,10,4));
		jp1.add(jlb2);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jlb3);
		jp1.add(jpf);
		jp1.add(jlb4);
		//设置jp2为流布局
		jp2=new JPanel();
		jp2.setLayout(new FlowLayout(FlowLayout.CENTER));
		jp2.add(jb2);
		jp2.add(jb3);
		//添加到面板
		this.add(jlb1,BorderLayout.NORTH);
		this.add(jp1,BorderLayout.CENTER);
		this.add(jp2,BorderLayout.SOUTH);
		
		//设置窗体属性
		setSize(324,230);
		setTitle("MiniMusicBox");
		setLocation(500, 200);
		//禁止改变窗体大小
		setResizable(false);
		//关闭窗口时退出程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			//注册用户
			Register userReg=new Register(); 
			
		}
		if(e.getSource()==jb2)
		{
			//发送验证信息到服务器
			UserModel u=new UserModel();	
			//得到用户名和密码？？？
			u.setUserName(jtf.getText().trim());
			u.setPassword(new String(jpf.getPassword()));
			
			//将从登陆界面拿到的用户信息在数据库中得到验证	
			//以下是与数据库连接后的操作
			Connection ct=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			
			//连接数据库，得到用户信息
			String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
			String url="jdbc:microsoft:sqlserver://222.20.73.200:1433;databaseName=miniMusicBox";
			String user="sa";
			String password="wen";
			
				
			try {
				//初始化对象
				//1、加载数据
				Class.forName(driver);
				//2、得到连接
				ct=DriverManager.getConnection(url,user,password);
				
				//得到用户消息
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
					
					//用户信息正确，登陆成功
					u.setOnline("是");
					System.out.println("用户名： "+u.getUserName()+"   密码: "+u.getPassword());
					rs.getString(3).equals(u.getOnline());
					//登陆
					MainView vl=new MainView(u.getUserName()+",欢迎你~");
					
					Thread t=new Thread(vl);
					t.start();
					this.dispose();	
					
				}else{
							JOptionPane.showMessageDialog(this,"用户名或密码错误，请重新输入！");
						}
				
					
					
				}catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}finally{
					//关闭资源
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
			//直接登录
			System.out.println("直接登录！！");
			MainView vl=new MainView("迷你音乐盒");
			Thread t=new Thread(vl);
			t.start();
			this.dispose();
			
		}
	}

}
