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
		this.minAllowable = minAllowable;
		this.maxAllowable = maxAllowable;
	}

	@Override
	protected Boolean isValid(String userInput) {
		Integer parsedInt;
		try {
			parsedInt = Integer.parseInt(userInput);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if((this.minAllowable != null) && (parsedInt < this.minAllowable) 
				|| (this.maxAllowable != null) && (parsedInt > this.maxAllowable)) {
			return false;
		}
		
		return true;
	}
}
