/**
 * 该类用于集合对数据库的增删查改的操作
 * 通过不同的sql语句对数据库做不同的处理
 * 1、列表显示,直接查找所有的数据并显示
 * 2、添加歌曲
 * 3、删除歌曲
 * 4、歌曲搜索（在本地数据库中搜索）
 */
package musicClient.model;

import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import musicClient.operateToDb.*;

public class MusicModel extends AbstractTableModel {

	//Vector 用来存放数据库中歌曲的信息
	Vector rowData,columnNames;
	
	//连接数据库
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	String driver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://localhost:1433;databaseName=miniMusicBox";
	String user="sa";//222.20.73.200
	String password="wen";
	
	
	
	//用于初始化数据模型
	public void init(String sql)
	{
		if(sql.equals(""))
		{
			sql="select * from musicInfo";
		}
		columnNames=new Vector();
	    //设置没列信息
		columnNames.add("歌曲名");
		columnNames.add("歌曲地址");
		columnNames.add("歌手");
		columnNames.add("所在列表");
		columnNames.add("歌词地址");
		
		rowData=new Vector();		
		//从数据库中取出各行数据
		try {
			//初始化对象
			//1、加载数据
			Class.forName(driver);
			//2、得到连接
			ct=DriverManager.getConnection(url,user,password);
			//3、创建火箭车
			ps=ct.prepareStatement(sql);
			//4、执行（如果是增删改，使用executeUpdate（），如果是查询，使用executeQuery（））
			rs=ps.executeQuery();
			
			//循环取出每行结数据
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
			//关闭资源
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
	
	//构造函数，得到所有歌曲都的信息
	public MusicModel()
	{
		this.init("");
	}
	
	//1、查询：通过传递的sql语句来查询某歌曲的信息
	//初始时得到所有的歌曲并显示在播放列表中
	public MusicModel(String sql)
	{
		this.init(sql);
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
	
	//通过extends AbstractTableModel可以得到每一个table的详细信息
	//得到共有多少列
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return this.columnNames.size();
	}

	//得到共有多少行
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.rowData.size();
	}

	//返回某行某列多少数据
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
