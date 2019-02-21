package cmput402.beartrails;

import java.util.List;

public class ScheduleManager {

    public ConnectionManager connectionManager;

    public ScheduleManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public List<Course> getStudentSchedule(String studentUsername)
    {
        return null;
    }

    public List<Course> getTeacherSchedule(String teacherUsername)
    {
        return null;
    }

    public List<User> getStudentsInCourse(int courseId)
    {
        return null;
    }

    public Boolean removeCourse(int courseId)
    {
        return null;
    }

    public Boolean assignTeacher(String teacherUsername, int courseId)
    {
        return null;
    }

    public Boolean unassignTeacher(int courseId)
    {
        return null;
    }

}
