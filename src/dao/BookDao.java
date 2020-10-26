package dao;

import bean.Book;
import util.JDBCUtils;

import java.sql.Types;
import java.util.ArrayList;

public class BookDao {

	public static Book findBook(String name) {
		String sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE name = ? ";
		Book book = null;
		JDBCUtils.setStmt(sql);
		JDBCUtils.setObject(1, name, Types.CHAR);
		JDBCUtils.executeQuery();
		if (JDBCUtils.next()) {
			book = new Book(JDBCUtils.getString("name"), 
							JDBCUtils.getString("author"),
							JDBCUtils.getString("press"), 
							JDBCUtils.getString("category"),
							JDBCUtils.getString("reader"));
		}
		JDBCUtils.close();
		return book;
	}

	public static ArrayList<Book> findBooks(String attribute, String value, String mode, String number) { // 查找属性attribute值为value的书，mode指定条件
		String sql = null;
		ArrayList<Book> books = new ArrayList<>();
		Book book = null;
		
		if (mode.equals("admin")) {// 管理员查找图书
			if (attribute.equals("all"))// 找所有
				sql = "SELECT * FROM " + JDBCUtils.table[1];
			else if(attribute.equals("borrowed"))//管理员查找已借出图书
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NOT NULL ";
			else if(attribute.equals("borrowable"))//管理员查找未借出图书
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL ";
			else//按条件
				sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE " + attribute + " = '" + value + "'";
		}
		
		else {
			if (mode.equals("borrowed")) { // 学生查找自己借阅的书（字段reader为NULL）
				if(attribute.equals("all"))//找所有
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader = " + number;
				else //按条件
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader = " + number + " AND " + attribute + " = '" + value + "'";
			}
			if (mode.equals("borrowable")) {// 学生查找可借阅的图书
				if (attribute.equals("all"))//找所有
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL ";
				else //按条件
					sql = "SELECT * FROM " + JDBCUtils.table[1] + " WHERE reader IS NULL AND " + attribute + " = '" + value + "'";
			}
		}
			
			
		JDBCUtils.setStmt(sql);
		JDBCUtils.executeQuery();
		while (JDBCUtils.next()) {
			book = new Book(JDBCUtils.getString("name"), 
							JDBCUtils.getString("author"),
							JDBCUtils.getString("press"), 
							JDBCUtils.getString("category"), 
							JDBCUtils.getString("reader"));
			books.add(book);
		}
		JDBCUtils.close();
		return books;
	}

	public static boolean borrowBook(String number, String bookName) {
		String sql = "UPDATE " + JDBCUtils.table[1] + " SET reader = " + number + " WHERE name = '" + bookName + "'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

	public static boolean returnBook(String number, String bookName) {
		String sql = "UPDATE  " + JDBCUtils.table[1] + " SET reader = null WHERE name = '" + bookName + "'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

	public static boolean addBook(String name, String author, String press, String category) {
		Book book = findBook(name);
		if (book == null) {
			String sql = "INSERT INTO " + JDBCUtils.table[1] + "(name, author, press, category) VALUES (?,?,?,?)";
			JDBCUtils.setStmt(sql);
			JDBCUtils.setObject(1, name, Types.CHAR);
			JDBCUtils.setObject(2, author, Types.CHAR);
			JDBCUtils.setObject(3, press, Types.CHAR);
			JDBCUtils.setObject(4, category, Types.CHAR);
			int result = JDBCUtils.executeUpdate();
			JDBCUtils.close();
			if (result != 0)
				return true;
		}
		return false;
	}

	public static boolean delete(String bookName) {
		String sql = "DELETE FROM " + JDBCUtils.table[1] + " WHERE name = '" + bookName +"'";
		JDBCUtils.setStmt(sql);
		int result = JDBCUtils.executeUpdate();
		JDBCUtils.close();
		if (result != 0)
			return true;
		return false;
	}

}
