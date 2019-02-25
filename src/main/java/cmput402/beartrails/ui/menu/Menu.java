package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.CourseManager;
import cmput402.beartrails.GradeManager;
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.IntegerPrompter;

public class Menu {
	private UserManager userManager;
	private GradeManager gradeManager;
	private CourseManager courseManager;
	private ScheduleManager scheduleManager;
	
	private User loggedInUser;
	
	private List<AbstractMenuAction> menuActions;
	
	public Menu(User user, CourseManager courseManager, 
			    GradeManager gradeManager, ScheduleManager scheduleManager, 
			    UserManager userManager) {
		
	}
	
	public void addMenuAction(AbstractMenuAction menuAction) {
		
	}
	
	public Boolean go() {
		return false;
	}
	
	public void setIntegerPrompter(IntegerPrompter ip) {
		
	}
}
