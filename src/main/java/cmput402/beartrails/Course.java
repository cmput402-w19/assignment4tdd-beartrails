package cmput402.beartrails;

import java.sql.Time;

public class Course {
    public enum DaysOfWeek{MonWedFri, TueThu}

    public int courseId;
    public String courseSubject;
    public String courseNumber;
    public DaysOfWeek courseDays;
    public Time startTime;
    public Integer duration;
    public String location;
}
