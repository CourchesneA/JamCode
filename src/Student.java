/**
 * @author Samuel Beaubien 
 * 
 * 
 *
 */

import java.util.ArrayList;


public class Student {
	
	// Properties 
	public int ID;
	
	public String name;
	
	public ArrayList<Slot> dispo = new ArrayList<Slot>();
	
	public ArrayList<Lecture> lecturesTaken = new ArrayList<Lecture>();
	
	public ArrayList<Lecture> possibleLectures = new ArrayList<Lecture>();
	
	
	// Constructor, takes 4 arguments
	public Student(int c_ID, String c_name, ArrayList<Slot> c_dispo)
	{
		ID = c_ID;
		name = c_name;
		dispo = c_dispo;
		
	}
	
}
