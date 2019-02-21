package cmput402.beartrails;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class GradeManager {

    public ConnectionManager connectionManager;

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
        Float totalGPA = 0.0f;

        List<List<Object>> queryResult = connectionManager.query("");

        for(List<Object> grades : queryResult)
        {
            for(Object grade : grades) {
                totalGPA += (Float)grade;
            }
        }

        Float studentGPA = totalGPA / queryResult.get(0).size();
        BigDecimal bd = new BigDecimal(studentGPA);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
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
