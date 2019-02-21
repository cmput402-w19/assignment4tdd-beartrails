package cmput402.beartrails;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;


public class GradeManagerTest extends TestCase {

    public void testgetStudentGPA() {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();
        List<Object> fakeGradesList = new ArrayList<Object>();
        fakeGradesList.add(3.5d);
        fakeGrades.add(fakeGradesList);

        fakeGradesList = new ArrayList<Object>();
        fakeGradesList.add(3.0d);
        fakeGrades.add(fakeGradesList);

        fakeGradesList = new ArrayList<Object>();
        fakeGradesList.add(3.7d);
        fakeGrades.add(fakeGradesList);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);

        Double result = gradeManager.getStudentGPA("repka");
        verify(mockConnectionManager, times(1)).query(anyString());
        Assert.assertEquals(result, 3.40d);
    }

    public void testgetCourseAverage()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();

        List<Object> fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add(4.0d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add(2.7d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add(3.7d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add(2.0d);
        fakeGrades.add(fakeUserGrade);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        Double result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 3.10d);
    }

    public void testgetCourseMarks()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();

        List<Object> fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add("benedict");
        fakeUserGrade.add(4.0d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add("smith");
        fakeUserGrade.add(2.7d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add("molly");
        fakeUserGrade.add(3.7d);
        fakeGrades.add(fakeUserGrade);

        fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add("james");
        fakeUserGrade.add(2.0d);
        fakeGrades.add(fakeUserGrade);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        HashMap<String, Double> result = gradeManager.getCourseMarks("CMPUT", "402");

        assertNotNull(result);
        assertTrue(result.size() == 4);
        Assert.assertEquals(result.get("benedict"), 4.0d);
        Assert.assertEquals(result.get("smith"), 2.7d);
        Assert.assertEquals(result.get("molly"), 3.7d);
        Assert.assertEquals(result.get("james"), 2.0d);
    }

    public void testgetStudentGrades()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();

        List<Object> fakeCourseGrade = new ArrayList<Object>();
        fakeCourseGrade.add("cmput");
        fakeCourseGrade.add("402");
        fakeCourseGrade.add(Course.DaysOfWeek.TueThu.name());
        fakeCourseGrade.add(13);
        fakeCourseGrade.add(1);
        fakeCourseGrade.add("CAB");
        fakeCourseGrade.add("snadi");
        fakeCourseGrade.add(3.5d);
        fakeGrades.add(fakeCourseGrade);

        fakeCourseGrade = new ArrayList<Object>();
        fakeCourseGrade.add("econ");
        fakeCourseGrade.add("281");
        fakeCourseGrade.add(Course.DaysOfWeek.TueThu.name());
        fakeCourseGrade.add(11);
        fakeCourseGrade.add(1);
        fakeCourseGrade.add("T");
        fakeCourseGrade.add("lpriem");
        fakeCourseGrade.add(3.2d);

        fakeGrades.add(fakeCourseGrade);

        fakeCourseGrade = new ArrayList<Object>();
        fakeCourseGrade.add("math");
        fakeCourseGrade.add("222");
        fakeCourseGrade.add(Course.DaysOfWeek.MonWedFri.name());
        fakeCourseGrade.add(10);
        fakeCourseGrade.add(1);
        fakeCourseGrade.add("CAB");
        fakeCourseGrade.add("tpas");
        fakeCourseGrade.add(4.0d);
        fakeGrades.add(fakeCourseGrade);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        HashMap<Course, Double> result = gradeManager.getStudentGrades("repka");

        assertNotNull(result);
        assertTrue(result.size() == 3);
        Course course = new Course();
        course.courseSubject = "cmput";
        course.courseNumber = "402";
        course.courseDays = Course.DaysOfWeek.TueThu;
        course.startTime = 13;
        course.duration = 1;
        course.professor = "snadi";
        course.location = "CAB";

        Course firstCourse = (Course)result.keySet().toArray()[0];

        Assert.assertEquals(result.get(firstCourse), 3.5d);
        Assert.assertTrue(firstCourse.courseSubject.equals(course.courseSubject));
        Assert.assertTrue(firstCourse.courseDays.equals(course.courseDays));
        Assert.assertTrue(firstCourse.startTime.equals(course.startTime));
        Assert.assertTrue(firstCourse.location.equals(course.location));
        Assert.assertTrue(firstCourse.professor.equals(course.professor));

    }
}
