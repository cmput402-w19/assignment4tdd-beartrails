package cmput402.beartrails.ui.menu;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
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
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

public class EnrollMenuActionTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    String course1Subject = "cmput";
    String course1Number = "402";
    Course.DaysOfWeek course1Days = Course.DaysOfWeek.MonWedFri;
    Integer course1Start = 13;
    Integer course1Duration = 1;
    String course1Location = "CSC";
    
    String course2Subject = "cmput";
    String course2Number = "300";
    Course.DaysOfWeek course2Days = Course.DaysOfWeek.TueThu;
    Integer course2Start = 10;
    Integer course2Duration = 1;
    String course2Location = "CAB";
    
	private Course course1 = 
			new Course(course1Subject, course1Number, course1Days,
					   course1Start, course1Duration, course1Location);
	private Course course2 = 
			new Course(course2Subject, course2Number, course2Days,
					   course2Start, course2Duration, course2Location);
	
	String username = "huntc";
	String firstName = "Corey";
	String lastName = "Hunt";
	User.Type userType = User.Type.Student;
	
	User newUser = new User(username, 
			   				firstName, 
			   				lastName, 
			   				userType);
	
	IntegerPrompter mockIntegerPrompter;
	CourseManager mockCourseManager;
	ScheduleManager mockScheduleManager;
	EnrollMenuAction enrollMenuAction;


	 /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        mockIntegerPrompter = mock(IntegerPrompter.class);
        mockCourseManager = mock(CourseManager.class);
        mockScheduleManager = mock(ScheduleManager.class);
    	
    	enrollMenuAction = new EnrollMenuAction();
    	enrollMenuAction.setIntegerPrompter(mockIntegerPrompter);
        enrollMenuAction.setCourseManager(mockCourseManager);
        enrollMenuAction.setScheduleManager(mockScheduleManager);
        enrollMenuAction.setUser(newUser);
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
    	assert(enrollMenuAction.toString().contains("enroll");
    }
    
    /*
     * No courses to register in
     */
    public void testZeroCourses() {
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(new ArrayList<Course>());
    	
    	enrollMenuAction.execute();
    	
    	String output = getOutput().toLowerCase();
    	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("no courses"));
    	//Don't attempt to enroll in any courses
    	verify(mockScheduleManager, never())
    		.addCourse(anyString(), anyString());
    	//Don't prompt the user, there's nothing to do
    	verify(mockIntegerPrompter, never())
    		.promptUser(anyString());
    	
    }
    
    /*
     * User successfully registers
     */
    public void testSuccessful() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1");
    	
    	when(mockScheduleManager.addCourse(anyString(), anyString()))
			.thenReturn(true);

    	enrollMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("successful"));
    	//Enroll exactly once in the course
    	verify(mockScheduleManager, times(1))
			.addCourse(course1Subject, course1Number);
    	//Prompt the user exactly once
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(1))
    		.promptUser(anyString(), eq(1), eq(2));
    }

    /*
     * User selects conflicting course on first attempt, 
     * then is successful on second
     */
    public void testFailure() {
       	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("2") // Let's pretend course 2 conflicts
    		.thenReturn("1");
    	
    	when(mockScheduleManager.addCourse(anyString(), anyString()))
			.thenReturn(false)
			.thenReturn(true);

    	enrollMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("again"));
    	assert(output.contains("successful"));
    	//Attempt exactly once to enroll in course 2
    	verify(mockScheduleManager, times(1))
			.addCourse(course2Subject, course2Number);
    	//Attempt exactly once to enroll in course 2
    	verify(mockScheduleManager, times(1))
			.addCourse(course1Subject, course1Number);
    	//Prompt the user exactly twice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User chooses to "go  back"
     */
    public void testGoBack() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("");
    	
    	//This is the line of interest for this test
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(true);

    	enrollMenuAction.execute();
	
    	//Don't enroll
    	verify(mockScheduleManager, never())
			.addCourse(anyString(), anyString());
    	//Prompt the user exactly once
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(1))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User enters invalid input the first time, then
     * is successful the second time
     */
    public void testInvalidInput() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("")
    		.thenReturn("1");
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockScheduleManager.addCourse(anyString(), anyString()))
			.thenReturn(true);

    	enrollMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("successful"));
    	//Enroll exactly once in the course
    	verify(mockScheduleManager, times(1))
			.addCourse(course1Subject, course1Number);
    	//Prompt the user exactly twice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
}
