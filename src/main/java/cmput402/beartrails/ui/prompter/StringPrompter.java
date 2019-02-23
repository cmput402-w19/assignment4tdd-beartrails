package cmput402.beartrails.ui.prompter;

public class StringPrompter extends AbstractPrompter {
	
	public StringPrompter(String promptText) {
		super(promptText);
	}

	@Override
	protected Boolean isValid(String userInput) {
		return false;
	}

}
