package cmput402.beartrails;

public class User {
    enum Type{Admin, Professor, Student}

    public String username;
    public String lastName;
    public String firstName;
    public Type userType;
}
