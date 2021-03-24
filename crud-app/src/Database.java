import java.sql.*;

public class Database {
	
	private final String URL = "jdbc:mysql://localhost/?serverTimezone=UTC";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	
	private final String DATABASE_NAME = "inventory";
	private final String TABLE_NAME = "product";
	private final String COLUMNS = 
		"product_id int PRIMARY KEY," +
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
	public int insertData(int product_id, String category, double quantity, 
			String uom, String name, double purchase_value, double sell_value) {
		try {
			PreparedStatement insert = con.prepareStatement(
				"INSERT INTO " + TABLE_NAME +
				"VALUES (?, ?, ?, ?, ?, ?, ?);");
			insert.setInt(1, product_id);
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
	
}
