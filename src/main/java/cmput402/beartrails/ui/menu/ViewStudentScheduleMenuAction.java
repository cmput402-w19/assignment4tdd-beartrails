package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;

public class ViewStudentScheduleMenuAction extends AbstractMenuAction {

	@Override
	public Boolean execute() {
		if(this.scheduleManager == null) {
			return false;
		}
		
		List<Course> courseList = this.scheduleManager
				.getStudentSchedule(loggedInUser.username);	
		
		if(courseList.size() == 0) {
			System.out.println("\nYou are not currently registered in any courses.");
			return true;
		}
		System.out.println();
		//https://stackoverflow.com/a/18672745/2038127
	    System.out.format("%15s%15s%15s%15s%15s%15s%15s\n", 
	    				  "Subject", "Number", 
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
			
			System.out.format("%15s%15s%15s%15s%15s%15s%15s\n", 
				        		subject, number, 
				        		days, startTime, duration, 
				        		location, professor);
		}
		
		return true;
	}

}
