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

public class AssignTeacherMenuActionTest extends TestCase {
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
	
	String username1 = "snadi";
	String firstName1 = "Sarah";
	String lastName1 = "Nadi";
	User.Type userType1 = User.Type.Professor;
	
	String username2 = "jhoover";
	String firstName2 = "Jim";
	String lastName2 = "Hoover";
	User.Type userType2 = User.Type.Professor;
	
	User professor1 = new User(username1, 
			   				firstName1, 
			   				lastName1, 
			   				userType1);
	
	User professor2 = new User(username2, 
				firstName2, 
				lastName2, 
				userType2);
	
	IntegerPrompter mockIntegerPrompter;
	UserManager mockUserManager;
	CourseManager mockCourseManager;
	ScheduleManager mockScheduleManager;
	AssignTeacherMenuAction assignTeacherMenuAction;


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
        mockUserManager = mock(UserManager.class);
    	
    	assignTeacherMenuAction = new AssignTeacherMenuAction();
    	assignTeacherMenuAction.setIntegerPrompter(mockIntegerPrompter);
        assignTeacherMenuAction.setCourseManager(mockCourseManager);
        assignTeacherMenuAction.setScheduleManager(mockScheduleManager);
        assignTeacherMenuAction.setUserManager(mockUserManager);
    }
    
    //Restore stdout and stdin
    public void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    private String getOutput() {
        return testOut.toString();
    }
    
    /*
     * No courses to register in
     */
    public void testZeroCourses() {
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(new ArrayList<Course>());
    	
    	assignTeacherMenuAction.execute();
    	
    	String output = getOutput().toLowerCase();
    	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("no courses"));
    	//Don't attempt to assignTeacher in any courses
    	verify(mockCourseManager, never())
    		.assignTeacher(anyString(), anyString(), anyString());
    	//Don't prompt the user, there's nothing to do
    	verify(mockIntegerPrompter, never())
    		.promptUser(anyString());
    	
    }
    
    /*
     * No teachers in the system
     */
    public void testZeroTeachers() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(courseList);
    	
    	//This is the important line; no teachers in the system
    	when(mockUserManager.getTeachers())
    		.thenReturn(new ArrayList<User>());

    	assignTeacherMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("no teachers"));
    	//Don't attempt to assign teacher to any courses
    	verify(mockCourseManager, never())
    		.assignTeacher(anyString(), anyString(), anyString());
    	//Don't prompt the user, there's nothing to do
    	verify(mockIntegerPrompter, never())
    		.promptUser(anyString());
    }
    
    /*
     * Teacher successfully assigned to course
     */
    public void testSuccessful() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers()) 
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockCourseManager.assignTeacher(anyString(), 
    										 anyString(), 
    										 anyString()))
			.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("successful"));
    	//Assign teacher exactly once
    	verify(mockCourseManager, times(1))
			.assignTeacher(username1, course1Subject, course1Number);
    	//Prompt the user exactly twice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(2))
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
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers())
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1")
    		.thenReturn("1") //Let's pretend teacher 1 has a conflict
    		.thenReturn("2");
    	
    	when(mockCourseManager.assignTeacher(anyString(), 
    										 anyString(), 
    										 anyString()))
			.thenReturn(false)
			.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("again"));
    	assert(output.contains("successful"));
    	//Attempt exactly once to assign teacher 1
    	verify(mockCourseManager, times(1))
			.assignTeacher(username2, course1Subject, course1Number);
    	//Attempt exactly once to assign teacher 2
    	verify(mockCourseManager, times(1))
			.assignTeacher(username1, course1Subject, course1Number);
    	//Prompt the user exactly thrice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(3))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User chooses to "go  back" at first chance possible
     */
    public void testGoBack1() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers())
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("");
    	
    	//This is the line of interest for this test
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	//Don't assign
    	verify(mockCourseManager, never())
			.assignTeacher(anyString(), anyString(), anyString());
    	//Prompt the user exactly once
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(1))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User asks to "go back" at second possible chance
     */
    public void testGoBack2() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers())
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1")
    		.thenReturn("");
    	
    	//This is the line of interest for this test
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(false)
    		.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	//Don't assign
    	verify(mockCourseManager, never())
			.assignTeacher(anyString(), anyString(), anyString());
    	//Prompt the user exactly twice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User enters invalid input the first time (for the first field), 
     * then is successful the second time
     */
    public void testInvalidInput1() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers())
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("")
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false)
    		.thenReturn(false);
    	
    	when(mockCourseManager.assignTeacher(anyString(), anyString(), anyString()))
			.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("successful"));
    	//Assign teacher exactly once
    	verify(mockCourseManager, times(1))
			.assignTeacher(username1, course1Subject, course1Number);
    	//Prompt the user exactly thrice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(3))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
    /*
     * User enters invalid input the first time (for the second field)
     * then is successful the next time.
     */
    public void testInvalidInput2() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> teacherList = new ArrayList<User>();
    	teacherList.add(professor1);
    	teacherList.add(professor2);
    	
    	when(mockCourseManager.getAllCourses())
			.thenReturn(courseList);
    	
    	when(mockUserManager.getTeachers())
    		.thenReturn(teacherList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1")
    		.thenReturn("")
    		.thenReturn("1");
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(false)
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockCourseManager.assignTeacher(anyString(), anyString(), anyString()))
			.thenReturn(true);

    	assignTeacherMenuAction.execute();
	
    	String output = getOutput().toLowerCase();
	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("successful"));
    	//Assign teacher exactly once
    	verify(mockCourseManager, times(1))
			.assignTeacher(username1, course1Subject, course1Number);
    	//Prompt the user exactly thrice
    	//Should specify bounds for the input
    	verify(mockIntegerPrompter, times(3))
    		.promptUser(anyString(), eq(1), eq(2));
    }
    
}
