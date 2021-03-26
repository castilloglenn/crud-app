import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
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

@SuppressWarnings("serial")
public class InputDialog extends JDialog {
	
	private final String TITLE = "Insert Form | Product Inventory | Allen Glenn E. Castillo";
	private JTextField productIDField;
	
	private Database db;
	private SystemUtility su;
	private JTextField categoryNewField;
	private JTextField uomField;
	private JTextField nameField;

	public InputDialog(ImageIcon icon, Database db, SystemUtility su) {
		this.db = db;
		this.su = su;
		
		setIconImage(icon.getImage());
		setTitle(TITLE);
		getContentPane().setBackground(new Color(51, 51, 51));
		setSize(400, 435);
		setResizable(false);
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
		
		categoryNewField = new JTextField();
		categoryNewField.setEditable(false);
		categoryNewField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		categoryNewField.setHorizontalAlignment(SwingConstants.TRAILING);
		categoryNewField.setBounds(142, 121, 212, 17);
		panel.add(categoryNewField);
		categoryNewField.setColumns(10);
		
		JComboBox<String> existingCategory = new JComboBox<String>();
		existingCategory.setFont(new Font("Tahoma", Font.PLAIN, 12));
		existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchCategories()));
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
		
		JSpinner quantityAmount = new JSpinner();
		quantityAmount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		quantityAmount.setModel(new SpinnerNumberModel(0.0, 0.0, 99999.0, 1.0));
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
		
		JSpinner purchaseAmount = new JSpinner();
		purchaseAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		purchaseAmount.setBounds(124, 233, 230, 18);
		panel.add(purchaseAmount);
		
		JLabel sellValue = new JLabel("SELL VALUE:");
		sellValue.setHorizontalAlignment(SwingConstants.TRAILING);
		sellValue.setForeground(Color.WHITE);
		sellValue.setFont(new Font("Tahoma", Font.BOLD, 12));
		sellValue.setBounds(10, 262, 104, 17);
		panel.add(sellValue);
		
		JSpinner sellAmount = new JSpinner();
		sellAmount.setModel(new SpinnerNumberModel(1.0, 0.0, 99999.0, 1.0));
		sellAmount.setBounds(124, 261, 230, 18);
		panel.add(sellAmount);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(65, 100, 150));
		panel_1.setBounds(10, 311, 344, 49);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 10, 0));
		
		JButton btnNewButton = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "INSERT NEW PRODUCT AND CLEAR ALL FIELDS"
				+ "</p>"
				+ "</html>"
		);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton(
				"<html>"
				+ "<p style=\"text-align:center;\">"
				+ "INSERT NEW PRODUCT AND CLOSE WINDOW"
				+ "</p>"
				+ "</html>");
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(btnNewButton_1);
		
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
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBackground(new Color(65, 100, 150));
		chckbxNewCheckBox.setBounds(120, 121, 25, 17);
		panel.add(chckbxNewCheckBox);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}
}
