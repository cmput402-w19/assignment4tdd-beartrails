package cmput402.beartrails;

import java.util.List;

public class UserManager {

    private ConnectionManager connectionManager;

    public UserManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public User login(String username)
    {
        return null;
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
