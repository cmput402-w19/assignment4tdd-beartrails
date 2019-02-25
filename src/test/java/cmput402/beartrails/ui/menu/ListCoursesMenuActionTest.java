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
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

public class ListCoursesMenuActionTest extends TestCase {
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
	
	CourseManager mockCourseManager;
	ListCoursesMenuAction listCoursesMenuAction;


	 /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        mockCourseManager = mock(CourseManager.class);
    	
    	listCoursesMenuAction = new ListCoursesMenuAction();
        listCoursesMenuAction.setCourseManager(mockCourseManager);
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
    	assert(listCoursesMenuAction.toString().contains("courses");
    }
    
    /*
     * Test output when there are no courses in the system
     */
    public void testZeroCourses() {
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(new ArrayList<Course>());
    	
    	listCoursesMenuAction.execute();
    	
    	String output = getOutput().toLowerCase();
    	
    	assert(output.contains("no courses".toLowerCase()));
    	
    }
    
    /*
     * Test output when there is a single course in the system
     */
    public void testOneCourse() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(courseList);
    	
    	listCoursesMenuAction.execute();
    	
    	String output = getOutput();
    	
    	assert(output.contains(course1Location));
    	assert(output.contains(course1Number));
    	assert(output.contains(course1Subject));
    	assert(output.contains(course1Days.toString()));
    	assert(output.contains(course1Start.toString()));
    	assert(output.contains(course1Duration.toString()));
    }
    
    /*
     * Test the output when there is more than one course in the system
     */
    public void testTwoCourses() {
    	List<Course> courseList = new ArrayList<Course>();
    	courseList.add(course1);
    	courseList.add(course2);
    	
    	when(mockCourseManager.getAllCourses())
    		.thenReturn(courseList);
    	
    	listCoursesMenuAction.execute();
    	
    	String output = getOutput();
    	
    	assert(output.contains(course1Location));
    	assert(output.contains(course1Number));
    	assert(output.contains(course1Subject));
    	assert(output.contains(course1Days.toString()));
    	assert(output.contains(course1Start.toString()));
    	assert(output.contains(course1Duration.toString()));
    	
    	assert(output.contains(course2Location));
    	assert(output.contains(course2Number));
    	assert(output.contains(course2Subject));
    	assert(output.contains(course2Days.toString()));
    	assert(output.contains(course2Start.toString()));
    	assert(output.contains(course2Duration.toString()));
    }

}
