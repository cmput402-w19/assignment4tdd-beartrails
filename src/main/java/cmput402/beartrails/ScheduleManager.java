package cmput402.beartrails;

import java.util.ArrayList;
import java.util.List;

import java.text.MessageFormat;

public class ScheduleManager {

    public ConnectionManager connectionManager;
    public User user;

    public ScheduleManager(ConnectionManager connectionManager, User user) {

        this.connectionManager = connectionManager;
        this.user = user;
    }

    public List<Course> getStudentSchedule(String studentUsername) {

        Object[] params = new Object[]{studentUsername};
        String query = MessageFormat.format("SELECT subject, number, days, time, duration, location" +
                " FROM enrollments NATURAL JOIN courses ORDER BY days, time;" +
                " WHERE student=\"{0}\";", params);
        List<List<Object>> rs = connectionManager.query(query);

        List<Course> courseList = new ArrayList<Course>();
        for (List<Object> row : rs) {
            String courseSubject = (String) row.get(0);
            String courseNumber = (String) row.get(1);
            Course.DaysOfWeek courseDays = (Course.DaysOfWeek) row.get(2);
            Integer startTime = (Integer) row.get(3);
            Integer duration = (Integer) row.get(4);
            String location = (String) row.get(5);
            courseList.add(new Course(courseSubject, courseNumber, courseDays, startTime, duration, location));
        }

        return courseList;
    }

    public List<Course> getTeacherSchedule(String teacherUsername) {

        Object[] params = new Object[]{teacherUsername};
        String query = MessageFormat.format("SELECT subject, number, days, time, duration, location" +
                " FROM courses WHERE professor = {0}", params);
        List<List<Object>> rs = connectionManager.query(query);

        List<Course> courseList = new ArrayList<Course>();
        for (List<Object> row : rs) {
            String courseSubject = (String) row.get(0);
            String courseNumber = (String) row.get(1);
            Course.DaysOfWeek courseDays = (Course.DaysOfWeek) row.get(2);
            Integer startTime = (Integer) row.get(3);
            Integer duration = (Integer) row.get(4);
            String location = (String) row.get(5);
            courseList.add(new Course(courseSubject, courseNumber, courseDays, startTime, duration, location));
        }

        return courseList;
    }

    public Boolean addCourse(String courseSubject, String courseNumber) {return null; }

    public Boolean removeCourse(String courseSubject, String courseNumber)
    {
        return null;
    }


}
