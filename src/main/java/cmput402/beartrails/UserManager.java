package cmput402.beartrails;

import java.util.List;
import java.util.ArrayList;

import java.text.MessageFormat;

public class UserManager {

    public ConnectionManager connectionManager;

    public UserManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public User login(String username) {

        Object[] params = new Object[]{username};
        String query = MessageFormat.format("SELECT * FROM users WHERE user_name = \"{0}\" ORDER BY last_name;", params);
        List<List<Object>> rs = connectionManager.query(query);

        // Return null if there was no valid user
        if (rs.size() == 0) {
            return null;
        }

        // Extract attributes
        String userName = (String) rs.get(0).get(0);
        String firstName = (String) rs.get(0).get(1);
        String lastName = (String) rs.get(0).get(2);
        User.Type userType = User.Type.values()[(Integer) rs.get(0).get(3)];

        return new User(userName, firstName, lastName, userType);
    }

    public boolean registerUser(User user) {

        Object[] params = new Object[]{user.username, user.firstName, user.lastName, user.userType};
        String query = MessageFormat.format( "INSERT INTO users VALUES (\"{0}\", \"{1}\", \"{2}\", {3});", params);

        return(connectionManager.execute(query));
    }

    public List<User> getStudents() {

        Object[] params = new Object[]{User.Type.Student.ordinal()};
        String query = MessageFormat.format("SELECT * FROM users WHERE type = {0} ORDER BY last_name;", params);
        List<List<Object>> rs = connectionManager.query(query);

        // Extract attributes from rows of the result set
        List<User> studentList = new ArrayList<User>();
        for (List<Object> row : rs) {
            String userName = (String) row.get(0);
            String firstName = (String) row.get(1);
            String lastName = (String) row.get(2);
            User.Type userType = User.Type.values()[(Integer) row.get(3)];
            studentList.add(new User(userName, firstName, lastName, userType));
        }

        return studentList;
    }

    public List<User> getTeachers() {

        Object[] params = new Object[]{User.Type.Professor.ordinal()};
        String query = MessageFormat.format("SELECT * FROM users WHERE type = {0} ORDER BY last_name;", params);
        List<List<Object>> rs = connectionManager.query(query);

        // Extract attributes from rows of the result set
        List<User> professorList = new ArrayList<User>();
        for (List<Object> row : rs) {
            String userName = (String) row.get(0);
            String firstName = (String) row.get(1);
            String lastName = (String) row.get(2);
            User.Type userType = User.Type.values()[(Integer) row.get(3)];
            professorList.add(new User(userName, firstName, lastName, userType));
        }

        return professorList;
    }
}
