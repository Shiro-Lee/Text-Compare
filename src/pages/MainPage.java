package pages;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import methods.*;
import java.awt.Font;

public class MainPage extends JFrame {

	private JPanel contentPane;
	private JScrollPane sp_sentence1;
	private JScrollPane sp_sentence2;
	private JTextPane tp_sentence1;
	private JTextPane tp_sentence2;
	private JButton button;
	private boolean state = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public MainPage() {
		
		super("句子比对");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 150, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel label = new JLabel("句1");
		label.setBounds(50, 30, 60, 15);
		contentPane.add(label);
		
		tp_sentence1 = new JTextPane();	//文本框1
		tp_sentence1.setFont(new Font("Monospaced", Font.PLAIN, 15));
		sp_sentence1 = new JScrollPane(tp_sentence1);
		sp_sentence1.setBounds(50, 50, 390, 100);
		sp_sentence1.setBorder(BorderFactory.createEtchedBorder());
		contentPane.add(sp_sentence1);
		
		JLabel label_1 = new JLabel("句2");
		label_1.setBounds(50, 170, 60, 15);
		contentPane.add(label_1);
		
		tp_sentence2 = new JTextPane();	//文本框2
		tp_sentence2.setFont(new Font("Monospaced", Font.PLAIN, 15));
		sp_sentence2 = new JScrollPane(tp_sentence2);
		sp_sentence2.setBounds(50, 190, 390, 100);
		sp_sentence2.setBorder(BorderFactory.createEtchedBorder());
		contentPane.add(sp_sentence2);
		
		button = new JButton("比对");
		button.setBounds(190, 320, 100, 25);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				state = !state;
				if(state) {	//比对完成
					String str1 = tp_sentence1.getText();
					String str2 = tp_sentence2.getText();
					Map<String, ArrayList<Integer>> map = Methods.compare(str1, str2);
					ArrayList<Integer> arr1 = map.get("res1");
					ArrayList<Integer> arr2 = map.get("res2");
					tp_sentence1.setText("");	//清空文本框内容，准备插入带色字体
					tp_sentence2.setText("");
					showResult(str1, arr1, 1);
					showResult(str2, arr2, 2);
					button.setText("重做");
					tp_sentence1.setEditable(false);
					tp_sentence2.setEditable(false);
				}
				else {	//重新开始
					button.setText("比对");
					tp_sentence1.setEditable(true);
					tp_sentence2.setEditable(true);
					insertDocument(" ", Color.BLACK, 1);
					insertDocument(" ", Color.BLACK, 2);
					tp_sentence1.setText("");
					tp_sentence2.setText("");
				}
			}
		});
		
	}
	
	/*在不同的文本框显示比对结果*/
	public void showResult(String orign, ArrayList<Integer> arr, int pane) {
		for(int i=0, j=arr.size()-1; i<orign.length(); i++) {
			if(j>=0 && i==arr.get(j)) {	//用蓝色标识相同文字
				insertDocument(String.valueOf(orign.charAt(i)), Color.BLUE, pane);
				j--;
			}
			else	//用红色标识不同文字
				insertDocument(String.valueOf(orign.charAt(i)), Color.RED, pane);
		}
	}
	
	/*根据传入的颜色及文字，将文字插入文本域*/
	public void insertDocument(String text, Color textColor, int pane) {
		SimpleAttributeSet set = new SimpleAttributeSet();
		StyleConstants.setForeground(set, textColor);	//设置文字颜色
		JTextPane textPane = pane==1?tp_sentence1:tp_sentence2;
		Document doc = textPane.getStyledDocument();
		try{
			doc.insertString(doc.getLength(), text, set);	//插入文字
		}
		catch (BadLocationException e){
			e.printStackTrace();
		}
	}
	
	
}
