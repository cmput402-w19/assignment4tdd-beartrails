package cmput402.beartrails;

import static org.mockito.Mock.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.mockito.Mockito;

public class ScheduleManagerTest extends TestCase {

    public void testGetStudentSchedule() {

        User student = new User("zred", "Redfern", "Zach", User.Type.Student);

        Course course1 = new Course("cmput", "404", Course.DaysOfWeek.MonWedFri,
                8, 1, "CSC");
        Course course2 = new Course("stat", "252", Course.DaysOfWeek.MonWedFri,
                9, 1, "ETLC");
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
        row1.add(course1.courseDays);
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays);
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays);
        row3.add(course3.startTime);
        row3.add(course3.duration);
        row3.add(course3.location);

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

    public void testGetTeacherSchedule() {

        User professor = new User("zred", "Redfern", "Zach", User.Type.Professor);

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
        row1.add(course1.courseDays);
        row1.add(course1.startTime);
        row1.add(course1.duration);
        row1.add(course1.location);

        row2.add(course2.courseSubject);
        row2.add(course2.courseNumber);
        row2.add(course2.courseDays);
        row2.add(course2.startTime);
        row2.add(course2.duration);
        row2.add(course2.location);

        row3.add(course3.courseSubject);
        row3.add(course3.courseNumber);
        row3.add(course3.courseDays);
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
}
