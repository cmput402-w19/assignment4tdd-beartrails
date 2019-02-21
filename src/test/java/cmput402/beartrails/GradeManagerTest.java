package cmput402.beartrails;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
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
}
