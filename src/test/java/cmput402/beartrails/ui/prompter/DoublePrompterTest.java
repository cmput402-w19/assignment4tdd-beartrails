package cmput402.beartrails.ui.prompter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import junit.framework.TestCase;

public class DoublePrompterTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    private DoublePrompter dp;
    private String promptText = "Please enter an decimal number: ";
    private Double minAllowable = 0d;
    private Double maxAllowable = 4d;

    /*
     * Idea of faking stdin and stdout
     * taken from Antonio
     * https://stackoverflow.com/a/50721326/2038127
     */
    //Fake stdout
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        
        this.dp = new DoublePrompter(this.promptText,
        		                     this.minAllowable,
        		                     this.maxAllowable);
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

    
    public void testLowestValidInput() {
       	String input = "0\n";
    	provideInput(input);
    	
        String rv = dp.promptUser();
        
        assertFalse(dp.inputWasGoBack());
        assertFalse(dp.inputWasInvalid());
        assert(getOutput().contentEquals(this.promptText));
        assert(rv.contentEquals(input));
    }
    
    public void testHighestValidInput() {
    	String input = "4\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertFalse(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(input));
    }
    
    public void testTooLowValue() {
    	String input = "-1\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertTrue(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testTooHighValue() {
    	String input = "4.1\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertTrue(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testEmptyString() {
    	String input = "\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertTrue(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testChar() {
    	String input = "5k\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertTrue(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testDecimal() {
    	String input = "3.0\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertFalse(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(input));
    }
    
    public void testSpace() {
    	String input = " 3\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertFalse(dp.inputWasGoBack());
    	assertFalse(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals("3"));
    }
    
    public void testGoBack() {
    	String input = dp.getGoBackChar() + "\n";
    	provideInput(input);
    	
    	String rv = dp.promptUser();
    	
    	assertTrue(dp.inputWasGoBack());
    	assertFalse(dp.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }

}
