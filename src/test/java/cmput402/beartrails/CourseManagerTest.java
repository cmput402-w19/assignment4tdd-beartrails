package cmput402.beartrails;

import junit.framework.Assert;
import junit.framework.TestCase;

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
        Assert.assertTrue(result);

        verify(mockConnectionManager, times(1)).execute(anyString());
    }
}
