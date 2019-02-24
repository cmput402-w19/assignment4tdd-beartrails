package cmput402.beartrails.ui.prompter;

public class DoublePrompter extends AbstractPrompter {
	private Double minAllowable;
	private Double maxAllowable;
	
	public String promptUser(String promptText) {
		this.minAllowable = null;
		this.maxAllowable = null;
		return super.promptUser(promptText);
	}
	
	public String promptUser(String promptText, 
						     Double minAllowable, 
						     Double maxAllowable) {
		this.minAllowable = minAllowable;
		this.maxAllowable = maxAllowable;
		return super.promptUser(promptText);
	}

	@Override
	protected Boolean isValid(String userInput) {
		Double parsedDouble;
		try {
			parsedDouble = Double.valueOf(userInput);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if((this.minAllowable != null) && (parsedDouble < this.minAllowable) 
			|| (this.maxAllowable != null) && (parsedDouble > this.maxAllowable)) {
			return false;
		}
			
	    return true;
	}

}
