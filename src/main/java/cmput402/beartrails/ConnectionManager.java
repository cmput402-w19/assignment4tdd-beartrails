package cmput402.beartrails;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class ConnectionManager {
	
	
	private final String DEFAULT_DB_URL = "db/beartrails.db";
	private final String URI_PREFIX = "jdbc:sqlite:";
	
	public ConnectionManager() {
		
	}
	
	/**
	 * Initialize the database.  Will only proceed if the DB file
	 * does not yet exist, otherwise will do nothing.
	 * 
	 * @return true if the database was created, false otherwise.
	 */
	private Boolean initializeDB() {
		return null;
	}
	
	/**
	 * Open a connection to the database, 
	 * using the default location. 
	 * Create the database if it does not exist yet.
	 * 
	 */
	public Boolean openConnection() {
		  return this.openConnection(this.DEFAULT_DB_URL);      
	}
	
	/**
	 * Open a connection to the database 
	 * located at dbPath.
	 * Create the database if it does not exist yet.
	 * 
	 * @param dbPath
	 */
	public Boolean openConnection(String dbPath) {
		// Create parent directory, if applicable
		File parentDir = new File(dbPath).getParentFile();
		if(parentDir != null) {
			parentDir.mkdirs();
		}
		
		String uri = this.URI_PREFIX + dbPath;

		try {
			Connection conn = DriverManager.getConnection(uri);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Close the connection with the database.
	 * This is part of the proper cleanup process.
	 */
	public Boolean closeConnection() {
		return null;
	}
	
	/**
	 * Query the database.
	 * 
	 * @param sql the query to execute.
	 * @return the result of the query.
	 */
	public Object query(String sql) {
		return null;
	}
	
}
