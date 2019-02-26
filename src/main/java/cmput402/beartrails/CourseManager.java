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
        String sub = (course.courseSubject == null)
                     ? null
                     : "\"" + course.courseSubject + "\"";
        String num = (course.courseNumber == null)
                ? null
                : "\"" + course.courseNumber + "\"";
        String loc = (course.location == null)
                ? null
                : "\"" + course.location + "\"";
        String prof = (course.professor == null)
                ? null
                : "\"" + course.professor + "\"";
        String days = (course.courseDays == null)
                ? null
                : "\"" + course.courseDays.name() + "\"";
        Integer start = course.startTime;
        Integer duration = course.duration;

        if(sub == null || num == null)
        {
            return false;
        }

        String sqlQuery = "INSERT INTO courses VALUES (" + sub + ", " + num + ", " +
                          days + ", " + start + ", " + duration + ", " + loc + ", " +
                          prof + ");";

        return connectionManager.execute(sqlQuery);
    }

    public List<User> getStudentsInCourse(String subject, String number)
    {
        ArrayList<User> courseStudents = new ArrayList<User>();

        // Get list of students from DB
        String sqlQuery = "SELECT user_name, first_name, last_name, type" +
                " FROM enrollments INNER JOIN users" +
        		" ON enrollments.student = users.user_name" +
                " WHERE subject = \"" + subject + "\"" +
                " AND number = \"" + number + "\";";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

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
        ArrayList<Course> courseList = new ArrayList<Course>();

        // Get list of courses from DB
        String sqlQuery = "SELECT * FROM courses;";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get student list from response
        Iterator<List<Object>> studentIterator = queryResult.iterator();

        // Add all students to list as users
        while(studentIterator.hasNext())
        {
            List<Object> currentCourse = studentIterator.next();
            String subject = currentCourse.get(0).toString();
            String number = currentCourse.get(1).toString();
            Course.DaysOfWeek days =  Course.DaysOfWeek.valueOf(currentCourse.get(2).toString());
            Integer startTime = (Integer) currentCourse.get(3);
            Integer duration = (Integer) currentCourse.get(4);
            String location = currentCourse.get(5).toString();

            Course course = new Course(subject, number, days, startTime, duration, location);
            course.professor = String.valueOf(currentCourse.get(6));

            courseList.add(course);
        }

        return courseList;
    }

    public Boolean removeCourse(String subject, String number)
    {
        String sub = (subject == null)
                ? null
                : "\"" + subject + "\"";
        String num = (number == null)
                ? null
                : "\"" + number + "\"";

        if(sub == null || num == null)
        {
            return false;
        }

        String sqlQuery = "DELETE FROM courses WHERE course_subject = " + sub + " AND course_number = " + num +";";
        return connectionManager.execute(sqlQuery);
    }

    public Boolean assignTeacher(String teacherUsername, String subject, String number)
    {
        String sub = (subject == null)
                ? null
                : "\"" + subject + "\"";
        String num = (number == null)
                ? null
                : "\"" + number + "\"";
        String prof = (teacherUsername == null)
                ? null
                : "\"" + teacherUsername + "\"";

        if(sub == null || num == null || prof == null)
        {
            return false;
        }

        String sqlQuery = "UPDATE courses SET professor = " + prof +
                " WHERE subject = " + sub +
                " AND number = " + num + ";";
        return connectionManager.execute(sqlQuery);
    }

    public Boolean unassignTeacher(String subject, String number)
    {
        String sub = (subject == null)
                ? null
                : "\"" + subject + "\"";
        String num = (number == null)
                ? null
                : "\"" + number + "\"";

        if(sub == null || num == null)
        {
            return false;
        }

        String sqlQuery = "UPDATE courses SET professor = null" +
                " WHERE course_subject = \"" + sub + "\"" +
                " AND course_number = \"" + num + "\";";
        return connectionManager.execute(sqlQuery);
    }
}