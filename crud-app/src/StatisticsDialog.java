import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.Cursor;
import javax.swing.border.SoftBevelBorder;
import java.awt.Insets;

@SuppressWarnings("serial")
public class StatisticsDialog extends JDialog {

	private final String TITLE = "Product Statistics | Product Inventory | Allen Glenn E. Castillo";
	
	private Database db;
	
	public StatisticsDialog(ImageIcon icon, Database db) {
		this.db = db;

		setIconImage(icon.getImage());
		setTitle(TITLE);
		setSize(400, 500);
		getContentPane().setBackground(new Color(51, 51, 51));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		JPanel panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(new Color(65, 100, 150));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel title = new JLabel("INVENTORY STATISTICS");
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 20));
		title.setBounds(10, 11, 249, 25);
		panel.add(title);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 36, 342, 2);
		panel.add(separator);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(12, 48, 340, 380);
		panel.add(scrollPane);
		
		JTextPane textField = new JTextPane();
		textField.setText(constructStatistics());
		textField.setEditable(false);
		textField.setMargin(new Insets(10, 10, 10, 10));
		textField.setFont(new Font("Dialog", Font.BOLD, 12));
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textField.setCaretPosition(0);
		scrollPane.setViewportView(textField);
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}
	
	public String constructStatistics() {
		String mepn = nullHandler(db.fetchMaxPurchaseValue());
		double mepv = nullHandler(db.fetchPurchaseValueByName(mepn));
		String mcpn = nullHandler(db.fetchMinPurchaseValue());
		double mcpv = nullHandler(db.fetchPurchaseValueByName(mcpn));
		String mespn = nullHandler(db.fetchMaxSellValue());
		double mespv = nullHandler(db.fetchSellValueByName(mespn));
		String mcspn = nullHandler(db.fetchMinSellValue());
		double mcspv = nullHandler(db.fetchSellValueByName(mcspn));
		String pwtms = nullHandler(db.fetchProductMaxStock());
		String pwtls = nullHandler(db.fetchProductMinStock());
		int tnop = db.fetchTotalCount();
		double tnopq = db.fetchSumQuantity();
		int tnoc = (db.fetchCategories()[0].equals("--No existing record--")) 
				? 0 : db.fetchCategories().length;
		String pcwtmp = nullHandler(db.fetchCategoryMostProduct());
		String pcwtlp = nullHandler(db.fetchCategoryLeastProduct());
		double app = db.fetchAveragePurchasePrice();
		double asp = db.fetchAverageSellingPrice();
		double paoapoh = db.fetchSumProductPurchase();
		double saoapoh = db.fetchSumProductSell();
		
		String report = String.format(
			  "Total Number of Products: %d\n"
			+ "Total Number of Product Quantity: %,.2f\n"
			+ "Total Number of Categories: %d\n\n"
			+ "Most Expensive Purchased Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Cheapest Purchased Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Expensive Selling Product: \n• %s priced at Php %,.2f\n\n"
			+ "Most Cheapest Selling Product: \n• %s priced at Php %,.2f\n\n"
			+ "Product with the Most Stocks: \n• %s\n\n"
			+ "Product with the Least Stocks: \n• %s\n\n"
			+ "Product Category with the Most Products: \n• %s\n\n"
			+ "Product Category with the Least Products: \n• %s\n\n"
			+ "Average Purchase Price: \n• Php %,.2f\n\n"
			+ "Average Selling Price: \n• Php %,.2f\n\n"
			+ "Purchase Amount of all Products On-hand: \n• Php %,.2f\n\n"
			+ "Selling Amount of all Products On-hand: \n• Php %,.2f",
			tnop, tnopq, tnoc, mepn, mepv, mcpn, mcpv, mespn, mespv, mcspn, 
			mcspv, pwtms, pwtls, pcwtmp, pcwtlp, app, asp, paoapoh, saoapoh
		);
		
		return report;
	}
	
	public String nullHandler(String message) {
		return (message == null || message.equals("--No existing record--")) ? "none" : message.toUpperCase();
	}
	
	public double nullHandler(double value) {
		return (value == -1) ? 0 : value;
	}
}
