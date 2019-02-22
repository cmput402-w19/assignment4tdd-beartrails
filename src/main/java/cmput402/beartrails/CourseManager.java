package cmput402.beartrails;

import java.util.ArrayList;
import java.util.Iterator;
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

        return connectionManager.execute(sqlQuery);
    }

    public List<User> getStudentsInCourse(String subject, String number)
    {
        ArrayList<User> courseStudents = new ArrayList<User>();

        // Get list of students from DB
        List<List<Object>> queryResult = connectionManager.query("");

        // Get student list from response
        Iterator<List<Object>> studentIterator = queryResult.iterator();

        // Add all students to list as users
        while(studentIterator.hasNext())
        {
            List<Object> currentStudent = studentIterator.next();
            String username = currentStudent.get(0).toString();
            String firstName = currentStudent.get(1).toString();
            String lastName = currentStudent.get(2).toString();
            User.Type userType = User.Type.values()[(Integer)currentStudent.get(3)];

            User courseStudent = new User(username, firstName, lastName, userType);
            courseStudents.add(courseStudent);
        }

        return courseStudents;
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
