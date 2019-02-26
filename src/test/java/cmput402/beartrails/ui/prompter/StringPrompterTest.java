package cmput402.beartrails.ui.prompter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

public class StringPrompterTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    private StringPrompter sp;
    private String promptText = "Please enter an one-word string: ";
    private Integer minAllowable = 1;
    private Integer maxAllowable = 10;

    /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        this.sp = new StringPrompter();
    }
    
    //Restore stdout and stdin
    public void tearDown() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    //Fake stdin
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    
   
    public void testEmptyString() {
    	String input = "\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertTrue(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testValid() {
    	String input = "huntc\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertFalse(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(input.trim()));
    }
    
    public void testSpace() {
    	String input = " huntc\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertFalse(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals("huntc"));
    }
    
    public void testTwoWords() {
    	String input = "Corey Hunt\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertTrue(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testSpecialCharacter() {
    	String input = "Corey?Hunt\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertTrue(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testNumber() {
    	String input = "Corey5Hunt\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertFalse(sp.inputWasGoBack());
    	assertTrue(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testGoBack() {
    	String input = sp.getGoBackChar() + "\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);
    	
    	assertTrue(sp.inputWasGoBack());
    	assertFalse(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testExit() {
    	String input = sp.getQuitChar() + "\n";
    	provideInput(input);
    	
    	String rv = sp.promptUser(this.promptText);

    	assertTrue(sp.inputWasGoBack());
    	assertTrue(sp.inputWasQuit());
    	assertFalse(sp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
}
