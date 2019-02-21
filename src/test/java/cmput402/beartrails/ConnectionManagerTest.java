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
	 * This is also an example of obtaining 1 row of results with multiple columns.
	 * 
	 * Ideally, execute() would be called in a setUp function, and then we would just test query().
	 * But since both functions are not trustworthy, it's non-trivial to test them separately.
	 */
	public void testSelectUser() {
		// Initialize fresh database
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		//Insert single row into users table
		String sql = "INSERT INTO users " +
		             "VALUES ('huntc', 'Corey', 'Hunt', 2);";
		Boolean rv = cm.execute(sql);
		assertTrue(rv);
				
		//SELECT row from table
		sql = "SELECT * FROM users";
		List<List<Object>> results = cm.query(sql);
		
		//1 row
		assert(1 == results.size());
		//4 columns
		assert(4 == results.get(0).size());
		
		List<Object> row = results.get(0);
		assert(row.get(0) instanceof String);
		String uname = (String) row.get(0);
		assert(row.get(1) instanceof String);
		String fname = (String) row.get(1);
		assert(row.get(2) instanceof String);
		String lname = (String) row.get(2);
		assert(row.get(3) instanceof Integer);
		Integer type = (Integer) row.get(3);
		
		//Ensure results came back in correct order
		assert("huntc".contentEquals(uname));
		assert("Corey".contentEquals(fname));
		assert("Hunt".contentEquals(lname));
		assert(2 == type);
		
		cm.closeConnection();
	}
	
	/*
	 * Test inserting into and selecting from enrollments table.
	 * Test whether REAL numbers are still returned as floats, 
	 * even if they're represented differently when inserted.
	 * (i.e. quoted floats or unquoted integers)
	 * 
	 * This is also a test of obtaining a result set with 1 column and multiple rows.
	 */
	public void testSelectGrades() {
		// Initialize fresh database
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		String sql = "INSERT INTO users VALUES (\"huntc\", \"Corey\", \"Hunt\", 2);\n" + 
				"INSERT INTO courses VALUES (\"cmput\", \"402\", \"TueThur\", 13, 1, \"CAB\", null);\n" + 
				"INSERT INTO courses VALUES (\"cmput\", \"300\", \"MonWedFri\", 12, 1, \"CSC\", null);\n" +
				"INSERT INTO courses VALUES (\"cmput\", \"340\", \"TueThur\", 15, 1, \"ETLC\", null);\n" +
				//String representation of float
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"402\", \"3.7\");\n" + 
				//Unquoted float
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"300\", 3.3);\n" +
				//Unquoted integer
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"340\", 3)";
		
		cm.execute(sql);
		
		sql = "SELECT grade FROM enrollments " +
		      "WHERE student = \"huntc\" " +
		      "ORDER BY grade DESC";
		List<List<Object>> results = cm.query(sql);
		
		//3 rows
		assert(3 == results.size());
		//1 column
		assert(1 == results.get(0).size());
		
		Object grade1 = results.get(0).get(0);
		Object grade2 = results.get(1).get(0);
		Object grade3 = results.get(2).get(0);
		
		assert(grade1 instanceof Double);
		assert(grade2 instanceof Double);
		assert(grade3 instanceof Double);
		
		assert(3.7 == (Double) grade1);
		assert(3.3 == (Double) grade2);
		assert(3.0 == (Double) grade3);
		
		cm.closeConnection();
		
	}
	
	/*
	 * Test executing a query which returns 0 results
	 */
	public void testSelectNoMatch() {
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		String sql = "INSERT INTO courses VALUES (\"cmput\", \"402\", \"TueThur\", 13, 1, \"CAB\", null);\n" + 
		"INSERT INTO courses VALUES (\"cmput\", \"300\", \"MonWedFri\", 12, 1, \"CSC\", null);\n" +
		"INSERT INTO courses VALUES (\"cmput\", \"340\", \"TueThur\", 15, 1, \"ETLC\", null);\n";
		
		Boolean rv = cm.execute(sql);
		assertTrue(rv);
		
		sql = "SELECT * FROM courses WHERE number = 201";
		List<List<Object>> results = cm.query(sql);
		
		assert(0 == results.size());		
		
		cm.closeConnection();
	}
	
	/*
	 * Test that we can successfully update values
	 */
	public void testUpdateGrade() {
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		String sql = "INSERT INTO users VALUES (\"huntc\", \"Corey\", \"Hunt\", 2);\n" + 
				"INSERT INTO courses VALUES (\"cmput\", \"402\", \"TueThur\", 13, 1, \"CAB\", null);\n" + 
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"402\", 3.7);\n";
		
		Boolean rv = cm.execute(sql);
		assertTrue(rv);
		
		sql = "SELECT grade FROM enrollments";
		List<List<Object>> results = cm.query(sql);
		
		assert(1 == results.size());
		assert(1 == results.get(0).size());
		assert(3.7 == (Double) results.get(0).get(0));
		
		sql = "UPDATE enrollments SET grade = 3.0\n" +
		      "WHERE student = 'huntc' and subject = 'cmput' and number = '402';";
		rv = cm.execute(sql);
		assertTrue(rv);
		
		sql = "SELECT grade FROM enrollments";
		results = cm.query(sql);
		
		//1 row
		assert(1 == results.size());
		//1 column
		assert(1 == results.get(0).size());
		//New grade value
		assert(3.0 == (Double) results.get(0).get(0));
		
		cm.closeConnection();
	}
	
	/*
	 * Test that we can join tables as expected, 
	 * and that the results are in the expected order.
	 * 
	 */
	public void testJoinTables() {
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		String sql = "INSERT INTO users VALUES (\"huntc\", \"Corey\", \"Hunt\", 2);\n" + 
				"INSERT INTO courses VALUES (\"cmput\", \"402\", \"TueThur\", 13, 1, \"CAB\", null);\n" + 
				"INSERT INTO courses VALUES (\"cmput\", \"300\", \"MonWedFri\", 12, 1, \"CSC\", null);\n" +
				"INSERT INTO courses VALUES (\"cmput\", \"340\", \"TueThur\", 15, 1, \"ETLC\", null);\n" +
				//String representation of float
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"402\", \"3.7\");\n" + 
				//Unquoted float
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"300\", 3.3);\n" +
				//Unquoted integer
				"INSERT INTO enrollments VALUES (\"huntc\", \"cmput\", \"340\", 3)";
		
		Boolean rv = cm.execute(sql);
		assertTrue(rv);
		
		sql = "SELECT subject, number, time, duration \n" + 
				"FROM enrollments NATURAL JOIN courses \n" + 
				"WHERE student=\"huntc\";\n" +
				"ORDER BY number ASC";
		List<List<Object>> results = cm.query(sql);
		
		//3 rows
		assert(3 == results.size());
		//4 columns
		assert(4 == results.get(0).size());
		
		List<Object> row = results.get(0);
		assert(row.get(0) instanceof String);
		assert("cmput".contentEquals((String) row.get(0)));
		assert(row.get(1) instanceof String);
		assert("300".contentEquals((String) row.get(1)));
		assert(row.get(2) instanceof Integer);
		assert(12 == (Integer) row.get(2));
		assert(row.get(3) instanceof Integer);
		assert(1 == (Integer) row.get(3));
		
		cm.closeConnection();
	}
	
	/*
	 * Test that the execute function fails gracefully when given bad sql
	 */
	public void testBadExecute() {
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		//Table users has 4 columns, but we only provide 3 values
		String sql = "INSERT INTO users VALUES ('huntc', 'Corey', 2);";
		
		Boolean rv = cm.execute(sql);
		assertFalse(rv);
		
		cm.closeConnection();
	}
	
	/*
	 * Test that the query function fails gracefully when given bad sql
	 */
	public void testBadQuery() {
		File db = new File(this.dbTestFile);
		db.delete();
		ConnectionManager cm = new ConnectionManager();
		cm.openConnection(this.dbTestFile);
		
		String sql = "INSERT INTO users VALUES (\"huntc\", \"Corey\", \"Hunt\", 2);\n";
		Boolean rv = cm.execute(sql);
		assertTrue(rv);
		
		//There is no column called "name"
		sql = "SELECT name FROM users;";
		List<List<Object>> results = cm.query(sql);
		
		assert(null == results);
		
		cm.closeConnection();
	}


}
