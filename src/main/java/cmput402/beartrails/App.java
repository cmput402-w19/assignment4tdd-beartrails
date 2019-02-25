package cmput402.beartrails;

import cmput402.beartrails.ui.menu.*;
import cmput402.beartrails.ui.menu.Menu;
import cmput402.beartrails.ui.prompter.StringPrompter;


public class App 
{
	
	
    public static void main( String[] args )
    {
    	Menu menu;
    	User user;
    	ConnectionManager connectionManager;
    	CourseManager courseManager;
    	GradeManager gradeManager;
    	ScheduleManager scheduleManager;
    	UserManager userManager;
    	StringPrompter stringPrompter;
    	
    	connectionManager = new ConnectionManager();
    	connectionManager.openConnection();
    	userManager = new UserManager(connectionManager);
        courseManager = new CourseManager(connectionManager);
        gradeManager = new GradeManager(connectionManager);
    	stringPrompter = new StringPrompter();
    	
    	
        System.out.println("Welcome to BearTrails!");
        while(true) {
        	String username = stringPrompter.promptUser("Enter your username: ");
        	if(stringPrompter.inputWasInvalid()) {
        		stringPrompter.printTryAgain();
        	} else if(stringPrompter.inputWasGoBack()) {
        		System.out.println("Thanks for using BearTrails!");
        		System.exit(0);
        	} else { //valid input!
        		user = userManager.login(username);
        		if(user == null) {
        			System.out.println("Username not recognized.  Please try again.");
        		} else {
        			System.out.println("Login successful!");
        			break;
        		}
        	}
        }
        
        scheduleManager = new ScheduleManager(connectionManager, user);
        
        menu = new Menu(user, courseManager, 
        			    gradeManager, scheduleManager, 
        			    userManager);
        
        switch(user.userType) {
        
        	case Admin:
        		menu.addMenuAction(new ListCoursesMenuAction());
        		menu.addMenuAction(new AddUserMenuAction());
        		menu.addMenuAction(new CreateCourseMenuAction());
        		menu.addMenuAction(new AssignTeacherMenuAction());
        		break;
        	case Professor:
        		menu.addMenuAction(new ListCoursesMenuAction());
        		menu.addMenuAction(new ViewTeacherScheduleMenuAction());
        		menu.addMenuAction(new AssignGradeMenuAction());
        		break;
        	case Student:
        		menu.addMenuAction(new ListCoursesMenuAction());
        		menu.addMenuAction(new EnrollMenuAction());
        		menu.addMenuAction(new ViewStudentScheduleMenuAction());
        		menu.addMenuAction(new ViewGPAMenuAction());
        		break;
        }
        
        menu.go();
    }
}
