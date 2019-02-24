package cmput402.beartrails.ui.menu;

import cmput402.beartrails.User;
import cmput402.beartrails.ui.prompter.IntegerPrompter;
import cmput402.beartrails.ui.prompter.StringPrompter;

public class AddUserMenuAction extends AbstractMenuAction {
	
	public AddUserMenuAction() {
		this.stringPrompter = new StringPrompter();
		this.integerPrompter = new IntegerPrompter();
	}

	/**
	 * @return true unless there was an error.
	 */
	@Override
	public Boolean execute() {
		if(this.userManager == null) {
			return false;
		}
		
		String username;
		String firstName;
		String lastName;
		User.Type userType;
		User newUser;
		
		//Loop until user is successfully added, or until user says "goBack"
		while(true) {

			//Loop until valid input is received, or until user says "goBack"
			while(true) {
				username = stringPrompter.promptUser("\nPlease enter a username: ");
				if(stringPrompter.inputWasGoBack()) {
					return true;
				} else if(stringPrompter.inputWasInvalid()) {
					stringPrompter.printTryAgain();
				} else {
					break;
				}
			}

			while(true) {
				firstName = stringPrompter.promptUser("\nPlease enter the first name: ");
				if(stringPrompter.inputWasGoBack()) {
					return true;
				} else if(stringPrompter.inputWasInvalid()) {
					stringPrompter.printTryAgain();
				} else {
					break;
				}
			}

			while(true) {
				lastName = stringPrompter.promptUser("\nPlease enter the last name: ");
				if(stringPrompter.inputWasGoBack()) {
					return true;
				} else if(stringPrompter.inputWasInvalid()) {
					stringPrompter.printTryAgain();
				} else {
					break;
				}
			} 

			Integer numValues = User.Type.values().length;
			String typePrompt = "\nPlease select the type of user to create:\n";
			for(Integer i = 1; i <= numValues; i++) {
				typePrompt += i.toString() + "\t" + User.Type.values()[i-1] + "\n";
			}
			typePrompt += "Enter a number (1-" + numValues.toString() + "): ";

			while(true) {
				String typeString = integerPrompter.promptUser(typePrompt);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					Integer typeInt = Integer.valueOf(typeString) - 1;
					userType = User.Type.values()[typeInt];
					break;
				}
			}

			newUser = new User(username, firstName, lastName, userType);
			Boolean rv = userManager.registerUser(newUser);
			
			if(rv == true) {
				return true;
			} //Else try again
		}

	}

}
