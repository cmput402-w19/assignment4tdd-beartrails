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

    public void testLoginInvalid() {

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        assert(null == userManager.login("badUserName"));
    }

    public void testRegisterUser() {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);

        when(mockConnectionManager.execute(Mockito.anyString())).thenReturn(true);

        User student = new User("zred", "Redfern", "Zach", User.Type.Student);

        assertTrue(userManager.registerUser(student));
    }

    public void testGetStudents() {

        User student1 = new User("dhall", "Hall", "Derek", User.Type.Student);
        User student2 = new User("bmontina", "Montina", "Brandon", User.Type.Student);
        User student3 = new User("cpanda", "Pandachuck", "Cole", User.Type.Student);

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(student1.username);
        row1.add(student1.firstName);
        row1.add(student1.lastName);
        row1.add(student1.userType);

        row2.add(student2.username);
        row2.add(student2.firstName);
        row2.add(student2.lastName);
        row2.add(student2.userType);

        row3.add(student3.username);
        row3.add(student3.firstName);
        row3.add(student3.lastName);
        row3.add(student3.userType);

        queryList.add(row1);
        queryList.add(row2);
        queryList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        List<User> studentList = userManager.getStudents();

        assert(studentList.size() == 3);

        assert(studentList.get(0).username.equals(student1.username));
        assert(studentList.get(0).lastName.equals(student1.lastName));
        assert(studentList.get(0).firstName.equals(student1.firstName));
        assert(studentList.get(0).userType.equals(student1.userType));

        assert(studentList.get(1).username.equals(student2.username));
        assert(studentList.get(1).lastName.equals(student2.lastName));
        assert(studentList.get(1).firstName.equals(student2.firstName));
        assert(studentList.get(1).userType.equals(student2.userType));

        assert(studentList.get(2).username.equals(student3.username));
        assert(studentList.get(2).lastName.equals(student3.lastName));
        assert(studentList.get(2).firstName.equals(student3.firstName));
        assert(studentList.get(2).userType.equals(student3.userType));
    }

    public void testGetTeachers() {

        User professor1 = new User("dhall", "Hall", "Derek", User.Type.Professor);
        User professor2 = new User("bmontina", "Montina", "Brandon", User.Type.Professor);
        User professor3 = new User("cpanda", "Pandachuck", "Cole", User.Type.Professor);

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        UserManager userManager = new UserManager(mockConnectionManager);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(professor1.username);
        row1.add(professor1.firstName);
        row1.add(professor1.lastName);
        row1.add(professor1.userType);

        row2.add(professor2.username);
        row2.add(professor2.firstName);
        row2.add(professor2.lastName);
        row2.add(professor2.userType);

        row3.add(professor3.username);
        row3.add(professor3.firstName);
        row3.add(professor3.lastName);
        row3.add(professor3.userType);

        queryList.add(row1);
        queryList.add(row2);
        queryList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        List<User> professorList = userManager.getTeachers();

        assert(professorList.size() == 3);

        assert(professorList.get(0).username.equals(professor1.username));
        assert(professorList.get(0).lastName.equals(professor1.lastName));
        assert(professorList.get(0).firstName.equals(professor1.firstName));
        assert(professorList.get(0).userType.equals(professor1.userType));

        assert(professorList.get(1).username.equals(professor2.username));
        assert(professorList.get(1).lastName.equals(professor2.lastName));
        assert(professorList.get(1).firstName.equals(professor2.firstName));
        assert(professorList.get(1).userType.equals(professor2.userType));

        assert(professorList.get(2).username.equals(professor3.username));
        assert(professorList.get(2).lastName.equals(professor3.lastName));
        assert(professorList.get(2).firstName.equals(professor3.firstName));
        assert(professorList.get(2).userType.equals(professor3.userType));
    }

}
