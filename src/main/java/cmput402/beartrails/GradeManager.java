package cmput402.beartrails;

import java.util.HashMap;

public class GradeManager {

    private ConnectionManager connectionManager;

    public GradeManager(ConnectionManager connectionManager)
    {
        this.connectionManager = connectionManager;
    }

    public boolean assignGrade(Float grade, String studentUsername, String subject, String number)
    {
        return false;
    }

    public Float getStudentGrade(String studentUsername, String subject, String number)
    {
        return null;
    }

    public HashMap<Course, Float> getStudentGrades(String studentUsername)
    {
        return null;
    }

    public Float getStudentGPA(String studentUsername)
    {
        return null;
    }

    public HashMap<String, Float> getCourseMarks(String subject, String number)
    {
        return null;
    }

    public Float getCourseAverage(String subject, String number)
    {
        return null;
    }
}
