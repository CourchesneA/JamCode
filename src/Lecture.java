/**
 * @author Samuel Beaubien 
 * 
 * 
 *
 */
	
import java.util.ArrayList;

public class Lecture {
	
	//Properties
	public Slot time;
	
	public ArrayList<Student> students = new ArrayList<Student>();
	
	public String courseName;
	
	public ArrayList<Student> possibleStudents = new ArrayList<Student>();
	
	
	// Constructor, takes 2 arguments
	public Lecture(Slot c_time, String courseName)
	{
		
		time = c_time;
		this.courseName = courseName;
		
	}
	
	private static String formatOutputTime(int timeValue){
		String period = (timeValue > 1200) ?  "PM" :  "AM";
		int value = period.equals("PM")? timeValue-=1200 : timeValue;
		String timeStr = (value < 1000) ? "0"+value : value+""; 
		return period+timeStr;
	}
	
	public String getNameIdentifier(){
		String returnstr = time.day.toUpperCase()+"-"+formatOutputTime(time.start)+"-"+formatOutputTime(time.end);
		return returnstr;
	}
	
}
