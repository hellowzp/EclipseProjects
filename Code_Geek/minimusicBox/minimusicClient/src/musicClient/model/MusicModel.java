/**
 * �������ڼ��϶����ݿ����ɾ��ĵĲ���
 * ͨ����ͬ��sql�������ݿ�����ͬ�Ĵ���
 * 1���б���ʾ,ֱ�Ӳ������е����ݲ���ʾ
 * 2����Ӹ���
 * 3��ɾ������
 * 4�������������ڱ������ݿ���������
 */
package musicClient.model;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import musicClient.operateToDb.*;

public class MusicModel extends AbstractTableModel {

	//Vector ����������ݿ��и�������Ϣ
	Vector rowData,columnNames;
	
	//�������ݿ�
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://localhost:1433;databaseName=miniMusicBox";
	String user="sa";//222.20.73.200
	String password="wen";
	
	
	
	//���ڳ�ʼ������ģ��
	public void init(String sql)
	{
		if(sql.equals(""))
		{
			sql="select * from musicInfo";
		}
		columnNames=new Vector();
	    //����û����Ϣ
		columnNames.add("������");
		columnNames.add("������ַ");
		columnNames.add("����");
		columnNames.add("�����б�");
		columnNames.add("��ʵ�ַ");
		
		rowData=new Vector();		
		//�����ݿ���ȡ����������
		try {
			//��ʼ������
			//1����������
			Class.forName(driver);
			//2���õ�����
			ct=DriverManager.getConnection(url,user,password);
			//3�����������
			ps=ct.prepareStatement(sql);
			//4��ִ�У��������ɾ�ģ�ʹ��executeUpdate����������ǲ�ѯ��ʹ��executeQuery������
			rs=ps.executeQuery();
			
			//ѭ��ȡ��ÿ�н�����
			while(rs.next())
			{
				Vector hang=new Vector();
				
				hang.add(rs.getString(1));
				hang.add(rs.getString(2));
				hang.add(rs.getString(3));
				hang.add(rs.getString(4));
				hang.add(rs.getString(5));
						
				rowData.add(hang);

			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//�ر���Դ
			try {
				if(rs!=null) rs.close();
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
			
		}
	}
	
	//���캯�����õ����и���������Ϣ
	public MusicModel()
	{
		this.init("");
	}
	
	//1����ѯ��ͨ�����ݵ�sql�������ѯĳ��������Ϣ
	//��ʼʱ�õ����еĸ�������ʾ�ڲ����б���
	public MusicModel(String sql)
	{
		this.init(sql);
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
	
	//ͨ��extends AbstractTableModel���Եõ�ÿһ��table����ϸ��Ϣ
	//�õ����ж�����
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	//�õ����ж�����
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	//����ĳ��ĳ�ж�������
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
