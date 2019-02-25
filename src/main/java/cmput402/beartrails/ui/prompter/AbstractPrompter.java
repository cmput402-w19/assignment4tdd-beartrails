package cmput402.beartrails.ui.prompter;

import java.util.Scanner;

public abstract class AbstractPrompter {
	private final String goBackChar = "b";
	private final String quitChar = "q";
	private final String tryAgainText = "Something went wrong, please try again.";
	
	protected Boolean goBack = false;
	protected Boolean quit = false;
	protected Boolean invalidInput = false;
	protected String promptText;
	
	AbstractPrompter() {
	}
		
	public String promptUser(String promptText) {
		System.out.print(promptText);
		
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine().trim();
		
		if(input.contentEquals(this.goBackChar)) {
			this.goBack = true;
			return "";
		} else {
			this.goBack = false;
		}
		
		//Usually the quitChar will be another alias
		//for goBackChar, but it will have special meaning
		//for those menus who check inputWasQuit
		if(input.contentEquals(this.quitChar)) {
			this.quit = true;
			this.goBack = true;
			return "";
		} else {
			this.quit = false;
		}
		
		if(input.contentEquals("") || !isValid(input)) {
			this.invalidInput = true;
			return "";
		} else {
			this.invalidInput = false;
			return input;
		}
	}
	
	protected abstract Boolean isValid(String userInput);
	
	public void printTryAgain() {
		System.out.println(tryAgainText);
	}
	
	
	public String getGoBackChar() {
		return this.goBackChar;
	}
	
	public String getQuitChar() {
		return this.quitChar;
	}
	
	public Boolean inputWasGoBack() {
		return this.goBack;
	}
	
	public Boolean inputWasQuit() {
		return this.quit;
	}
	
	public Boolean inputWasInvalid() {
		return this.invalidInput;
	}
	
	
}
