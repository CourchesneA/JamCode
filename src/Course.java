/**
 * @author Samuel Beaubien 
 * 
 * 
 *
 */

import java.util.ArrayList;

public class Course {
	
	//Properties
	public String ID;
	
	public String Name;
	
	public ArrayList<Lecture> occurrences = new ArrayList<Lecture>();
	
	
	//Constructor, takes 3 arguments
	public Course(String c_ID, String c_Name, ArrayList<Lecture> c_occurrences)
	{
		
		ID = c_ID;
		Name = c_Name;
		occurrences = c_occurrences;
		
	}
}








