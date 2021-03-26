import javax.swing.ImageIcon;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.SpringLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class InputDialog extends JDialog {
	
	private final String TITLE = "Insert Form | Product Inventory | Allen Glenn E. Castillo";

	public InputDialog(ImageIcon icon) {
		setIconImage(icon.getImage());
		setTitle(TITLE);
		getContentPane().setBackground(new Color(51, 51, 51));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		setSize(450, 500);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(new Color(65, 100, 150));
		springLayout.putConstraint(SpringLayout.NORTH, panel, 11, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -11, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, getContentPane());
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewProduct = new JLabel("NEW PRODUCT");
		lblNewProduct.setBounds(10, 11, 158, 25);
		lblNewProduct.setForeground(Color.WHITE);
		lblNewProduct.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel.add(lblNewProduct);
		
		
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);
		setVisible(true);
	}
}
