package cmput402.beartrails.ui.menu;

import java.util.List;

import cmput402.beartrails.Course;
import cmput402.beartrails.User;

public class AssignGradeMenuAction extends AbstractMenuAction {

	public String toString() {
		return "Assign a grade";
	}
	
	@Override
	public Boolean execute() {
		if(this.scheduleManager == null 
				|| this.gradeManager == null
				|| this.courseManager == null) {
			return false;
		}

		List<Course> courses = 
				this.scheduleManager.getTeacherSchedule(loggedInUser.username);
		Integer numCourses = courses.size();

		if(numCourses == 0) {
			System.out.println("\nYou are not currently assigned to any courses.");
			return true;
		}

		//Loop until we successfully assign a grade
		while(true) {
			//Display course list
			System.out.println();
			//https://stackoverflow.com/a/18672745/2038127
			System.out.format("%3s%15s%15s\n", 
					"", "Subject", "Number");
			Integer i = 1;
			for(Course course: courses) {
				String subject = course.courseSubject;
				String number = course.courseNumber.toString();

				System.out.format("%3d%15s%15s\n", 
						i, subject, number);
				i++;
			}


			//Loop until we have a valid input
			String input;
			while(true) {
				input = integerPrompter.promptUser("Select a course (1-" +
						numCourses + "): ", 1, numCourses);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					break;
				}
			}

			Course selectedCourse = courses.get(Integer.valueOf(input)-1);

			List<User> students = this.courseManager.getStudentsInCourse(
					selectedCourse.courseSubject, 
					selectedCourse.courseNumber);

			Integer numStudents = students.size();

			if(numStudents == 0) {
				System.out.println("\nThere are no students in that class.");
				return true;
			}

			//Display student list
			System.out.println();
			//https://stackoverflow.com/a/18672745/2038127
			System.out.format("%3s%15s%15ss\n", 
					"", "First Name", "Last Name");
			i = 1;
			for(User student: students) {
				String firstName = student.firstName;
				String lastName = student.lastName;
				System.out.format("%3d%15s%15s\n", 
						i, firstName, lastName);
				i++;
			}

			//Loop until we have a valid input
			while(true) {
				input = integerPrompter.promptUser("Select a student (1-" +
						numStudents + "): ", 1, numStudents);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					break;
				}
			}
			
			User selectedStudent = students.get(Integer.valueOf(input)-1);
			
			//Loop until we have a valid input
			while(true) {
				input = doublePrompter.promptUser("Enter a grade: ", 0d, 4d);
				if(doublePrompter.inputWasGoBack()) {
					return true;
				} else if(doublePrompter.inputWasInvalid()) {
					doublePrompter.printTryAgain();
				} else {
					break;
				}
			}

			Boolean rv = gradeManager.assignGrade(Double.valueOf(input), 
												  selectedStudent.username, 
												  selectedCourse.courseSubject, 
												  selectedCourse.courseNumber); 
					
			if(rv == true) {
				System.out.println("Grade assigned successfully!");
				return true;
			} else {
				System.out.println("There was a problem assigning the grade." +
						" Please try again.");
				//And loop again
			}
		}
	}
}

