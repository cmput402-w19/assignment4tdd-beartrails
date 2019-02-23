package cmput402.beartrails.ui.prompter;

import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class IntegerPrompterTest extends TestCase {
	private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;
    
    private IntegerPrompter ip;
    private String promptText = "Please enter an integer: ";
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
        
        this.ip = new IntegerPrompter(this.promptText,
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
       	String input = "1\n";
    	provideInput(input);
    	
        String rv = ip.promptUser();
        
        assertFalse(ip.inputWasGoBack());
        assertFalse(ip.inputWasInvalid());
        assert(getOutput().contentEquals(this.promptText));
        assert(rv.contentEquals(input.trim()));
    }
    
    public void testHighestValidInput() {
    	String input = "10\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertFalse(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(input.trim()));
    }
    
    public void testTooLowValue() {
    	String input = "0\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertTrue(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testTooHighValue() {
    	String input = "11\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertTrue(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testEmptyString() {
    	String input = "\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertTrue(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testChar() {
    	String input = "5k\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertTrue(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testDecimal() {
    	String input = "3.0\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertTrue(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }
    
    public void testSpace() {
    	String input = " 3\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertFalse(ip.inputWasGoBack());
    	assertFalse(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals("3"));
    }
    
    public void testGoBack() {
    	String input = ip.getGoBackChar() + "\n";
    	provideInput(input);
    	
    	String rv = ip.promptUser();
    	
    	assertTrue(ip.inputWasGoBack());
    	assertFalse(ip.inputWasInvalid());
    	assert(getOutput().contentEquals(this.promptText));
    	assert(rv.contentEquals(""));
    }

}
