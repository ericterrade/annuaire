package fr.treeptik.annuaire.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

	private static Connection connection;

	public static Connection getConnection() {

		try {
			
			if (connection == null || connection.isClosed() ) {

				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/annuaire", "root", "root");
				
				connection.setAutoCommit(false);
				
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
		
	}

}
