import javax.swing.DefaultComboBoxModel;
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

@SuppressWarnings("serial")
public class InputDialog extends JDialog {
	
	private final String TITLE = "Insert Form | Product Inventory | Allen Glenn E. Castillo";
	private JTextField productIDField;
	
	private Database db;
	private SystemUtility su;
	private JTextField categoryNewField;

	public InputDialog(ImageIcon icon, Database db, SystemUtility su) {
		this.db = db;
		this.su = su;
		
		setIconImage(icon.getImage());
		setTitle(TITLE);
		getContentPane().setBackground(new Color(51, 51, 51));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		setSize(500, 500);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(new Color(65, 100, 150));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("NEW PRODUCT");
		title.setBounds(10, 20, 158, 25);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(title);
		
		JLabel productIDLabel = new JLabel("PRODUCT ID:");
		productIDLabel.setForeground(Color.WHITE);
		productIDLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		productIDLabel.setBounds(10, 57, 90, 17);
		panel.add(productIDLabel);
		
		productIDField = new JTextField(Long.toString(su.generateProductID()));
		productIDField.setHorizontalAlignment(SwingConstants.TRAILING);
		productIDField.setEditable(false);
		productIDField.setBounds(98, 57, 356, 17);
		panel.add(productIDField);
		productIDField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 444, 2);
		panel.add(separator);
		
		JLabel category = new JLabel("CATEGORY:");
		category.setForeground(Color.WHITE);
		category.setFont(new Font("Tahoma", Font.BOLD, 12));
		category.setBounds(10, 85, 80, 17);
		panel.add(category);
		
		categoryNewField = new JTextField();
		categoryNewField.setHorizontalAlignment(SwingConstants.CENTER);
		categoryNewField.setBounds(98, 85, 154, 17);
		panel.add(categoryNewField);
		categoryNewField.setColumns(10);
		
		
		JLabel or = new JLabel("OR");
		or.setForeground(Color.WHITE);
		or.setFont(new Font("Tahoma", Font.BOLD, 12));
		or.setBounds(262, 85, 24, 17);
		panel.add(or);
		
		JComboBox<String> existingCategory = new JComboBox<String>();
		existingCategory.setModel(new DefaultComboBoxModel<String>(db.fetchCategories()));
		existingCategory.setBounds(296, 85, 158, 17);
		panel.add(existingCategory);
		
		JLabel quantity = new JLabel("QUANTITY:");
		quantity.setForeground(Color.WHITE);
		quantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantity.setBounds(10, 113, 80, 17);
		panel.add(quantity);
		
		JSpinner quantityAmount = new JSpinner();
		quantityAmount.setModel(new SpinnerNumberModel(0.0, 0.0, 99999.0, 1.0));
		quantityAmount.setBounds(98, 113, 80, 17);
		panel.add(quantityAmount);
		
		JLabel uomLabel = new JLabel("UNIT:");
		uomLabel.setHorizontalAlignment(SwingConstants.CENTER);
		uomLabel.setForeground(Color.WHITE);
		uomLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		uomLabel.setBounds(188, 113, 51, 17);
		panel.add(uomLabel);
		
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}
}
