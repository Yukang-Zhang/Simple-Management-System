package test;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

class Test
{
	public static void test()
	{
		try {
			//Class.forName("com.mysql.jdbc.Driver");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123456");
			System.out.println("Connected");
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("Select * from user");
			while(resultSet.next())
			{
				System.out.print(resultSet.getInt("id"));
				System.out.println(' '+resultSet.getString("name"));
			}
			
			connection.close();
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	}
}




public class DBTest
{
	static User localUser=new User();
	public static String formatString(String str)
	{
		StringBuilder newStr=new StringBuilder();
		for(int i=0;i<str.length();i++)
		{
			char c=str.charAt(i);
			if(c=='\''||c=='\"'||c=='\\')
				newStr.append('\\');
			newStr.append(c);
		}
		
		return newStr.toString();
	}
	
	private static void setGUI()
	{
		//设置主窗口
		JFrame jFrame=new JFrame("图书管理系统（未登录）");
		jFrame.setVisible(true);
		jFrame.setBackground(Color.RED);
		jFrame.setBounds(400, 300, 600, 300);
		jFrame.setLayout(null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//主窗口的菜单栏
		JMenuBar jMenuBar=new JMenuBar();
		
		//登录
		JMenu loginMenu=new JMenu("登录");
		JMenuItem loginItem=new JMenuItem("登录");
        JMenuItem regItem=new JMenuItem("注册");
        JMenuItem exitItem=new JMenuItem("退出登录");
        loginMenu.add(loginItem);
        loginMenu.add(regItem);
        loginMenu.addSeparator();
        loginMenu.add(exitItem);
        jMenuBar.add(loginMenu);
        
        //设置登录选项的功能
        loginItem.addActionListener(e->{
        	JFrame jFrameLogin=new JFrame("输入用户信息");
        	jFrameLogin.setLayout(null);
        	jFrameLogin.setBounds(350, 350, 400, 100);
        	JLabel jLabelLogin1=new JLabel("用户名");
        	jLabelLogin1.setBounds(20, 10, 50, 20);
        	JTextField jTextFieldUsername1=new JTextField();
        	jTextFieldUsername1.setBounds(75, 10, 100, 20);
        	JLabel jLabelLogin2=new JLabel("密码");
        	jLabelLogin2.setBounds(20, 32, 50, 20);
        	JTextField jTextFieldUsername2=new JTextField();
        	jTextFieldUsername2.setBounds(75, 32, 100, 20);
        	jFrameLogin.add(jLabelLogin1);
        	jFrameLogin.add(jTextFieldUsername1);
        	jFrameLogin.add(jLabelLogin2);
        	jFrameLogin.add(jTextFieldUsername2);
        	
        	JButton jButtonLogin=new JButton("登录");
        	JButton jButtonCancel=new JButton("取消");
        	jButtonLogin.setBounds(180, 10, 90, 42);
        	jButtonCancel.setBounds(275, 10, 90, 42);
        	jFrameLogin.add(jButtonLogin);
        	jFrameLogin.add(jButtonCancel);
        	jFrameLogin.setVisible(true);
        	
        	jButtonLogin.addActionListener(e1->{
        		String username=jTextFieldUsername1.getText();
        		String password=jTextFieldUsername2.getText();
        		if(localUser.login(username, password))
	        	{
	        		JOptionPane.showMessageDialog(null, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
	        		jFrame.setTitle("图书管理系统（已登录）");
	        		jFrameLogin.setVisible(false);
	        	}
        		
        		else
        			JOptionPane.showMessageDialog(null, "登录失败：用户名或密码错误", "错误", JOptionPane.INFORMATION_MESSAGE);
        	});
        	
        	jButtonCancel.addActionListener(e1->{
        		jFrameLogin.setVisible(false);
        	});
        });
        
      //设置注册选项的功能
        regItem.addActionListener(e->{
        	JFrame jFrameLogin=new JFrame("输入用户信息");
        	jFrameLogin.setLayout(null);
        	jFrameLogin.setBounds(350, 350, 400, 100);
        	JLabel jLabelLogin1=new JLabel("用户名");
        	jLabelLogin1.setBounds(20, 10, 50, 20);
        	JTextField jTextFieldUsername1=new JTextField();
        	jTextFieldUsername1.setBounds(75, 10, 100, 20);
        	JLabel jLabelLogin2=new JLabel("密码");
        	jLabelLogin2.setBounds(20, 32, 50, 20);
        	JTextField jTextFieldUsername2=new JTextField();
        	jTextFieldUsername2.setBounds(75, 32, 100, 20);
        	jFrameLogin.add(jLabelLogin1);
        	jFrameLogin.add(jTextFieldUsername1);
        	jFrameLogin.add(jLabelLogin2);
        	jFrameLogin.add(jTextFieldUsername2);
        	
        	JButton jButtonLogin=new JButton("注册");
        	JButton jButtonCancel=new JButton("取消");
        	jButtonLogin.setBounds(180, 10, 90, 42);
        	jButtonCancel.setBounds(275, 10, 90, 42);
        	jFrameLogin.add(jButtonLogin);
        	jFrameLogin.add(jButtonCancel);
        	jFrameLogin.setVisible(true);
        	
        	jButtonLogin.addActionListener(e1->{
        		String username=jTextFieldUsername1.getText();
        		String password=jTextFieldUsername2.getText();
        		if(localUser.register(username, password))
	        	{
	        		JOptionPane.showMessageDialog(null, "注册成功", "提示", JOptionPane.INFORMATION_MESSAGE);
	        		jFrame.setTitle("图书管理系统（已登录）");
	        		jFrameLogin.setVisible(false);
	        	}
        		
        		else
        			JOptionPane.showMessageDialog(null, "注册失败：访问数据库出错", "错误", JOptionPane.INFORMATION_MESSAGE);
        	});
        	
        	jButtonCancel.addActionListener(e1->{
        		jFrameLogin.setVisible(false);
        	});
        });
        
        //帮助
        JMenu helpMenu=new JMenu("帮助");
        JMenuItem helpItem=new JMenuItem("帮助");
        JMenuItem aboutItem=new JMenuItem("关于");
        helpMenu.add(helpItem);
        helpMenu.addSeparator();
        helpMenu.add(aboutItem);
        jMenuBar.add(helpMenu);
        //将主窗口的菜单设为这个
        jFrame.setJMenuBar(jMenuBar);
        
        //创建一组选项卡
        JTabbedPane jTabbedPane=new JTabbedPane();
        JPanel jPanel1=new JPanel();
        JPanel jPanel2=new JPanel();
        JPanel jPanel3=new JPanel();
        JPanel jPanel4=new JPanel();
        //JPanel jPanel5=new JPanel();
        //添加选项卡并设置标题
        jTabbedPane.addTab("浏览和查询", jPanel1);
        jTabbedPane.addTab("借阅信息", jPanel2);
        jTabbedPane.addTab("账户信息", jPanel3);
        jTabbedPane.addTab("后台管理", jPanel4);
        //jTabbedPane.addTab("后台管理（用户）", jPanel5);
        //将选项卡面板添加到窗口中
        jTabbedPane.setBounds(10, 0, 565, 230);
        jFrame.add(jTabbedPane);
        
        //给第一个选项卡（浏览和查询）加功能
        //----------------------------------------------------------------
        jPanel1.setLayout(null);
        JLabel jLabel1_1=new JLabel("书籍类型");
        jLabel1_1.setBounds(0, 0, 50, 20);
        jPanel1.add(jLabel1_1);
        
        JComboBox<String> jComboBoxBookType1=new JComboBox<>();
        jComboBoxBookType1.addItem("全部");
        jComboBoxBookType1.addItem("文学");
        jComboBoxBookType1.addItem("语言");
        jComboBoxBookType1.addItem("理学");
        jComboBoxBookType1.addItem("工学");
        jComboBoxBookType1.addItem("哲学");
        jComboBoxBookType1.addItem("艺术");
        jComboBoxBookType1.addItem("历史");
        jComboBoxBookType1.addItem("其他");
        jComboBoxBookType1.setBounds(55, 0, 60, 20);
        jPanel1.add(jComboBoxBookType1);
        
        JLabel jLabel1_2=new JLabel("馆藏位置");
        jLabel1_2.setBounds(120, 0, 50, 20);
        jPanel1.add(jLabel1_2);
        
        JComboBox<String> jComboBoxLocation1=new JComboBox<>();
        jComboBoxLocation1.addItem("全部");
        jComboBoxLocation1.addItem("江安图书馆");
        jComboBoxLocation1.addItem("望江图书馆");
        jComboBoxLocation1.addItem("华西图书馆");
        jComboBoxLocation1.addItem("其他");
        jComboBoxLocation1.setBounds(175, 0, 80, 20);
        jPanel1.add(jComboBoxLocation1);
        
        JLabel jLabel1_3=new JLabel("被借阅次数");
        jLabel1_3.setBounds(260, 0, 70, 20);
        jPanel1.add(jLabel1_3);
        
        JComboBox<String> jComboBoxBorrowedTimes1=new JComboBox<>();
        jComboBoxBorrowedTimes1.addItem(">=0");
        jComboBoxBorrowedTimes1.addItem(">=5");
        jComboBoxBorrowedTimes1.addItem(">=10");
        jComboBoxBorrowedTimes1.addItem(">=50");
        jComboBoxBorrowedTimes1.addItem(">=100");
        jComboBoxBorrowedTimes1.addItem(">=500");
        jComboBoxBorrowedTimes1.addItem(">=1000");
        jComboBoxBorrowedTimes1.setBounds(330, 0, 60, 20);
        jPanel1.add(jComboBoxBorrowedTimes1);
        
        JLabel jLabel1_4=new JLabel("评分");
        jLabel1_4.setBounds(400, 0, 50, 20);
        jPanel1.add(jLabel1_4);
        
        JComboBox<String> jComboBoxScore1=new JComboBox<>();
        jComboBoxScore1.addItem(">=0");
        jComboBoxScore1.addItem(">=20");
        jComboBoxScore1.addItem(">=40");
        jComboBoxScore1.addItem(">=60");
        jComboBoxScore1.addItem(">=80");
        jComboBoxScore1.addItem(">=100");
        jComboBoxScore1.setBounds(430, 0, 50, 20);
        jPanel1.add(jComboBoxScore1);
        
        JLabel jLabel1_5=new JLabel("图书名");
        jLabel1_5.setBounds(10, 30, 50, 20);
        jPanel1.add(jLabel1_5);
        
        JTextField jTextFieldBookName1=new JTextField("");
        jTextFieldBookName1.setBounds(65, 30, 100, 20);
        jPanel1.add(jTextFieldBookName1);
        
        JCheckBox jCheckBoxBookName1=new JCheckBox("模糊查询");
        jCheckBoxBookName1.setBounds(170, 30, 80, 20);
        jPanel1.add(jCheckBoxBookName1);
        
        JLabel jLabel1_6=new JLabel("作者名");
        jLabel1_6.setBounds(10, 60, 50, 20);
        jPanel1.add(jLabel1_6);
        
        JTextField jTextFieldAuthorName1=new JTextField("");
        jTextFieldAuthorName1.setBounds(65, 60, 100, 20);
        jPanel1.add(jTextFieldAuthorName1);
        
        JCheckBox jCheckBoxAuthorName1=new JCheckBox("模糊查询");
        jCheckBoxAuthorName1.setBounds(170, 60, 80, 20);
        jPanel1.add(jCheckBoxAuthorName1);
        
        JLabel jLabel1_7=new JLabel("出版社名");
        jLabel1_7.setBounds(10, 90, 50, 20);
        jPanel1.add(jLabel1_7);
        
        JTextField jTextFieldPressName1=new JTextField("");
        jTextFieldPressName1.setBounds(65, 90, 100, 20);
        jPanel1.add(jTextFieldPressName1);
        
        JCheckBox jCheckBoxPressName1=new JCheckBox("模糊查询");
        jCheckBoxPressName1.setBounds(170, 90, 80, 20);
        jPanel1.add(jCheckBoxPressName1);
        
        JCheckBox jCheckBoxInStock1=new JCheckBox("只看在馆图书");
        jCheckBoxInStock1.setBounds(250, 27, 200, 20);
        jPanel1.add(jCheckBoxInStock1);
        
        JCheckBox jCheckBoxSameMajor1=new JCheckBox("只看本专业同学借阅过的书");
        jCheckBoxSameMajor1.setBounds(250, 48, 200, 20);
        jPanel1.add(jCheckBoxSameMajor1);
        
        JCheckBox jCheckBoxBorrowed1=new JCheckBox("只看自己借阅过的书");
        jCheckBoxBorrowed1.setBounds(250, 69, 200, 20);
        jPanel1.add(jCheckBoxBorrowed1);
        
        JCheckBox jCheckBoxNotBorrowed1=new JCheckBox("只看自己没借过的书");
        jCheckBoxNotBorrowed1.setBounds(250, 90, 200, 20);
        jPanel1.add(jCheckBoxNotBorrowed1);
        
        JButton jButtonQuery=new JButton("查询");
        jButtonQuery.setBounds(10, 120, 400, 30);
        jPanel1.add(jButtonQuery);
        
        //给查询按钮添加事件监听
        jButtonQuery.addActionListener(e->{
        	
        	//localUser.login("root", "123456");
        	
        	try {
        		if(!localUser.ifOnline())
        		{
        			JOptionPane.showMessageDialog(null, "查询失败：未登录用户没有查询权限", "错误", JOptionPane.INFORMATION_MESSAGE);
        			return;
        		}
        		
        		JFrame resultJFrame=new JFrame("查询结果");
        		resultJFrame.setVisible(true);
        		resultJFrame.setLayout(null);
        		resultJFrame.setBounds(400, 200, 600, 600);
        		
    			StringBuilder queryBuilder=new StringBuilder("SELECT * FROM book WHERE 1=1");
    			//如果图书名一栏不为空，增加查找条件
    			if(jTextFieldBookName1.getText().length()>0)
    			{
    				queryBuilder.append(" AND name");
    				if(jCheckBoxBookName1.isSelected()) //启用模糊查询
    				{
    					queryBuilder.append(" LIKE \'");
    					queryBuilder.append(formatString(jTextFieldBookName1.getText()));
    					queryBuilder.append("\'");
    				}
    				
    				else
    				{
    					queryBuilder.append("=\'");
    					queryBuilder.append(formatString(jTextFieldBookName1.getText()));
    					queryBuilder.append("\'");
    				}
    			}
    			
    			//如果作者名一栏不为空，增加查找条件
    			if(jTextFieldAuthorName1.getText().length()>0)
    			{
    				queryBuilder.append(" AND author");
    				if(jCheckBoxAuthorName1.isSelected()) //启用模糊查询
    				{
    					queryBuilder.append(" LIKE \'");
    					queryBuilder.append(formatString(jTextFieldAuthorName1.getText()));
    					queryBuilder.append("\'");
    				}
    				
    				else
    				{
    					queryBuilder.append("=\'");
    					queryBuilder.append(formatString(jTextFieldAuthorName1.getText()));
    					queryBuilder.append("\'");
    				}
    			}
    			
    			//如果出版社名一栏不为空，增加查找条件
    			if(jTextFieldPressName1.getText().length()>0)
    			{
    				queryBuilder.append(" AND press");
    				if(jCheckBoxPressName1.isSelected()) //启用模糊查询
    				{
    					queryBuilder.append(" LIKE \'");
    					queryBuilder.append(formatString(jTextFieldPressName1.getText()));
    					queryBuilder.append("\'");
    				}
    				
    				else
    				{
    					queryBuilder.append("=\'");
    					queryBuilder.append(formatString(jTextFieldPressName1.getText()));
    					queryBuilder.append("\'");
    				}
    			}
    			
    			//书籍类型
    			String majorOption=(String)jComboBoxBookType1.getSelectedItem();
    			if(majorOption!="全部")
    			{
    				queryBuilder.append(" AND major=\'");
    				queryBuilder.append(majorOption);
    				queryBuilder.append("\'");
    			}
    			
    			//馆藏位置
    			String locationOption=(String)jComboBoxLocation1.getSelectedItem();
    			if(locationOption!="全部")
    			{
    				queryBuilder.append(" AND location=\'");
    				queryBuilder.append(locationOption);
    				queryBuilder.append("\'");
    			}
    			
    			
    			//只看在馆图书
    			if(jCheckBoxInStock1.isSelected())
    				queryBuilder.append(" AND book_id IN (SELECT book_id FROM at_lib)");
    			//只看自己借阅过的书
    			if(jCheckBoxBorrowed1.isSelected())
    				queryBuilder.append(" AND book_id IN (SELECT book_id FROM local_record)");
    			//只看自己没借过的书
    			if(jCheckBoxNotBorrowed1.isSelected())
    				queryBuilder.append(" AND book_id NOT IN (SELECT book_id FROM local_record)");
    			
    			DefaultTableModel model=new DefaultTableModel();
    			model.addColumn("ID");
    	        model.addColumn("书名");
    	        model.addColumn("作者");
    	        model.addColumn("出版社");
    	        model.addColumn("专业");
    			String query=queryBuilder.toString();
    			
    			localUser.queryBook(model, query);
    			
    			
    			JTable table=new JTable(model);
    	        table.getColumnModel().getColumn(0).setPreferredWidth(40);
    	        table.getColumnModel().getColumn(1).setPreferredWidth(200);
    	        table.getColumnModel().getColumn(2).setPreferredWidth(150);
    	        table.getColumnModel().getColumn(3).setPreferredWidth(150);
    	        table.getColumnModel().getColumn(4).setPreferredWidth(47);
    	        JScrollPane scrollPane=new JScrollPane(table);
    	        scrollPane.setBounds(0, 20, 587, 500);
    	        resultJFrame.add(scrollPane);
    	        JButton borrowBookResult1=new JButton("借阅选中书籍");
    	        borrowBookResult1.setBounds(0, 0, 200, 20);
    	        resultJFrame.add(borrowBookResult1);
    	        borrowBookResult1.addActionListener(e1->{
    	        	int[] selectedRows=table.getSelectedRows();
    	        	int numRows=selectedRows.length;
    	        	
    	        	boolean allSuccess=true, allFailure=true;
    	        	for(int i=0;i<numRows;i++)
    	        		if(localUser.borrowBook(Integer.parseInt((String)model.getValueAt(selectedRows[i], 0))))
    	        			allFailure=false;
    	        		else
    	        			allSuccess=false;
    	        	if(allSuccess)
    	        		JOptionPane.showMessageDialog(null, "借阅成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    	        	else if(allFailure)
    	        		JOptionPane.showMessageDialog(null, "借阅失败", "错误", JOptionPane.INFORMATION_MESSAGE);
    	        	else
    	        		JOptionPane.showMessageDialog(null, "部分书籍借阅成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    	        });
    	        
    	        JButton removeBookResult1=new JButton("将选中书籍从库中移除");
    	        removeBookResult1.setBounds(202, 0, 200, 20);
    	        resultJFrame.add(removeBookResult1);
    	        removeBookResult1.addActionListener(e1->{
    	        	int[] selectedRows=table.getSelectedRows();
    	        	int numRows=selectedRows.length;
    	        	boolean allSuccess=true, allFailure=true;
    	        	for(int i=0;i<numRows;i++)
    	        		if(localUser.removeBook(Integer.parseInt((String)model.getValueAt(selectedRows[i], 0))))
    	        			allFailure=false;
    	        		else
    	        			allSuccess=false;
    	        	if(allSuccess)
    	        		JOptionPane.showMessageDialog(null, "移除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    	        	else if(allFailure)
    	        		JOptionPane.showMessageDialog(null, "移除失败", "错误", JOptionPane.INFORMATION_MESSAGE);
    	        	else
    	        		JOptionPane.showMessageDialog(null, "部分书籍移除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    	        });
    	        
    	        JButton addBookResult1=new JButton("向库中添加书籍");
    	        addBookResult1.setBounds(400, 0, 200, 20);
    	        resultJFrame.add(addBookResult1);
    	        addBookResult1.addActionListener(e1->{
    	        	JFrame jFrameAddBook=new JFrame("输入书籍信息");
    	        	jFrameAddBook.setLayout(null);
    	        	jFrameAddBook.setBounds(300, 300, 300, 200);
    	        	
    	        	JLabel jLabelAddBook1=new JLabel("书名");
    	        	jLabelAddBook1.setBounds(10, 10, 50, 20);
    	        	JLabel jLabelAddBook2=new JLabel("作者");
    	        	jLabelAddBook2.setBounds(10, 32, 50, 20);
    	        	JLabel jLabelAddBook3=new JLabel("出版社");
    	        	jLabelAddBook3.setBounds(10, 54, 50, 20);
    	        	JLabel jLabelAddBook4=new JLabel("类型");
    	        	jLabelAddBook4.setBounds(10, 76, 50, 20);
    	        	JLabel jLabelAddBook5=new JLabel("馆藏位置");
    	        	jLabelAddBook5.setBounds(10, 98, 50, 20);
    	        	
    	        	JTextField jTextFieldAddBook1=new JTextField();
    	        	jTextFieldAddBook1.setBounds(65, 10, 150, 20);
    	        	JTextField jTextFieldAddBook2=new JTextField();
    	        	jTextFieldAddBook2.setBounds(65, 32, 150, 20);
    	        	JTextField jTextFieldAddBook3=new JTextField();
    	        	jTextFieldAddBook3.setBounds(65, 54, 150, 20);
    	        	JTextField jTextFieldAddBook4=new JTextField();
    	        	jTextFieldAddBook4.setBounds(65, 76, 150, 20);
    	        	JTextField jTextFieldAddBook5=new JTextField();
    	        	jTextFieldAddBook5.setBounds(65, 98, 150, 20);
    	        	
    	        	jFrameAddBook.add(jLabelAddBook1);
    	        	jFrameAddBook.add(jLabelAddBook2);
    	        	jFrameAddBook.add(jLabelAddBook3);
    	        	jFrameAddBook.add(jLabelAddBook4);
    	        	jFrameAddBook.add(jLabelAddBook5);
    	        	
    	        	jFrameAddBook.add(jTextFieldAddBook1);
    	        	jFrameAddBook.add(jTextFieldAddBook2);
    	        	jFrameAddBook.add(jTextFieldAddBook3);
    	        	jFrameAddBook.add(jTextFieldAddBook4);
    	        	jFrameAddBook.add(jTextFieldAddBook5);
    	        	
    	        	JButton jButtonAddBook=new JButton("添加书籍");
    	        	jButtonAddBook.setBounds(10, 120, 200, 30);
    	        	jFrameAddBook.add(jButtonAddBook);
    	        	jButtonAddBook.addActionListener(e2->{
    	        		if(localUser.addBook(jTextFieldAddBook1.getText(), jTextFieldAddBook2.getText(), jTextFieldAddBook3.getText(),jTextFieldAddBook4.getText(),jTextFieldAddBook5.getText()))
    	        			JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    	        		else
    	        			JOptionPane.showMessageDialog(null, "添加失败", "错误", JOptionPane.INFORMATION_MESSAGE);
    	        	});
    	        	
    	        	jFrameAddBook.setVisible(true);
    	        	
    	        });
    	        
    		} catch(Exception e1) {
    			e1.printStackTrace();
    		}
        });
        //----------------------------------------------------------------
        
        //给第二个选项卡（借阅信息）加功能
        //----------------------------------------------------------------
        jPanel2.setLayout(null);
        JLabel jLabel2_1=new JLabel("时间限制");
        jLabel2_1.setBounds(20, 15, 50, 20);
        JComboBox<String> jComboBoxTimeLimit=new JComboBox<>();
        jComboBoxTimeLimit.setBounds(75, 15, 80, 20);
        jComboBoxTimeLimit.addItem("全部");
        jComboBoxTimeLimit.addItem("待归还");
        jComboBoxTimeLimit.addItem("即将超期");
        jComboBoxTimeLimit.addItem("已经超期");
        jPanel2.add(jLabel2_1);
        jPanel2.add(jComboBoxTimeLimit);
        
        JLabel jLabel2_2=new JLabel("馆藏位置");
        jLabel2_2.setBounds(20, 37, 50, 20);
        JComboBox<String> jComboBoxLocation=new JComboBox<>();
        jComboBoxLocation.setBounds(75, 37, 80, 20);
        jComboBoxLocation.addItem("全部");
        jComboBoxLocation.addItem("江安图书馆");
        jComboBoxLocation.addItem("望江图书馆");
        jComboBoxLocation.addItem("华西图书馆");
        jPanel2.add(jLabel2_2);
        jPanel2.add(jComboBoxLocation);
        
        JLabel jLabel2_3=new JLabel("书籍类型");
        jLabel2_3.setBounds(20, 59, 50, 20);
        JComboBox<String> jComboBoxMajor=new JComboBox<>();
        jComboBoxMajor.setBounds(75, 59, 80, 20);
        jComboBoxMajor.addItem("全部");
        jComboBoxMajor.addItem("文学");
        jComboBoxMajor.addItem("语言");
        jComboBoxMajor.addItem("理学");
        jComboBoxMajor.addItem("工学");
        jComboBoxMajor.addItem("哲学");
        jComboBoxMajor.addItem("艺术");
        jComboBoxMajor.addItem("历史");
        jComboBoxMajor.addItem("其他");
        jPanel2.add(jLabel2_3);
        jPanel2.add(jComboBoxMajor);
        
        JButton jButton2_1=new JButton("查询待归还书籍信息");
        jButton2_1.setBounds(10, 85, 150, 30);
        jPanel2.add(jButton2_1);
        jButton2_1.addActionListener(e1->{
        	if(!localUser.ifOnline())
    		{
    			JOptionPane.showMessageDialog(null, "查询失败：未登录用户没有查询权限", "错误", JOptionPane.INFORMATION_MESSAGE);
    			return;
    		}
        	
        	JFrame resultJFrame=new JFrame("查询结果");
    		resultJFrame.setVisible(true);
    		resultJFrame.setLayout(null);
    		resultJFrame.setBounds(400, 200, 600, 600);
        	
        	String timeLimit=(String)jComboBoxTimeLimit.getSelectedItem();
        	String location=(String)jComboBoxLocation.getSelectedItem();
        	String major=(String)jComboBoxMajor.getSelectedItem();
        	StringBuilder queryBuilder=new StringBuilder("SELECT user_id, book.book_id, book.name, start_time, end_time, deadline FROM local_record, book WHERE end_time IS NULL AND book.book_id=local_record.book_id");
        	if(timeLimit.equals("待归还"))
        		queryBuilder.append(" and timestampdiff(day, now(), deadline)>=7");
        	else if(timeLimit.equals("即将超期"))
        		queryBuilder.append(" and timestampdiff(day, now(), deadline)<7 and timestampdiff(day, now(), deadline)>0");
        	else if(timeLimit.equals("已经超期"))
        		queryBuilder.append(" and timestampdiff(day, now(), deadline)<=0");
        	if(!location.equals("全部"))
        	{
        		queryBuilder.append(" and location=");
        		queryBuilder.append(location);
        	}
        	
        	if(!major.equals("全部"))
        	{
        		queryBuilder.append(" and major=");
        		queryBuilder.append(major);
        	}
        	
        	DefaultTableModel model=new DefaultTableModel();
			model.addColumn("用户ID");
			model.addColumn("书籍ID");
	        model.addColumn("书名");
	        model.addColumn("借书时间");
	        model.addColumn("还书时间");
	        model.addColumn("归还期限");
			String query=queryBuilder.toString();
			
			localUser.queryRecord(model, query);
			JTable table=new JTable(model);
			
	        table.getColumnModel().getColumn(0).setPreferredWidth(40);
	        table.getColumnModel().getColumn(1).setPreferredWidth(40);
	        table.getColumnModel().getColumn(2).setPreferredWidth(140);
	        table.getColumnModel().getColumn(3).setPreferredWidth(122);
	        table.getColumnModel().getColumn(4).setPreferredWidth(122);
	        table.getColumnModel().getColumn(5).setPreferredWidth(123);
	        
	        JScrollPane scrollPane=new JScrollPane(table);
	        System.out.println(model.getValueAt(0, 0));
	        scrollPane.setBounds(0, 20, 587, 500);
	        resultJFrame.add(scrollPane);
	        
	        JButton borrowBookResult1=new JButton("归还选中书籍");
	        borrowBookResult1.setBounds(0, 0, 200, 20);
	        resultJFrame.add(borrowBookResult1);
	        borrowBookResult1.addActionListener(e->{
	        	int[] selectedRows=table.getSelectedRows();
	        	int numRows=selectedRows.length;
	        	boolean allSuccess=true, allFailure=true;
	        	for(int i=0;i<numRows;i++)
	        		if(localUser.returnBook(Integer.parseInt((String)model.getValueAt(selectedRows[i], 1))))
	        			allFailure=false;
	        		else
	        			allSuccess=false;
	        	if(allSuccess)
	        		JOptionPane.showMessageDialog(null, "归还成功", "提示", JOptionPane.INFORMATION_MESSAGE);
	        	else if(allFailure)
	        		JOptionPane.showMessageDialog(null, "归还失败", "错误", JOptionPane.INFORMATION_MESSAGE);
	        	else
	        		JOptionPane.showMessageDialog(null, "部分书籍归还成功", "提示", JOptionPane.INFORMATION_MESSAGE);
	        });
        });
        
        
        JLabel jLabel2_4=new JLabel("借阅状态");
        jLabel2_4.setBounds(170, 15, 50, 20);
        JComboBox<String> jComboBoxTimeLimit1=new JComboBox<>();
        jComboBoxTimeLimit1.setBounds(225, 15, 80, 20);
        jComboBoxTimeLimit1.addItem("全部");
        jComboBoxTimeLimit1.addItem("待归还");
        jComboBoxTimeLimit1.addItem("已归还");
        jPanel2.add(jLabel2_4);
        jPanel2.add(jComboBoxTimeLimit1);
        
        JLabel jLabel2_5=new JLabel("馆藏位置");
        jLabel2_5.setBounds(170, 37, 50, 20);
        JComboBox<String> jComboBoxLocation2=new JComboBox<>();
        jComboBoxLocation2.setBounds(225, 37, 80, 20);
        jComboBoxLocation2.addItem("全部");
        jComboBoxLocation2.addItem("江安图书馆");
        jComboBoxLocation2.addItem("望江图书馆");
        jComboBoxLocation2.addItem("华西图书馆");
        jPanel2.add(jLabel2_5);
        jPanel2.add(jComboBoxLocation2);
        
        JLabel jLabel2_6=new JLabel("书籍类型");
        jLabel2_6.setBounds(170, 59, 50, 20);
        JComboBox<String> jComboBoxMajor1=new JComboBox<>();
        jComboBoxMajor1.setBounds(225, 59, 80, 20);
        jComboBoxMajor1.addItem("全部");
        jComboBoxMajor1.addItem("文学");
        jComboBoxMajor1.addItem("语言");
        jComboBoxMajor1.addItem("理学");
        jComboBoxMajor1.addItem("工学");
        jComboBoxMajor1.addItem("哲学");
        jComboBoxMajor1.addItem("艺术");
        jComboBoxMajor1.addItem("历史");
        jComboBoxMajor1.addItem("其他");
        jPanel2.add(jLabel2_6);
        jPanel2.add(jComboBoxMajor1);
        
        JButton jButton2_2=new JButton("查询全部借阅信息");
        jButton2_2.setBounds(160, 85, 150, 30);
        jPanel2.add(jButton2_2);
        jButton2_2.addActionListener(e1->{
        	if(!localUser.ifOnline())
    		{
    			JOptionPane.showMessageDialog(null, "查询失败：未登录用户没有查询权限", "错误", JOptionPane.INFORMATION_MESSAGE);
    			return;
    		}
        	
        	JFrame resultJFrame=new JFrame("查询结果");
    		resultJFrame.setVisible(true);
    		resultJFrame.setLayout(null);
    		resultJFrame.setBounds(400, 200, 600, 600);
        	
        	String timeLimit=(String)jComboBoxTimeLimit.getSelectedItem();
        	String location=(String)jComboBoxLocation.getSelectedItem();
        	String major=(String)jComboBoxMajor.getSelectedItem();
        	StringBuilder queryBuilder=new StringBuilder("SELECT user_id, book.book_id, book.name, start_time, end_time, deadline FROM local_record, book WHERE book.book_id=local_record.book_id");
        	if(timeLimit.equals("待归还"))
        		queryBuilder.append(" and end_time IS NULL");
        	else if(timeLimit.equals("已归还"))
        		queryBuilder.append(" and end_time IS NOT NULL");
        	if(!location.equals("全部"))
        	{
        		queryBuilder.append(" and location=");
        		queryBuilder.append(location);
        	}
        	
        	if(!major.equals("全部"))
        	{
        		queryBuilder.append(" and major=");
        		queryBuilder.append(major);
        	}
        	
        	DefaultTableModel model=new DefaultTableModel();
			model.addColumn("用户ID");
			model.addColumn("书籍ID");
	        model.addColumn("书名");
	        model.addColumn("借书时间");
	        model.addColumn("还书时间");
	        model.addColumn("归还期限");
			String query=queryBuilder.toString();
			
			localUser.queryRecord(model, query);
			JTable table=new JTable(model);
			
	        table.getColumnModel().getColumn(0).setPreferredWidth(40);
	        table.getColumnModel().getColumn(1).setPreferredWidth(40);
	        table.getColumnModel().getColumn(2).setPreferredWidth(140);
	        table.getColumnModel().getColumn(3).setPreferredWidth(122);
	        table.getColumnModel().getColumn(4).setPreferredWidth(122);
	        table.getColumnModel().getColumn(5).setPreferredWidth(123);
	        
	        JScrollPane scrollPane=new JScrollPane(table);
	        System.out.println(model.getValueAt(0, 0));
	        scrollPane.setBounds(0, 20, 587, 500);
	        resultJFrame.add(scrollPane);
        });
        
        //给第三个选项卡（账户信息）加功能
        //----------------------------------------------------------------
        jPanel3.setLayout(new FlowLayout());
        
        JButton jButton3=new JButton("查询当前用户信息");
        jPanel3.add(jButton3);
        jButton3.addActionListener(e1->{
        	if(!localUser.ifOnline())
    		{
    			JOptionPane.showMessageDialog(null, "查询失败：未登录用户没有查询权限", "错误", JOptionPane.INFORMATION_MESSAGE);
    			return;
    		}
        	
        	JFrame jFrame3=new JFrame("查询结果");
        	jFrame3.setBounds(300, 300, 100, 150);
        	jFrame3.setLayout(null);
        	JLabel jLabel3_1=new JLabel("用户名：");
        	JLabel jTextField3_1=new JLabel(localUser.getUsername());
        	JLabel jLabel3_2=new JLabel("ID：");
        	JLabel jTextField3_2=new JLabel(localUser.getID());
        	JLabel jLabel3_3=new JLabel("权限：");
        	JLabel jTextField3_3=new JLabel(localUser.is_admin()==1?"管理员":"普通用户");
        	JLabel jLabel3_4=new JLabel("欠费：");
        	JLabel jTextField3_4=new JLabel(Double.toString(localUser.getDebt()));
        	jLabel3_1.setBounds(10, 10, 50, 20);
        	jTextField3_1.setBounds(65, 10, 50, 20);
        	jLabel3_2.setBounds(10, 35, 50, 20);
        	jTextField3_2.setBounds(65, 35, 50, 20);
        	jLabel3_3.setBounds(10, 60, 50, 20);
        	jTextField3_3.setBounds(65, 60, 50, 20);
        	jLabel3_4.setBounds(10, 85, 50, 20);
        	jTextField3_4.setBounds(65, 85, 50, 20);
        	
        	jFrame3.add(jLabel3_1);
        	jFrame3.add(jTextField3_1);
        	jFrame3.add(jLabel3_2);
        	jFrame3.add(jTextField3_2);
        	jFrame3.add(jLabel3_3);
        	jFrame3.add(jTextField3_3);
        	jFrame3.add(jLabel3_4);
        	jFrame3.add(jTextField3_4);
        	jFrame3.setVisible(true);
        });
        //----------------------------------------------------------------
        
        //给第四个选项卡（后台管理）加功能
        //----------------------------------------------------------------
        jPanel4.setLayout(null);
        
        JLabel jLabel4_1=new JLabel("ID");
        JTextField jTextField4_1=new JTextField();
        jLabel4_1.setBounds(10, 10, 50, 20);
        jTextField4_1.setBounds(65, 10, 70, 20);
        jPanel4.add(jLabel4_1);
        jPanel4.add(jTextField4_1);
        
        JLabel jLabel4_2=new JLabel("用户名");
        JTextField jTextField4_2=new JTextField();
        jLabel4_2.setBounds(10, 32, 50, 20);
        jTextField4_2.setBounds(65, 32, 70, 20);
        jPanel4.add(jLabel4_2);
        jPanel4.add(jTextField4_2);
        
        JButton jButton4_1=new JButton("查询用户信息");
        jButton4_1.setBounds(10, 54, 120, 30);
        jPanel4.add(jButton4_1);
        jButton4_1.addActionListener(e1->{
        	String user_id=jTextField4_1.getText();
        	String name=jTextField4_2.getText();
        	String[] res=new String[5];
        	
        	if(!localUser.getUserInfo(res, user_id, name))
        	{
        		JOptionPane.showMessageDialog(null, "查询账户失败：没有权限或找不到满足条件的账户", "错误", JOptionPane.INFORMATION_MESSAGE);
        		return;
        	}
        	
        	JFrame jFrame3=new JFrame("查询结果");
        	jFrame3.setBounds(300, 300, 250, 150);
        	jFrame3.setLayout(null);
        	JLabel jLabel3_1=new JLabel("用户名：");
        	JLabel jTextField3_1=new JLabel(res[2]);
        	JLabel jLabel3_2=new JLabel("ID：");
        	JLabel jTextField3_2=new JLabel(res[1]);
        	JLabel jLabel3_3=new JLabel("权限：");
        	JLabel jTextField3_3=new JLabel(res[3]);
        	JLabel jLabel3_4=new JLabel("欠费：");
        	JLabel jTextField3_4=new JLabel(res[4]);
        	jLabel3_1.setBounds(10, 10, 50, 20);
        	jTextField3_1.setBounds(65, 10, 50, 20);
        	jLabel3_2.setBounds(10, 35, 50, 20);
        	jTextField3_2.setBounds(65, 35, 50, 20);
        	jLabel3_3.setBounds(10, 60, 50, 20);
        	jTextField3_3.setBounds(65, 60, 50, 20);
        	jLabel3_4.setBounds(10, 85, 50, 20);
        	jTextField3_4.setBounds(65, 85, 50, 20);
        	
        	/*JButton jButton4_3=new JButton("切换");
        	jButton4_3.setBounds(120, 60, 80, 20);
        	jFrame3.add(jButton4_3);*/
        	
        	JButton jButton4_4=new JButton("清空");
        	jButton4_4.setBounds(120, 85, 80, 20);
        	jFrame3.add(jButton4_4);
        	jButton4_4.addActionListener(e2->{
        		if(localUser.clearDebt(res[1]))
        			JOptionPane.showMessageDialog(null, "清除欠款成功", "提示", JOptionPane.INFORMATION_MESSAGE);
        		else
        			JOptionPane.showMessageDialog(null, "清除欠款失败", "错误", JOptionPane.INFORMATION_MESSAGE);
        	});
        	
        	jFrame3.add(jLabel3_1);
        	jFrame3.add(jTextField3_1);
        	jFrame3.add(jLabel3_2);
        	jFrame3.add(jTextField3_2);
        	jFrame3.add(jLabel3_3);
        	jFrame3.add(jTextField3_3);
        	jFrame3.add(jLabel3_4);
        	jFrame3.add(jTextField3_4);
        	jFrame3.setVisible(true);
        });
        
        JLabel jLabel4_3=new JLabel("书籍类型");
        jLabel4_3.setBounds(140, 10, 50, 20);
        JComboBox<String> jComboBox4_3=new JComboBox<>();
        jComboBox4_3.setBounds(195, 10, 70, 20);
        jComboBox4_3.addItem("全部");
        jComboBox4_3.addItem("文学");
        jComboBox4_3.addItem("语言");
        jComboBox4_3.addItem("理学");
        jComboBox4_3.addItem("工学");
        jComboBox4_3.addItem("哲学");
        jComboBox4_3.addItem("艺术");
        jComboBox4_3.addItem("历史");
        jComboBox4_3.addItem("其他");
        jPanel4.add(jLabel4_3);
        jPanel4.add(jComboBox4_3);
        
        JLabel jLabel4_4=new JLabel("馆藏位置");
        jLabel4_4.setBounds(140, 32, 50, 20);
        JComboBox<String> jComboBox4_4=new JComboBox<>();
        jComboBox4_4.setBounds(195, 32, 70, 20);
        jComboBox4_4.addItem("全部");
        jComboBox4_4.addItem("江安图书馆");
        jComboBox4_4.addItem("望江图书馆");
        jComboBox4_4.addItem("华西图书馆");
        jComboBox4_4.addItem("其他");
        jPanel4.add(jLabel4_4);
        jPanel4.add(jComboBox4_4);
        
        JButton jButton4_2=new JButton("查询借阅数据统计");
        jButton4_2.setBounds(140, 54, 140, 30);
        jPanel4.add(jButton4_2);
        jButton4_2.addActionListener(e1->{
        	
        });
        
        jFrame.repaint();
	}
	
	public static void main(String[] args)
	{
		//System.setProperty("file.encoding", "UTF-8");
		setGUI();
	}
}




/*




*/

