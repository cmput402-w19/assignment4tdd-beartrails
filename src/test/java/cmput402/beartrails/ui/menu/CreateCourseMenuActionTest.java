package cmput402.beartrails.ui.menu;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import cmput402.beartrails.Course;
import cmput402.beartrails.CourseManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;


public class CreateCourseMenuActionTest extends TestCase {
	private final InputStream systemIn = System.in;
	private final PrintStream systemOut = System.out;

	private ByteArrayInputStream testIn;
	private ByteArrayOutputStream testOut;

	String subject = "cmput";
	String number = "401";
	String days = String.valueOf(Course.DaysOfWeek.MonWedFri.ordinal())+1;
	String startTime = "13";
	String duration = "1";
	String location = "CSC";
	Course newCourse = new Course(subject, 
			number, 
			Course.DaysOfWeek.MonWedFri,
			Integer.valueOf(startTime), 
			Integer.valueOf(duration),
			location);

	StringPrompter mockStringPrompter;
	IntegerPrompter mockIntegerPrompter;
	CourseManager mockCourseManager;
	CreateCourseMenuAction createCourseMenuAction;

	/*
	 * Idea of faking stdin and stdout
	 * taken from Antonio
	 * https://stackoverflow.com/a/50721326/2038127
	 */
	//Fake stdout
	public void setUp() {
		testOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOut));

		mockStringPrompter = mock(StringPrompter.class);
		mockIntegerPrompter = mock(IntegerPrompter.class);
		mockCourseManager = mock(CourseManager.class);

		createCourseMenuAction = new CreateCourseMenuAction();
		createCourseMenuAction.setCourseManager(mockCourseManager);
		createCourseMenuAction.setStringPrompter(mockStringPrompter);
		createCourseMenuAction.setIntegerPrompter(mockIntegerPrompter);
	}

	//Restore stdout and stdin
	public void tearDown() {
		System.setIn(systemIn);
		System.setOut(systemOut);
	}

	private String getOutput() {
		return testOut.toString();
	}

	public void testHappyPath() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 1 string
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	/*
	 * Error encountered when trying to create the course the first time
	 * Successful second attempt.
	 */
	public void testDBError() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration)
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(false)
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//(attempt to) Create course exactly twice
		verify(mockCourseManager, times(2)).createCourse(any(Course.class));
		//Prompt for 4 strings
		verify(mockStringPrompter, times(4)).promptUser(anyString());
		//Prompt twice for Course number
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt twice for Days
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt twice for startTime
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt twice for duration
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput1() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn("")
		.thenReturn(subject)
		.thenReturn(location);

		when(mockStringPrompter.inputWasInvalid())
		.thenReturn(true)
		.thenReturn(false);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 3 string
		verify(mockStringPrompter, times(3)).promptUser(anyString());
		//Course number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput2() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn("")
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasInvalid())
		.thenReturn(true)
		.thenReturn(false);
		
		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 2 string
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput3() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn("")
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasInvalid())
		.thenReturn(false)
		.thenReturn(true)
		.thenReturn(false);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 3 string
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt twice for Days
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput4() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn("")
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasInvalid())
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(true)
		.thenReturn(false);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 3 string
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt twice for startTime
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput5() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn("")
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasInvalid())
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(true)
		.thenReturn(false);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 3 string
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt twice for duration
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testInvalidInput6() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn("")
		.thenReturn(location);

		when(mockStringPrompter.inputWasInvalid())
		.thenReturn(false)
		.thenReturn(true)
		.thenReturn(false);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		assert(output.contains("success"));
		//Create course exactly once
		verify(mockCourseManager, times(1)).createCourse(eq(newCourse));
		//Prompt for 4 string
		verify(mockStringPrompter, times(3)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Prompt once for Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//Prompt once for startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//Prompt once for duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack1() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn("")
		.thenReturn(subject)
		.thenReturn(location);

		when(mockStringPrompter.inputWasGoBack())
		.thenReturn(true);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(1)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack2() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);


		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn("")
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasGoBack())
		.thenReturn(true);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(1)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack3() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn("")
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasGoBack())
		.thenReturn(false)
		.thenReturn(true);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(1)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack4() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn("")
		.thenReturn(startTime)
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasGoBack())
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(true);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(1)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(0)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack5() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn(location);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn("")
		.thenReturn(duration);
		
		when(mockIntegerPrompter.inputWasGoBack())
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(false)
		.thenReturn(true);

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(1)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

	public void testGoBack6() {
		when(mockStringPrompter.promptUser(anyString()))
		.thenReturn(subject)
		.thenReturn("")
		.thenReturn(location);
		
		when(mockStringPrompter.inputWasGoBack())
		.thenReturn(false)
		.thenReturn(true);

		when(mockIntegerPrompter.promptUser(anyString(), 
				anyInt(), 
				anyInt()))
		.thenReturn(number)
		.thenReturn(days)
		.thenReturn(startTime)
		.thenReturn(duration);
	

		when(mockCourseManager.createCourse(any(Course.class)))
		.thenReturn(true);

		createCourseMenuAction.execute();

		String output = getOutput().toLowerCase();

		//Do not create course		
		verify(mockCourseManager, times(0)).createCourse(eq(newCourse));
		//String
		verify(mockStringPrompter, times(2)).promptUser(anyString());
		//Course Number
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(100), 
				eq(699));
		//Days
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(2));
		//startTime
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(8), 
				eq(20));
		//duration
		verify(mockIntegerPrompter, times(1)).promptUser(anyString(), 
				eq(1), 
				eq(3));
	}

}