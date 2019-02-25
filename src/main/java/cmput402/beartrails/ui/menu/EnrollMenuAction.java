package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;

public class EnrollMenuAction extends AbstractMenuAction {

	public String toString() {
		return "Enroll in a class";
	}
	
	@Override
	public Boolean execute() {
		if(this.courseManager == null || this.scheduleManager == null) {
			return false;
		}
		
		List<Course> availableCourses = 
				this.courseManager.getAllCourses();
		
		Integer numCourses = availableCourses.size();
		
		if(numCourses == 0) {
			System.out.println("\nSorry, there are currently no courses entered into the system");
			return true;
		}
		
		System.out.println();
		//https://stackoverflow.com/a/18672745/2038127
	    System.out.format("%3s%15s%15s%15s%15s%15s%15s%15s\n", 
	    				  "", "Subject", "Number", 
	    				  "Days", "Start Time", "Duration", 
	    				  "Location", "Professor");
		Integer i = 1;
		for(Course course: availableCourses) {
			String subject = course.courseSubject;
			String number = course.courseNumber.toString();
			String days = course.courseDays.toString();
			String startTime = course.startTime.toString() + ":00";
			String duration = course.duration.toString();
			String location = course.location;
			String professor = course.professor;
			
			System.out.format("%3d%15s%15s%15s%15s%15s%15s%15s\n", 
							  i, subject, number, 
							  days, startTime, duration, 
							  location, professor);
			i++;
		}
		
		//Loop until enrollment is successful
		while(true) {
			
			//Loop until we have a valid input
			String input;
			while(true) {
				input = integerPrompter.promptUser("Select a course to enroll in (1-" +
						availableCourses.size() + "): ", 1, numCourses);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					break;
				}
			}

			Course selectedCourse = availableCourses.get(Integer.valueOf(input)-1);

			Boolean rv = scheduleManager.addCourse(selectedCourse.courseSubject, 
					selectedCourse.courseNumber);

			if(rv == true) {
				System.out.println("Enrollment successful!");
				return true;
			} else {
				System.out.println("There was a problem enrolling in that course." +
			                       " Please try again.");
				//And loop again
			}
		}
		
	}

}
