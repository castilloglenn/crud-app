import java.util.Calendar;

import javax.swing.table.DefaultTableModel;

public class SystemUtility {
	
	private Database db;
	
	SystemUtility(Database db) {
		this.db = db;
	}
	
	/* PRODUCT ID FORMAT:
	 * example:
	 * 	4210325001
	 *  4-21-03-25-001
	 *  4 is the Product Code, 21 is the Year 20(21), 03 is the Month
	 *  25 for the day, and 001 is for auto increment
	 */
	public long generateProductID() {
		StringBuilder markup = new StringBuilder("4");
		
		Calendar c = Calendar.getInstance();
		markup.append(Integer.toString(c.get(Calendar.YEAR)).substring(2));
		
		int month = c.get(Calendar.MONTH) + 1;
		if (month < 10) markup.append("0");
		markup.append(Integer.toString(month));
		markup.append(Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
		
		long latest = db.fetchLastID();
		if (latest == -1) {
			markup.append("001");
		} else {
			String lastNum = Long.toString(latest).substring(7);
			int increment = Integer.parseInt(lastNum) + 1;
			markup.append(String.format("%03d", increment));
		}
		
		return Long.parseLong(markup.toString());
	}
	
	@SuppressWarnings("serial")
	public DefaultTableModel generateTable(Object[][] rows, Object[] column) {
		return new DefaultTableModel(
			rows, column
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
	}

}
