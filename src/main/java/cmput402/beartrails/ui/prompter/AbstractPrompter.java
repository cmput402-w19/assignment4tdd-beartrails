package cmput402.beartrails.ui.prompter;

public abstract class AbstractPrompter {
	private final String goBackChar = "b";
	protected Boolean goBack = false;
	protected Boolean inputError = false;
	protected String promptText;
	
	AbstractPrompter(String promptText) {
		
	}
	
	public String promptUser() {
		return "";
	}
	
	public Boolean getGoBack() {
		return false;
	}
	
	protected abstract Boolean isValid(String userInput);
	
}
