package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


class Administrator
{
	static public int register(String username, String password)
	{
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123456");
			try {
				
				Statement statement=connection.createStatement();
				StringBuilder queryBuilder=new StringBuilder("CREATE USER \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\' IDENTIFIED BY \'");
				queryBuilder.append(password);
				queryBuilder.append("\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.book TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.local_record TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.local_user TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.borrow_count TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.major_record TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("GRANT SELECT ON library.at_lib TO \'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'@\'localhost\'");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("INSERT INTO user (name, max_amount) VALUES (\'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\', 20)");
				statement.executeUpdate(queryBuilder.toString());
				
				queryBuilder.setLength(0);
				queryBuilder.append("SELECT user_id FROM user WHERE name=\'");
				queryBuilder.append(DBTest.formatString(username));
				queryBuilder.append("\'");
				ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
				while(resultSet.next())
				{
					int id=resultSet.getInt("user_id");
					connection.close();
					return id;
				}
				
				return 0;
			} catch(SQLException e1) {
				e1.printStackTrace();
				return 0;
			}
		} catch(SQLException e2) {
			e2.printStackTrace();
			return 0;
		}
	}
	
	static public boolean borrowBook(int book_id, int user_id)
	{
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123456");
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("INSERT INTO borrow_record (user_id, book_id, deadline) VALUES (");
			queryBuilder.append(user_id);
			queryBuilder.append(", ");
			queryBuilder.append(book_id);
			queryBuilder.append(", DATE_ADD(NOW(), INTERVAL 30 DAY))");
			statement.executeUpdate(queryBuilder.toString());
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
				
		return true;
	}
	
	static public boolean returnBook(int book_id)
	{
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "123456");
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("UPDATE borrow_record SET end_time=NOW() WHERE end_time IS NULL AND book_id=");
			queryBuilder.append(book_id);
			statement.executeUpdate(queryBuilder.toString());
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	static public boolean removeUser(String username)
	{
		
		return true;
	}
	
	static public boolean setAdmin(String username)
	{
		
		return true;
	}
}


public class User
{
	private String username;
	private boolean online=false;
	private int user_id=0;
	
	private Connection connection;
	
	public User()
	{
		
	}
	
