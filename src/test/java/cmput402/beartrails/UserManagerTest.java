package cmput402.beartrails;

import static org.mockito.Mock.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import cmput402.beartrails.User;
import cmput402.beartrails.UserManager;

public class UserManagerTest extends TestCase {

    UserManager userManager;

    public void setUp() throws Exception {
        System.out.println("Setting up UserManagerTest ...");
    }

    public void tearDown() throws Exception {
        System.out.println("Tearing down up UserManagerTest ...");
    }

    public void testLoginStudent() {

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

        // Professor who is logging in
        User professor = new User("drepka", "Repka", "Derek", User.Type.Professor);

        // User returned by the login process
        User loggedInUser = userManager.login(professor.username);

        assert(professor.username.equals(loggedInUser.username));
        assert(professor.lastName.equals(loggedInUser.lastName));
        assert(professor.firstName.equals(loggedInUser.firstName));
        assert(professor.userType.equals(loggedInUser.userType));
    }

    public void testLoginAdmin() {

        // Admin who is logging in
        User admin = new User("corey", "Hunt", "Corey", User.Type.Admin);

        // User returned by the login process
        User loggedInUser = userManager.login(admin.username);

        assert(admin.username.equals(loggedInUser.username));
        assert(admin.lastName.equals(loggedInUser.lastName));
        assert(admin.firstName.equals(loggedInUser.firstName));
        assert(admin.userType.equals(loggedInUser.userType));
    }

}
