package cmput402.beartrails.ui.menu;

import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyDouble;
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
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

public class AssignGradeMenuActionTest extends TestCase {
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
	
	String username1 = "repka";
	String firstName1 = "Derek";
	String lastName1 = "Repka";
	User.Type userType1 = User.Type.Student;
	
	String username2 = "redfern";
	String firstName2 = "Araien";
	String lastName2 = "Redfern";
	User.Type userType2 = User.Type.Student;
	
	String username3 = "snadi";
	String firstName3 = "Sarah";
	String lastName3 = "Nadi";
	User.Type userType3 = User.Type.Professor;

	
	User student1 = new User(username1, 
			   				firstName1, 
			   				lastName1, 
			   				userType1);
	
	User student2 = new User(username2, 
							firstName2, 
							lastName2, 
							userType2);
	
	User professor = new User(username3,
							  firstName3,
							  lastName3,
							  userType3);
	
	IntegerPrompter mockIntegerPrompter;
	DoublePrompter mockDoublePrompter;
	CourseManager mockCourseManager;
	ScheduleManager mockScheduleManager;
	GradeManager mockGradeManager;
	AssignGradeMenuAction assignGradeMenuAction;


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
        mockDoublePrompter = mock(DoublePrompter.class);
        mockScheduleManager = mock(ScheduleManager.class);
        mockGradeManager = mock(GradeManager.class);
        mockCourseManager = mock(CourseManager.class);
    	
    	assignGradeMenuAction = new AssignGradeMenuAction();
    	assignGradeMenuAction.setIntegerPrompter(mockIntegerPrompter);
    	assignGradeMenuAction.setDoublePrompter(mockDoublePrompter);
        assignGradeMenuAction.setCourseManager(mockCourseManager);
        assignGradeMenuAction.setScheduleManager(mockScheduleManager);
        assignGradeMenuAction.setGradeManager(mockGradeManager);
        assignGradeMenuAction.setUser(professor);
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
    	assert(assignGradeMenuAction.toString().contains("grade");
    }
    
    /*
     * Teacher is assigned to no courses
     */
    public void testZeroCourses() {
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(new ArrayList<Course>());
    	
    	assignGradeMenuAction.execute();
    	
    	String output = getOutput().toLowerCase();
    	
    	//Keep the test general; this isn't the full output
    	assert(output.contains("any courses"));
    	//Don't attempt to assign grade
    	verify(mockGradeManager, never())
    		.assignGrade(anyDouble(), anyString(), 
    				     anyString(), anyString());
    	//Don't prompt the user, there's nothing to do
    	verify(mockIntegerPrompter, never())
    		.promptUser(anyString());
    	verify(mockDoublePrompter, never())
    		.promptUser(anyString());
    }
    
    /*
     * No Students in the class
     */
    public void testZeroStudents() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(new ArrayList<User>());
    	
