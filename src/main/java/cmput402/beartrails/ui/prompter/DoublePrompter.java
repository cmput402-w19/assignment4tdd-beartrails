package cmput402.beartrails.ui.prompter;

public class DoublePrompter extends AbstractPrompter {
	private Double minAllowable;
	private Double maxAllowable;
	
	public DoublePrompter(String promptText) {
		super(promptText);
	}
	
	public DoublePrompter(String promptText, 
					      Double minAllowable, 
			              Double maxAllowable) {
		super(promptText);
	}

	@Override
	protected Boolean isValid(String userInput) {
		return false;
	}

}
