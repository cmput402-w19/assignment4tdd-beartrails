package cmput402.beartrails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

        // Get list of grades from DB
        String sqlQuery = "SELECT grade FROM enrollments " +
                          "WHERE student = \"" + studentUsername + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get grade list from response
        List<Object> gradeList = queryResult.get(0);
        Iterator<Object> gradeIterator = gradeList.iterator();

        // Add up all grades
        while(gradeIterator.hasNext())
        {
            totalGPA += (Float)gradeIterator.next();
        }

        // Calculate average and round to 2 decimal places.
        Float studentGPA = totalGPA / gradeList.size();
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
        Float totalGPA = 0.0f;

        // Get list of student and grades from DB
        String sqlQuery = "SELECT student, grade FROM enrollments " +
                "WHERE subject = \"" + subject + "\"" +
                "AND number = \"" + number + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get grade list from response
        List<Object> gradeUsersList = queryResult.get(0);
        List<Float> gradeList = (ArrayList) gradeUsersList.get(1);
        Iterator<Float> gradeIterator = gradeList.iterator();

        // Add up all grades in the course
        while (gradeIterator.hasNext()) {
            totalGPA += gradeIterator.next();
        }

        // Calcualte course average and round to 2 decimal places.
        Float courseAverage = totalGPA / gradeList.size();
        BigDecimal bd = new BigDecimal(courseAverage);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
