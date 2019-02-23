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
		this.minAllowable = minAllowable;
		this.maxAllowable = maxAllowable;
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
