package cmput402.beartrails;

public class Course {
    public enum DaysOfWeek{MonWedFri, TueThu}

    public String courseSubject;
    public String courseNumber;
    public DaysOfWeek courseDays;
    public Integer startTime;
    public Integer duration;
    public String location;
    public String professor;
}
