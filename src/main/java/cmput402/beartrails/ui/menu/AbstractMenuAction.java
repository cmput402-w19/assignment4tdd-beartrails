package cmput402.beartrails.ui.menu;

import cmput402.beartrails.CourseManager;
import cmput402.beartrails.GradeManager;
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;
import cmput402.beartrails.ui.prompter.DoublePrompter;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;

public abstract class AbstractMenuAction {
	
	private User loggedInUser;
	private CourseManager courseManager;
	private GradeManager gradeManager;
	private ScheduleManager scheduleManager;
	
	private DoublePrompter doublePrompter;
	private IntegerPrompter integerPrompter;
	private StringPrompter stringPrompter;
	
	private UserManager userManager;
	
	AbstractMenuAction() {
	}
	
	abstract public Boolean execute();
	
	/*
	 * Pavel suggests using 'withXYZ' in contexts similar to this
	 * https://stackoverflow.com/a/26236963/2038127
	 */
	public AbstractMenuAction withUser(User loggedInUser) {
		return null;
	}
	
	public AbstractMenuAction withCourseManager(CourseManager cm) {
		return null;
	}
	
	public AbstractMenuAction withGradeManager(GradeManager gm) {
		return null;
	}
	
	public AbstractMenuAction withScheduleManager(ScheduleManager sm) {
		return null;
	}
	
	public AbstractMenuAction withUserManager(UserManager um) {
		return null;
	}
	
	public AbstractMenuAction withDoublePrompter(DoublePrompter db) {
		return null;
	}
	
	public AbstractMenuAction withIntegerPrompter(IntegerPrompter ip) {
		return null;
	}
	
	public AbstractMenuAction withStringPrompter(StringPrompter sp) {
		return null;
	}
	

}
