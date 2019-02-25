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
	
	protected User loggedInUser;
	protected CourseManager courseManager;
	protected GradeManager gradeManager;
	protected ScheduleManager scheduleManager;
	
	protected DoublePrompter doublePrompter;
	protected IntegerPrompter integerPrompter;
	protected StringPrompter stringPrompter;
	
	protected UserManager userManager;
	
	AbstractMenuAction() {
	}
	
	abstract public Boolean execute();
	
	abstract public String toString();
	
	/*
	 * Pavel suggests using 'withXYZ' in contexts similar to this
	 * https://stackoverflow.com/a/26236963/2038127
	 */
	public void setUser(User loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
	public void setCourseManager(CourseManager cm) {
		this.courseManager = cm;
	}
	
	public void setGradeManager(GradeManager gm) {
		this.gradeManager = gm;
	}
	
	public void setScheduleManager(ScheduleManager sm) {
		this.scheduleManager = sm;
	}
	
	public void setUserManager(UserManager um) {
		this.userManager = um;
	}
	
	public void setDoublePrompter(DoublePrompter db) {
		this.doublePrompter = db;
	}
	
	public void setIntegerPrompter(IntegerPrompter ip) {
		this.integerPrompter = ip;
	}
	
	public void setStringPrompter(StringPrompter sp) {
		this.stringPrompter = sp;
	}
	

}
