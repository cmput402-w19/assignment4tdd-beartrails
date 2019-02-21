package cmput402.beartrails;

import java.io.File;


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



}
