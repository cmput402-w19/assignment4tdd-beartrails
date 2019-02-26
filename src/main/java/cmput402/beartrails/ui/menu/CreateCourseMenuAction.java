package cmput402.beartrails.ui.menu;

import cmput402.beartrails.User;
import cmput402.beartrails.Course;
import cmput402.beartrails.Course.DaysOfWeek;

public class CreateCourseMenuAction extends AbstractMenuAction {

	public String toString() {
		return "Create a new course";
	}
	
	@Override
	public Boolean execute() {
		if(this.courseManager == null) {
			return false;
		}
		
		String subject;
		String number;
		Course.DaysOfWeek days;
		Integer startTime;
		Integer duration;
		String location;

		
		//Loop until course is successfully created
		while(true) {
			
			//courseSubject;
			while(true) {
				subject = stringPrompter.promptUser("\nCourse subject: ");
				if(stringPrompter.inputWasGoBack()) {
					return true;
				} else if(stringPrompter.inputWasInvalid()) {
					stringPrompter.printTryAgain();
				} else {
					break;
				}
			}
			
		    //courseNumber;
			while(true) {
				number = integerPrompter.promptUser("\nCourse number (between 100 and 699): ", 100, 699);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					break;
				}
			}
			
		    //courseDays;
			Integer numValues = Course.DaysOfWeek.values().length;
			String daysPrompt = "\nPlease select the days the course will be on:\n";
			for(Integer i = 1; i <= numValues; i++) {
				daysPrompt += i.toString() + "\t" + Course.DaysOfWeek.values()[i-1] + "\n";
			}
			daysPrompt += "Enter a number (1-" + numValues.toString() + "): ";
			
			while(true) {
				String input = integerPrompter.promptUser(daysPrompt, 1, numValues);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					days = Course.DaysOfWeek.values()[Integer.valueOf(input)-1];
					break;
				}
			}
			
		    //startTime;
			while(true) {
				String input = integerPrompter.promptUser("\nStart time (hour between 8 and 20): ", 8, 20);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					startTime = Integer.valueOf(input);
					break;
				}
			}
			
		    //duration;
			while(true) {
				String input = integerPrompter.promptUser("\nDuration (#hours between 1 and 3): ", 1, 3);
				if(integerPrompter.inputWasGoBack()) {
					return true;
				} else if(integerPrompter.inputWasInvalid()) {
					integerPrompter.printTryAgain();
				} else {
					duration = Integer.valueOf(input);
					break;
				}
			}
			
		    //location;
			while(true) {
				location = stringPrompter.promptUser("\nLocation (no spaces): ");
				if(stringPrompter.inputWasGoBack()) {
					return true;
				} else if(stringPrompter.inputWasInvalid()) {
					stringPrompter.printTryAgain();
				} else {
					break;
				}
			}
			
			Course newCourse = new Course(subject, 
										  number, 
										  days, 
										  startTime, 
										  duration, 
										  location);
			
			Boolean rv = courseManager.createCourse(newCourse);
			
			if(rv == true) {
				System.out.println("Course created successfully!");
				return true;
			} else {
				System.out.println("There was an issue adding the course." +
									"Please try again.");
			} //And loop again
		}
	}

}
