import java.sql.*;
import java.util.ArrayList;

public class Database {
	
	private final String URL = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private final String DATABASE_NAME = "inventory";
	private final String TABLE_NAME = "product";
	private final String COLUMNS = 
		"product_id BIGINT PRIMARY KEY," +
		"category VARCHAR(255) NOT NULL," +
		"quantity DOUBLE(8, 2) NOT NULL," +
		"uom VARCHAR(255) DEFAULT \"piece(s)\"," +
		"name VARCHAR(255) UNIQUE NOT NULL," +
		"purchase_value DOUBLE(8, 2) NOT NULL," +
		"sell_value DOUBLE(8, 2) NOT NULL";
	
	private Connection con;
	private Statement stmt;
	
	
	Database() {
		try {
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			stmt = con.createStatement();
			createDatabase();
			createTable();
//			insertData(
//				4210330002L,
//				"Human Trafficking",
//				1.0,
//				"human",
//				"AB Son Goku",
//				99999.99,
//				99999.99
//			);
//			updateData(
//				201910428,
//				new String[] {"name", "purchase_value"},
//				new Object[] {"Diego Fuego", 69420.88}
//			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createDatabase() throws SQLException {
		stmt.execute(String.format("CREATE DATABASE IF NOT EXISTS %s;", DATABASE_NAME));
		stmt.execute(String.format("USE %s;", DATABASE_NAME));
	}
	
	public void createTable() throws SQLException {
		stmt.execute(String.format("CREATE TABLE IF NOT EXISTS %s (%s);", TABLE_NAME, COLUMNS));
	}
	
	// This method will return -1 in some cases that an error might occur within the database.
	public int insertData(long product_id, String category, double quantity, 
			String uom, String name, double purchase_value, double sell_value) {
		try {
			PreparedStatement insert = con.prepareStatement(
				"INSERT INTO " + TABLE_NAME +
				" VALUES (?, ?, ?, ?, ?, ?, ?);");
			insert.setLong(1, product_id);
			insert.setString(2, category);
			insert.setDouble(3, quantity);
			insert.setString(4, uom);
			insert.setString(5, name);
			insert.setDouble(6, purchase_value);
			insert.setDouble(7, sell_value);
			return insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	// Returns false if there are inconsistencies within the argument's length.
	public boolean updateData(long product_id, String[] columns, Object[] datas) {
		if (columns.length != datas.length) return false;
		
		int totalUpdated = 0;
		for (int index = 0; index < columns.length; index++) {
			try {
				PreparedStatement updateOne = con.prepareStatement(
					String.format(
						"UPDATE %s"
						+ " SET %s = ?"
						+ " WHERE product_id = ?;"
					, TABLE_NAME, columns[index]));
				
				switch (columns[index]) {
					case "category": case "uom": case "name":
						updateOne.setString(1, datas[index].toString());
						break;
					case "quantity": case "purchase_value": case "sell_value":
						updateOne.setDouble(1, Double.parseDouble(datas[index].toString()));
						break;
				}
				
				updateOne.setLong(2, product_id);
				updateOne.executeUpdate();
				totalUpdated++;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return (totalUpdated == columns.length);
	}
	
	public void deleteData() {
		// TO-DO
	}
	
	public long fetchLastID() {
		try {
			PreparedStatement fetchMax = con.prepareStatement(
				"SELECT MAX(product_id) FROM " + TABLE_NAME + ";"
			);
			
			ResultSet pid = fetchMax.executeQuery();
			pid.next();
			if (pid.getLong(1) != 0) return pid.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Object[][] fetchAll() {
		try {
			PreparedStatement countAll = con.prepareStatement(
				"SELECT COUNT(product_id) FROM " + TABLE_NAME + ";"
			);
			ResultSet count = countAll.executeQuery();
			count.next();
			int size = count.getInt(1);
			
			PreparedStatement fetchAll = con.prepareStatement(
				"SELECT * FROM " + TABLE_NAME + ";",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY
			);
			
			ResultSet datas = fetchAll.executeQuery();
			Object[][] convertedList = new Object[size][7];
			
			int index = 0;
			while (datas.next()) {
				Object[] row = new Object[7];
				row[0] = datas.getLong(1);
				row[1] = datas.getString(2);
				row[2] = datas.getDouble(3);
				row[3] = datas.getString(4);
				row[4] = datas.getString(5);
				row[5] = datas.getDouble(6);
				row[6] = datas.getDouble(7);
				convertedList[index] = row;
				index++;
			}
			
			return convertedList;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] fetchCategories() {
		try {
			PreparedStatement fetch = con.prepareStatement(
				"SELECT DISTINCT category FROM " + TABLE_NAME
			);
			ResultSet categories = fetch.executeQuery();
			ArrayList<String> temp = new ArrayList<>();
			while (categories.next()) {
				temp.add(categories.getString(1));
			}
			if (temp.toArray(new String[0]).length == 0)
				return new String[] {"--No existing record--"};
			
			return temp.toArray(new String[0]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
