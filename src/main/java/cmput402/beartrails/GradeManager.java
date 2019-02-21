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

    public boolean assignGrade(Double grade, String studentUsername, String subject, String number)
    {
        return false;
    }

    public Double getStudentGrade(String studentUsername, String subject, String number)
    {
        return null;
    }

    public ArrayList<CourseGrade> getStudentGrades(String studentUsername)
    {
        ArrayList<CourseGrade> studentGrades = new ArrayList<CourseGrade>();

        // Get list of courses and grades from DB
        String sqlQuery = "SELECT subject, number, days, time, duration, location, professor, grade" +
                          "FROM enrollments NATURAL JOIN courses" +
                          "WHERE student=\"" + studentUsername + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get course and grade list from response
        Iterator<List<Object>> courseGradeIterator = queryResult.iterator();

        // Add all courses and grades to hashmap
        while(courseGradeIterator.hasNext())
        {
            List<Object> currentCourseGrade = courseGradeIterator.next();
            Course currentCourse = new Course();
            currentCourse.courseSubject = currentCourseGrade.get(0).toString();
            currentCourse.courseNumber = currentCourseGrade.get(1).toString();
            currentCourse.courseDays =  Course.DaysOfWeek.valueOf(currentCourseGrade.get(2).toString());
            currentCourse.startTime = (Integer) currentCourseGrade.get(3);
            currentCourse.duration = (Integer) currentCourseGrade.get(4);
            currentCourse.location = currentCourseGrade.get(5).toString();
            currentCourse.professor = currentCourseGrade.get(6).toString();

            CourseGrade courseGrade = new CourseGrade();
            courseGrade.course = currentCourse;
            courseGrade.grade = (Double)currentCourseGrade.get(7);

            studentGrades.add(courseGrade);
        }

        return studentGrades;
    }

    public Double getStudentGPA(String studentUsername)
    {
        Double totalGPA = 0.0d;

        // Get list of grades from DB
        String sqlQuery = "SELECT grade FROM enrollments " +
                          "WHERE student = \"" + studentUsername + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get grade list from response
        Iterator<List<Object>> gradeIterator = queryResult.iterator();

        // Add up all grades
        while(gradeIterator.hasNext())
        {
            totalGPA += (Double)gradeIterator.next().get(0);
        }

        // Calculate average and round to 2 decimal places.
        Double studentGPA = totalGPA / queryResult.size();
        BigDecimal bd = new BigDecimal(studentGPA);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public HashMap<String, Double> getCourseMarks(String subject, String number)
    {
        HashMap<String, Double> courseMarks = new HashMap<String, Double>();

        // Get list of students and grades from DB
        String sqlQuery = "SELECT student, grade FROM enrollments " +
                "WHERE subject = \"" + subject + "\"" +
                "AND number = \"" + number + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get grade list and student list from response
        Iterator<List<Object>> userGradeIterator = queryResult.iterator();

        // Add all students and grades to hashmap
        while(userGradeIterator.hasNext())
        {
            List<Object> currentStudent = userGradeIterator.next();
            courseMarks.put(currentStudent.get(0).toString(), (Double)currentStudent.get(1));
        }

        return courseMarks;
    }

    public Double getCourseAverage(String subject, String number)
    {
        Double totalGPA = 0.0d;

        // Get list of student and grades from DB
        String sqlQuery = "SELECT grade FROM enrollments " +
                "WHERE subject = \"" + subject + "\"" +
                "AND number = \"" + number + "\"";
        List<List<Object>> queryResult = connectionManager.query(sqlQuery);

        // Get grade list from response
        Iterator<List<Object>> gradeIterator = queryResult.iterator();

        // Add up all grades in the course
        while (gradeIterator.hasNext()) {
            totalGPA += (Double)gradeIterator.next().get(0);
        }

        // Calculate course average and round to 2 decimal places.
        Double courseAverage = totalGPA / queryResult.size();
        BigDecimal bd = new BigDecimal(courseAverage);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
