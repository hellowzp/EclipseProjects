package musicClient.view;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class Register extends JFrame implements ActionListener {
		
		//��������Ҫ�����
		JLabel jlb1,jlb2,jlb3;
		JTextField jtf;
		JPasswordField jpf1,jpf2;
		JButton jb1,jb2;
		
		JPanel jp1,jp2,jp3;
	
	public Register(){
		jlb1=new JLabel(" �û���: ",JLabel.CENTER);
		jlb2=new JLabel(" ��  ��: ",JLabel.CENTER);
		jlb3=new JLabel(" ������������ :",JLabel.CENTER);
		
		jtf=new JTextField(15);
		jpf1=new JPasswordField(15);
		jpf2=new JPasswordField(15);
		
		jb1=new JButton("���");
		jb2=new JButton("ȡ��");
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
				
		//��Ʋ���
		jp1.setLayout(new GridLayout(3,1));
		jp2.setLayout(new GridLayout(3,1));
		this.add(jp1,BorderLayout.WEST);
		this.add(jp2,BorderLayout.CENTER);
		this.add(jp3,BorderLayout.SOUTH);
		
		//������ʾ
		this.setSize(280, 160);
		this.setTitle("�û�ע��...");
	//	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(640, 200);
		this.setResizable(false);
		this.setVisible(true);
		
	}

	//ʵ�ּ���
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==jb1)
		{
			this.Add(this);
				
		}else if(e.getSource()==jb2){
			//�رնԻ���
			this.dispose();
		}
		
		
	}
	
	public void Add(Register reg){
		
		Connection ct=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		//�������ݿ⣬�õ��û���Ϣ
		String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
		String url="jdbc:microsoft:sqlserver://222.20.73.200:1433;databaseName=miniMusicBox";
		String user="sa";
		String password="wen";
		
		 boolean b=true;
		
		try {
			//��ʼ������
			//1����������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			
			ps=ct.prepareStatement("select userName from userInfo");
			
			
			// ���⣺����ӵ��û�����ԭ���û����ظ�ʱ����ʾ��Ϣ������		
			rs=ps.executeQuery();
			while(rs.next()){
				if(reg.jtf.getText().equals(rs.getString(1)))				
				{
					JOptionPane.showMessageDialog(reg, "�û��������ظ������������룡��");
					b=false; 
					return;
				}
				   
			}
			
			if(reg.jtf.getText().trim().equals(""))
			{
				JOptionPane.showMessageDialog(reg, "�û�������Ϊ�գ���");
				b=false; 
				
			}
			String pass1=new String(jpf1.getPassword());
			String pass2=new String(jpf2.getPassword());
			if(!(pass1.equals(pass2)))
			{
				JOptionPane.showMessageDialog(reg, "�������벻ƥ�䣡��");
				jpf2.setText("");
				b=false; 
			}
			if(b){
				//3�����������
				String sql="insert into userInfo values(?,?,?) ";
				ps=ct.prepareStatement(sql);
				//��������ֵ
				String online="��"; //Ĭ���û���������ģʽ
				ps.setString(1, reg.jtf.getText());
				ps.setString(2, new String(reg.jpf1.getPassword()));
				ps.setString(3, online);
			
				//�������ݿ�
				ps.executeUpdate();
				
				//�رնԻ���
				reg.dispose();
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
				
	}


	
	
}	

	