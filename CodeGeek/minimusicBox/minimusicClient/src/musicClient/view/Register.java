package musicClient.view;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class Register extends JFrame implements ActionListener {
		
		//定义所需要的组件
		JLabel jlb1,jlb2,jlb3;
		JTextField jtf;
		JPasswordField jpf1,jpf2;
		JButton jb1,jb2;
		
		JPanel jp1,jp2,jp3;
	
	public Register(){
		jlb1=new JLabel(" 用户名: ",JLabel.CENTER);
		jlb2=new JLabel(" 密  码: ",JLabel.CENTER);
		jlb3=new JLabel(" 重新输入密码 :",JLabel.CENTER);
		
		jtf=new JTextField(15);
		jpf1=new JPasswordField(15);
		jpf2=new JPasswordField(15);
		
		jb1=new JButton("添加");
		jb2=new JButton("取消");
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		jp1.add(jlb1);
		jp1.add(jlb2);
		jp1.add(jlb3);
		
		jp2.add(jtf);
		jp2.add(jpf1);
		jp2.add(jpf2);
		
		jp3.add(jb1);
		jp3.add(jb2);
				
		//设计布局
		jp1.setLayout(new GridLayout(3,1));
		jp2.setLayout(new GridLayout(3,1));
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		//窗口显示
		this.setSize(280, 160);
		this.setTitle("用户注册...");
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(640, 200);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	//实现监听
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			this.Add(this);
				
		}else if(e.getSource()==jb2){
			//关闭对话框
			this.dispose();
		}
		
		
	}
	
	public void Add(Register reg){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		//连接数据库，得到用户信息
		String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
		String url="jdbc:microsoft:sqlserver://222.20.73.200:1433;databaseName=miniMusicBox";
		String user="sa";
		String password="wen";
		
		 boolean b=true;
		
		try {
			//初始化对象
			//1、加载数据
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			
			ps=ct.prepareStatement("select userName from userInfo");
			
			
			// 问题：当添加的用户名与原有用户名重复时，提示信息并报错。		
			rs=ps.executeQuery();
			while(rs.next()){
				if(reg.jtf.getText().equals(rs.getString(1)))				
				{
					JOptionPane.showMessageDialog(reg, "用户名不能重复，请重新输入！！");
					b=false; 
					return;
				}
				   
			}
			
			if(reg.jtf.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(reg, "用户名不能为空！！");
				b=false; 
				
			}
			String pass1=new String(jpf1.getPassword());
			String pass2=new String(jpf2.getPassword());
			if(!(pass1.equals(pass2)))
			{
				JOptionPane.showMessageDialog(reg, "两次密码不匹配！！");
				jpf2.setText("");
				b=false; 
			}
			if(b){
				//3、创建火箭车
				String sql="insert into userInfo values(?,?,?) ";
				ps=ct.prepareStatement(sql);
				//给参数赋值
				String online="否"; //默认用户处于离线模式
				ps.setString(1, reg.jtf.getText());
				ps.setString(2, new String(reg.jpf1.getPassword()));
				ps.setString(3, online);
			
				//更新数据库
				ps.executeUpdate();
				
				//关闭对话框
				reg.dispose();
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
				
	}


	
	
}	

	