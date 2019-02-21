package cmput402.beartrails;

import java.util.List;

public class ConnectionManager {
	
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
		return null;
	}
	
	/**
	 * Open a connection to the database 
	 * located at dbPath.
	 * Create the database if it does not exist yet.
	 * 
	 * @param dbPath
	 */
	public Boolean openConnection(String dbPath) {
		return null;
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
	public List<List<Object>> query(String sql) {
		return null;
	}
	
}
