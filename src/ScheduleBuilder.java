/*
 * CodeJam 2016
 * @author Samuel Beaubien
 * 
 */

import java.util.ArrayList;

public class ScheduleBuilder {

	
	public static void main(String[] args)
	{
		
		
		
		
	}
	
	public static void buildSchedule(ArrayList<Course> arrayCourses, ArrayList<Student> arrayStudents)
	{
		
	}
	
	
	
	/* Takes a ListArray of courses as arguments
	 * Returns a ListArray of lecture
	 * 
	 * This method gets all occurrences of each Course and assign them to an ArrayList of lectures
	 */
	public static ArrayList<Lecture> getArrayLectures(ArrayList<Course> arrayOfCourses)
	{
		
		ArrayList<Lecture> arrayOfLectures = new ArrayList<Lecture>();	// Initialize ArrayList
		
		int size_arrayOfCourses = arrayOfCourses.size(); 
		
		// For each courses, get all occurrence in arrayOfLectures
		for (int i = 0; i < size_arrayOfCourses; i++)
		{
			
			int size_occurrences = arrayOfCourses.get(i).occurrences.size();	// Get the size of the ArrayList occurrences
			
			// For all lectures in the occurrences array, append to the end of arrayOfLectures
			for (int j = 0; j < size_occurrences; j++)
			{
				arrayOfLectures.add(arrayOfCourses.get(i).occurrences.get(j));	//Append
			}	
		}
		return arrayOfLectures;	
	}
	
	
	/*	Takes an ArrayList of Lectures and a Student arguments
	 * 	Returns void 
	 * 
	 * This method fill all possible lecture matching the students schedule 
	 * It then append those lectures in the possibleLectures array of the students class
	 * 
	 * 
	 * For each lecture, the program goes over each slot of the student and verifies if 
	 * the lecture fits in. It uses the method fitsIn
	 */
	
	public static void fillPossibleArrays(ArrayList<Lecture> arrayOfLecture, ArrayList<Student> arrayOfStudents)
	{
		
		// Each iteration uses a new student
		for (int indexStudents = 0; indexStudents < arrayOfStudents.size(); indexStudents++)
		{

			Student currentStudent = arrayOfStudents.get(indexStudents);
			// At each iteration, a new lecture is taken 
			for (int indexLectures = 0; indexLectures < arrayOfLecture.size(); indexLectures++ )
			{

				Lecture currentLecture = arrayOfLecture.get(indexLectures);

				// At each iteration, the programs goes over all the student slots to see if 
				// 		the lecture fits in. If yes, the lecture is added the possible lectures array
				for (int indexStudentSlots = 0; indexStudentSlots < currentStudent.dispo.size(); indexStudentSlots++)
				{
					// Checks whether the current lecture slot fits in the current student slot
					// If yes, put lecture in possibleLecture ArrayList and student in possibleStudents ArrayList
					if (currentLecture.time.fitsIn(currentStudent.dispo.get(indexStudentSlots))) 
					{
						currentStudent.possibleLectures.add(currentLecture);
						currentLecture.possibleStudents.add(currentStudent);
					}
				}
			}
		}
	}
	
	
	
	/*
	 * Takes one ArrayList<Student> as only argument
	 * Returns one ArrayList<Student> 
	 * 
	 * This method sorts an ArrayList of student in order of possible lectures
	 * It goes over the received array, find the student with the lowest number of possible lectures
	 * and append it to the sorted array. In order to keep the first array intact, it maes a copy
	 * of the given array. 
	 * 
	 */
		
	public static ArrayList<Student> sortStudents(ArrayList<Student> arrayOfStudents)
	{
		
		ArrayList<Student> copy_arrayOfStudents = new ArrayList<Student>(arrayOfStudents);	//Copy
		ArrayList<Student> sorted_arrayOfStudents = new ArrayList<Student>();	//Sorted Array
		
		//Each iteration finds the element with the lowest possibleLectures, copies it in 
		// 		the sorted array, then delete the element from the copy. 
		
		int initialSize_arrayOfStudent = copy_arrayOfStudents.size();
		
		for (int counter_copiedElements = 0; counter_copiedElements < initialSize_arrayOfStudent; counter_copiedElements++)
		{
			// At each iteration, we compare the new_minStudent with the minStudent
			// If the new_minStudent is smaller, we change it to minStudent and update the 
			// 	indexOfMinStudent
			
			Student minStudent = copy_arrayOfStudents.get(0);
			int indexOfMinStudent = 0;
			
			for (int counter_search = 1; counter_search < copy_arrayOfStudents.size(); counter_search++)
			{
				Student new_minStudent = copy_arrayOfStudents.get(counter_search);
				
				if (new_minStudent.possibleLectures.size() < minStudent.possibleLectures.size())
				{
					minStudent = new_minStudent;
					indexOfMinStudent = counter_search;
				}	
			}
			
			sorted_arrayOfStudents.add(minStudent);	// Put the minStudent in sorted_arrayOfStudents
			copy_arrayOfStudents.remove(indexOfMinStudent);	// Remove the the minStudent from copy_arrayOfStudents
			
		} // End last for loop
		
		return sorted_arrayOfStudents;
	}
	
	
	
