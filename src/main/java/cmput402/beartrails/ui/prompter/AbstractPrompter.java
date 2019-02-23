package cmput402.beartrails.ui.prompter;

import java.util.Scanner;

public abstract class AbstractPrompter {
	private final String goBackChar = "b";
	protected Boolean goBack = false;
	protected Boolean invalidInput = false;
	protected String promptText;
	
	AbstractPrompter(String promptText) {
		this.promptText = promptText;
	}
		
	public String promptUser() {
		System.out.print(this.promptText);
		
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine().trim();
		
		if(input.contentEquals(this.goBackChar)) {
			this.goBack = true;
			return "";
		} else if(input.contentEquals("") || !isValid(input)) {
			this.invalidInput = true;
			return "";
		} else {
			return input;
		}
	}
	
	protected abstract Boolean isValid(String userInput);
	
	public String getGoBackChar() {
		return this.goBackChar;
	}
	
	public Boolean inputWasGoBack() {
		return this.goBack;
	}
	
	public Boolean inputWasInvalid() {
		return this.invalidInput;
	}
	
	
}
