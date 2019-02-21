package cmput402.beartrails;

public class User {
    enum Type{Admin, Professor, Student}

    public String username;
    public String lastName;
    public String firstName;
    public Type userType;

    User(String username, String lastName, String firstName, Type userType) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.userType = userType;
    }
}
