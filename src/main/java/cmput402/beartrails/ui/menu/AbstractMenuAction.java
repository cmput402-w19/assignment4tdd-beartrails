package cmput402.beartrails.ui.menu;

import cmput402.beartrails.CourseManager;
import cmput402.beartrails.GradeManager;
import cmput402.beartrails.ScheduleManager;
import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;

public abstract class AbstractMenuAction {
	
	private User loggedInUser;
	private CourseManager courseManager;
	private GradeManager gradeManager;
	private ScheduleManager scheduleManager;
	private UserManager userManager;
	
	AbstractMenuAction() {

	}
	
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
	
	abstract public Boolean execute();

}
