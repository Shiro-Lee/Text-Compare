package service;

import bean.Book;
import bean.User;
import dao.BookDao;
import dao.UserDao;

import java.util.ArrayList;

public class UserService {
	
    public static boolean regist(String number, String password, String name, String class_name) {
        return UserDao.regist(number, password, name, class_name);
    }

    public static User login(String number, String password,boolean isStudent) {
        return UserDao.login(number, password,isStudent);
    }

    public static boolean borrowBook(String number, String bookName) {
        return BookDao.borrowBook(number, bookName);
    }

    public static boolean returnBook(String number, String bookName) {
        return BookDao.returnBook(number, bookName);
    }


    public static ArrayList<Book> findBooks(String attribute, String value, String mode, String number){
    	return BookDao.findBooks(attribute, value, mode, number);
    }
    
    public static ArrayList<User> getUsers(String character, String value){
    	return UserDao.getUsers(character, value);
    }
    
    public static boolean addBook(String name, String author, String press, String category) {
        return BookDao.addBook(name, author, press, category);
    }

    public static User findUser(String number, boolean isStudent) {
    	return UserDao.findUser(number, isStudent);
    }
    
    public static boolean deleteBook(String bookName) {
        return BookDao.delete(bookName);
    }

}
