package cmput402.beartrails;

import junit.framework.TestCase;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScheduleManagerTest extends TestCase {

    public void testGetStudentSchedule() {

        User student = new User("zred", "Zach", "Redfern", User.Type.Student);

        Course course1 = new Course("cmput", "404", Course.DaysOfWeek.MonWedFri,
                8, 1, "CSC");
        Course course2 = new Course("stat", "252", Course.DaysOfWeek.MonWedFri,
                9, 1, "ETLC");
        course2.professor = "snadi";
        Course course3 = new Course("cmput", "402", Course.DaysOfWeek.TueThu,
                11, 1, "CSC");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, student);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(course1.courseSubject);
        row1.add(course1.courseNumber);
        row1.add(course1.courseDays.name());
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);
        row1.add(null);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays.name());
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);
        row2.add(course2.professor);

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays.name());
        row3.add(course3.startTime);
        row3.add(course3.duration);
        row3.add(course3.location);
        row3.add(null);

        queryList.add(row1);
        queryList.add(row2);
        queryList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        List<Course> courseList = scheduleManager.getStudentSchedule(student.username);

        assert(courseList.size() == 3);

        assert(courseList.get(0).courseSubject.equals(course1.courseSubject));
        assert(courseList.get(0).courseNumber.equals(course1.courseNumber));
        assert(courseList.get(0).courseDays.equals(course1.courseDays));
        assert(courseList.get(0).startTime.equals(course1.startTime));
        assert(courseList.get(0).duration.equals(course1.duration));
        assert(courseList.get(0).location.equals(course1.location));
        assertNull(courseList.get(0).professor);

        assert(courseList.get(1).courseSubject.equals(course2.courseSubject));
        assert(courseList.get(1).courseNumber.equals(course2.courseNumber));
        assert(courseList.get(1).courseDays.equals(course2.courseDays));
        assert(courseList.get(1).startTime.equals(course2.startTime));
        assert(courseList.get(1).duration.equals(course2.duration));
        assert(courseList.get(1).location.equals(course2.location));
        assert(courseList.get(1).professor.equals(course2.professor));

        assert(courseList.get(2).courseSubject.equals(course3.courseSubject));
        assert(courseList.get(2).courseNumber.equals(course3.courseNumber));
        assert(courseList.get(2).courseDays.equals(course3.courseDays));
        assert(courseList.get(2).startTime.equals(course3.startTime));
        assert(courseList.get(2).duration.equals(course3.duration));
        assert(courseList.get(2).location.equals(course3.location));
        assertNull(courseList.get(2).professor);
    }

    public void testGetTeacherSchedule() {

        User professor = new User("zred", "Zach", "Redfern", User.Type.Professor);

        Course course1 = new Course("cmput", "404", Course.DaysOfWeek.MonWedFri,
                8, 1, "CSC");
        Course course2 = new Course("stat", "252", Course.DaysOfWeek.MonWedFri,
                9, 1, "ETLC");
        Course course3 = new Course("cmput", "402", Course.DaysOfWeek.TueThu,
                11, 1, "CSC");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, professor);
        List<List<Object>> queryList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(course1.courseSubject);
        row1.add(course1.courseNumber);
        row1.add(course1.courseDays.name());
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays.name());
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays.name());
        row3.add(course3.startTime);
        row3.add(course3.duration);
        row3.add(course3.location);

        queryList.add(row1);
        queryList.add(row2);
        queryList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(queryList);

        List<Course> courseList = scheduleManager.getTeacherSchedule(professor.username);

        assert(courseList.size() == 3);

        assert(courseList.get(0).courseSubject.equals(course1.courseSubject));
        assert(courseList.get(0).courseNumber.equals(course1.courseNumber));
        assert(courseList.get(0).courseDays.equals(course1.courseDays));
        assert(courseList.get(0).startTime.equals(course1.startTime));
        assert(courseList.get(0).duration.equals(course1.duration));
        assert(courseList.get(0).location.equals(course1.location));

        assert(courseList.get(1).courseSubject.equals(course2.courseSubject));
        assert(courseList.get(1).courseNumber.equals(course2.courseNumber));
        assert(courseList.get(1).courseDays.equals(course2.courseDays));
        assert(courseList.get(1).startTime.equals(course2.startTime));
        assert(courseList.get(1).duration.equals(course2.duration));
        assert(courseList.get(1).location.equals(course2.location));

        assert(courseList.get(2).courseSubject.equals(course3.courseSubject));
        assert(courseList.get(2).courseNumber.equals(course3.courseNumber));
        assert(courseList.get(2).courseDays.equals(course3.courseDays));
        assert(courseList.get(2).startTime.equals(course3.startTime));
        assert(courseList.get(2).duration.equals(course3.duration));
        assert(courseList.get(2).location.equals(course3.location));
    }

    public void testAddCourse() {

        User student = new User("zred", "Zach", "Redfern", User.Type.Student);

        Course course1 = new Course("cmput", "404", Course.DaysOfWeek.MonWedFri,
                8, 1, "CSC");
        Course course2 = new Course("stat", "252", Course.DaysOfWeek.MonWedFri,
                9, 1, "ETLC");
        Course course3 = new Course("cmput", "402", Course.DaysOfWeek.TueThu,
                11, 1, "CSC");

        Course newCourse = new Course("math", "222", Course.DaysOfWeek.TueThu,
                12, 1, "CAB");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, student);

        // The day and time of the course the student is trying to add
        List<List<Object>> courseList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();
        row.add(newCourse.courseDays);
        row.add(newCourse.startTime);
        row.add(newCourse.duration);
        courseList.add(row);

        List<List<Object>> scheduleList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(course1.courseSubject);
        row1.add(course1.courseNumber);
        row1.add(course1.courseDays.name());
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);
        row1.add(null);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays.name());
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);
        row2.add("snadi");

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays.name());
        row3.add(course3.startTime);
        row3.add(course3.duration);
        row3.add(course3.location);
        row3.add(null);

        scheduleList.add(row1);
        scheduleList.add(row2);
        scheduleList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(courseList).thenReturn(scheduleList);

        when(mockConnectionManager.execute(Mockito.anyString())).thenReturn(true);

        assertTrue(scheduleManager.addCourse(newCourse.courseSubject, newCourse.courseNumber));
    }

    public void testAddCourseDoesNotExist() {

        User student = new User("zred", "Zach", "Redfern", User.Type.Professor);

        Course newCourse = new Course("fake", "999", Course.DaysOfWeek.TueThu,
                12, 1, "CAB");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, student);

        // An empty list since the query should return no results (as the course doesn't exist)
        List<List<Object>> courseList = new ArrayList<List<Object>>();

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(courseList);

        assertFalse(scheduleManager.addCourse(newCourse.courseSubject, newCourse.courseNumber));
    }

    public void testAddCourseConflict() {

        User student = new User("zred", "Zach", "Redfern", User.Type.Professor);

        Course course1 = new Course("cmput", "404", Course.DaysOfWeek.MonWedFri,
                8, 1, "CSC");
        Course course2 = new Course("stat", "252", Course.DaysOfWeek.MonWedFri,
                9, 1, "ETLC");
        Course course3 = new Course("cmput", "402", Course.DaysOfWeek.TueThu,
                11, 1, "CSC");

        // This new course conflicts with course3 since they are at the same time
        Course newCourse = new Course("math", "222", Course.DaysOfWeek.TueThu,
                11, 1, "CAB");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, student);

        // The day and time of the course the student is trying to add
        List<List<Object>> courseList = new ArrayList<List<Object>>();
        List<Object> row = new ArrayList<Object>();
        row.add(newCourse.courseDays.name());
        row.add(newCourse.startTime);
        row.add(newCourse.duration);
        courseList.add(row);

        List<List<Object>> scheduleList = new ArrayList<List<Object>>();
        List<Object> row1 = new ArrayList<Object>();
        List<Object> row2 = new ArrayList<Object>();
        List<Object> row3 = new ArrayList<Object>();

        row1.add(course1.courseSubject);
        row1.add(course1.courseNumber);
        row1.add(course1.courseDays.name());
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);
        row1.add(null);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays.name());
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);
        row2.add("snadi");

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays.name());
        row3.add(course3.startTime);
        row3.add(course3.duration);
        row3.add(course3.location);
        row3.add(null);

        scheduleList.add(row1);
        scheduleList.add(row2);
        scheduleList.add(row3);

        when(mockConnectionManager.query(Mockito.anyString())).thenReturn(courseList).thenReturn(scheduleList);

        when(mockConnectionManager.execute(Mockito.anyString())).thenReturn(true);

        assertFalse(scheduleManager.addCourse(newCourse.courseSubject, newCourse.courseNumber));
    }

    public void testRemoveCourse() {

        User student = new User("zred", "Zach", "Redfern", User.Type.Student);

        Course newCourse = new Course("math", "201", Course.DaysOfWeek.TueThu,
                12, 1, "CAB");

        ConnectionManager mockConnectionManager = mock(ConnectionManager.class);
        ScheduleManager scheduleManager = new ScheduleManager(mockConnectionManager, student);

        when(mockConnectionManager.execute(Mockito.anyString())).thenReturn(true);

        assertTrue(scheduleManager.removeCourse(newCourse.courseSubject, newCourse.courseNumber));
    }
}
