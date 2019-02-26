package cmput402.beartrails;

import junit.framework.TestCase;

public class UserTest extends TestCase {
	
	String username1 = "huntc";
	String fname1    = "Corey";
	String lname1    = "Hunt";
	User.Type type1  = User.Type.Student;
	
	String username2 = "repka";
	String fname2    = "Derek";
	String lname2    = "Repka";
	User.Type type2  = User.Type.Admin;
	
	/*
	 * Test equality between an object and itself
	 */
	public void testSameObject() {
		User user1 = new User(username1, fname1, lname1, type1);
		User user2 = user1;
		
		assertTrue(user1.equals(user2));
	}
	
	public void testDifferentTypes() {
		User user1 = new User(username1, fname1, lname1, type1);
		String string = "I'm not a user!";
		
		assertFalse(user1.equals(string));
	}
	
	/*
	 * Different objects, but equal fields/properties
	 */
	public void testEqualFields() {
		User user1 = new User(username1, fname1, lname1, type1);
		User user2 = new User(username1, fname1, lname1, type1);
		
		assertTrue(user1.equals(user2));
	}
	
	/*
	 * Test equality when only username is same
	 */
	public void testUpToUsername() {
		User user1 = new User(username1, fname1, lname1, type1);
		User user2 = new User(username1, fname2, lname2, type2);
		
		assertFalse(user1.equals(user2));

	}
	
	/*
	 * Username and first name equal
	 */
	public void testUpToFname() {
		User user1 = new User(username1, fname1, lname1, type1);
		User user2 = new User(username1, fname1, lname2, type2);
		
		assertFalse(user1.equals(user2));
	}
	
	/*
	 * Username, first name and last name equal
	 */
	public void testUpToLname() {
		User user1 = new User(username1, fname1, lname1, type1);
		User user2 = new User(username1, fname1, lname1, type2);
		
		assertFalse(user1.equals(user2));
	}
}
