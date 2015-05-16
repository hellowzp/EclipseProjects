package be.kuleuven.wang;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

public class AccessDB {

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("driver loaded!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(
							"jdbc:sqlserver://studev.groept.be\\groept;database=a13_SD205",
							"a13_SD205", "a13_SD205");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("connected!");
		return con;
	}

	public static ResultSet getUser(String username) {
		Connection con = getConnection();		
		PreparedStatement preStat = null;
		ResultSet res = null;
		String sql = "select * from UserData where Username = ?";
		try {
			preStat = con.prepareStatement(sql);
			preStat.setString(1,username);
			res = preStat.executeQuery();
        } catch (SQLException e) {
			e.printStackTrace();
		}			
		return res;
	}
	
	public static Map<String,String> getAllUsers() {
		ResultSet res = null;
		Map<String,String> users = new HashMap<String,String>();
		Connection con = AccessDB.getConnection();	
		try {
			Statement state = con.createStatement();
			res = state.executeQuery("Select * from UserData");
            while(res.next()) {
            	users.put(res.getString(2), res.getString(3));
            }
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		for (Map.Entry<String, String> entry : users.entrySet())
        {
              System.out.println(entry.getKey() + " " + entry.getValue());
        }
		return users;
	}
	
	public static ResultSet getAll() {
		ResultSet res = null;
		Connection con = AccessDB.getConnection();	
		try {
			Statement state = con.createStatement();
			res = state.executeQuery("Select * from UserData");           
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return res;
	}
	
	public static void addUser(String user, String mail) {
		Connection con = AccessDB.getConnection();	
		PreparedStatement preStat = null;
		String sql = "INSERT INTO UserData (Username, Email) VALUES (?,?)" ;
		try {
			preStat = con.prepareStatement(sql);
			preStat.setString(1,user);
			preStat.setString(2,mail);
			preStat.executeUpdate();;	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void setScore(String user, int score) {
		Connection con = AccessDB.getConnection();	
		PreparedStatement preStat = null;
		String sql = "UPDATE UserData SET Score = ? WHERE Username = ? " ;
		try {
			preStat = con.prepareStatement(sql);
			preStat.setInt(1,score);
			preStat.setString(2,user);
			preStat.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public static JFrame createTable() {		
		Vector<String> columnNames = new Vector<>();
		columnNames.add("UserID");
		columnNames.add("User Name");
		columnNames.add("Email");
		columnNames.add("Score");
		
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		ResultSet rs = AccessDB.getAll();
		try {
			while(rs.next()) {
				Vector<Object> row = new Vector<>();
				row.add(rs.getInt(1));
				row.add(rs.getString(2));
				row.add(rs.getString(3));
				row.add(rs.getInt(4));
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		DefaultTableModel model = new DefaultTableModel(data,columnNames) {
//			@Override
//			public Class getColumnClass(int column) {
//				if ((column >= 0) && (column < getColumnCount())) {
//					return getValueAt(0, column).getClass();
//				} else {
//					return Object.class;
//				}
//			}
//		};
		JTable table = new JTable(data,columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAlignmentX(JTable.LEFT_ALIGNMENT);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
//        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        TableColumn mailColumn = table.getColumnModel().getColumn(2);
        mailColumn.setPreferredWidth(200);
        
//        JTextField tfEditor = new JTextField();
//        tfEditor.setHorizontalAlignment(SwingConstants.CENTER);
//        for(int i=0; i<3; i++) {
//        	table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(tfEditor));
//        }
		
		final JFrame f = new JFrame("RankTable");
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container c = f.getContentPane(); 
		c.add(new JScrollPane(table), "Center");
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setVisible(false);
			}
		});
		c.add(BorderLayout.SOUTH, ok);
		f.setSize(450, 200);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		return f;		
	}

	public static void main(String[] args) throws IOException, SQLException {
		new AccessDB();
//		AccessDB.addUser("WANG", "gmail");
//		AccessDB.setScore("wang", 1000);
		AccessDB.getAllUsers();		
	}

}

