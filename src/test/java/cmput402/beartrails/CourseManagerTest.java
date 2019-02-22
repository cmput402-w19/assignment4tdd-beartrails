package cmput402.beartrails;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class CourseManagerTest extends TestCase {

    public void testcreateCourse()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        when(mockConnectionManager.execute(anyString())).thenReturn(true);

        CourseManager courseManager = new CourseManager(mockConnectionManager);
        Course newCourse = new Course();

        newCourse.courseSubject = "CMPUT";
        newCourse.courseNumber = "402";
        newCourse.startTime = 15;
        newCourse.location = "CAB";
        newCourse.duration = 1;
        newCourse.courseDays = Course.DaysOfWeek.TueThu;
        newCourse.professor = "snadi";

        Boolean result = courseManager.createCourse(newCourse);
        assertNotNull(result);
        Assert.assertTrue(result);

        verify(mockConnectionManager, times(1)).execute(anyString());
    }

    public void testgetStudentsInCourse()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        List<List<Object>> fakeResponse = new ArrayList<List<Object>>();

        List<Object> fakeStudents = new ArrayList<Object>();
        fakeStudents.add("repka");
        fakeStudents.add("derek");
        fakeStudents.add("repka");
        fakeStudents.add(2);
        fakeResponse.add(fakeStudents);

        fakeStudents = new ArrayList<Object>();
        fakeStudents.add("hunt1");
        fakeStudents.add("corey");
        fakeStudents.add("hunt");
        fakeStudents.add(2);
        fakeResponse.add(fakeStudents);

        fakeStudents = new ArrayList<Object>();
        fakeStudents.add("araien");
        fakeStudents.add("araien");
        fakeStudents.add("redfern");
        fakeStudents.add(2);
        fakeResponse.add(fakeStudents);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeResponse);

        CourseManager courseManager = new CourseManager(mockConnectionManager);
        List<User> result = courseManager.getStudentsInCourse("CMPUT", "402");
        assertNotNull(result);
        Assert.assertTrue(result.size() == 3);
        Assert.assertTrue(result.get(0).username.equals("repka"));
        Assert.assertTrue(result.get(1).firstName.equals("corey"));
        Assert.assertTrue(result.get(2).lastName.equals("redfern"));
        Assert.assertTrue(result.get(0).userType.equals(User.Type.Student));
        Assert.assertTrue(result.get(1).userType.equals(User.Type.Student));
        Assert.assertTrue(result.get(2).userType.equals(User.Type.Student));

        verify(mockConnectionManager, times(1)).query(anyString());
    }
}
