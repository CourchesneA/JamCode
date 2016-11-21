/**
 * CodeJam 2016
 * @author Samuel Beaubien 
 * 
 */

import java.util.ArrayList;

public class Slot {
	
	//Properties 
	public String day; 
	
	public int start; 
	
	public int end;
	
	
	// Constructor : Slot <var_name> = new Slot(day, start, end) 
	public Slot(String c_day, int  c_start, int c_end)
	{
		day = c_day;
		start = c_start;
		end = c_end;
		
	}
	
	
	
	
	
	/*
	 * This method is non-static and takes a slot as argument. 
	 * It returns a boolean 
	 * 
	 * This method check whether this fits in the argument and returns a boolean corresponding
	 * to the answer
	 */
	
	public boolean fitsIn(Slot containingSlot)
	{
		
		// Checks if same day
		// If not return false
		if (this.day.equals(containingSlot.day) == false)
		{
			return false;
		}
		
		// Checks if the starting time of <this> is smaller than containingSlot starting time
		// If yes, return false
		else if (this.start < containingSlot.start)
		{
			return false;
		}
		
		//Checks if <this> end time it bigger than containingSlot end time
		// If yes, return false
		else if (this.end > containingSlot.end)
		{
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
