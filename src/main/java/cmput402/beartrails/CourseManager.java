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
        String sub = (course.courseSubject.isEmpty() || course.courseSubject == null)
                     ? null
                     : "\"" + course.courseSubject + "\"";
        String num = (course.courseNumber.isEmpty() || course.courseNumber == null)
                ? null
                : "\"" + course.courseNumber + "\"";
        String loc = (course.location.isEmpty() || course.location == null)
                ? null
                : "\"" + course.location + "\"";
        String prof = (course.professor.isEmpty() || course.professor == null)
                ? null
                : "\"" + course.professor + "\"";
        String days = (course.courseDays == null)
                ? null
                : "\"" + course.courseDays.name() + "\"";
        Integer start = course.startTime;
        Integer duration = course.duration;

        String sqlQuery = "INSERT INTO courses VALUES (" + sub + ", " + num + ", " +
                          days + ", " + start + ", " + duration + ", " + loc + ", " +
                          prof + ");";
        System.out.print(sqlQuery);

        return connectionManager.execute(sqlQuery);
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
