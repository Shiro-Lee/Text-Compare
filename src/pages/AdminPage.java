package pages;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import bean.Book;
import bean.User;
import service.UserService;


public class AdminPage extends JFrame {

	private JPanel contentPane;//主面板
	private JTextField attributeValue;//依据属性attribute对图书进行搜索的attribute值
	private JTable bookTable;//图书表格
	private JTable studentTable;//学生表格
	private JScrollPane bookScrollPane;//容纳图书表格的滚动面板
	private JScrollPane studentScrollPane;//容纳学生表格的滚动面板
	private DefaultTableModel bookDTM;//图书表格模板
	private DefaultTableModel studentDTM;//学生表格模板
	private JButton addBook;//添加图书按钮
	private JButton deleteBook;//删除图书按钮
	private JButton showStudents;//显示学生按钮
	private JButton showBooks;//显示图书按钮
	private JComboBox<String> selectAttributes;//选择图书查找的依据属性复选框
	private JComboBox<String> selectCategory;//选择图书类别复选框
	private JComboBox<String> selectCharacter;//选择学生查找的依据的属性复选框
	private JComboBox<String> selectClass;//选择班级复选框
	private JButton searchBooks;//查找按钮
	private JPanel bookOperatePanel;//图书操作面板
	private JPanel studentOperatePanel;//学生操作面板
	private JButton searchStudent;//查找学生按钮
	private JTextField characterValue;//依据属性character对图书进行搜索的character值
	private JFrame addBookPage;//添加图书子窗口
	
