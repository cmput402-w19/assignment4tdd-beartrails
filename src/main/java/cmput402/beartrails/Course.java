package cmput402.beartrails;

public class Course {
    public enum DaysOfWeek{MonWedFri, TueThu}

    public Course(String courseSubject, String courseNumber, DaysOfWeek courseDays,
                  Integer startTime, Integer duration, String location) {
        this.courseSubject = courseSubject;
        this.courseNumber = courseNumber;
        this.courseDays = courseDays;
        this.startTime = startTime;
        this.duration = duration;
        this.location = location;
    }
    
    public String courseSubject;
    public String courseNumber;
    public DaysOfWeek courseDays;
    public Integer startTime;
    public Integer duration;
    public String location;
    public String professor;
}