	/*
	 * This method takes an arrayList of Lectures and sorts it
	 * It does not create a new array, it only modifies it!
	 * 
	 * This method sorts an array of lecture in increasing order of possible Student
	 * It uses two loop. One goes over each element of the array one time and either swap it or not
	 * The other go over each element of the array many time and find the smallest, which will be swaped
	 */
	
	public static void sortLectures(ArrayList<Lecture> arrayOfLecture)
	{
		
		int sizeArrayOfLecture = arrayOfLecture.size();
		
			// Each iteration : (1) get a currentLecture, (2) find the smallest newLecture the rest of the list
			//	(3), swap if currentLecture if biggest than newLecture
		for (int counter_currentLecture = 0; counter_currentLecture < sizeArrayOfLecture; counter_currentLecture++)
		{
			
			Lecture currentLecture = arrayOfLecture.get(counter_currentLecture);	// First, get the first lecture
			Lecture smallest_newLecture = currentLecture;	 // Then, get the second lecture
			int index_smallest_newLecture = counter_currentLecture;
			
				// Go get the smallest element in the rest of the array
			for (int counter_newLecture = counter_currentLecture + 1; counter_newLecture < sizeArrayOfLecture ;counter_newLecture++)
			{
				Lecture newLecture = arrayOfLecture.get(counter_newLecture);
				
					// If newLecture is smallest than smallest_newLecture , then change
				if (smallest_newLecture.possibleStudents.size() > newLecture.possibleStudents.size())
				{
					smallest_newLecture = newLecture;
					index_smallest_newLecture = counter_newLecture;
				}
			}
			
				// If current lecture is smallest than smallest_newLecture, swap
			
			if (currentLecture.possibleStudents.size() > smallest_newLecture.possibleStudents.size())
			{
				Lecture temp = currentLecture;
				
				arrayOfLecture.set(counter_currentLecture, smallest_newLecture);
				arrayOfLecture.set(index_smallest_newLecture, temp);
			}
		} //End first for loop	
	} // End method
	
	
	
	/*
	 * 	Takes two arguments. An ArrayList<Lecture> and an ArrayList<Student>
	 * 	Returns void
	 * 
	 * This method is the main method of the ScheduleBuilder class. This method assigns students to lectures and lectures to students
	 * 
	 * First, the method takes one students in the arrayOfStudents ArrayList
	 * Then, the method takes in order the lectures in the arrayOfLecture class and check whether it is in the ArrayList of 
	 * possibleLecture of the student. Once one lecture is selected, it must not conflicts with another lecture and ont student must not have two time the same course
	 * If yes, the lecture is assign to the student and the student is assign to the class
	 * Then, the student is removed from the possibleStudents ArrayList in the lecture and the lecture is removed from the possibleLecture of the student
	 * If the lecture has now 20 students (max), the method removeLecture is used to remove the lecture and sort the array of lecture and students
	 * 
	 */
	
