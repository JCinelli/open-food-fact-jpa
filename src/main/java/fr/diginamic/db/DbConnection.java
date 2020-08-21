package fr.diginamic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DbConnection {

	public static java.sql.Connection getConnection() {

		ResourceBundle db = ResourceBundle.getBundle("database");

		try {

			Class.forName(db.getString("db.driver"));

			Connection connection = DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"), db.getString("db.pass"));

			return connection;

		} catch (SQLException | ClassNotFoundException e) {

			System.err.println("Echec de la connection a la base .. " + e.getMessage());
			return null;

		}

	}

}
