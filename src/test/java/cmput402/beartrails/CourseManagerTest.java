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
        when(mockConnectionManager.execute(anyString())).thenReturn(true).thenReturn(false);

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

        newCourse = new Course();
        newCourse.courseSubject = null;
        newCourse.courseNumber = null;
        newCourse.startTime = null;
        newCourse.location = null;
        newCourse.duration = null;
        newCourse.courseDays = null;
        newCourse.professor = null;

        result = courseManager.createCourse(newCourse);
        assertNotNull(result);
        Assert.assertFalse(result);

        // Execute should only be called once, since createCourse will reject empty or null subject/number
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

    public void testgetAllCourses()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        List<List<Object>> fakeResponse = new ArrayList<List<Object>>();

        List<Object> fakeCourse = new ArrayList<Object>();
        fakeCourse.add("cmput");
        fakeCourse.add("402");
        fakeCourse.add(Course.DaysOfWeek.TueThu.name());
        fakeCourse.add(15);
        fakeCourse.add(1);
        fakeCourse.add("CAB");
        fakeCourse.add("snadi");
        fakeResponse.add(fakeCourse);

        fakeCourse = new ArrayList<Object>();
        fakeCourse.add("cmput");
        fakeCourse.add("300");
        fakeCourse.add(Course.DaysOfWeek.MonWedFri.name());
        fakeCourse.add(17);
        fakeCourse.add(1);
        fakeCourse.add("CSC");
        fakeCourse.add("yang");
        fakeResponse.add(fakeCourse);

        when(mockConnectionManager.query(anyString())).thenReturn(fakeResponse);
        CourseManager courseManager = new CourseManager(mockConnectionManager);
        List<Course> result = courseManager.getAllCourses();
        assertNotNull(result);
        Assert.assertTrue(result.size() == 2);
        Assert.assertTrue(result.get(0).courseSubject.equals("cmput"));
        Assert.assertTrue(result.get(1).courseNumber.equals("300"));
        Assert.assertTrue(result.get(0).courseDays.equals(Course.DaysOfWeek.TueThu));
        Assert.assertTrue(result.get(1).location.equals("CSC"));
        Assert.assertTrue(result.get(0).duration.equals(1));
        Assert.assertTrue(result.get(1).startTime.equals(17));
        Assert.assertTrue(result.get(0).professor.equals("snadi"));

        verify(mockConnectionManager, times(1)).query(anyString());
    }

    public void testremoveCourse()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        when(mockConnectionManager.execute(anyString())).thenReturn(true).thenReturn(false);

        CourseManager courseManager = new CourseManager(mockConnectionManager);

        Boolean result = courseManager.removeCourse("cmput", "402");
        assertNotNull(result);
        Assert.assertTrue(result);

        result = courseManager.removeCourse(null, null);
        assertNotNull(result);
        Assert.assertFalse(result);

        // Execute should only be called once, since removeCourse will reject empty or null subject/number
        verify(mockConnectionManager, times(1)).execute(anyString());
    }

    public void testassignTeacher()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        when(mockConnectionManager.execute(anyString())).thenReturn(true).thenReturn(false);

        CourseManager courseManager = new CourseManager(mockConnectionManager);

        Boolean result = courseManager.assignTeacher("snadi", "cmput", "402");
        assertNotNull(result);
        Assert.assertTrue(result);

        result = courseManager.assignTeacher(null, null, null);
        assertNotNull(result);
        Assert.assertFalse(result);

        // Execute should only be called once, since assignTeacher will reject empty or null subject/number
        verify(mockConnectionManager, times(1)).execute(anyString());
    }

    public void testunassignTeacher()
    {
        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        when(mockConnectionManager.execute(anyString())).thenReturn(true).thenReturn(false);

        CourseManager courseManager = new CourseManager(mockConnectionManager);

        Boolean result = courseManager.unassignTeacher("cmput", "402");
        assertNotNull(result);
        Assert.assertTrue(result);

        result = courseManager.assignTeacher(null, null, null);
        assertNotNull(result);
        Assert.assertFalse(result);

        // Execute should only be called once, since unassignTeacher will reject empty or null subject/number
        verify(mockConnectionManager, times(1)).execute(anyString());
    }
}
