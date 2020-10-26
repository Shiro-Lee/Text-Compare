package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import service.UserService;
import bean.User;

public class WelcomePage extends JFrame {
	private JTextField number; // 登录面板用户名，即学号
	private JPasswordField password; // 登录面板密码
	private JRadioButton student; // 登录面板学生单选按钮
	private JRadioButton admin; // 登录面板管理员单选按钮
	private JTextField regist_number; // 注册面板学号
	private JTextField regist_name;// 注册面板姓名
	private JPasswordField regist_password; // 注册面板密码
	private JComboBox<String> selectClass; // 注册面板选择班级下拉框
	private JPanel registPanel; // 登陆面板
	private JPanel loginPanel; // 注册面板
	Font font = new Font("宋体", Font.PLAIN, 12);// 字体

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomePage frame = new WelcomePage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomePage() {

		super("欢迎来到17级计科班级图书管理系统");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 150, 450, 320);
		Container c = getContentPane();
		c.setLayout(null);
		ButtonGroup character = new ButtonGroup(); // 单选按钮组
		ImageIcon bookIcon = new ImageIcon("src/icon/book.png"); // 标题图案

		registPanel = new JPanel();
		registPanel.setBounds(45, 60, 350, 220);
		c.add(registPanel);
		registPanel.setLayout(null);

		loginPanel = new JPanel();
		loginPanel.setBounds(45, 60, 350, 210);
		c.add(loginPanel);
		loginPanel.setLayout(null);

		JLabel title = new JLabel("图书管理系统", bookIcon, JLabel.RIGHT); // 标题
		title.setFont(new Font("宋体", Font.PLAIN, 30));
		title.setBounds(85, 22, 240, 44);
		c.add(title);

		// ------------------------------------------------注册面板------------------------------------------------

