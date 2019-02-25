package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;
import cmput402.beartrails.User;

public class AssignTeacherMenuAction extends AbstractMenuAction {
	
	public String toString() {
		return "Assign a teacher to a class";
	}
	
	@Override
	public Boolean execute() {
		if(this.courseManager == null 
				|| this.scheduleManager == null
				|| this.userManager == null) {
			return false;
		}
		
		List<Course> availableCourses = 
				this.courseManager.getAllCourses();
		Integer numCourses = availableCourses.size();
		
		if(numCourses == 0) {
			System.out.println("\nSorry, there are currently no courses " +
								"entered into the system");
			return true;
		}
		
		List<User> teacherList =
				this.userManager.getTeachers();
		Integer numTeachers = teacherList.size();
		
		if(numTeachers == 0) {
			System.out.println("\nSorry, there are currently no teachers " +
								"entered into the system.");
			return true;
		}
		
		
		//Display course list
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
		

		//Loop until we have a valid input
		String input;
		while(true) {
			input = integerPrompter.promptUser("Select a course to assign (1-" +
					numCourses + "): ", 1, numCourses);
			if(integerPrompter.inputWasGoBack()) {
				return true;
			} else if(integerPrompter.inputWasInvalid()) {
				integerPrompter.printTryAgain();
			} else {
				break;
			}
		}

		Course selectedCourse = availableCourses.get(Integer.valueOf(input)-1);
		
		//Display teacher list
		System.out.println();
		//https://stackoverflow.com/a/18672745/2038127
	    System.out.format("%3s%15s%15ss\n", 
	    				  "", "First Name", "Last Name");
		i = 1;
		for(User teacher: teacherList) {
			String firstName = teacher.firstName;
			String lastName = teacher.lastName;
			System.out.format("%3d%15s%15s\n", 
							  i, firstName, lastName);
			i++;
		}
		
		//Loop until we successfully assign teacher
		while(true) {
			//Loop until we have a valid input
			while(true) {
				input = integerPrompter.promptUser("Select a teacher to assign (1-" +
						numTeachers + "): ", 1, numTeachers);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					break;
				}
			}
			
			User selectedTeacher = teacherList.get(Integer.valueOf(input)-1);

			Boolean rv = courseManager.assignTeacher(selectedTeacher.username, 
													 selectedCourse.courseSubject, 
													 selectedCourse.courseNumber);
			if(rv == true) {
				System.out.println("Teacher assigned successfully!");
				return true;
			} else {
				System.out.println("There was a problem assigning that teacher" +
						" Please try again.");
				//And loop again
			}
		}
	}
}
