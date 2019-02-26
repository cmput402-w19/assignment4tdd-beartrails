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

        List<List<Object>> fakeGrades2 = new ArrayList<List<Object>>();
        List<List<Object>> fakeGrades3 = new ArrayList<List<Object>>();
        fakeGrades3.add(null);
        List<List<Object>> fakeGrades4 = new ArrayList<List<Object>>();
        fakeGrades4.add(new ArrayList<Object>());

        List<List<Object>> fakeGrades5 = new ArrayList<List<Object>>();
        fakeGradesList = new ArrayList<Object>();
        fakeGradesList.add(3.0d);
        fakeGradesList.add(null);
        fakeGrades5.add(fakeGradesList);

        List<List<Object>> fakeGrades6 = new ArrayList<List<Object>>();
        fakeGradesList = new ArrayList<Object>();
        fakeGradesList.add(null);
        fakeGrades6.add(fakeGradesList);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades).thenReturn(null).thenReturn(fakeGrades2).thenReturn(fakeGrades3)
                .thenReturn(fakeGrades4).thenReturn(fakeGrades5).thenReturn(fakeGrades6);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);

        Double result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 3.40d);

        result = gradeManager.getStudentGPA("repka");
        assertNull(result);

        result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGPA("repka");
        Assert.assertEquals(result, 0.0d);
        verify(mockConnectionManager, times(7)).query(anyString());
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

        List<List<Object>> fakeGrades2 = new ArrayList<List<Object>>();
        List<List<Object>> fakeGrades3 = new ArrayList<List<Object>>();
        fakeGrades3.add(null);
        List<List<Object>> fakeGrades4 = new ArrayList<List<Object>>();
        fakeGrades4.add(new ArrayList<Object>());

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades).thenReturn(null).thenReturn(fakeGrades2)
                .thenReturn(fakeGrades3).thenReturn(fakeGrades4);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        Double result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 3.10d);

        result = gradeManager.getCourseAverage("CMPUT", "402");
        assertNull(result);

        result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 0.0d);

        verify(mockConnectionManager, times(5)).query(anyString());
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

        List<List<Object>> fakeGrades2 = new ArrayList<List<Object>>();
        fakeGrades2.add(null);
        List<List<Object>> fakeGrades3 = new ArrayList<List<Object>>();
        fakeGrades3.add(new ArrayList<Object>());

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades).thenReturn(null)
                .thenReturn(fakeGrades2).thenReturn(fakeGrades3);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        HashMap<String, Double> result = gradeManager.getCourseMarks("CMPUT", "402");

        assertNotNull(result);
        assertTrue(result.size() == 4);
        Assert.assertEquals(result.get("benedict"), 4.0d);
        Assert.assertEquals(result.get("smith"), 2.7d);
        Assert.assertEquals(result.get("molly"), 3.7d);
        Assert.assertEquals(result.get("james"), 2.0d);

        result = gradeManager.getCourseMarks("CMPUT", "402");
        assertNotNull(result);
        assertTrue(result.size() == 0);

        result = gradeManager.getCourseMarks("CMPUT", "402");
        assertNotNull(result);
        assertTrue(result.size() == 0);

        result = gradeManager.getCourseMarks("CMPUT", "402");
        assertNotNull(result);
        assertTrue(result.size() == 0);
        verify(mockConnectionManager, times(4)).query(anyString());
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

        List<List<Object>> fakeGrades2 = new ArrayList<List<Object>>();
        fakeGrades2.add(null);
        List<List<Object>> fakeGrades3 = new ArrayList<List<Object>>();
        fakeGrades3.add(new ArrayList<Object>());

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades).thenReturn(null)
                .thenReturn(fakeGrades2).thenReturn(fakeGrades3);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        ArrayList<CourseGrade> result = gradeManager.getStudentGrades("repka");

        assertNotNull(result);
        assertTrue(result.size() == 3);
        Course course = new Course("cmput", "402", Course.DaysOfWeek.TueThu,
                                    13, 1, "CAB");
        course.professor = "snadi";

        Course firstCourse = result.get(0).course;

        Assert.assertEquals(result.get(0).grade, 3.5d);
        Assert.assertTrue(firstCourse.courseSubject.equals(course.courseSubject));
        Assert.assertTrue(firstCourse.courseDays.equals(course.courseDays));
        Assert.assertTrue(firstCourse.startTime.equals(course.startTime));
        Assert.assertTrue(firstCourse.location.equals(course.location));
        Assert.assertTrue(firstCourse.professor.equals(course.professor));

        result = gradeManager.getStudentGrades("repka");

        assertNotNull(result);
        assertTrue(result.size() == 0);

        result = gradeManager.getStudentGrades("repka");

        assertNotNull(result);
        assertTrue(result.size() == 0);

        result = gradeManager.getStudentGrades("repka");

        assertNotNull(result);
        assertTrue(result.size() == 0);

        verify(mockConnectionManager, times(4)).query(anyString());
    }

    public void testassignGrade()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        when(mockConnectionManager.execute(anyString())).thenReturn(true).thenReturn(false).thenReturn(false);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        Boolean result = gradeManager.assignGrade(3.5d, "repka", "CMPUT", "402");
        Assert.assertTrue(result);

        result = gradeManager.assignGrade(4.8d, "repka", "CMPUT", "402");
        Assert.assertFalse(result);

        result = gradeManager.assignGrade(-0.5d, "repka", "CMPUT", "402");
        Assert.assertFalse(result);

        // Execute should only be called once, since function should reject grades over 4.0 and below 0.
        verify(mockConnectionManager, times(1)).execute(anyString());
    }

    public void testgetStudentGrade()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrade = new ArrayList<List<Object>>();

        List<Object> fakeUserGrade = new ArrayList<Object>();
        fakeUserGrade.add(3.5d);
        fakeGrade.add(fakeUserGrade);

        List<List<Object>> fakeGrades2 = new ArrayList<List<Object>>();
        List<List<Object>> fakeGrades3 = new ArrayList<List<Object>>();
        fakeGrades3.add(null);
        List<List<Object>> fakeGrades4 = new ArrayList<List<Object>>();
        fakeGrades4.add(new ArrayList<Object>());
        List<List<Object>> fakeGrades5 = new ArrayList<List<Object>>();
        fakeUserGrade = new ArrayList<Object>();
        fakeGrades5.add(fakeUserGrade);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrade).thenReturn(null)
                .thenReturn(fakeGrades2).thenReturn(fakeGrades3).thenReturn(fakeGrades4).thenReturn(fakeGrades5);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        Double result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        Assert.assertEquals(result, 3.5d);

        result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        assertNull(result);

        result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        assertNull(result);

        result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        assertNotNull(result);
        Assert.assertEquals(result, 0.0d);

        result = gradeManager.getStudentGrade("repka", "CMPUT", "402");
        assertNotNull(result);
        Assert.assertEquals(result, 0.0d);

        verify(mockConnectionManager, times(6)).query(anyString());
    }
}
