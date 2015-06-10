/**
 * ���ܣ��������ݿ⣬�����ݿ���Ϣ���в���
 * �����û���Ϣ����
 */
package musicServer.model;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

import musicServer.operateToDb.*;

public class LinkDBModel extends AbstractTableModel{
	
	//Vector ����������ݿ����Ϣ
	Vector rowData,columnNames;
	
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//�������ݿ⣬�õ��û���Ϣ
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://localhost:1433;databaseName=miniMusicBox";
	String user="sa";
	String password="wen";
	
	//��ʼ�����ݿ���Ϣ����ʾ�ڷ�������
	public void init(String sql,String tableName){
		
		columnNames=new Vector();
	    //��������
		if(tableName.equals("userInfo"))
		{
			columnNames.add("�û���");
			columnNames.add("����");
			columnNames.add("�Ƿ�����");
		}else if(tableName.equals("musicInfo")){
			columnNames.add("������");
			columnNames.add("������ַ");
			columnNames.add("����");
			columnNames.add("�����б�");
			columnNames.add("��ʵ�ַ");	
		}
		rowData=new Vector();		
		//�����ݿ���ȡ����������
		try {
			//1����������
			//��Ҫ���������jar��������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			//3�����������
			ps=ct.prepareStatement(sql);
			//4��ִ�У��������ɾ�ģ�ʹ��executeUpdate����������ǲ�ѯ��ʹ��executeQuery������
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				Vector hang=new Vector();
				//�õ���������
				for(int i=1;i<=this.getColumnCount();i++)
				{
					//��ÿһ���е����ݷֱ���ӵ�hang�У�����ӵ�������
					hang.add(rs.getString(i));
				
				}
				rowData.add(hang);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(ct!=null)ct.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
			
		}
	
	}
	
	public  LinkDBModel(String tableName)
	{
		//���Ը�����Ϣ����
		this.init("select * from musicInfo","musicInfo");
	}
	//1����ѯ
	//ͨ����ʼ���������Եõ����ݿ�������е���Ϣ��tableName��ʾ����ʾ�ı�
	public  LinkDBModel(String sql,String tableName)
	{
		this.init(sql,tableName);
	}
	

	

	//2���޸�
	public void UpdateM(UpdateMusic upm)
	{
		
		try {
			//��ʼ������
			//1����������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			//3�����������
			String sql="update musicInfo set musicName=?,musicAddress=?,singer=?," +
					"musicList=?,lrcAddress=? where musicName=?";
			ps=ct.prepareStatement(sql);
			//��?��ֵ
			ps.setString(1, upm.jtf_songname.getText());
			ps.setString(2, upm.jtf_songaddress.getText());
			ps.setString(3, upm.jtf_singername.getText());
			ps.setString(4, upm.getListName());
			ps.setString(5, upm.jtf_songlrc.getText());
			ps.setString(6, upm.jtf_songname.getText());	
			
			ps.executeUpdate();
		}catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}finally{
			//�ر���Դ
			try{
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			}catch(Exception e3){
				e3.printStackTrace();
				}
		}
		
	}
	//3������
	public void AddM(AddMusic am)
	{

		PreparedStatement pst=null;
		
		 boolean b=true;
		
		try {
			//��ʼ������
			//1����������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			
			pst=ct.prepareStatement("select musicName from musicInfo");
			
			// ���⣺����ӵĸ�������ԭ�и������ظ�ʱ����ʾ��Ϣ������	
			//��ѯ���Ƚϸ�����Ϣ�������ظ����
			rs=pst.executeQuery();
			while(rs.next()){
				if(am.jtf_songname.getText().equals(rs.getString(1)))				
				{
					JOptionPane.showMessageDialog(am, "�����������ظ�����������ӣ���");
					b=false; 
					return;
				}
				   
			}
			if(b){
				//3�����������
				String sql="insert into musicInfo values(?,?,?,?,?) ";
				ps=ct.prepareStatement(sql);
				//��������ֵ
				ps.setString(1, am.jtf_songname.getText());
				ps.setString(2, am.jtf_songaddress.getText());
				ps.setString(3, am.jtf_singername.getText());
				ps.setString(4, am.getListName());
				ps.setString(5, am.jtf_songlrc.getText());
				
				ps.executeUpdate();
				
				//�رնԻ���
				am.dispose();
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
	//4��ɾ��
	public  void DeleteM(String songName)
	{	
		try {
			//��ʼ������
			//1����������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			//3�����������
			String sql="delete from musicInfo where musicName=? ";
			ps=ct.prepareStatement(sql);
			ps.setString(1, songName);
			//ɾ���󣬸������ݿ�
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			//�ر���Դ
			try {
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return ((Vector)this.rowData.get(row)).get(column);
	}
	
	//������д
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}
}
