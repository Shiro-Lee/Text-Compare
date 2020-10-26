package pages;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bean.Book;
import bean.User;
import dao.BookDao;
import service.UserService;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentPage extends JFrame {

	private JPanel contentPane;
	private User user;
	private JTextField attributeValue;//依据属性attribute对图书进行搜索的attribute值
	private JTable bookTable;//图书馆表格
	private JScrollPane bookScrollPane;//容纳图书表格的滚动面板
	private JPanel bookOperatePanel;//图书馆功能面板
	private DefaultTableModel bookDTM;//图书馆表格模板
	private JButton borrowBook;
	private JButton returnBook;
	private JButton self;//显示已借阅图书按钮
	private JButton library;//显示图书馆按钮
	
	/**
	 * Create the frame.
	 */
	public StudentPage(String number) {
		
		super("学生页面");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon studentIcon = new ImageIcon("src/icon/student.png");//标题标签左侧的图标
		
		user = UserService.findUser(number, true);
		JLabel studenTitle = new JLabel("欢迎您，"+user.getName()+"同学", studentIcon, JLabel.RIGHT);//标题标签
		studenTitle.setHorizontalAlignment(SwingConstants.CENTER);
		studenTitle.setFont(new Font("宋体", Font.PLAIN, 30));
		studenTitle.setBounds(180, 10, 322, 44);
		contentPane.add(studenTitle);
		
		JButton exit = new JButton("退出登录");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WelcomePage().setVisible(true);
			}
		});
		exit.setBounds(40, 10, 90, 23);
		contentPane.add(exit);
		
		
		// ------------------------------------------------图书馆部分------------------------------------------------
		
		library = new JButton("图书馆");	//图书馆按钮，切换到未借阅的图书表格
		library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowBook.setVisible(true);//显示借书按钮
				borrowBook.setEnabled(false);//初始时未选中行，使其无效
				returnBook.setVisible(false);//隐藏还书按钮
				showBooks("all", null, "borrowable", null);//显示当前学生可借阅的图书
				library.setEnabled(false);//使图书馆按钮不可用
				self.setEnabled(true);//使已借阅按钮可用
			}
		});
		library.setBounds(40, 62, 90, 23);
		library.setEnabled(false);
		contentPane.add(library);
		
		bookOperatePanel = new JPanel();	//添加图书、删除图书、查找图书功能面板
		bookOperatePanel.setBounds(230, 40, 410, 45);
		contentPane.add(bookOperatePanel);
		bookOperatePanel.setLayout(null);
		
		attributeValue = new JTextField();//查找依据的属性值输入框
		attributeValue.setBounds(268, 23, 80, 21);
		bookOperatePanel.add(attributeValue);
		attributeValue.setColumns(10);
		
		JComboBox<String> categorySelect = new JComboBox<String>();//选择类别
		categorySelect.setBounds(268, 23, 80, 21);
		bookOperatePanel.add(categorySelect);
		String[] categories = {"哲学类","社会科学类","政治类","法律类","军事类",
				"文学类","计算机类","艺术类","生物学类","医学类","天文学类","历史类"};
		for(String item: categories)
			categorySelect.addItem(item);
		categorySelect.setVisible(false);
		
		JComboBox<String> selectAttributes = new JComboBox<String>();//选择查找依据的属性
		selectAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectAttributes.getSelectedIndex()==4) {//若选择查找属性为类别，则将属性值输入框换为类别下拉框
					attributeValue.setVisible(false);
					categorySelect.setVisible(true);
				}
				else {
					attributeValue.setVisible(true);
					categorySelect.setVisible(false);
				}
				attributeValue.setText("");
			}
		});
		selectAttributes.setBounds(188, 22, 75, 23);
		bookOperatePanel.add(selectAttributes);
		selectAttributes.addItem("所有");//对应JComboBox下标0
		String []attributes = {"书名","作者","出版社","类别"};//中文属性，用于界面选择，对应JComboBox下标1-4
		for(String attribute: attributes)
			selectAttributes.addItem(attribute);
		String []attributes_eng = {"all","name","author","press","category"};//英文属性，用于后台查找，对应JComboBox下标0-4
		
		bookDTM = new DefaultTableModel(){//图书表格模板，通过重写isCellEditable方法使其不可编辑
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		bookDTM.setColumnIdentifiers(attributes);//设置表格列名
		bookTable = new JTable(bookDTM);	
		bookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selected = bookTable.getSelectedRow();//获取选中行下标值
				if(selected>=0 && selected<=bookTable.getRowCount()) {
					borrowBook.setEnabled(true);
					returnBook.setEnabled(true);
				}
				else {
					borrowBook.setEnabled(false);
					returnBook.setEnabled(false);
				}
			}
		});
		bookScrollPane = new JScrollPane(bookTable);//容纳表格的滚动面板
		bookScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(bookScrollPane);
		showBooks("all", null, "borrowable", null);//显示当前学生可借阅的图书
		
		JButton searchBook = new JButton("查找");//确认查找按钮
		searchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnBook.setEnabled(false);
				borrowBook.setEnabled(false);
				int attributeIndex = selectAttributes.getSelectedIndex();//获取查找依据的属性名
				String attribute = attributes_eng[attributeIndex];
				String value;
				if(attributeIndex==4)//获取获取查找依据的属性值，若为书的类别则从categorySelect处获取值
					value = (String)categorySelect.getSelectedItem();
				else
					value = attributeValue.getText();
				if(value.equals("")&&selectAttributes.getSelectedIndex()!=0) {
					JOptionPane.showMessageDialog(null,"请输入查找依据值");
					return;
				}
				String mode = (library.isEnabled()?"borrowed":"borrowable");//若当前显示的是图书馆表格，则列出可借阅的图书，否则列出已借阅的图书
				showBooks(attribute, value, mode, number);
			}
		});
		searchBook.setBounds(350, 22, 60, 23);
		bookOperatePanel.add(searchBook);
		
		borrowBook = new JButton("借书");
		borrowBook.setEnabled(false);
		borrowBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//获取选中行下标值
				String name = (String)bookTable.getValueAt(selected, 0);//获取书名
				int choice = JOptionPane.showConfirmDialog(null, "确定要借阅《" + name + "》吗？","借阅图书",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					UserService.borrowBook(number, name);
					JOptionPane.showMessageDialog(null,"借阅成功");
					showBooks("all", null, "borrowable", null);//显示当前学生可借阅的图书
				}
			}
		});
		borrowBook.setBounds(82, 22, 97, 23);
		borrowBook.setEnabled(false);
		bookOperatePanel.add(borrowBook);
		
		
		// -----------------------------------------------个人已借阅部分------------------------------------------------
		
		self = new JButton("我的借阅");
		self.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				borrowBook.setVisible(false);//隐藏借书按钮
				returnBook.setVisible(true);//显示还书按钮
				returnBook.setEnabled(false);//初始时未选中行，使其无效
				bookDTM.setRowCount(0);//清空表格
				showBooks("all", null, "borrowed", number);//显示当前学生已借阅的图书
				library.setEnabled(true);//使图书馆按钮可用
				self.setEnabled(false);//使已借阅按钮不可用
			}
		});
		self.setBounds(130, 62, 90, 23);
		contentPane.add(self);
		
		returnBook = new JButton("还书");
		returnBook.setEnabled(false);
		returnBook.setBounds(82, 22, 97, 23);
		returnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//获取选中行下标值
				String name = (String)bookTable.getValueAt(selected, 0);//获取书名
				int choice = JOptionPane.showConfirmDialog(null, "确定要归还《" + name + "》吗？","归还图书",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					BookDao.returnBook(number, name);
					JOptionPane.showMessageDialog(null,"还书成功");
					showBooks("all", null, "borrowed", number);//显示当前学生可借阅的图书
				}
			}
		});
		returnBook.setVisible(false);
		bookOperatePanel.add(returnBook);
		
	}
	
	//依据条件显示图书，attribute:属性，value:属性值，mode:管理员("admin")/可借阅("borrowable")/已借阅(borrowed)，number:学号
	void showBooks(String attribute, String value, String mode, String number) {
		bookDTM.setRowCount(0);//清空表格
		ArrayList<Book> bookResult = UserService.findBooks(attribute, value, mode, number);
		String[] bookItem = new String[4];
		for(int i=0; i<bookResult.size(); i++) {//向表格添加图书数据
			bookItem[0]=bookResult.get(i).getName();
			bookItem[1]=bookResult.get(i).getAuthor();
			bookItem[2]=bookResult.get(i).getPress();
			bookItem[3]=bookResult.get(i).getCategory();
			bookDTM.addRow(bookItem);
		}
	}
	
}