	public static ArrayList<Student> assignCourses(ArrayList<Lecture> arrayOfLectures, ArrayList<Student> arrayOfStudents)
	{
		
		ArrayList<Student> filledArrayOfStudents = new ArrayList<Student>(); 
		
			// While there is students, continue
		while(arrayOfStudents.size() > 0 )
		{
				// Get the student
			Student currentStudent = arrayOfStudents.get(0);


			// Try, in order, each of the arrayLectures to see if fits in student schedule, While the students doesn't have 5 lecture or the ArrayList of possibleLectures is empty, continue
			for (int counter_arrayOfLectures = 0; counter_arrayOfLectures < arrayOfLectures.size() && currentStudent.lecturesTaken.size() < 5 && currentStudent.possibleLectures.size() != 0; counter_arrayOfLectures++)
			{

				//First get the lecture
				Lecture currentLecture = arrayOfLectures.get(counter_arrayOfLectures);

				// If possibleLecture contains currentLecture, proceed, else restart
				if (currentStudent.possibleLectures.contains(currentLecture))
				{

					//Check if there if any conflict between currentStudent lecturesTaken and currentLecture
					// If true (no conflict) proceed
					if(isConflict(currentStudent.lecturesTaken, currentLecture))
					{

						// Put current lecture in lectureTaken 
						currentStudent.lecturesTaken.add(currentLecture);
						// Removes currentLecture form possibleLectures
						currentStudent.possibleLectures.remove(currentLecture);

						//Put student in lecture
						currentLecture.students.add(currentStudent);
						// Removes student from possible student
						currentLecture.possibleStudents.remove(currentStudent);

						// If lecture is full remove it from arrayLecture
						if (currentLecture.students.size() >= 20)
						{
							removeLecture(arrayOfLectures, currentLecture, arrayOfStudents);
						}	
					}
				}
			} // End for loop 


			// Remove the student from all array of Lecture where he was possibleStudent 
			for (int counter_arrayOfLectures = 0; counter_arrayOfLectures < arrayOfLectures.size(); counter_arrayOfLectures++)
			{
				if (arrayOfLectures.get(counter_arrayOfLectures).possibleStudents.contains(currentStudent))
				{
					arrayOfLectures.get(counter_arrayOfLectures).possibleStudents.remove(currentStudent);
				}
			}

			filledArrayOfStudents.add(currentStudent);
			
			// Remove the student
			arrayOfStudents.remove(0);
			
		} // While loop arrayOfStudent
		
		return filledArrayOfStudents;
		
	} // End Method
	
	/*
	 * Takes a lecture and ArrayList<Lecture> of lecture taken
	 * Return boolean true lecture fit in schedule and false otherwise
	 * 
	 * This method check if there is any conflict between a lecture and an array of lecture
	 */
	
	public static boolean isConflict(ArrayList<Lecture> arrayOfLectures, Lecture sampleLecture)
	{
		// For all lectures in arrayOfLecture, check if ok
		for (int counter_arrayOfLectures = 0; counter_arrayOfLectures < arrayOfLectures.size() ;counter_arrayOfLectures++)
		{
				// Get lecture to compare with
			Lecture currentLecture = arrayOfLectures.get(counter_arrayOfLectures);
			
				// Same lecture
			if (currentLecture.courseName.equals(sampleLecture.courseName))
			{
				return false;
			}
			
				// If same day check time
			else if (currentLecture.time.day.equals(sampleLecture.time.day))
			{
					// Check if sample starts during current
				if (sampleLecture.time.start > currentLecture.time.start && sampleLecture.time.start < currentLecture.time.end)
				{
					return false;
				}
				
				// Check if sample finishes during current
				if (sampleLecture.time.end > currentLecture.time.start && sampleLecture.time.end < currentLecture.time.end)
				{
					return false;
				}
				
				// Check if current in included in sample
				if (sampleLecture.time.start <= currentLecture.time.start && sampleLecture.time.end >= currentLecture.time.end)
				{
					return false;
				}
			}
		}

		return true;
	}
	
	
	/*
	 * Takes an ArrayList<Lecture>, an ArrayList<Student>, and a Lecture
	 * Return void
	 * 
	 * This method removes a lecture from the arrayOfLectures given. 
	 * It then removes the lecture from the Students's possibleLectures array, for the one who had it in the arrayOfStudents 
	 * It then sort the arrayOfLectures
	 * Finally, it sorts the arrayOfStudents array
	 */
	
	public static void removeLecture(ArrayList<Lecture> arrayOfLectures, Lecture currentLecture, ArrayList<Student> arrayOfStudents)
	{
		
		// Removes a lecture from arraOfLectures
		arrayOfLectures.remove(currentLecture);
		
			// For all students in arrayOfStudents, remove the lecture currentLecture if present in ArrayList possibleLectures
		for (int counter_arrayOfStudents = 0 ; counter_arrayOfStudents < arrayOfStudents.size(); counter_arrayOfStudents++)
		{
			Student currentStudent = arrayOfStudents.get(counter_arrayOfStudents);
			
			if (currentStudent.possibleLectures.contains(currentLecture))
			{
				currentStudent.possibleLectures.remove(currentLecture);
			}
		}
		
		// Sort students
		
		arrayOfStudents = sortStudents(arrayOfStudents);
		
		sortLectures(arrayOfLectures);
			
	}

	
	
	
	
	
	
	
}




