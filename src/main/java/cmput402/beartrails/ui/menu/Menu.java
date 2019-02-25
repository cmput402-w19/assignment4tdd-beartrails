package cmput402.beartrails.ui.menu;

import java.util.ArrayList;
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

	private IntegerPrompter integerPrompter;

	private List<AbstractMenuAction> menuActions;

	public Menu(User user, CourseManager courseManager, 
			GradeManager gradeManager, ScheduleManager scheduleManager, 
			UserManager userManager) {
		this.loggedInUser = user;
		this.courseManager = courseManager;
		this.gradeManager = gradeManager;
		this.scheduleManager = scheduleManager;
		this.userManager = userManager;

		this.menuActions = new ArrayList<AbstractMenuAction>();
		this.integerPrompter = new IntegerPrompter();
	}

	public void addMenuAction(AbstractMenuAction menuAction) {
		menuAction.setUser(loggedInUser);
		menuAction.setCourseManager(courseManager);
		menuAction.setGradeManager(gradeManager);
		menuAction.setScheduleManager(scheduleManager);
		menuAction.setUserManager(userManager);

		this.menuActions.add(menuAction);
	}

	public void go() {
		Integer numActions = this.menuActions.size();
		String input;
		Integer selectedIndex;
		AbstractMenuAction selectedAction;
		
		while(true) {

			//Display menu actions
			System.out.println();
			Integer i = 1;
			for(AbstractMenuAction menuAction : this.menuActions) {
				System.out.println(i + "\t" + menuAction.toString());
			}

			while(true) {
				//Prompt user for a selection
				input = integerPrompter.promptUser("Select an option (1-" + 
						numActions + "): ", 1, numActions);

				if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else if(integerPrompter.inputWasGoBack()) {
					System.out.println("Thanks for using BearTrails!");
					return;
				} else { //valid input!
					break; 
				}
			}

			
			selectedIndex = Integer.valueOf(input) - 1;
			selectedAction = menuActions.get(selectedIndex);

			selectedAction.execute();
		}
	}

	public void setIntegerPrompter(IntegerPrompter ip) {
		this.integerPrompter = ip;
	}
}
