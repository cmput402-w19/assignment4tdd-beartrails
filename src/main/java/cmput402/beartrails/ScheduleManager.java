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

    public List<Course> getStudentSchedule(String studentUsername)
    {
        return null;
    }

    public List<Course> getTeacherSchedule(String teacherUsername)
    {
        return null;
    }

    public Boolean addCourse(String courseSubject, String courseNumber) {return null; }

    public Boolean removeCourse(String courseSubject, String courseNumber)
    {
        return null;
    }


}
