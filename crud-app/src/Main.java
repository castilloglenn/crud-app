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
import javax.swing.DefaultComboBoxModel;

public class Main {
	
	private final String APP_NAME = "Product Inventory | Java CRUD | Allen Glenn E. Castillo";
	private final String[] COLUMNS = {
		"Product ID", "Category", "Quantity", "Unit", 
		"Product Name", "Purchase Value", "Sell Value"
	};
	
	private ImageIcon icon = new ImageIcon("images/chest.png");
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField searchField;
	
	private Database db;
	private SystemUtility su;

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
		db = new Database();
		su = new SystemUtility(db);
		initialize();
	}

	@SuppressWarnings("serial")
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
			null, COLUMNS
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
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
		
		JLabel searchLabel = new JLabel("SEARCH:");
		sl_panel.putConstraint(SpringLayout.NORTH, searchLabel, 10, SpringLayout.SOUTH, title);
		sl_panel.putConstraint(SpringLayout.WEST, searchLabel, 10, SpringLayout.WEST, panel);
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(searchLabel);
		
		JLabel searchForLabel = new JLabel("SEARCH FOR:");
		sl_panel.putConstraint(SpringLayout.NORTH, searchForLabel, 65, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, searchLabel, -10, SpringLayout.NORTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.WEST, searchForLabel, 10, SpringLayout.WEST, panel);
		searchForLabel.setForeground(Color.WHITE);
		searchForLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(searchForLabel);
		
		JLabel orderByLabel = new JLabel("ORDER BY:");
		sl_panel.putConstraint(SpringLayout.NORTH, orderByLabel, 10, SpringLayout.SOUTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.WEST, orderByLabel, 10, SpringLayout.WEST, panel);
		orderByLabel.setForeground(Color.WHITE);
		orderByLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(orderByLabel);
		
		searchField = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, searchField, -2, SpringLayout.NORTH, searchLabel);
		sl_panel.putConstraint(SpringLayout.WEST, searchField, 6, SpringLayout.EAST, searchLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, searchField, 2, SpringLayout.SOUTH, searchLabel);
		sl_panel.putConstraint(SpringLayout.EAST, searchField, 398, SpringLayout.EAST, searchLabel);
		panel.add(searchField);
		searchField.setColumns(10);
		
		JButton submitButton = new JButton("SUBMIT");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// initially generate a select * command and paste to the JTable
				table.setModel(su.generateTable(db.fetchAll(), COLUMNS));
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, submitButton, 7, SpringLayout.SOUTH, title);
		sl_panel.putConstraint(SpringLayout.WEST, submitButton, 467, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, submitButton, 88, SpringLayout.EAST, searchField);
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(submitButton);
		
		JComboBox<String> categories = new JComboBox<String>();
		categories.setModel(new DefaultComboBoxModel<String>(
				new String[] {
						"-- All Columns --", "Product ID", "Category", 
						"Quantity", "Unit", "Product Name", "Purchase Value", "Sell Value"
				}));
		sl_panel.putConstraint(SpringLayout.NORTH, categories, -2, SpringLayout.NORTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.WEST, categories, 6, SpringLayout.EAST, searchForLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, categories, 2, SpringLayout.SOUTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.EAST, categories, 368, SpringLayout.EAST, searchForLabel);
		panel.add(categories);
		
		JComboBox<String> sortByColumn = new JComboBox<String>();
		sortByColumn.setModel(new DefaultComboBoxModel<String>(
				new String[] {
						"-- Default --", "Product ID", "Category", "Quantity", 
						"Unit", "Product Name", "Purchase Value", "Sell Value"
				}));
		sl_panel.putConstraint(SpringLayout.NORTH, sortByColumn, -2, SpringLayout.NORTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.WEST, sortByColumn, 6, SpringLayout.EAST, orderByLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, sortByColumn, 2, SpringLayout.SOUTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.EAST, sortByColumn, 384, SpringLayout.EAST, orderByLabel);
		panel.add(sortByColumn);
		
		JList<String> sortDirection = new JList<String>();
		sl_panel.putConstraint(SpringLayout.NORTH, sortDirection, 71, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, sortDirection, 0, SpringLayout.EAST, submitButton);
		sl_panel.putConstraint(SpringLayout.SOUTH, submitButton, -4, SpringLayout.NORTH, sortDirection);
		sl_panel.putConstraint(SpringLayout.WEST, sortDirection, 6, SpringLayout.EAST, categories);
		sl_panel.putConstraint(SpringLayout.SOUTH, sortDirection, 0, SpringLayout.SOUTH, sortByColumn);
		sortDirection.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"ASCENDING", "DESCENDING"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		sortDirection.setSelectedIndex(0);
		panel.add(sortDirection);
	}
}
