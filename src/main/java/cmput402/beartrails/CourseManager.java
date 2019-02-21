package cmput402.beartrails;

import java.util.List;

public class CourseManager {

    public ConnectionManager connectionManager;

    public CourseManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public boolean createCourse(Course course)
    {
        return false;
    }

    public List<User> getStudentsInCourse(String subject, String number)
    {
        return null;
    }

    public List<Course> getAllCourses()
    {
        return null;
    }

    public boolean removeCourse(String subject, String number)
    {
        return false;
    }

    public boolean assignTeacher(String teacherUsername, String subject, String number)
    {
        return false;
    }

    public boolean unassignTeacher(String subject, String number)
    {
        return false;
    }

}
