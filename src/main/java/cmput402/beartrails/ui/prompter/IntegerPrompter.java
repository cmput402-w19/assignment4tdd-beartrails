package cmput402.beartrails.ui.prompter;

public class IntegerPrompter extends AbstractPrompter {
	private Integer minAllowable;
	private Integer maxAllowable;
	
	public IntegerPrompter(String promptText) {
		super(promptText);
	}
	
	public IntegerPrompter(String promptText, 
			               Integer minAllowable, 
			               Integer maxAllowable) {
		super(promptText);
	}

	@Override
	protected Boolean isValid(String userInput) {
		return false;
	}
}
