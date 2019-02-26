package cmput402.beartrails;

public class User {
    public enum Type{Admin, Professor, Student}

    public String username;
    public String lastName;
    public String firstName;
    public Type userType;

    public User(String username, String firstName, String lastName, Type userType) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userType = userType;
    }
    
    public boolean equals(Object o) {
    	if(o == this) {
    		return true;
    	}
    	
    	if(!(o instanceof User)) {
    		return false;
    	}
    	
    	User u = (User) o;
    	
    	return (this.username.contentEquals(u.username) &&
    			this.firstName.contentEquals(u.firstName) &&
    			this.lastName.contentEquals(u.lastName) &&
    			this.userType.equals(u.userType));
    }
}
