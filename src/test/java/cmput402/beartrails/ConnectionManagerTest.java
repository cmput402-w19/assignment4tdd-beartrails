package cmput402.beartrails;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Note: purposefully NOT using mocks or stubs in this class,
 *       as these tests become trivial if not actually accessing a DB on file.
 *       That is, these are integration tests, not unit tests.
 */

import junit.framework.TestCase;

public class ConnectionManagerTest extends TestCase {
	
	private final String dbTestFile = "db/test-temp.db";

	public ConnectionManagerTest(String name) {
		super(name);
	}

	/*
	 * Simply tests that a database file is created if it didn't yet exist.
	 * There doesn't appear to be a way to tell whether a file is currently open/in use.
	 * Also, calling openConnection should initialize the database, but it's not possible
	 * to test for that without calling query(), and that will be done in other tests.
	 */
	public void testOpenConnection() {
		File db = new File(this.dbTestFile);
		db.delete();
		assertFalse(db.exists());
		
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		assertTrue(db.exists());
		
		cm.closeConnection();
	
	}
	
	public void testOpenDuplicateConnect() {
		File db = new File(this.dbTestFile);
		db.delete();
		
		ConnectionManager cm = new ConnectionManager();
		Boolean rv = cm.openConnection(this.dbTestFile);
		assertTrue(rv);
		
		// Should refuse to open connection again.
		rv = cm.openConnection(this.dbTestFile);
		assertFalse(rv);
		
		cm.closeConnection();
	}
	
	public void testCloseConnection() {
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection();
		
		Boolean rv = cm.closeConnection();
		assertTrue(rv);
		
		rv = cm.closeConnection();
		assertFalse(rv);
		
	}

	/*
	 * Test inserting data into a row, providing the expected data types.
	 * Then selects the same data to ensure we can read it properly.
	 * 
	 * Ideally, execute() would be called in a setUp function, and then we would just test query().
	 * But since both functions are not trustworthy, it's non-trivial to test them separately.
	 */
	public void testInsertSelectUser() {
		// Initialize fresh database
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		//Insert single row into users table
		String sql = "INSERT INTO users" +
		             "VALUES ('huntc', 'Corey', 'Hunt', 2);";
		List<Object> params = new ArrayList<Object>();
		Boolean rv = cm.execute(sql, params);
		assertTrue(rv);
		
		//SELECT row from table
		sql = "SELECT * FROM users";
		List<List<Object>> results = cm.query(sql, params);
		
		//1 row
		assert(1 == results.size());
		//4 columns
		assert(4 == results.get(0).size());
		
		List<Object> row = results.get(0);
		String uname = (String) row.get(0);
		String fname = (String) row.get(1);
		String lname = (String) row.get(2);
		Integer type = (Integer) row.get(3);
		
		//Ensure results came back in correct order
		assert("huntc" == uname);
		assert("Corey" == fname);
		assert("Hunt" == lname);
		assert(2 == type);
		
		cm.closeConnection();
	}


}
