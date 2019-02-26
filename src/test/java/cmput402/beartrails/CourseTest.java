package cmput402.beartrails;

import junit.framework.TestCase;

public class CourseTest extends TestCase {
    String subject1 = "cmput";
    String number1 = "402";
    Course.DaysOfWeek days1 = Course.DaysOfWeek.MonWedFri;
    Integer start1 = 12;
    Integer duration1 = 1;
    String loc1 = "CAB";
    String prof1 = "snadi";

    String subject2 = "math";
    String number2 = "222";
    Course.DaysOfWeek days2 = Course.DaysOfWeek.TueThu;
    Integer start2 = 13;
    Integer duration2 = 2;
    String loc2 = "CSC";
    String prof2 = "tpas";

    /*
     * Test equality between an object and itself
     */
    public void testSameObject() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = course1;

        assertTrue(course1.equals(course2));
    }

    public void testDifferentTypes() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        String string = "I'm not a course!";

        assertFalse(course1.equals(string));
    }

    /*
     * Different objects, but equal fields/properties
     */
    public void testEqualFields() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course2.professor = prof1;

        assertTrue(course1.equals(course2));
    }

    /*
     * Test equality when no fields are the same
     */
    public void testNotEqual() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject2, number2, days2, start2, duration2, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Test equality when only subject is same
     */
    public void testUpToSubject() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number2, days2, start2, duration2, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Subject and number equal
     */
    public void testUpToNumber() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days2, start2, duration2, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Subject, number and days equal
     */
    public void testUpToDays() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days1, start2, duration2, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Subject, number, days and start equal
     */
    public void testUpToStart() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days1, start1, duration2, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Subject, number, days, start and duration equal
     */
    public void testUpToDuration() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days1, start2, duration1, loc2);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }

    /*
     * Subject, number, days, start, duration, location equal
     */
    public void testUpToLocation() {
        Course course1 = new Course(subject1, number1, days1, start1, duration1, loc1);
        course1.professor = prof1;
        Course course2 = new Course(subject1, number1, days1, start2, duration1, loc1);
        course2.professor = prof2;

        assertFalse(course1.equals(course2));
    }
}
