package cmput402.beartrails;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        String test = "abc123 ?";
        
        //https://stackoverflow.com/a/4047836/2038127        
        for(int i = 0; i < test.length(); i++) {
        	System.out.println(Character.isLetter(test.charAt(i)));
        }
    }
}
