package cmput402.beartrails;

import org.mockito.Mockito;
import static org.mockito.Mock.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UserManagerTest extends TestCase {

    public void testLoginStudent() {

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();

        row.add("zred");
        row.add("Zach");
        row.add("Redfern");
        row.add(User.Type.Student);
        queryList.add(row);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        // Student who is logging in
        User student = new User("zred", "Redfern", "Zach", User.Type.Student);

        // User returned by the login process
        User loggedInUser = userManager.login(student.username);

        assert(student.username.equals(loggedInUser.username));
        assert(student.lastName.equals(loggedInUser.lastName));
        assert(student.firstName.equals(loggedInUser.firstName));
        assert(student.userType.equals(loggedInUser.userType));
    }

    public void testLoginProfessor() {

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();

        row.add("zred");
        row.add("Zach");
        row.add("Redfern");
        row.add(User.Type.Professor);
        queryList.add(row);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        // Professor who is logging in
        User professor = new User("zred", "Redfern", "Zach", User.Type.Professor);

        // User returned by the login process
        User loggedInUser = userManager.login(professor.username);

        assert(professor.username.equals(loggedInUser.username));
        assert(professor.lastName.equals(loggedInUser.lastName));
        assert(professor.firstName.equals(loggedInUser.firstName));
        assert(professor.userType.equals(loggedInUser.userType));
    }

    public void testLoginAdmin() {

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();

        row.add("zred");
        row.add("Zach");
        row.add("Redfern");
        row.add(User.Type.Admin);
        queryList.add(row);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        // Admin who is logging in
        User admin = new User("zred", "Redfern", "Zach", User.Type.Admin);

        // User returned by the login process
        User loggedInUser = userManager.login(admin.username);

        assert(admin.username.equals(loggedInUser.username));
        assert(admin.lastName.equals(loggedInUser.lastName));
        assert(admin.firstName.equals(loggedInUser.firstName));
        assert(admin.userType.equals(loggedInUser.userType));
    }

}
