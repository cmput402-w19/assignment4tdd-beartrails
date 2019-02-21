package cmput402.beartrails;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.io.File;
import java.sql.Connection;

public class ConnectionManager {
	
	
	private final String DEFAULT_DB_URL = "db/beartrails.db";
	private final String URI_PREFIX = "jdbc:sqlite:";
	
	private Connection conn = null;
	
	public ConnectionManager() {
	
	}
	
	/**
	 * Initialize the database, by creating the necessary tables
	 * if they don't yet exist.
	 * 
	 * @return true if the database was initialized successfully, false otherwise.
	 */
	private Boolean initializeDB() {
		String createTableSql = "CREATE TABLE IF NOT EXISTS users (" + 
				"    user_name  TEXT PRIMARY KEY, " + 
				"    first_name TEXT, " + 
				"    last_name  TEXT, " + 
				"    type       INTEGER" + 
				");" + 
				"" + 
				"CREATE TABLE IF NOT EXISTS courses (" + 
				"    subject    TEXT," + 
				"    number     TEXT," + 
				"    days       TEXT," + 
				"    time       INTEGER," + 
				"    duration   INTEGER," + 
				"    location   TEXT," + 
				"    professor  TEXT," + 
				"    PRIMARY KEY (subject, number)," + 
				"    FOREIGN KEY (professor) references users(user_name) ON DELETE CASCADE" + 
				");" + 
				"" + 
				"CREATE TABLE IF NOT EXISTS enrollments (" + 
				"    student        TEXT," + 
				"    subject        TEXT," + 
				"    number         TEXT," + 
				"    grade          REAL," + 
				"    PRIMARY KEY (student, subject, number)," + 
				"    FOREIGN KEY (student) references users(user_name) ON DELETE CASCADE," + 
				"    FOREIGN KEY (subject, number) " + 
				"                   references courses(subject, number) ON DELETE CASCADE" + 
				");";
		
		Statement stmt;
		try {
			stmt = this.conn.createStatement();
			stmt.executeUpdate(createTableSql);
			stmt.close();
		} catch (SQLException e) {
			return false;
		}
		
		return true;
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
		String uri = this.URI_PREFIX + dbPath;

		if(this.conn != null) {
			return false;
		}
		
		// Create parent directory, if applicable
		File parentDir = new File(dbPath).getParentFile();
		if(parentDir != null) {
			parentDir.mkdirs();
		}
		
		// Open connection and initialize Database if necessary
		try {
			this.conn = DriverManager.getConnection(uri);
			Boolean rv = this.initializeDB();
			return rv;
		} catch (SQLException e) {
			return false;
		}
	}
	
	/**
	 * Close the connection with the database.
	 * This is part of the proper cleanup process.
	 */
	public Boolean closeConnection() {
		if(this.conn == null) {
			return false;
		}
		
		try {
			this.conn.close();
			this.conn = null;
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Query the database.
	 * 
	 * @param sql the query to execute.
	 * @return the result of the query.
	 */
	public Object query(String sql, List<Object> params) {
		return null;
	}
	
}
