package main.java.com.newsaggregator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.btnListener;
import model.txtSearch;

public class MainUI {
	// Tạo JFrame (cửa sổ)
    JFrame frame = new JFrame("OOP PROJECT GROUP 14");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    

    // Tạo JPanel với FlowLayout để thêm nhiều ArPanel nằm ngang
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    

    // Thêm nhiều ArPanel vào JPanel để kích hoạt thanh cuộn ngang
    for (int i = 0; i < 10; i++) {
    	//String Ar = resVt.getAt(i);
    	JPanel ArPanel = new JPanel();
    	ArPanel.setPreferredSize(new Dimension(200, 500)); // Kích thước xác định
        ArPanel.setBackground(Color.WHITE);
        ArPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    	panel.add(ArPanel);
    	ArPanel.setLayout(new BorderLayout());
    	JLabel titleLb = new JLabel("Title");
    	titleLb.setSize(50, 20);
    	ArPanel.add(titleLb, BorderLayout.NORTH);
    	JLabel Date_AuthorLb = new JLabel("Date-Author");
    	Date_AuthorLb.setSize(50, 70);
    	ArPanel.add(Date_AuthorLb, BorderLayout.CENTER);
        JButton button = new JButton("Xem bài viết");
        button.setSize(50, 10);
        ArPanel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		openLink(/*Ar.substring(Ar.indexOf('#'))*/"https://google.com");
			}
        });
    }

    // Tạo JScrollPane chứa JPanel
    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    txtSearch foo = new txtSearch();
    
    // Tạo JButton (nút Search)
    JTextField txtSearch = new JTextField();
	txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
	txtSearch.setColumns(40);
	txtSearch.getDocument().addDocumentListener(null);
	
	ActionListener ac = new btnListener(this);
	
	JButton btnSearch = new JButton("Search");
//	btnSearch.addActionListener(new ActionListener() {
//		
//	});
	
	btnSearch.setBounds(561, 10, 122, 36);
	
	String arrList[] = {"title","author","type","websiteSource","category"};
    list = new JComboBox(arrList);
	list.setBounds(700, 10, 122, 36);
    
	JPanel SPanel = new JPanel();
	SPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	SPanel.add(txtSearch);
    SPanel.add(btnSearch);
    SPanel.add(list);

    // Tạo JPanel chứa nút Search và JScrollPane
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.add(SPanel, BorderLayout.NORTH);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    // Thêm mainPanel vào JFrame
    frame.getContentPane().add(mainPanel);

    // Hiển thị JFrame
    frame.setVisible(true);
}

public static void openLink(String link) {
    try {
        Desktop.getDesktop().browse(new URI(link));
    } catch (Exception e) {
        e.printStackTrace();
    }
}


}
