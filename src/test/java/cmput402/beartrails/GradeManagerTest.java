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
}
