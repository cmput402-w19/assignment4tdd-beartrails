package cmput402.beartrails;

import junit.framework.TestCase;

import java.util.HashMap;

import static org.mockito.Mockito.*;


public class GradeManagerTest extends TestCase {

    public void testgetStudentGPA() {
        GradeManager mockGradeManager = mock(GradeManager.class);
        User student = new User("repka", "Repka", "Derek", User.Type.Student);
        Course fakeCourse = new Course();
        fakeCourse.courseNumber = "402";
        fakeCourse.courseSubject = "CMPUT";

        Course fakeCourse2 = new Course();
        fakeCourse.courseNumber = "300";
        fakeCourse.courseSubject = "CMPUT";

        Course fakeCourse3 = new Course();
        fakeCourse.courseNumber = "201";
        fakeCourse.courseSubject = "CMPUT";

        HashMap<Course, Float> fakeGrades = new HashMap<Course, Float>();
        fakeGrades.put(fakeCourse, 3.5f);
        fakeGrades.put(fakeCourse2, 3.0f);
        fakeGrades.put(fakeCourse3, 3.7f);

        when(mockGradeManager.getStudentGrades(student.username)).thenReturn(fakeGrades);

        Float result = mockGradeManager.getStudentGPA(student.username);
        assertTrue(result == 3.4f);
    }
}
