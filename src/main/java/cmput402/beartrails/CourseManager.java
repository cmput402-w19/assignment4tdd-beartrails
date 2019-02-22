package cmput402.beartrails;

import java.util.List;

public class CourseManager {

    public ConnectionManager connectionManager;

    public CourseManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public Boolean createCourse(Course course)
    {
        String sub = course.courseSubject;
        String num = course.courseNumber;
        String loc = course.location;
        String prof = course.professor;
        String days = course.courseDays.name();
        Integer start = course.startTime;
        Integer duration = course.duration;

        return connectionManager.execute("");
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