		JPanel registTextPanel = new JPanel(); // 注册面板的输入框面板
		registTextPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		registTextPanel.setBounds(40, 5, 280, 150);
		registPanel.add(registTextPanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 88, 108, 0, 0 };
		gbl_panel.rowHeights = new int[] { 36, 29, 32, 32, 35, 25, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		registTextPanel.setLayout(gbl_panel);

		JLabel regist_numberLabel = new JLabel("学号："); // 学号标签
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		registTextPanel.add(regist_numberLabel, gbc_lblNewLabel);

		regist_number = new JTextField(); // 学号输入框
		GridBagConstraints gbc_regist_number = new GridBagConstraints();
		gbc_regist_number.insets = new Insets(0, 0, 5, 5);
		gbc_regist_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_number.gridx = 1;
		gbc_regist_number.gridy = 1;
		registTextPanel.add(regist_number, gbc_regist_number);
		regist_number.setColumns(10);

		JLabel regist_nameLabel = new JLabel("姓名："); // 姓名标签
		GridBagConstraints gbc_regist_nameLabel = new GridBagConstraints();
		gbc_regist_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_nameLabel.gridx = 0;
		gbc_regist_nameLabel.gridy = 2;
		registTextPanel.add(regist_nameLabel, gbc_regist_nameLabel);

		regist_name = new JTextField();
		GridBagConstraints gbc_regist_name = new GridBagConstraints(); // 姓名输入框
		gbc_regist_name.insets = new Insets(0, 0, 5, 5);
		gbc_regist_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_name.gridx = 1;
		gbc_regist_name.gridy = 2;
		registTextPanel.add(regist_name, gbc_regist_name);
		regist_name.setColumns(10);

		JLabel regist_classLabel = new JLabel("班级："); // 班级标签
		GridBagConstraints gbc_regist_classLabel = new GridBagConstraints();
		gbc_regist_classLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_classLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_classLabel.gridx = 0;
		gbc_regist_classLabel.gridy = 3;
		registTextPanel.add(regist_classLabel, gbc_regist_classLabel);

		selectClass = new JComboBox<String>(); // 下拉选择班级
		GridBagConstraints gbc_classSelect = new GridBagConstraints();
		gbc_classSelect.insets = new Insets(0, 0, 5, 5);
		gbc_classSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_classSelect.gridx = 1;
		gbc_classSelect.gridy = 3;
		String[] classes = { "师范1班", "计科2班", "计科3班", "计科4班", "计科5班", "网工6班", "网工7班" };
		for (int i = 0; i < 7; i++)
			selectClass.addItem(classes[i]);
		registTextPanel.add(selectClass, gbc_classSelect);

		JLabel regist_passwordLabel = new JLabel("密码："); // 密码标签
		GridBagConstraints gbc_regist_passwordLabel = new GridBagConstraints();
		gbc_regist_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_regist_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_regist_passwordLabel.gridx = 0;
		gbc_regist_passwordLabel.gridy = 4;
		registTextPanel.add(regist_passwordLabel, gbc_regist_passwordLabel);

		regist_password = new JPasswordField(); // 密码输入框
		GridBagConstraints gbc_regist_password = new GridBagConstraints();
		gbc_regist_password.insets = new Insets(0, 0, 5, 5);
		gbc_regist_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_regist_password.gridx = 1;
		gbc_regist_password.gridy = 4;
		registTextPanel.add(regist_password, gbc_regist_password);

		JButton registNow = new JButton("立即注册"); // 立即注册按钮
		registNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registActionPerformed(e);
			}
		});
		registNow.setFont(new Font("宋体", Font.PLAIN, 15));
		registNow.setBounds(120, 160, 120, 30);
		registPanel.add(registNow);

		JLabel returnLogin = new JLabel("返回登录页");
		returnLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				registPanel.setVisible(false);
				loginPanel.setVisible(true);
			}
		});
		returnLogin.setBounds(145, 200, 65, 15);
		returnLogin.setForeground(Color.BLUE);
		registPanel.add(returnLogin);

		registPanel.setVisible(false); // 初始时设为不可见

		// ------------------------------------------------登录面板------------------------------------------------

		JPanel loginTextPanel = new JPanel(); // 登录面板的输入框面板
		loginTextPanel.setBounds(40, 25, 280, 80);
		loginPanel.add(loginTextPanel);
		loginTextPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_loginTextPanel = new GridBagLayout();
		gbl_loginTextPanel.columnWidths = new int[] { 80, 108, 0, 0 };
		gbl_loginTextPanel.rowHeights = new int[] { 36, 0, 38, 22, 0 };
		gbl_loginTextPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_loginTextPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		loginTextPanel.setLayout(gbl_loginTextPanel);

		JLabel numberLabel = new JLabel("学号："); // 学号输入框左侧标签
		GridBagConstraints gbc_numberLabel = new GridBagConstraints();
		gbc_numberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_numberLabel.anchor = GridBagConstraints.EAST;
		gbc_numberLabel.gridx = 0;
		gbc_numberLabel.gridy = 1;
		loginTextPanel.add(numberLabel, gbc_numberLabel);

		number = new JTextField(); // 学号输入框
		GridBagConstraints gbc_number = new GridBagConstraints();
		gbc_number.insets = new Insets(0, 0, 5, 5);
		gbc_number.fill = GridBagConstraints.HORIZONTAL;
		gbc_number.gridx = 1;
		gbc_number.gridy = 1;
		loginTextPanel.add(number, gbc_number);
		number.setColumns(10);

		JLabel passwordLabel = new JLabel("密码："); // 密码输入框左侧标签
		GridBagConstraints gbc_passwordLabel = new GridBagConstraints();
		gbc_passwordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passwordLabel.anchor = GridBagConstraints.EAST;
		gbc_passwordLabel.gridx = 0;
		gbc_passwordLabel.gridy = 2;
		loginTextPanel.add(passwordLabel, gbc_passwordLabel);

		password = new JPasswordField(); // 密码输入框
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.gridx = 1;
		gbc_password.gridy = 2;
		loginTextPanel.add(password, gbc_password);

		JButton regist = new JButton("注册"); // 注册按钮
		regist.setBounds(60, 170, 100, 25);
		loginPanel.add(regist);
		regist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginPanel.setVisible(false);
				registPanel.setVisible(true);
			}
		});

		JButton login = new JButton("登录"); // 登录按钮
		login.setBounds(190, 170, 100, 25);
		loginPanel.add(login);
		login.addActionListener(new ActionListener() { // 验证用户名密码是否正确，然后进行登录
			public void actionPerformed(ActionEvent e) {
				loginActionPerformed(e);
			}
		});

		JPanel characterPanel = new JPanel(); // 单选按钮面板
		characterPanel.setBounds(60, 115, 230, 44);
		loginPanel.add(characterPanel);
		characterPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel characterLabel = new JLabel("用户类型："); // 选择用户类型标签
		characterLabel.setFont(font);
		characterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		characterPanel.add(characterLabel);

		student = new JRadioButton("学生"); // 学生单选按钮
		student.setFont(font);
		student.setSelected(true);
		student.setHorizontalAlignment(SwingConstants.CENTER);
		characterPanel.add(student);
		character.add(student);

		admin = new JRadioButton("管理员"); // 管理员单选按钮
		admin.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) { // 选择管理员类型时使注册按钮不可用（后台指定一个管理员）
				if (e.getStateChange() == ItemEvent.SELECTED)
					regist.setEnabled(false);
				else
					regist.setEnabled(true);
			}
		});
		admin.setFont(font);
		admin.setHorizontalAlignment(SwingConstants.CENTER);
		characterPanel.add(admin);
		character.add(admin);

		JLabel gradeLabel = new JLabel("17级计科");
		gradeLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		gradeLabel.setBounds(190, 10, 70, 15);
		getContentPane().add(gradeLabel);

	}

	// 校验注册面板输入情况
	WelcomePage page = this;
	public void registActionPerformed(ActionEvent e) {
		String regist_number = this.regist_number.getText();
		String regist_password = new String(this.regist_password.getPassword());
		String regist_class = (String) this.selectClass.getSelectedItem();
		String regist_name = this.regist_name.getText();
		if (regist_number.equals("")) {
			JOptionPane.showMessageDialog(null, "学号不能为空");
			return;
		}
		if (regist_name.equals("")) {
			JOptionPane.showMessageDialog(null, "姓名不能为空");
			return;
		}
		if (regist_password.equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}

		// 注册方法：
		boolean flag = UserService.regist(regist_number, regist_password, regist_name, regist_class);
		try {
			if (flag) {
				JOptionPane.showMessageDialog(null, "注册成功");
				registPanel.setVisible(false);
				loginPanel.setVisible(true);
			} else
				JOptionPane.showMessageDialog(null, "用户已存在");
			page.regist_number.setText("");
			page.regist_password.setText("");
			page.regist_name.setText("");
			page.selectClass.setSelectedIndex(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// 校验登录面板输入情况
	public void loginActionPerformed(ActionEvent e) {
		String number = this.number.getText();
		String password = new String(this.password.getPassword());
		if (number.equals("")) {
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			return;
		}
		if (password.equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}

		// 登录方法：
		boolean selectStudent = student.isSelected();
		User user = UserService.login(number, password, selectStudent);
		try {
			if (user != null) {
				if (selectStudent)
					new StudentPage(number).setVisible(true); // 学生登录
				else
					new AdminPage(number).setVisible(true); // 管理员登录
				dispose(); // 释放当前窗口资源
			} else
				JOptionPane.showMessageDialog(null, "用户名或密码错误");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
