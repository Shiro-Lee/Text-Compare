package pages;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import service.UserService;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddBookPage extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField author;
	private JTextField press;

	/**
	 * Create the frame.
	 */
	public AddBookPage(AdminPage parentPage) {
		
		setBounds(550, 180, 380, 265);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon addBookIcon = new ImageIcon("src/icon/addbook.png");
		JLabel addBookTitle = new JLabel("添加图书",addBookIcon,JLabel.RIGHT);
		addBookTitle.setFont(new Font("宋体", Font.PLAIN, 30));
		addBookTitle.setBounds(80, 10, 180, 44);
		contentPane.add(addBookTitle);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(30, 55, 310, 130);
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{51, 67, 167, 126, 0};
		gbl_panel.rowHeights = new int[]{15, 0, 0, 0, 24, 8, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel nameLabel = new JLabel("书名：");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.EAST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 1;
		panel.add(nameLabel, gbc_nameLabel);
		
		name = new JTextField();
		GridBagConstraints gbc_name = new GridBagConstraints();
		gbc_name.insets = new Insets(0, 0, 5, 5);
		gbc_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_name.gridx = 2;
		gbc_name.gridy = 1;
		panel.add(name, gbc_name);
		name.setColumns(10);
		
		JLabel authorLabel = new JLabel("作者：");
		authorLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_authorLabel = new GridBagConstraints();
		gbc_authorLabel.anchor = GridBagConstraints.EAST;
		gbc_authorLabel.insets = new Insets(0, 0, 5, 5);
		gbc_authorLabel.gridx = 1;
		gbc_authorLabel.gridy = 2;
		panel.add(authorLabel, gbc_authorLabel);
		
		author = new JTextField();
		GridBagConstraints gbc_author = new GridBagConstraints();
		gbc_author.insets = new Insets(0, 0, 5, 5);
		gbc_author.fill = GridBagConstraints.HORIZONTAL;
		gbc_author.gridx = 2;
		gbc_author.gridy = 2;
		panel.add(author, gbc_author);
		author.setColumns(10);
		
		JLabel pressLabel = new JLabel("出版社：");
		GridBagConstraints gbc_pressLabel = new GridBagConstraints();
		gbc_pressLabel.anchor = GridBagConstraints.EAST;
		gbc_pressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_pressLabel.gridx = 1;
		gbc_pressLabel.gridy = 3;
		panel.add(pressLabel, gbc_pressLabel);
		
		press = new JTextField();
		GridBagConstraints gbc_press = new GridBagConstraints();
		gbc_press.insets = new Insets(0, 0, 5, 5);
		gbc_press.fill = GridBagConstraints.HORIZONTAL;
		gbc_press.gridx = 2;
		gbc_press.gridy = 3;
		panel.add(press, gbc_press);
		press.setColumns(10);
		
		JLabel categoryLabel = new JLabel("类别：");
		GridBagConstraints gbc_categoryLabel = new GridBagConstraints();
		gbc_categoryLabel.anchor = GridBagConstraints.EAST;
		gbc_categoryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_categoryLabel.gridx = 1;
		gbc_categoryLabel.gridy = 4;
		panel.add(categoryLabel, gbc_categoryLabel);
		
		JComboBox<String> category = new JComboBox<String>();
		GridBagConstraints gbc_category = new GridBagConstraints();
		gbc_category.insets = new Insets(0, 0, 5, 5);
		gbc_category.fill = GridBagConstraints.HORIZONTAL;
		gbc_category.gridx = 2;
		gbc_category.gridy = 4;
		panel.add(category, gbc_category);
		String[] categorys = {"哲学类","社会科学类","政治类","法律类","军事类",
				"文学类","计算机类","艺术类","生物学类","医学类","天文学类","历史类"};
		for(String item: categorys)
			category.addItem(item);
		
		JButton confirm = new JButton("确定");
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookName = name.getText();
				String bookAuthor = author.getText();
				String bookPress = press.getText();
				String bookCategory = (String)category.getSelectedItem();
				if(bookName.equals("")) {
					JOptionPane.showMessageDialog(null,"请填写书名");
					return;
				}
				if(bookAuthor.equals("")) {
					JOptionPane.showMessageDialog(null,"请填写作者");
					return;
				}
				if(bookPress.equals("")) {
					JOptionPane.showMessageDialog(null,"请填写出版社");
					return;
				}
				boolean flag = UserService.addBook(bookName, bookAuthor, bookPress, bookCategory);
				if(flag) {
					JOptionPane.showMessageDialog(null,"添加成功");
					name.setText("");
					author.setText("");
					press.setText("");
					category.setSelectedIndex(1);
					parentPage.showBooks("all", null, "admin", null);
				}
				else 
					JOptionPane.showMessageDialog(null,"图书已存在");
			}
		});
		confirm.setBounds(120, 192, 60, 22);
		contentPane.add(confirm);
		
		JButton cancel = new JButton("取消");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancel.setBounds(200, 192, 60, 22);
		contentPane.add(cancel);
		
		JButton reset = new JButton("重置");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				name.setText("");
				author.setText("");
				press.setText("");
				category.setSelectedIndex(0);
			}
		});
		reset.setBounds(280, 30, 60, 22);
		contentPane.add(reset);
	}
}
