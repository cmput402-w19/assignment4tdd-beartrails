package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;

public class ListCoursesMenuAction extends AbstractMenuAction {

	@Override
	public Boolean execute() {
		if(this.courseManager == null) {
			return false;
		}
		
		List<Course> courseList = this.courseManager.getAllCourses();
		
		if(courseList.size() == 0) {
			System.out.println("\nSorry, there are currently no courses entered into the system");
		}
		System.out.println();
		//https://stackoverflow.com/a/18672745/2038127
	    System.out.format("%15s%15s%15s%15s%15s%15s%15s\n", "Subject", "Number", 
	    				  "Days", "Start Time", "Duration", 
	    				  "Location", "Professor");
		for(Course course: courseList) {
			String subject = course.courseSubject;
			String number = course.courseNumber.toString();
			String days = course.courseDays.toString();
			String startTime = course.startTime.toString() + ":00";
			String duration = course.duration.toString();
			String location = course.location;
			String professor = course.professor;
			
			System.out.format("%15s%15s%15s%15s%15s%15s%15s\n", subject, number, 
  				  days, startTime, duration, 
  				  location, professor);
		}
		
		return true;
	}

}
