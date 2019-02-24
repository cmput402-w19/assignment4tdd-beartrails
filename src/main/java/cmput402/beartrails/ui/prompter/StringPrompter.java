package cmput402.beartrails.ui.prompter;

import java.util.Scanner;

public class StringPrompter extends AbstractPrompter {

	@Override
	protected Boolean isValid(String userInput) {
		//https://stackoverflow.com/a/4047836/2038127        
        for(int i = 0; i < userInput.length(); i++) {
        	if(!Character.isLetter(userInput.charAt(i))) {
        		return false;
        	}
        }
        return true;
	}

}