    	//Select the first course
    	when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
    		.thenReturn("1");

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("no students"));
    	//Don't attempt to assign grade
    	verify(mockGradeManager, never())
    	.assignGrade(anyDouble(), anyString(), 
    			anyString(), anyString());
    	//Prompt the user exactly once (to choose course)
    	verify(mockIntegerPrompter, times(1))
    		.promptUser(anyString(), anyInt(), anyInt());
    	verify(mockDoublePrompter, never())
    		.promptUser(anyString());
    }
    
    /*
     * Teacher successfully assigns a grade
     */
    public void testSuccessful() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("4.0");
    	
    	when(mockGradeManager.assignGrade(anyDouble(), 
    								      anyString(), 
    								      anyString(), 
    								      anyString()))
    		.thenReturn(true);

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("success"));
    	//Assign grade exactly once
    	verify(mockGradeManager, times(1))
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly twice
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly once
    	verify(mockDoublePrompter, times(1))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }

    /*
     * Failure to save grade the first time, but success on second try
     */
    public void testFailure() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("4.0");
    	
    	when(mockGradeManager.assignGrade(anyDouble(), 
    								      anyString(), 
    								      anyString(), 
    								      anyString()))
    		.thenReturn(false)
    		.thenReturn(true);

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("success"));
    	//(Attempt to) assign grade exactly twice
    	verify(mockGradeManager, times(2))
    		.assignGrade(anyDouble(), anyString(), 
    					 anyString(), anyString());
    	//Prompt the user exactly four times
    	verify(mockIntegerPrompter, times(4))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly twice
    	verify(mockDoublePrompter, times(2))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User chooses to "go  back" at first chance possible
     */
    public void testGoBack1() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);

    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("");
    	
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(true);
        	
    	assignGradeMenuAction.execute();

    	//Do not attempt to assign grade
    	verify(mockGradeManager, never())
    		.assignGrade(anyDouble(), anyString(), 
    					 anyString(), anyString());
    	//Prompt the user exactly once
    	verify(mockIntegerPrompter, times(1))
    		.promptUser(anyString(), eq(1), eq(2));
    	verify(mockDoublePrompter, never())
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User asks to "go back" at second possible chance
     */
    public void testGoBack2() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("");
    	
    	when(mockIntegerPrompter.inputWasGoBack())
    		.thenReturn(false)
    		.thenReturn(true);
    	
    	assignGradeMenuAction.execute();

    	//do not assign grade
    	verify(mockGradeManager, never())
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly twice
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    	verify(mockDoublePrompter, never())
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User asks to "go back" at third possible chance
     */
    public void testGoBack3() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("");
    	
    	when(mockDoublePrompter.inputWasGoBack())
    		.thenReturn(true);
    	
    	assignGradeMenuAction.execute();

    	//Do not assign grade
    	verify(mockGradeManager, never())
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly twice
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly once
    	verify(mockDoublePrompter, times(1))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User enters invalid input the first time (for the first field), 
     * then is successful the second time
     */
    public void testInvalidInput1() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("")
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("4.0");
    	
    	when(mockGradeManager.assignGrade(anyDouble(), 
    								      anyString(), 
    								      anyString(), 
    								      anyString()))
    		.thenReturn(true);

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("success"));
    	//Assign grade exactly once
    	verify(mockGradeManager, times(1))
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly thrice
    	verify(mockIntegerPrompter, times(3))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly once
    	verify(mockDoublePrompter, times(1))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User enters invalid input the first time (for the second field)
     * then is successful the next time.
     */
    public void testInvalidInput2() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("")
    		.thenReturn("1");
    	
    	when(mockIntegerPrompter.inputWasInvalid())
    		.thenReturn(false)
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("4.0");
    	
    	when(mockGradeManager.assignGrade(anyDouble(), 
    								      anyString(), 
    								      anyString(), 
    								      anyString()))
    		.thenReturn(true);

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("success"));
    	//Assign grade exactly once
    	verify(mockGradeManager, times(1))
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly thrice
    	verify(mockIntegerPrompter, times(3))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly once
    	verify(mockDoublePrompter, times(1))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
    /*
     * User enters invalid input the first time (for the third field)
     * then is successful the next time.
     */
    public void testInvalidInput3() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	List<User> studentList = new ArrayList<User>();
    	studentList.add(student1);
    	studentList.add(student2);
    	
    	when(mockScheduleManager.getTeacherSchedule(anyString()))
    		.thenReturn(courseList);
    	
    	when(mockCourseManager.getStudentsInCourse(course1Subject, 
    											   course1Number))
    		.thenReturn(studentList);
    	
    	when(mockIntegerPrompter.promptUser(anyString(), 
    										anyInt(), 
    										anyInt()))
    		.thenReturn("1")
    		.thenReturn("1");
    	
    	when(mockDoublePrompter.promptUser(anyString(), 
    									   anyDouble(), 
    									   anyDouble()))
    		.thenReturn("")
    		.thenReturn("4.0");
    	
    	when(mockDoublePrompter.inputWasInvalid())
    		.thenReturn(true)
    		.thenReturn(false);
    	
    	when(mockGradeManager.assignGrade(anyDouble(), 
    								      anyString(), 
    								      anyString(), 
    								      anyString()))
    		.thenReturn(true);

    	assignGradeMenuAction.execute();

    	String output = getOutput().toLowerCase();

    	//Keep the test general; this isn't the full output
    	assert(output.contains("success"));
    	//Assign grade exactly once
    	verify(mockGradeManager, times(1))
    		.assignGrade(4.0d, username1, 
    					 course1Subject, course1Number);
    	//Prompt the user exactly twice
    	verify(mockIntegerPrompter, times(2))
    		.promptUser(anyString(), eq(1), eq(2));
    	//Prompt the user exactly twice
    	verify(mockDoublePrompter, times(2))
    		.promptUser(anyString(), eq(0d), eq(4d));
    }
    
}
