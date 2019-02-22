package cmput402.beartrails;

import java.util.List;

import java.text.MessageFormat;

public class UserManager {

    public ConnectionManager connectionManager;

    public UserManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public User login(String username) {

        Object[] params = new Object[]{username};
        String query = MessageFormat.format("SELECT * FROM users WHERE user_name = {0} ORDER BY last_name;", params);
        List<List<Object>> rs = connectionManager.query(query);

        // Return null if there was no valid user
        if (rs.size() == 0) {
            return null;
        }

        // Extract attributes
        String userName = (String) rs.get(0).get(0);
        String firstName = (String) rs.get(0).get(1);
        String lastName = (String) rs.get(0).get(2);
        User.Type userType = (User.Type)rs.get(0).get(3);

        return new User(userName, lastName, firstName, userType);
    }

    public boolean registerUser(User user)
    {
        return false;
    }

    public List<User> getStudents()
    {
        return null;
    }

    public List<User> getTeachers()
    {
        return null;
    }
}
