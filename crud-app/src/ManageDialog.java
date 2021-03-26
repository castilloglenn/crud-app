import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

@SuppressWarnings("serial")
public class ManageDialog extends JDialog {

	private final String TITLE = "Manage Form | Product Inventory | Allen Glenn E. Castillo";
	private String[] columnUpdate = {
		"category", "quantity", "uom", "name", "purchase_value", "sell_value"
	};
	private String[] categories;
	private Object[] data;
	private int indexStorage;

	private Database db;
	
	private JTextField productIDField;
	private JTextField categoryNew;
	private JTextField uomField;
	private JTextField nameField;
	private JComboBox<String> existingCategory;
	private JSpinner quantityAmount;
	private JSpinner purchaseAmount;
	private JSpinner sellAmount;
	private JButton updateButton;
	private JButton deleteButton;
	private JCheckBox categoryCheckBox;
	
	
	public ManageDialog(ImageIcon icon, Database db) {
		setResizable(false);
		this.db = db;

		categories = db.fetchCategories();
		
		setIconImage(icon.getImage());
		setTitle(TITLE);
		getContentPane().setBackground(new Color(51, 51, 51));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(new Color(65, 100, 150));
		getContentPane().add(panel);
		
		JLabel title = new JLabel("MANAGE PRODUCT");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setBounds(10, 20, 192, 25);
		panel.add(title);
		
		JLabel productIDLabel = new JLabel("PRODUCT ID:");
		productIDLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDLabel.setForeground(Color.WHITE);
		productIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		productIDLabel.setBounds(10, 66, 104, 17);
		panel.add(productIDLabel);
		
		productIDField = new JTextField("Enter the product ID here");
		productIDField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (productIDField.getText().equals("Enter the product ID here")) {
					productIDField.setText("");
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (productIDField.getText().isBlank()) {
					productIDField.setText("Enter the product ID here");
				}
				try {
					data = db.fetchDataByID(Long.parseLong(productIDField.getText()));
					if (data != null) {
						enableAndSetFields();
					} else {
						clearAndDisableFields();
					}
				} catch (NumberFormatException e2) {
					clearAndDisableFields();
				}
			}
		});
		productIDField.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productIDField.setColumns(10);
		productIDField.setBounds(124, 65, 230, 17);
		panel.add(productIDField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 344, 2);
		panel.add(separator);
		
		JLabel category = new JLabel("CATEGORY:");
		category.setHorizontalAlignment(SwingConstants.TRAILING);
		category.setForeground(Color.WHITE);
		category.setFont(new Font("Tahoma", Font.BOLD, 12));
		category.setBounds(10, 94, 104, 17);
		panel.add(category);
		
		categoryNew = new JTextField();
		categoryNew.setEnabled(false);
		categoryNew.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryNew.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryNew.setEditable(false);
		categoryNew.setColumns(10);
		categoryNew.setBounds(142, 121, 212, 17);
		panel.add(categoryNew);
		
		existingCategory = new JComboBox<String>();
		existingCategory.setEnabled(false);
		existingCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
		existingCategory.setRenderer(listRenderer);
		existingCategory.setBounds(124, 93, 230, 17);
		existingCategory.setSelectedIndex(-1);
		panel.add(existingCategory);
		
		JLabel quantity = new JLabel("QUANTITY:");
		quantity.setHorizontalAlignment(SwingConstants.TRAILING);
		quantity.setForeground(Color.WHITE);
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 150, 104, 17);
		panel.add(quantity);
		
		quantityAmount = new JSpinner();
		quantityAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		quantityAmount.setEnabled(false);
		quantityAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		quantityAmount.setBounds(124, 150, 230, 17);
		panel.add(quantityAmount);
		
		JLabel uomLabel = new JLabel(
				"<html>"
				+ "<p style=\"text-align:right;\">"
				+ "UNIT OF MEASUREMENT:"
				+ "</p>"
				+ "</html>");
		uomLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		uomLabel.setForeground(Color.WHITE);
		uomLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		uomLabel.setBounds(10, 174, 104, 26);
		panel.add(uomLabel);
		
		uomField = new JTextField();
		uomField.setEnabled(false);
		uomField.setHorizontalAlignment(SwingConstants.TRAILING);
		uomField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		uomField.setColumns(10);
		uomField.setBounds(124, 178, 230, 17);
		panel.add(uomField);
		
		JLabel name = new JLabel("PRODUCT NAME:");
		name.setHorizontalAlignment(SwingConstants.TRAILING);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(10, 206, 104, 17);
		panel.add(name);
		
		nameField = new JTextField();
		nameField.setEnabled(false);
		nameField.setHorizontalAlignment(SwingConstants.TRAILING);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameField.setColumns(10);
		nameField.setBounds(124, 206, 230, 17);
		panel.add(nameField);
		
		JLabel purchaseValue = new JLabel("<html>"
				+ "<p style=\"text-align:right;\">"
				+ "PURCHASE VALUE:"
				+ "</p>"
				+ "</html>");
		purchaseValue.setHorizontalAlignment(SwingConstants.TRAILING);
		purchaseValue.setForeground(Color.WHITE);
		purchaseValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		purchaseValue.setBounds(10, 229, 104, 28);
		panel.add(purchaseValue);
		
		purchaseAmount = new JSpinner();
		purchaseAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		purchaseAmount.setEnabled(false);
		purchaseAmount.setBounds(124, 233, 230, 18);
		panel.add(purchaseAmount);
		
		JLabel sellValue = new JLabel("SELL VALUE:");
		sellValue.setHorizontalAlignment(SwingConstants.TRAILING);
		sellValue.setForeground(Color.WHITE);
		sellValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sellValue.setBounds(10, 262, 104, 17);
		panel.add(sellValue);
		
		sellAmount = new JSpinner();
		sellAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		sellAmount.setEnabled(false);
		sellAmount.setBounds(124, 261, 230, 18);
		panel.add(sellAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(65, 100, 150));
		panel_1.setBounds(10, 311, 344, 49);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 10, 0));
		
		updateButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "UPDATE PRODUCT"
				+ "</p>"
				+ "</html>");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) {
					productIDField.setText("Enter the product ID here");
					clearAndDisableFields();
				}
			}
		});
		updateButton.setEnabled(false);
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		updateButton.setBackground(Color.WHITE);
		panel_1.add(updateButton);
		
		deleteButton = new JButton(
				"<html><p style=\"text-align:center;\">DELETE PRODUCT</p></html>");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmation = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to delete (" + nameField.getText() + ")?", 
						"Confirmation", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE
				);
				
				if (confirmation == 0) {
					if (db.deleteData(Long.parseLong(productIDField.getText()))) {
						JOptionPane.showMessageDialog(null, 
								"Product #" + productIDField.getText() + " has been deleted.\n"
										+ "Product Name: " + nameField.getText());
						productIDField.setText("Enter the product ID here");
						clearAndDisableFields();
					}
				}
			}
		});
		deleteButton.setEnabled(false);
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		deleteButton.setBackground(new Color(240, 150, 150));
		panel_1.add(deleteButton);
		
		JLabel newCategoryLabel = new JLabel(
				"<html>"
				+ "<p style=\"text-align:right;\">"
				+ "OR ADD NEW CATEGORY:"
				+ "</p>"
				+ "</html>");
		newCategoryLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		newCategoryLabel.setForeground(Color.WHITE);
		newCategoryLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		newCategoryLabel.setBounds(10, 118, 104, 27);
		panel.add(newCategoryLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 298, 344, 2);
		panel.add(separator_1);
		
		categoryCheckBox = new JCheckBox("");
		categoryCheckBox.setEnabled(false);
		categoryCheckBox.setBackground(new Color(65, 100, 150));
		categoryCheckBox.setBounds(120, 121, 21, 17);
		panel.add(categoryCheckBox);
		setSize(400, 435);
		
		
		// Listeners
		existingCategory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateSwitch();
			}});
		categoryNew.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		quantityAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		uomField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				updateSwitch();
			}});
		purchaseAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		sellAmount.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				updateSwitch();
			}});
		categoryCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (categoryCheckBox.isSelected()) {
					categoryNew.setEditable(true);
					existingCategory.setSelectedIndex(-1);
					existingCategory.setEnabled(false);
				} else if (!categoryCheckBox.isSelected()) {
					existingCategory.setEnabled(true);
					existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchCategories()));
					existingCategory.setSelectedIndex(indexStorage);
					categoryNew.setText("");
					categoryNew.setEditable(false);
				}
				updateSwitch();
			}
		});
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}
	
	public void clearAndDisableFields() {
		existingCategory.setSelectedIndex(-1); existingCategory.setEnabled(false);
		categoryNew.setText(""); categoryNew.setEnabled(false);
		quantityAmount.setValue(1); quantityAmount.setEnabled(false);
		uomField.setText(""); uomField.setEnabled(false);
		nameField.setText(""); nameField.setEnabled(false);
		purchaseAmount.setValue(1); purchaseAmount.setEnabled(false);
		sellAmount.setValue(1); sellAmount.setEnabled(false);
		
		categoryCheckBox.setEnabled(false);
		updateButton.setEnabled(false);
		deleteButton.setEnabled(false);
	}
	
	public void enableAndSetFields() {
		for (int index = 0; index < categories.length; index++) {
			if (data[1].toString().equals(categories[index])) {
				existingCategory.setSelectedIndex(index);
				indexStorage = index;
			}
		}
		
		quantityAmount.setValue(data[2]); 
		uomField.setText(data[3].toString()); 
		nameField.setText(data[4].toString()); 
		purchaseAmount.setValue(data[5]); 
		sellAmount.setValue(data[6]); 
		
		existingCategory.setEnabled(true); categoryCheckBox.setEnabled(true);
		categoryNew.setEnabled(true); quantityAmount.setEnabled(true);
		uomField.setEnabled(true); nameField.setEnabled(true);
		purchaseAmount.setEnabled(true); sellAmount.setEnabled(true);
		deleteButton.setEnabled(true);
	}
	
	public void updateSwitch() {
		try {
			boolean categorySelector = (existingCategory.getSelectedItem() == null) 
				? categoryNew.getText().equals(data[1])
				: existingCategory.getSelectedItem().toString().equals(data[1]);
			
			if (categorySelector &&
				quantityAmount.getValue().toString().equals(data[2].toString()) &&
				uomField.getText().equals(data[3]) &&
				nameField.getText().equals(data[4]) &&
				purchaseAmount.getValue().toString().equals(data[5].toString()) &&
				sellAmount.getValue().toString().equals(data[6].toString())
			) {
				updateButton.setEnabled(false);
			} else {
				updateButton.setEnabled(true);
			}
		} catch (NullPointerException e) {
			// The system will not handle null pointers because this is also key-sensitive
		}
	}
	
	public Object[] getAllFields() {
		Object[] data = new Object[7];
		Object[] errors = new Object[1];
		boolean flagged = false;
		
		data[0] = productIDField.getText();
		if (categoryCheckBox.isSelected()) {
			String input = categoryNew.getText();
			data[1] = input;
		} else if (!categoryCheckBox.isSelected()) {
			data[1] = existingCategory.getSelectedItem().toString();
		}
		data[2] = quantityAmount.getValue().toString();
		String uom = uomField.getText();
		String name = nameField.getText();
		if (uom.isBlank()) {
			data[3] = "piece(s)";
		} else {
			data[3] = uom;
		}
		if (name.isBlank()) {
			flagged = true;
			errors[0] = "• Product name cannot be empty.";
		}
		data[4] = name;
		
		data[5] = purchaseAmount.getValue();
		data[6] = sellAmount.getValue();
			
		return (flagged) ? errors : data;
	}
	
	public boolean evaluateFields() {
		Object[] data = getAllFields();
		if (data.length == 7) {
			Object[] inputs = {
				(existingCategory.getSelectedItem() == null) 
					? categoryNew.getText()
					: existingCategory.getSelectedItem().toString(),
				Double.parseDouble(quantityAmount.getValue().toString()),
				uomField.getText(),
				nameField.getText(),
				Double.parseDouble(purchaseAmount.getValue().toString()),
				Double.parseDouble(sellAmount.getValue().toString())
			};
			db.updateData(Long.parseLong(productIDField.getText()), columnUpdate, inputs);
			JOptionPane.showMessageDialog(null, 
					"Product #" + productIDField.getText() + " has been updated.\n"
					+ "Product Name: " + nameField.getText()
			);
			return true;
		} else if (data.length == 2) {
			JOptionPane.showMessageDialog(null,
				String.format("Please check your inputs:\n"
						+ "%s%s",
						(data[0] == null) ? data[1].toString() : data[0],
						(data[0] == null) ? "" : "\n" + data[1])
			);
		}
		return false;
	}
	
}
