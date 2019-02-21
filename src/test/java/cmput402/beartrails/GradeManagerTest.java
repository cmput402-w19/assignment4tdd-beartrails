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

        fakeGradesList.add(3.5f);
        fakeGradesList.add(3.0f);
        fakeGradesList.add(3.7f);

        fakeGrades.add(fakeGradesList);
        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);

        Float result = gradeManager.getStudentGPA("repka");
        verify(mockConnectionManager, times(1)).query(anyString());
        Assert.assertEquals(result, 3.40f);
    }

    public void testgetCourseAverage()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();
        List<Object> fakeGradesList = new ArrayList<Object>();

        List<String> fakeUsersList = new ArrayList<String>();
        List<Float> fakeUserGradeList = new ArrayList<Float>();

        fakeUsersList.add("benedict");
        fakeUserGradeList.add(4.0f);

        fakeUsersList.add("smith");
        fakeUserGradeList.add(2.7f);

        fakeUsersList.add("molly");
        fakeUserGradeList.add(3.7f);

        fakeUsersList.add("james");
        fakeUserGradeList.add(2.0f);

        fakeGradesList.add(fakeUsersList);
        fakeGradesList.add(fakeUserGradeList);
        fakeGrades.add(fakeGradesList);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        Float result = gradeManager.getCourseAverage("CMPUT", "402");
        Assert.assertEquals(result, 3.10f);
    }

    public void testgetCourseMarks()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);

        List<List<Object>> fakeGrades = new ArrayList<List<Object>>();
        List<Object> fakeGradesList = new ArrayList<Object>();

        List<String> fakeUsersList = new ArrayList<String>();
        List<Float> fakeUserGradeList = new ArrayList<Float>();

        fakeUsersList.add("benedict");
        fakeUserGradeList.add(4.0f);

        fakeUsersList.add("smith");
        fakeUserGradeList.add(2.7f);

        fakeUsersList.add("molly");
        fakeUserGradeList.add(3.7f);

        fakeUsersList.add("james");
        fakeUserGradeList.add(2.0f);

        fakeGradesList.add(fakeUsersList);
        fakeGradesList.add(fakeUserGradeList);
        fakeGrades.add(fakeGradesList);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeGrades);

        GradeManager gradeManager = new GradeManager(mockConnectionManager);
        HashMap<String, Float> result = gradeManager.getCourseMarks("CMPUT", "402");
        assertNotNull(result);
        assertTrue(result.size() == 4);
        Assert.assertEquals(result.get("benedict"), 4.0f);
        Assert.assertEquals(result.get("smith"), 2.7f);
        Assert.assertEquals(result.get("molly"), 3.7f);
        Assert.assertEquals(result.get("james"), 2.0f);
    }
}
