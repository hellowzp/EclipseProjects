/**
 * 功能：连接数据库，对数据库信息进行操作
 * 进行用户信息管理
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
	
	//Vector 用来存放数据库的信息
	Vector rowData,columnNames;
	
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	//连接数据库，得到用户信息
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://localhost:1433;databaseName=miniMusicBox";
	String user="sa";
	String password="wen";
	
	//初始化数据库信息并显示在服务器端
	public void init(String sql,String tableName){
		
		columnNames=new Vector();
	    //设置列名
		if(tableName.equals("userInfo"))
		{
			columnNames.add("用户名");
			columnNames.add("密码");
			columnNames.add("是否在线");
		}else if(tableName.equals("musicInfo")){
			columnNames.add("歌曲名");
			columnNames.add("歌曲地址");
			columnNames.add("歌手");
			columnNames.add("播放列表");
			columnNames.add("歌词地址");	
		}
		rowData=new Vector();		
		//从数据库中取出各行数据
		try {
			//1、加载数据
			//重要：添加三个jar包！！！
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			//3、创建火箭车
			ps=ct.prepareStatement(sql);
			//4、执行（如果是增删改，使用executeUpdate（），如果是查询，使用executeQuery（））
			rs=ps.executeQuery();
			
			while(rs.next()){
				
				Vector hang=new Vector();
				//得到各行数据
				for(int i=1;i<=this.getColumnCount();i++)
				{
					//将每一行中的数据分别添加到hang中，并添加到向量中
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
		//仅对歌曲信息操作
		this.init("select * from musicInfo","musicInfo");
	}
	//1、查询
	//通过初始化操作可以得到数据库表中所有的信息，tableName表示所显示的表
	public  LinkDBModel(String sql,String tableName)
	{
		this.init(sql,tableName);
	}
	

	

	//2、修改
	public void UpdateM(UpdateMusic upm)
	{
		
		try {
			//初始化对象
			//1、加载数据
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			//3、创建火箭车
			String sql="update musicInfo set musicName=?,musicAddress=?,singer=?," +
					"musicList=?,lrcAddress=? where musicName=?";
			ps=ct.prepareStatement(sql);
			//给?赋值
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
			//关闭资源
			try{
				if(ps!=null) ps.close();
				if(ct!=null) ct.close();
			}catch(Exception e3){
				e3.printStackTrace();
				}
		}
		
	}
	//3、增加
	public void AddM(AddMusic am)
	{

		PreparedStatement pst=null;
		
		 boolean b=true;
		
		try {
			//初始化对象
			//1、加载数据
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			
			pst=ct.prepareStatement("select musicName from musicInfo");
			
			// 问题：当添加的歌曲名与原有歌曲名重复时，提示信息并报错。	
			//查询并比较歌曲信息，避免重复添加
			rs=pst.executeQuery();
			while(rs.next()){
				if(am.jtf_songname.getText().equals(rs.getString(1)))				
				{
					JOptionPane.showMessageDialog(am, "歌曲名不能重复，请重新添加！！");
					b=false; 
					return;
				}
				   
			}
			if(b){
				//3、创建火箭车
				String sql="insert into musicInfo values(?,?,?,?,?) ";
				ps=ct.prepareStatement(sql);
				//给参数赋值
				ps.setString(1, am.jtf_songname.getText());
				ps.setString(2, am.jtf_songaddress.getText());
				ps.setString(3, am.jtf_singername.getText());
				ps.setString(4, am.getListName());
				ps.setString(5, am.jtf_songlrc.getText());
				
				ps.executeUpdate();
				
				//关闭对话框
				am.dispose();
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
	//4、删除
	public  void DeleteM(String songName)
	{	
		try {
			//初始化对象
			//1、加载数据
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			//3、创建火箭车
			String sql="delete from musicInfo where musicName=? ";
			ps=ct.prepareStatement(sql);
			ps.setString(1, songName);
			//删除后，更新数据库
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			//关闭资源
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
	
	//列名重写
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return (String)this.columnNames.get(column);
	}
}