	public boolean login(String username, String password)
	{
		try {
			if(online)
				connection.close();
			connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/library", username, password);
			this.username=username;
			online=true;
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT user_id FROM local_user");
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			while(resultSet.next())
			{
				user_id=resultSet.getInt("user_id");
				return true;
			}
			
			return false;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean register(String username, String password)
	{
		if((user_id=Administrator.register(username, password))==0)
			return false;
		if(!login(username, password))
			JOptionPane.showMessageDialog(null, "注册成功，但登录失败", "错误", JOptionPane.INFORMATION_MESSAGE);
		return true;
	}
	
	public boolean checkBookAtLib(int book_id)
	{
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT * FROM at_lib WHERE book_id=\'");
			queryBuilder.append(book_id);
			queryBuilder.append("\'");
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			while(resultSet.next())
				return true;
			return false;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean noDebt()
	{
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT debt FROM local_user");
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			while(resultSet.next())
				if(resultSet.getDouble("debt")>0)
					return false;
			return true;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public int getBorrowCount()
	{
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT count(book_id) AS count_book FROM local_record WHERE end_time IS NULL GROUP BY user_id");
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			while(resultSet.next())
				return resultSet.getInt("count_book");
			return -1;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return 2147483647;
		}
	}
	
	public int getMaxAmount()
	{
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT max_amount FROM local_user");
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			while(resultSet.next())
				return resultSet.getInt("max_amount");
			return -1;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
	}
	
	public boolean borrowBook(int book_id)
	{
		if(!checkBookAtLib(book_id))
			JOptionPane.showMessageDialog(null, "借阅"+Integer.toString(book_id)+"号书籍失败：该书已被借阅", "错误", JOptionPane.INFORMATION_MESSAGE);
		else if(!noDebt())
			JOptionPane.showMessageDialog(null, "借阅"+Integer.toString(book_id)+"号书籍失败：账户处于欠费状态", "错误", JOptionPane.INFORMATION_MESSAGE);
		else if(getBorrowCount()>=getMaxAmount())
			JOptionPane.showMessageDialog(null, "借阅"+Integer.toString(book_id)+"号书籍失败：已达到最大借阅数量", "错误", JOptionPane.INFORMATION_MESSAGE);
		else
			return Administrator.borrowBook(book_id, user_id);
		return false;
	}
	
	public boolean returnBook(int book_id)
	{
		return Administrator.returnBook(book_id);
	}
	
	public boolean ifOnline()
	{
		return online;
	}
	
	public void queryBook(DefaultTableModel model, String query)
	{
		try {
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(query);
			while(resultSet.next())
			{
				Vector<String> rowData=new Vector<>();
				String book_id=Integer.toString(resultSet.getInt("book_id"));
		        String name=resultSet.getString("name");
		        String author=resultSet.getString("author");
		        String press=resultSet.getString("press");
		        String major=resultSet.getString("major");
		        rowData.add(book_id);
		        rowData.add(name);
		        rowData.add(author);
		        rowData.add(press);
		        rowData.add(major);
		        model.addRow(rowData);
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "查询失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	
	public boolean removeBook(int book_id)
	{
		try {
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT is_admin FROM local_user");
			while(resultSet.next())
				if(resultSet.getInt("is_admin")==0)
				{
					JOptionPane.showMessageDialog(null, "修改失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			
			
			StringBuilder queryBuilder=new StringBuilder("DELETE FROM borrow_record WHERE book_id=");
			queryBuilder.append(book_id);
			statement.executeUpdate(queryBuilder.toString());
			
			queryBuilder.setLength(0);
			queryBuilder.append("DELETE FROM book WHERE book_id=");
			queryBuilder.append(book_id);
			statement.executeUpdate(queryBuilder.toString());
			
			return true;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public boolean addBook(String name, String author, String press, String major, String location)
	{
		try {
			if(name.length()==0)
			{
				JOptionPane.showMessageDialog(null, "必须输入书名", "错误", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
			
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("INSERT INTO book (name");
			if(author.length()>0)
				queryBuilder.append(", author");
			if(press.length()>0)
				queryBuilder.append(", press");
			if(major.length()>0)
				queryBuilder.append(", major");
			if(location.length()>0)
				queryBuilder.append(", location");
			queryBuilder.append(") VALUES (\'");
			queryBuilder.append(DBTest.formatString(name));
			queryBuilder.append("\'");
			if(author.length()>0)
			{
				queryBuilder.append(", \'");
				queryBuilder.append(DBTest.formatString(author));
				queryBuilder.append("\'");
			}
			
			if(press.length()>0)
			{
				queryBuilder.append(", \'");
				queryBuilder.append(DBTest.formatString(press));
				queryBuilder.append("\'");
			}
			
			if(major.length()>0)
			{
				queryBuilder.append(", \'");
				queryBuilder.append(DBTest.formatString(major));
				queryBuilder.append("\'");
			}
			
			if(location.length()>0)
			{
				queryBuilder.append(", \'");
				queryBuilder.append(DBTest.formatString(location));
				queryBuilder.append("\'");
			}
			
			queryBuilder.append(")");
			statement.executeUpdate(queryBuilder.toString());
			return true;
		} catch(SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public void queryRecord(DefaultTableModel model, String query)
	{
		try {
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(query);
			while(resultSet.next())
			{
				Vector<String> rowData=new Vector<>();
				String user_id=Integer.toString(resultSet.getInt("user_id"));
				String book_id=Integer.toString(resultSet.getInt("book.book_id"));
		        String name=resultSet.getString("book.name");
		        String start_time=resultSet.getString("start_time");
		        String end_time=resultSet.getString("end_time");
		        String deadline=resultSet.getString("deadline");
		        rowData.add(user_id);
		        rowData.add(book_id);
		        rowData.add(name);
		        rowData.add(start_time);
		        rowData.add(end_time);
		        rowData.add(deadline);
		        
		        model.addRow(rowData);
			}
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "查询失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getID()
	{
		return Integer.toString(user_id);
	}
	
	public int is_admin()
	{
		try {
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT is_admin FROM local_user");
			while(resultSet.next())
				return resultSet.getInt("is_admin");
			return 0;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "查询账户状态失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
			return 0;
		}
	}
	
	public double getDebt()
	{
		try {
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT debt FROM local_user");
			while(resultSet.next())
				return resultSet.getDouble("debt");
			return 0;
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "查询账户状态失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
			return 0;
		}
	}
	
	public boolean getUserInfo(String[] res, String id, String name)
	{
		if(id.length()==0&&name.length()==0)
			return false;
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("SELECT * FROM user WHERE");
			
			if(id.length()>0)
			{
				queryBuilder.append(" user_id=");
				queryBuilder.append(id);
			}
			
			if(name.length()>0)
			{
				queryBuilder.append(" name=\'");
				queryBuilder.append(DBTest.formatString(name));
				queryBuilder.append("\'");
			}
			
			ResultSet resultSet=statement.executeQuery(queryBuilder.toString());
			
			while(resultSet.next())
			{
				res[1]=Integer.toString(resultSet.getInt("user_id"));
				res[2]=resultSet.getString("name");
				res[3]=resultSet.getInt("is_admin")==1?"管理员":"普通用户";
				res[4]=Double.toString(resultSet.getDouble("debt"));
				return true;
			}
			
			return false;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean clearDebt(String id)
	{
		try {
			Statement statement=connection.createStatement();
			StringBuilder queryBuilder=new StringBuilder("UPDATE user SET debt=0 WHERE user_id=");
			queryBuilder.append(id);
			statement.executeUpdate(queryBuilder.toString());
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}










