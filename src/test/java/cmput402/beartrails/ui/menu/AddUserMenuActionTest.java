package cmput402.beartrails.ui.menu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

public class AddUserMenuActionTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    String username = "huntc";
	String firstName = "Corey";
	String lastName = "Hunt";
	String userType = String.valueOf(User.Type.Student.ordinal()+1);
	User newUser;
	
	StringPrompter mockStringPrompter;
	IntegerPrompter mockIntegerPrompter;
	UserManager mockUserManager;
	AddUserMenuAction addUserMenuAction;


	 /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        newUser = new User(username.trim(), 
        				   firstName.trim(), 
        				   lastName.trim(), 
        				   User.Type.Student);
        
        mockStringPrompter = mock(StringPrompter.class);
        mockIntegerPrompter = mock(IntegerPrompter.class);
    	mockUserManager = mock(UserManager.class);
    	
    	addUserMenuAction = new AddUserMenuAction();
        addUserMenuAction.setUserManager(mockUserManager);
        addUserMenuAction.setStringPrompter(mockStringPrompter);
        addUserMenuAction.setIntegerPrompter(mockIntegerPrompter);
    }
    
    //Restore stdout and stdin
    public void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private String getOutput() {
        return testOut.toString();
    }
    
    public void testToString() {
    	assert(addUserMenuAction.toString().contains("new user"));
    }
    
    public void testHappyPath() {
    	
    	when(mockStringPrompter.promptUser(anyString()))
    		.thenReturn(username)
    		.thenReturn(firstName)
    		.thenReturn(lastName);
    		
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn(userType);
    	
    	when(mockUserManager.registerUser(any(User.class)))
    		.thenReturn(true);
        
        addUserMenuAction.execute();
        
        verify(mockUserManager, times(1)).registerUser(eq(newUser));
    }
    
    /*
     * If there is a DB error the first time, the system should
     * prompt the user to try a second time. 
     * We are successful the second time.
     */
    public void testDBError() {
    	when(mockStringPrompter.promptUser(anyString()))
			.thenReturn(username)
			.thenReturn(firstName)
			.thenReturn(lastName)
			.thenReturn(username)
			.thenReturn(firstName)
			.thenReturn(lastName);
    	
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn(userType)
    		.thenReturn(userType);
	
    	//This is where we introduce the "error" the first time
    	when(mockUserManager.registerUser(any(User.class)))
    		.thenReturn(false)
    		.thenReturn(true);
    
    	addUserMenuAction.execute();
    
    	verify(mockUserManager, times(2)).registerUser(any(User.class));
    }
    
    /*
     * User entry error on first field
     * We allow the user to try again
     */
    public void testInputError1() {
    	when(mockStringPrompter.promptUser(anyString()))
    		.thenReturn("")
    		.thenReturn(username)
    		.thenReturn(firstName)
    		.thenReturn(lastName);
    	
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn(userType);

    	when(mockStringPrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockUserManager.registerUser(any(User.class)))
    		.thenReturn(true);

    	addUserMenuAction.execute();

    	verify(mockStringPrompter, times(4)).promptUser(anyString());
    	verify(mockIntegerPrompter, times(1)).promptUser(anyString());
    	verify(mockUserManager).registerUser(any(User.class));
    }
    
    /*
     * User entry error on second field
     * We allow the user to try again
     */
    public void testInputError2() {
    	when(mockStringPrompter.promptUser(anyString()))
    		.thenReturn(username)
    		.thenReturn("")
    		.thenReturn(firstName)
    		.thenReturn(lastName);
    	
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn(userType);

    	when(mockStringPrompter.inputWasInvalid())
    		.thenReturn(false)
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockUserManager.registerUser(any(User.class)))
			.thenReturn(true);

    	addUserMenuAction.execute();

    	verify(mockStringPrompter, times(4)).promptUser(anyString());
    	verify(mockIntegerPrompter, times(1)).promptUser(anyString());
    	verify(mockUserManager).registerUser(any(User.class));
    }
    
    /*
     * User entry error on third field
     * We allow the user to try again
     */
    public void testInputError3() {
    	when(mockStringPrompter.promptUser(anyString()))
    		.thenReturn(username)
    		.thenReturn(firstName)
    		.thenReturn("")
    		.thenReturn(lastName);
    	
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn(userType);

    	when(mockStringPrompter.inputWasInvalid())
    		.thenReturn(false)
    		.thenReturn(false)
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockUserManager.registerUser(any(User.class)))
			.thenReturn(true);

    	addUserMenuAction.execute();

    	verify(mockStringPrompter, times(4)).promptUser(anyString());
    	verify(mockIntegerPrompter, times(1)).promptUser(anyString());
    	verify(mockUserManager).registerUser(any(User.class));
    }
    
    /*
     * User entry error on fourth field
     * We allow the user to try again
     */
    public void testInputError4() {
    	when(mockStringPrompter.promptUser(anyString()))
    		.thenReturn(username)
    		.thenReturn(firstName)
    		.thenReturn(lastName);
    	
    	when(mockIntegerPrompter.promptUser(anyString()))
    		.thenReturn("")
    		.thenReturn(userType);

    	when(mockStringPrompter.inputWasInvalid())
    		.thenReturn(false);
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockUserManager.registerUser(any(User.class)))
			.thenReturn(true);

    	addUserMenuAction.execute();

    	verify(mockStringPrompter, times(3)).promptUser(anyString());
    	verify(mockIntegerPrompter, times(2)).promptUser(anyString());
    	verify(mockUserManager).registerUser(any(User.class));
    }
    
    /*
     * Choose to go back at the first possible chance
     */
    public void testGoBack1() {
    	when(mockStringPrompter.promptUser(anyString()))
			.thenReturn("");
    	
    	when(mockStringPrompter.inputWasGoBack())
    		.thenReturn(true);
    	
    	addUserMenuAction.execute();
    	
    	verify(mockStringPrompter, times(1)).promptUser(anyString());
    	verify(mockUserManager, never()).registerUser(any(User.class));
    }
    
    /*
     * Choose to go back at the second possible chance
     */
    public void testGoBack2() {
    	when(mockStringPrompter.promptUser(anyString()))
			.thenReturn(username)
			.thenReturn("");
    	
    	when(mockStringPrompter.inputWasGoBack())
    		.thenReturn(false)
    		.thenReturn(true);
    	
    	addUserMenuAction.execute();
    	
    	verify(mockStringPrompter, times(2)).promptUser(anyString());
    	verify(mockUserManager, never()).registerUser(any(User.class));
    }
    
    /*
     * Choose to go back at the third possible chance
     */
    public void testGoBack3() {
    	when(mockStringPrompter.promptUser(anyString()))
			.thenReturn(username)
			.thenReturn(firstName)
			.thenReturn("");
    	
    	when(mockStringPrompter.inputWasGoBack())
    		.thenReturn(false)
    		.thenReturn(false)
    		.thenReturn(true);
    	
    	addUserMenuAction.execute();
    	
    	verify(mockStringPrompter, times(3)).promptUser(anyString());
    	verify(mockIntegerPrompter, never()).promptUser(anyString());
    	verify(mockUserManager, never()).registerUser(any(User.class));
    }
    
    /*
     * Choose to go back at the fourth possible chance
     */
    public void testGoBack4() {
    	when(mockStringPrompter.promptUser(anyString()))
			.thenReturn(username)
			.thenReturn(firstName)
			.thenReturn(lastName)
			.thenReturn("");
    	
    	when(mockStringPrompter.inputWasGoBack())
    		.thenReturn(false);
    	
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(true);
    	
    	addUserMenuAction.execute();
    	
    	verify(mockStringPrompter, times(3)).promptUser(anyString());
    	verify(mockIntegerPrompter, times(1)).promptUser(anyString());
    	verify(mockUserManager, never()).registerUser(any(User.class));
    }

}
