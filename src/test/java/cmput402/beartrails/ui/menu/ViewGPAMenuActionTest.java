package cmput402.beartrails.ui.menu;

import junit.framework.TestCase;

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

import java.util.ArrayList;
import java.util.List;

import cmput402.beartrails.Course;
import cmput402.beartrails.CourseManager;
import cmput402.beartrails.GradeManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

public class ViewGPAMenuActionTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
	
	GradeManager mockGradeManager;
	ViewGPAMenuAction viewGPAMenuAction;
	
	String username = "huntc";
	String firstName = "Corey";
	String lastName = "Hunt";
	User.Type userType = User.Type.Student;
	User newUser;


	 /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        newUser = new User(username, 
        				   firstName, 
        				   lastName, 
        				   userType);
        
        mockGradeManager = mock(GradeManager.class);
    	
    	viewGPAMenuAction = new ViewGPAMenuAction();
    	viewGPAMenuAction.setUser(newUser);
        viewGPAMenuAction.setGradeManager(mockGradeManager);
    }

	//Restore stdout and stdin
    public void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private String getOutput() {
        return testOut.toString();
    }	
    
    
    public void testGetGPA() {
    	when(mockGradeManager.getStudentGPA(anyString()))
    		.thenReturn(Double.parseDouble("3.65"));
    	
    	String output = getOutput();
    	
    	assert(output.contains("3.65"));
    	verify(mockGradeManager).getStudentGPA(username);
    }
}
