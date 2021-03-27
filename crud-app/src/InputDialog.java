import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class InputDialog extends JDialog {
	
	private final String TITLE = "Insert Form | Product Inventory | Allen Glenn E. Castillo";
	private String[] categories;
	
	private Database db;
	private SystemUtility su;

	private JTextField productIDField;
	private JTextField categoryNew;
	private JTextField uomField;
	private JTextField nameField;
	private JCheckBox categoryCheckBox;
	private JComboBox<String> existingCategory;
	private JSpinner quantityAmount;
	private JSpinner purchaseAmount;
	private JSpinner sellAmount;

	public InputDialog(ImageIcon icon, Database db, SystemUtility su) {
		this.db = db;
		this.su = su;
		
		categories = db.fetchCategories();
		
		setIconImage(icon.getImage());
		setTitle(TITLE);
		getContentPane().setBackground(new Color(51, 51, 51));
		setSize(400, 435);
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(new Color(65, 100, 150));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("NEW PRODUCT");
		title.setBounds(10, 20, 158, 25);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(title);
		
		JLabel productIDLabel = new JLabel("PRODUCT ID:");
		productIDLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDLabel.setForeground(Color.WHITE);
		productIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		productIDLabel.setBounds(10, 66, 104, 17);
		panel.add(productIDLabel);
		
		productIDField = new JTextField(Long.toString(su.generateProductID()));
		productIDField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		productIDField.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDField.setEditable(false);
		productIDField.setBounds(124, 65, 230, 17);
		panel.add(productIDField);
		productIDField.setColumns(10);
		
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
		categoryNew.setEditable(false);
		categoryNew.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryNew.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryNew.setBounds(142, 121, 212, 17);
		panel.add(categoryNew);
		categoryNew.setColumns(10);
		
		existingCategory = new JComboBox<String>();
		existingCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
		existingCategory.setBounds(124, 93, 230, 17);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
		listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
		existingCategory.setRenderer(listRenderer);
		panel.add(existingCategory);
		
		JLabel quantity = new JLabel("QUANTITY:");
		quantity.setHorizontalAlignment(SwingConstants.TRAILING);
		quantity.setForeground(Color.WHITE);
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 150, 104, 17);
		panel.add(quantity);
		
		quantityAmount = new JSpinner();
		quantityAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		quantityAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		quantityAmount.setBounds(124, 150, 230, 17);
		panel.add(quantityAmount);
		
		JLabel uomLabel = new JLabel(
			"<html>"
			+ "<p style=\"text-align:right;\">"
			+ "UNIT OF MEASUREMENT:"
			+ "</p>"
			+ "</html>"
		);
		uomLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		uomLabel.setForeground(Color.WHITE);
		uomLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		uomLabel.setBounds(10, 174, 104, 26);
		panel.add(uomLabel);
		
		uomField = new JTextField();
		uomField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		uomField.setHorizontalAlignment(SwingConstants.TRAILING);
		uomField.setBounds(124, 178, 230, 17);
		panel.add(uomField);
		uomField.setColumns(10);
		
		JLabel name = new JLabel("PRODUCT NAME:");
		name.setHorizontalAlignment(SwingConstants.TRAILING);
		name.setForeground(Color.WHITE);
		name.setFont(new Font("Tahoma", Font.BOLD, 12));
		name.setBounds(10, 206, 104, 17);
		panel.add(name);
		
		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.TRAILING);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameField.setBounds(124, 206, 230, 17);
		panel.add(nameField);
		nameField.setColumns(10);
		
		JLabel purchaseValue = new JLabel(
				"<html>"
				+ "<p style=\"text-align:right;\">"
				+ "PURCHASE "
				+ "VALUE:"
				+ "</p>"
				+ "</html>"
			);
		purchaseValue.setHorizontalAlignment(SwingConstants.TRAILING);
		purchaseValue.setForeground(Color.WHITE);
		purchaseValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		purchaseValue.setBounds(10, 229, 104, 28);
		panel.add(purchaseValue);
		
		purchaseAmount = new JSpinner();
		purchaseAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		purchaseAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		purchaseAmount.setBounds(124, 233, 230, 18);
		panel.add(purchaseAmount);
		
		JLabel sellValue = new JLabel("SELL VALUE:");
		sellValue.setHorizontalAlignment(SwingConstants.TRAILING);
		sellValue.setForeground(Color.WHITE);
		sellValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sellValue.setBounds(10, 262, 104, 17);
		panel.add(sellValue);
		
		sellAmount = new JSpinner();
		sellAmount.setFont(new Font("Tahoma", Font.PLAIN, 11));
		sellAmount.setModel(new SpinnerNumberModel(1.0, 1.0, 99999.0, 1.0));
		sellAmount.setBounds(124, 261, 230, 18);
		panel.add(sellAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(65, 100, 150));
		panel_1.setBounds(10, 311, 344, 49);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 10, 0));
		
		JButton insertClearButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "ADD NEW PRODUCT AND CLEAR ALL FIELDS"
				+ "</p>"
				+ "</html>"
		);
		insertClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) clearFields();
			}
		});
		insertClearButton.setBackground(Color.WHITE);
		insertClearButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(insertClearButton);
		
		JButton insertExitButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "ADD NEW PRODUCT AND CLOSE WINDOW"
				+ "</p>"
				+ "</html>");
		insertExitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (evaluateFields()) dispose();
			}
		});
		insertExitButton.setBackground(Color.WHITE);
		insertExitButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(insertExitButton);
		
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
		categoryCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (categoryCheckBox.isSelected()) {
					categoryNew.setEditable(true);
					existingCategory.setSelectedIndex(-1);
					existingCategory.setEnabled(false);
				}
				if (!categoryCheckBox.isSelected()) {
					existingCategory.setEnabled(true);
					existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchCategories()));
					existingCategory.setSelectedIndex(0);
					categoryNew.setText("");
					categoryNew.setEditable(false);
				}
			}
		});
		categoryCheckBox.setBackground(new Color(65, 100, 150));
		categoryCheckBox.setBounds(120, 121, 21, 17);
		panel.add(categoryCheckBox);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
	
	private Object[] getAllFields() {
		Object[] data = new Object[7];
		Object[] errors = new Object[2];
		boolean flagged = false;
		
		data[0] = productIDField.getText();
		if (categoryCheckBox.isSelected()) {
			String input = categoryNew.getText();
			if (input.isBlank()) {
				flagged = true;
				errors[0] = "• Your new category cannot be empty.";
			} else {
				data[1] = input;
				for (String category : categories) {
					if (input.toLowerCase().equals(category.toLowerCase())) {
						flagged = true;
						errors[0] = "• Your new category already exists.";
					}
				}
			}
		} else if (!categoryCheckBox.isSelected()) {
			String category = existingCategory.getSelectedItem().toString();
			if (category.equals("--No existing record--")) {
				flagged = true;
				errors[0] = "• Please enter a new category.";
			} else {
				data[1] = category;
			}
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
			errors[1] = "• Product name cannot be empty.";
		}
		data[4] = name;
		
		data[5] = purchaseAmount.getValue();
		data[6] = sellAmount.getValue();
			
		return (flagged) ? errors : data;
	}
	
	private boolean evaluateFields() {
		Object[] data = getAllFields();
		if (data.length == 7) {
			JOptionPane.showMessageDialog(null, "New Product Added! (" + nameField.getText() + ")");
			db.insertData(
				Long.parseLong(data[0].toString()),
				data[1].toString(),
				Double.parseDouble(data[2].toString()),
				data[3].toString(),
				data[4].toString(),
				Double.parseDouble(data[5].toString()),
				Double.parseDouble(data[6].toString())
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
	
	private void clearFields() {
		productIDField.setText(Long.toString(su.generateProductID()));
		if (categoryCheckBox.isSelected()) {
			categoryNew.setText("");
		} else {
			categories = db.fetchCategories();
			existingCategory.setModel(new DefaultComboBoxModel<String>(categories));
			existingCategory.setSelectedIndex(0);
		}
		quantityAmount.setValue(1);
		uomField.setText("");
		nameField.setText("");
		purchaseAmount.setValue(1);
		sellAmount.setValue(1);
	}
	
}