	/**
	 * Create the frame.
	 */
	public AdminPage(String number) {
		
		super("管理员页面");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 150, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon adminIcon = new ImageIcon("src/icon/admin.png");//标题标签左侧的图标
		
		JLabel adminTitle = new JLabel("欢迎您，管理员",adminIcon,JLabel.RIGHT);//标题标签
		adminTitle.setHorizontalAlignment(SwingConstants.CENTER);
		adminTitle.setFont(new Font("宋体", Font.PLAIN, 30));
		adminTitle.setBounds(230, 10, 280, 44);
		contentPane.add(adminTitle);
		
		JButton exit = new JButton("退出登录");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WelcomePage().setVisible(true);
			}
		});
		exit.setBounds(40, 10, 90, 23);
		contentPane.add(exit);
		
		
		// ------------------------------------------------图书部分------------------------------------------------
		
		bookOperatePanel = new JPanel();
		bookOperatePanel.setBounds(230, 35, 410, 50);
		contentPane.add(bookOperatePanel);
		bookOperatePanel.setLayout(null);
		
		addBook = new JButton("添加图书");
		addBook.setBounds(0, 27, 90, 23);
		AdminPage self = this;
		addBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBookPage = new AddBookPage(self);
				addBookPage.setVisible(true);
			}
		});
		bookOperatePanel.add(addBook);
		
		deleteBook = new JButton("删除图书");
		deleteBook.setBounds(90, 27, 90, 23);
		bookOperatePanel.add(deleteBook);
		deleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = bookTable.getSelectedRow();//获取选中行下标值	
				String name = (String)bookTable.getValueAt(selected, 0);//获取书名
				int choice = JOptionPane.showConfirmDialog(null, "确定要删除《" + name + "》吗？","删除图书",JOptionPane.YES_NO_OPTION);
				if (choice==0) {
					UserService.deleteBook(name);
					JOptionPane.showMessageDialog(null,"删除成功");
					showBooks("all", null, "admin", null);
				}
			}
		});
		deleteBook.setEnabled(false);
		
		selectAttributes = new JComboBox<>();
		selectAttributes.setBounds(190, 27, 65, 23);
		selectAttributes.addItem("所有");//对应JComboBox下标0
		String []attributes = {"书名","作者","出版社","类别","借阅者学号"};//中文属性，用于界面选择，对应JComboBox下标1-5
		String []attributes_eng = {"all","name","author","press","category","reader","borrowed","borrowable"};//英文属性，用于后台查找，对应JComboBox下标0-7
		for(String attribute: attributes)
			selectAttributes.addItem(attribute);
		selectAttributes.addItem("已借阅");
		selectAttributes.addItem("未借阅");
		selectAttributes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectAttributes.getSelectedIndex()==4) {//若选择查找属性为类别，则将属性值输入框换为类别下拉框
					attributeValue.setVisible(false);
					selectCategory.setVisible(true);
				}
				else {
					attributeValue.setVisible(true);
					selectCategory.setVisible(false);
				}
			}
		});
		bookOperatePanel.add(selectAttributes);
		
		attributeValue = new JTextField();
		attributeValue.setBounds(258, 27, 90, 21);
		bookOperatePanel.add(attributeValue);
		attributeValue.setColumns(10);
		
		selectCategory = new JComboBox<>();
		selectCategory.setBounds(268, 27, 80, 21);
		bookOperatePanel.add(selectCategory);
		selectCategory.setVisible(false);
		
		showBooks = new JButton("查看图书");	//查看图书按钮，切换到图书部分
		showBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookScrollPane.setVisible(true);
				studentScrollPane.setVisible(false);
				showBooks.setEnabled(false);//使显示图书按钮不可用
				showStudents.setEnabled(true);//使显示学生按钮可用
				bookOperatePanel.setVisible(true);//显示图书操作面板
				studentOperatePanel.setVisible(false);
			}
		});
		showBooks.setBounds(40, 62, 90, 23);
		showBooks.setEnabled(false);
		contentPane.add(showBooks);
		String[] categorys = {"哲学类","社会科学类","政治类","法律类","军事类",
				"文学类","计算机类","艺术类","生物学类","医学类","天文学类","历史类"};
		for(String item: categorys)
			selectCategory.addItem(item);
		
		
		searchBooks = new JButton("查找");
		searchBooks.setBounds(350, 27, 60, 23);
		searchBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBook.setEnabled(false);
				int attributeIndex = selectAttributes.getSelectedIndex();//获取查找依据的属性名
				String attribute = attributes_eng[attributeIndex];
				String value;
				if(attributeIndex==4)//获取获取查找依据的属性值，若为书的类别则从categorySelect处获取值
					value = (String)selectCategory.getSelectedItem();
				else
					value = attributeValue.getText();
				int selectedIndex = selectAttributes.getSelectedIndex();
				if(value.equals("")&&selectedIndex!=0&&selectedIndex!=6&&selectedIndex!=7) {
					JOptionPane.showMessageDialog(null,"请输入查找依据值");
					return;
				}
				showBooks(attribute, value, "admin", null);
			}
		});
		bookOperatePanel.add(searchBooks);
		
		
		bookDTM = new DefaultTableModel(){//图书表格模板，通过重写isCellEditable方法使其不可编辑
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		bookDTM.setColumnIdentifiers(attributes);//设置表格列名
		bookTable = new JTable(bookDTM);	
		bookScrollPane = new JScrollPane(bookTable);//容纳表格的滚动面板
		bookScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(bookScrollPane);
		showBooks("all", null, "admin", null);//初始化表格
		bookTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selected = bookTable.getSelectedRow();//获取选中行下标值
				boolean borrowable = (bookTable.getValueAt(selected, 4))==null;
				if(selected>=0 && selected<=bookTable.getRowCount()&&borrowable)
					deleteBook.setEnabled(true);
				else deleteBook.setEnabled(false);
			}
		});
		bookScrollPane.setVisible(true);
		

		// ------------------------------------------------学生部分------------------------------------------------
		
		showStudents = new JButton("查看学生");	//查看学生按钮，切换到学生部分
		showStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookScrollPane.setVisible(false);
				studentScrollPane.setVisible(true);
				showBooks.setEnabled(true);
				showStudents.setEnabled(false);
				bookOperatePanel.setVisible(false);
				studentOperatePanel.setVisible(true);
			}
		});
		showStudents.setBounds(130, 62, 90, 23);
		contentPane.add(showStudents);
		
		studentOperatePanel = new JPanel();
		studentOperatePanel.setBounds(420, 35, 220, 50);
		contentPane.add(studentOperatePanel);
		studentOperatePanel.setLayout(null);
		
		selectCharacter = new JComboBox<>();
		selectCharacter.setBounds(0, 27, 65, 23);
		selectCharacter.addItem("所有");//对应JComboBox下标0
		String[] characters = {"学号","姓名","班级"};//学生属性，用于界面选择，对应JComboBox下标1-3
		String[] character_eng = {"all","number","name","class_name"};//英文属性，用于后台查找，对应JComboBox下标0-3
		for(int i=0; i<3; i++)
			selectCharacter.addItem(characters[i]);
		selectCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectCharacter.getSelectedIndex()==3) {//若选择查找属性为班级，则将属性值输入框换为班级下拉框
					characterValue.setVisible(false);
					selectClass.setVisible(true);
				}
				else {
					characterValue.setVisible(true);
					selectClass.setVisible(false);
				}
			}
		});
		studentOperatePanel.add(selectCharacter);
		
		selectClass = new JComboBox<>();
		String[] classes = { "师范1班", "计科2班", "计科3班", "计科4班", "计科5班", "网工6班", "网工7班" };
		for (int i = 0; i < 7; i++)
			selectClass.addItem(classes[i]);
		selectClass.setBounds(70, 27, 90, 21);
		selectClass.setVisible(false);
		studentOperatePanel.add(selectClass);
		
		characterValue = new JTextField();
		characterValue.setBounds(68, 27, 90, 21);
		studentOperatePanel.add(characterValue);
		characterValue.setColumns(10);
		
		searchStudent = new JButton("查找");
		searchStudent.setBounds(160, 27, 60, 23);
		searchStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int characterIndex = selectCharacter.getSelectedIndex();//获取查找依据的属性名
				String character = character_eng[characterIndex];
				String value;
				if(characterIndex==3)//获取获取查找依据的属性值，若为班级则从selectClass处获取值
					value = (String)selectClass.getSelectedItem();
				else
					value = characterValue.getText();
				if(value.equals("")&&selectCharacter.getSelectedIndex()!=0) {
					JOptionPane.showMessageDialog(null,"请输入查找依据值");
					return;
				}
				showStudents(character, value);
			}
		});
		studentOperatePanel.add(searchStudent);
		
		studentDTM = new DefaultTableModel(){//学生表格模板，通过重写isCellEditable方法使其不可编辑
            public boolean isCellEditable(int row, int column){
                return false;
            }
		};
		studentScrollPane = new JScrollPane();
		studentScrollPane.setBounds(40, 90, 600, 335);
		contentPane.add(studentScrollPane);
		studentDTM.setColumnIdentifiers(characters);//设置表格列名
		studentTable = new JTable(studentDTM);	
		showStudents("all",null);
		studentScrollPane.setViewportView(studentTable);
		studentScrollPane.setVisible(false);
		
	}
	
	
	//依据条件显示图书，attribute:属性，value:属性值，mode:管理员("admin")/可借阅("borrowable")/已借阅(borrowed)，number:学号
	public void showBooks(String attribute, String value, String mode, String number) {
		bookDTM.setRowCount(0);//清空表格
		ArrayList<Book> bookResult = UserService.findBooks(attribute, value, mode, number);
		String[] bookItem = new String[5];
		for(int i=0; i<bookResult.size(); i++) {//向表格添加图书数据
			bookItem[0]=bookResult.get(i).getName();
			bookItem[1]=bookResult.get(i).getAuthor();
			bookItem[2]=bookResult.get(i).getPress();
			bookItem[3]=bookResult.get(i).getCategory();
			bookItem[4]=bookResult.get(i).getReader();
			bookDTM.addRow(bookItem);
		}
	}
	
	public void showStudents(String character, String value) {
		studentDTM.setRowCount(0);//清空表格
		ArrayList<User> studentResult = UserService.getUsers(character,value);
		String[] studentItem = new String[3];
		for(int i=0; i<studentResult.size(); i++) {//向表格添加学生数据
			studentItem[0]=studentResult.get(i).getNumber();
			studentItem[1]=studentResult.get(i).getName();
			studentItem[2]=studentResult.get(i).getClass_name();
			studentDTM.addRow(studentItem);
		}
	}
}
