package cmput402.beartrails.ui.menu;

public class ViewGPAMenuAction extends AbstractMenuAction {

	@Override
	public Boolean execute() {
		if(loggedInUser == null || gradeManager == null) {
			return false;
		}
		
		Double gpa = gradeManager.getStudentGPA(loggedInUser.username);
		
		System.out.println("Your GPA is currently: " + gpa.toString());
		
		return true;
	}

}
