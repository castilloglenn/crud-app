import java.awt.EventQueue;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

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
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ListSelectionModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
	
	private final String APP_NAME = "Product Inventory | Java CRUD | Allen Glenn E. Castillo";
	private final String[] COLUMNS = {
		"Product ID", "Category", "Quantity", "Unit", 
		"Product Name", "Purchase Value", "Sell Value"
	};
	private final String[] DB_COLUMNS = {
		"product_id", "category", "quantity", "uom",
		"name", "purchase_value", "sell_value"
	};
	
	private String[] searchArgs = new String[4];
	private ImageIcon icon = new ImageIcon("images/chest.png");
	
	private JFrame frame;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField searchField;
	private JComboBox<String> categories;
	private JComboBox<String> sortByColumn;
	private JList<String> sortDirection;
	private JButton submitButton;
	
	private Database db;
	private SystemUtility su;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Main window = new Main();
				window.frame.setVisible(true);
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
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, frame.getContentPane());
		panel.setBackground(new Color(65, 100, 150));
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel title = new JLabel("PRODUCT INVENTORY");
		sl_panel.putConstraint(SpringLayout.NORTH, title, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, title, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, title, 30, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, title, 235, SpringLayout.WEST, panel);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setForeground(Color.WHITE);
		panel.add(title);
		
		scrollPane = new JScrollPane();
		sl_panel.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, panel);
		scrollPane.setViewportBorder(null);
		panel.add(scrollPane);
		
		table = new JTable(30, 7);
		table.setBackground(Color.WHITE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowHeight(20);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setModel(su.generateTable(db.fetchAll(), COLUMNS));
		scrollPane.setViewportView(table);
		
		JPopupMenu tablePopup = new JPopupMenu();
		addPopup(table, tablePopup);
		
		JMenuItem popupCopy = new JMenuItem("Copy Cell");
		popupCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
				StringSelection ss = new StringSelection(text);
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				cb.setContents(ss, null);
			}
		});
		tablePopup.add(popupCopy);
		
		JPanel buttonsPanel = new JPanel();
		sl_panel.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, buttonsPanel);
		sl_panel.putConstraint(SpringLayout.NORTH, buttonsPanel, -40, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, buttonsPanel, -11, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, buttonsPanel, -10, SpringLayout.EAST, panel);
		buttonsPanel.setBackground(new Color(65, 100, 150));
		sl_panel.putConstraint(SpringLayout.WEST, buttonsPanel, 10, SpringLayout.WEST, panel);
		panel.add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(1, 0, 10, 0));
		
		JButton insertButton = new JButton("NEW PRODUCT");
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InputDialog(icon, db, su);
			}
		});
		insertButton.setBackground(new Color(255, 255, 255));
		insertButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonsPanel.add(insertButton);
		
		JButton manageButton = new JButton("MANAGE PRODUCT");
		manageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ManageDialog(icon, db);
			}
		});
		manageButton.setBackground(new Color(255, 255, 255));
		manageButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		buttonsPanel.add(manageButton);
		
		JButton statisticsButton = new JButton("SHOW STATISTICS");
		statisticsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StatisticsDialog(icon, db);
			}
		});
		statisticsButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		statisticsButton.setBackground(Color.WHITE);
		buttonsPanel.add(statisticsButton);
		
		JLabel searchLabel = new JLabel("SEARCH:");
		sl_panel.putConstraint(SpringLayout.NORTH, searchLabel, 8, SpringLayout.SOUTH, title);
		sl_panel.putConstraint(SpringLayout.WEST, searchLabel, 10, SpringLayout.WEST, panel);
		searchLabel.setForeground(Color.WHITE);
		searchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(searchLabel);
		
		JLabel searchForLabel = new JLabel("SEARCH FOR:");
		sl_panel.putConstraint(SpringLayout.SOUTH, searchLabel, -10, SpringLayout.NORTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.NORTH, searchForLabel, 65, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, searchForLabel, 10, SpringLayout.WEST, panel);
		searchForLabel.setForeground(Color.WHITE);
		searchForLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(searchForLabel);
		
		JLabel orderByLabel = new JLabel("ORDER BY:");
		sl_panel.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.NORTH, orderByLabel, 10, SpringLayout.SOUTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.WEST, orderByLabel, 10, SpringLayout.WEST, panel);
		orderByLabel.setForeground(Color.WHITE);
		orderByLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(orderByLabel);
		
		searchField = new JTextField();
		searchField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					submitButton.doClick();
				}
			}
		});
		sl_panel.putConstraint(SpringLayout.NORTH, searchField, -2, SpringLayout.NORTH, searchLabel);
		sl_panel.putConstraint(SpringLayout.WEST, searchField, 6, SpringLayout.EAST, searchLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, searchField, 2, SpringLayout.SOUTH, searchLabel);
		sl_panel.putConstraint(SpringLayout.EAST, searchField, 398, SpringLayout.EAST, searchLabel);
		panel.add(searchField);
		searchField.setColumns(10);
		
		submitButton = new JButton("SUBMIT");
		sl_panel.putConstraint(SpringLayout.NORTH, submitButton, 35, SpringLayout.NORTH, panel);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSearchArgs();
				table.setModel(su.generateTable(
						db.fetchSpecific(searchArgs[0], searchArgs[1], searchArgs[2], searchArgs[3]), COLUMNS
				));
			}
		});
		sl_panel.putConstraint(SpringLayout.WEST, submitButton, 467, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, submitButton, 88, SpringLayout.EAST, searchField);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(searchField, popupMenu);
		
		JMenuItem paste = new JMenuItem("Paste");
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable t = cb.getContents(null);
				if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					try {
						searchField.setText((String) t.getTransferData(DataFlavor.stringFlavor));
					} catch (UnsupportedFlavorException | IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		popupMenu.add(paste);
		submitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel.add(submitButton);
		
		categories = new JComboBox<String>();
		categories.setModel(new DefaultComboBoxModel<String>(COLUMNS));
		categories.setSelectedIndex(4);
		sl_panel.putConstraint(SpringLayout.NORTH, categories, -2, SpringLayout.NORTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.WEST, categories, 6, SpringLayout.EAST, searchForLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, categories, 2, SpringLayout.SOUTH, searchForLabel);
		sl_panel.putConstraint(SpringLayout.EAST, categories, 368, SpringLayout.EAST, searchForLabel);
		panel.add(categories);
		
		sortByColumn = new JComboBox<String>();
		sortByColumn.setModel(new DefaultComboBoxModel<String>(COLUMNS));
		sl_panel.putConstraint(SpringLayout.NORTH, sortByColumn, -2, SpringLayout.NORTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.WEST, sortByColumn, 6, SpringLayout.EAST, orderByLabel);
		sl_panel.putConstraint(SpringLayout.SOUTH, sortByColumn, 2, SpringLayout.SOUTH, orderByLabel);
		sl_panel.putConstraint(SpringLayout.EAST, sortByColumn, 384, SpringLayout.EAST, orderByLabel);
		panel.add(sortByColumn);
		
		sortDirection = new JList<String>();
		sl_panel.putConstraint(SpringLayout.SOUTH, submitButton, -4, SpringLayout.NORTH, sortDirection);
		sl_panel.putConstraint(SpringLayout.NORTH, sortDirection, 71, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, sortDirection, 0, SpringLayout.EAST, submitButton);
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
		

		frame.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent e) {
				resetFields();
				table.setModel(su.generateTable(db.fetchAll(), COLUMNS));
			}
			public void windowLostFocus(WindowEvent e) {}
		});
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				if (component == table && table.getSelectedRow() != -1)
					popup.show(e.getComponent(), e.getX(), e.getY());
				else if (component == searchField) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}
	
	public void resetFields() {
		searchField.setText("");
		categories.setSelectedIndex(4);
		sortByColumn.setSelectedIndex(0);
		sortDirection.setSelectedIndex(0);
	}
	
	public void getSearchArgs() {
		searchArgs[0] = "%" + searchField.getText() + "%";
		searchArgs[1] = DB_COLUMNS[categories.getSelectedIndex()];
		searchArgs[2] = DB_COLUMNS[sortByColumn.getSelectedIndex()];
		searchArgs[3] = (sortDirection.getSelectedIndex() == 0) ? "ASC" : "DESC";
	}
}
