package cmput402.beartrails.ui.prompter;

public class IntegerPrompter extends AbstractPrompter {
	private Integer minAllowable;
	private Integer maxAllowable;
	
	public String promptUser(String promptText) {
		this.minAllowable = null;
		this.maxAllowable = null;
		return super.promptUser(promptText);
	}
	
	public String promptUser(String promptText, 
						     Integer minAllowable, 
						     Integer maxAllowable) {
		this.minAllowable = minAllowable;
		this.maxAllowable = maxAllowable;
		return super.promptUser(promptText);
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
