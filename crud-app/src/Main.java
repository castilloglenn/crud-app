import java.awt.EventQueue;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.UIManager;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;

public class Main {
	
	private final String APP_NAME = "Product Inventory | Java CRUD | Allen Glenn E. Castillo";
	private ImageIcon icon = new ImageIcon("images/chest.png");
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setTitle(APP_NAME);
		frame.getContentPane().setBackground(new Color(51, 51, 51));
		frame.setIconImage(icon.getImage());
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, frame.getContentPane());
		panel.setBackground(new Color(45, 65, 65));
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel title = new JLabel("PRODUCT INVENTORY");
		sl_panel.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, title, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, title, 30, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, title, -10, SpringLayout.EAST, panel);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setForeground(Color.WHITE);
		panel.add(title);
		
		scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panel);
		scrollPane.setViewportBorder(null);
		panel.add(scrollPane);
		
		table = new JTable(30, 7);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		scrollPane.setViewportView(table);
		
		JPanel buttonsPanel = new JPanel();
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, buttonsPanel);
		sl_panel.putConstraint(SpringLayout.NORTH, buttonsPanel, -40, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, buttonsPanel, -11, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, buttonsPanel, -10, SpringLayout.EAST, panel);
		buttonsPanel.setBackground(new Color(45, 65, 65));
		sl_panel.putConstraint(SpringLayout.WEST, buttonsPanel, 10, SpringLayout.WEST, panel);
		panel.add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton insertButton = new JButton("INSERT PRODUCT");
		insertButton.setBackground(new Color(255, 255, 255));
		insertButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonsPanel.add(insertButton);
		
		JButton updateButton = new JButton("UPDATE PRODUCT");
		updateButton.setBackground(new Color(255, 255, 255));
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonsPanel.add(updateButton);
		
		JButton deleteButton = new JButton("DELETE PRODUCT");
		deleteButton.setBackground(new Color(255, 255, 255));
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonsPanel.add(deleteButton);
		
		JLabel lblNewLabel = new JLabel("SEARCH:");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.SOUTH, title);
		sl_panel.putConstraint(SpringLayout.WEST, lblNewLabel, 10, SpringLayout.WEST, panel);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblNewLabel);
		
		JLabel lblSearchFor = new JLabel("SEARCH FOR:");
		sl_panel.putConstraint(SpringLayout.NORTH, lblSearchFor, 65, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblNewLabel, -10, SpringLayout.NORTH, lblSearchFor);
		sl_panel.putConstraint(SpringLayout.WEST, lblSearchFor, 10, SpringLayout.WEST, panel);
		lblSearchFor.setForeground(Color.WHITE);
		lblSearchFor.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblSearchFor);
		
		JLabel lblOrderBy = new JLabel("ORDER BY:");
		sl_panel.putConstraint(SpringLayout.NORTH, lblOrderBy, 10, SpringLayout.SOUTH, lblSearchFor);
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, lblOrderBy);
		sl_panel.putConstraint(SpringLayout.WEST, lblOrderBy, 10, SpringLayout.WEST, panel);
		lblOrderBy.setForeground(Color.WHITE);
		lblOrderBy.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblOrderBy);
		
		textField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField, -2, SpringLayout.NORTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.WEST, textField, 6, SpringLayout.EAST, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, textField, 2, SpringLayout.SOUTH, lblNewLabel);
		sl_panel.putConstraint(SpringLayout.EAST, textField, -100, SpringLayout.EAST, panel);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("SUBMIT");
		sl_panel.putConstraint(SpringLayout.NORTH, btnNewButton, -1, SpringLayout.NORTH, textField);
		sl_panel.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, textField);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnNewButton, 10, SpringLayout.SOUTH, textField);
		sl_panel.putConstraint(SpringLayout.EAST, btnNewButton, -10, SpringLayout.EAST, panel);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox, -2, SpringLayout.NORTH, lblSearchFor);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox, 6, SpringLayout.EAST, lblSearchFor);
		sl_panel.putConstraint(SpringLayout.SOUTH, comboBox, 2, SpringLayout.SOUTH, lblSearchFor);
		sl_panel.putConstraint(SpringLayout.EAST, comboBox, -100, SpringLayout.EAST, panel);
		panel.add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		sl_panel.putConstraint(SpringLayout.NORTH, comboBox_1, -2, SpringLayout.NORTH, lblOrderBy);
		sl_panel.putConstraint(SpringLayout.WEST, comboBox_1, 6, SpringLayout.EAST, lblOrderBy);
		sl_panel.putConstraint(SpringLayout.SOUTH, comboBox_1, 2, SpringLayout.SOUTH, lblOrderBy);
		sl_panel.putConstraint(SpringLayout.EAST, comboBox_1, -100, SpringLayout.EAST, panel);
		panel.add(comboBox_1);
		
		JList list = new JList();
		sl_panel.putConstraint(SpringLayout.NORTH, list, 4, SpringLayout.SOUTH, btnNewButton);
		sl_panel.putConstraint(SpringLayout.WEST, list, 6, SpringLayout.EAST, comboBox);
		sl_panel.putConstraint(SpringLayout.SOUTH, list, 0, SpringLayout.SOUTH, comboBox_1);
		sl_panel.putConstraint(SpringLayout.EAST, list, -10, SpringLayout.EAST, panel);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"ASCENDING", "DESCENDING"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		panel.add(list);
	}
}
