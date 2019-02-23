package cmput402.beartrails.ui.prompter;

public abstract class AbstractPrompter {
	private final String goBackChar = "b";
	protected Boolean goBack = false;
	protected Boolean inputError = false;
	protected String promptText;
	
	AbstractPrompter(String promptText) {
		
	}
	
	public String getGoBackChar() {
		return this.goBackChar;
	}
	
	public String promptUser() {
		return "";
	}
	
	public Boolean inputWasGoBack() {
		return false;
	}
	
	public Boolean inputWasInvalid() {
		return false;
	}
	
	protected abstract Boolean isValid(String userInput);
	
}
