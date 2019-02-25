package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;

public class ViewTeacherScheduleMenuAction extends AbstractMenuAction {
	
	public String toString() {
		return "View schedule";
	}
	
	@Override
	public Boolean execute() {
		if(this.scheduleManager == null) {
			return false;
		}
		
		List<Course> courseList = this.scheduleManager
				.getTeacherSchedule(loggedInUser.username);	
		
		if(courseList.size() == 0) {
			System.out.println("\nYou are not currently assigned to teach any courses.");
			return true;
		}
		System.out.println();
		//https://stackoverflow.com/a/18672745/2038127
	    System.out.format("%15s%15s%15s%15s%15s%15s\n", 
	    				  "Subject", "Number", 
	    				  "Days", "Start Time", "Duration", 
	    				  "Location");
		for(Course course: courseList) {
			String subject = course.courseSubject;
			String number = course.courseNumber.toString();
			String days = course.courseDays.toString();
			String startTime = course.startTime.toString() + ":00";
			String duration = course.duration.toString();
			String location = course.location;
			
			System.out.format("%15s%15s%15s%15s%15s%15s\n", 
				        		subject, number, 
				        		days, startTime, duration, 
				        		location);
		}
		
		return true;
	}
}
