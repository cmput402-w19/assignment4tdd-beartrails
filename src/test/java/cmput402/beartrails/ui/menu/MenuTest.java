package cmput402.beartrails.ui.menu;

import cmput402.beartrails.ConnectionManager;
import cmput402.beartrails.CourseManager;
import cmput402.beartrails.GradeManager;
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class MenuTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

	
	private IntegerPrompter mockIntegerPrompter;
	private AddUserMenuAction mockAddUserMenuAction;
	private CreateCourseMenuAction mockCreateCourseMenuAction;
	
	private CourseManager mockCourseManager;
	private GradeManager mockGradeManager;
	private ScheduleManager mockScheduleManager;
	private UserManager mockUserManager;
	private User mockUser;
	
	private Menu menu;
	
	public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

		
		mockIntegerPrompter = mock(IntegerPrompter.class);
		mockAddUserMenuAction = mock(AddUserMenuAction.class);
		mockCreateCourseMenuAction = mock(CreateCourseMenuAction.class);
		
		mockCourseManager = mock(CourseManager.class);
		mockGradeManager = mock(GradeManager.class);
		mockScheduleManager = mock(ScheduleManager.class);
		mockUserManager = mock(UserManager.class);
		
		menu = new Menu(mockUser, mockCourseManager, 
						mockGradeManager, mockScheduleManager, 
						mockUserManager);
		menu.setIntegerPrompter(mockIntegerPrompter);
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
	 * Test that MenuActions are properly instantiated when added
	 */
	public void testAddMenuAction() {
		menu.addMenuAction(mockAddUserMenuAction);
		menu.addMenuAction(mockCreateCourseMenuAction);
		
		verify(mockAddUserMenuAction).setUser(mockUser);
		verify(mockAddUserMenuAction).setCourseManager(mockCourseManager);
		verify(mockAddUserMenuAction).setGradeManager(mockGradeManager);
		verify(mockAddUserMenuAction).setScheduleManager(mockScheduleManager);
		verify(mockAddUserMenuAction).setUserManager(mockUserManager);
	}
	
	public void testDisplayAction() {
		menu.addMenuAction(mockAddUserMenuAction);
		
		String out = "Test menu action";
		
		when(mockAddUserMenuAction.toString())
			.thenReturn(out);
		
		when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
			.thenReturn("");
		
		when(mockIntegerPrompter.inputWasGoBack())
			.thenReturn(true);
		
		menu.go();
		
		String output = getOutput();
		
		assert(output.contains(out));
		assert(output.contains("1"));
		verify(mockIntegerPrompter).promptUser(anyString(), eq(1), eq(1));
	}
	
	public void testSelectAction() {
		menu.addMenuAction(mockAddUserMenuAction);
		menu.addMenuAction(mockCreateCourseMenuAction);

		when(mockIntegerPrompter.promptUser(anyString(), anyInt(), anyInt()))
				.thenReturn("1")
				.thenReturn("");
		
		when(mockIntegerPrompter.inputWasGoBack())
			.thenReturn(false)
			.thenReturn(true);
		
		menu.go();
		
		verify(mockAddUserMenuAction).execute();
		verify(mockIntegerPrompter, times(2)).promptUser(anyString(), eq(1), eq(2));
	}

}
