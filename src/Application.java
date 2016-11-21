
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * CodeJam 2016
 * @author Anthony Courchesne
 *
 */
public class Application {
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws Exception{

		GraphicHandler fileprompt = new GraphicHandler(2);
		fileprompt.promptFileChoose();
		while(fileprompt.chooseDone == false){
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){
				
			}
		}
		if(!fileprompt.promptfile){
			File htmlFile = new File("webInterface/index.html");
			Desktop.getDesktop().browse(htmlFile.toURI());
			System.exit(0);
		}
		GraphicHandler gh = new GraphicHandler(5);
		File classesFile;
		File studentsFile;
		//Show file chooser
		gh.showFileChooser();
		while(gh.chooseDone == false){
			try{
				Thread.sleep(200);
			}catch(InterruptedException e){
				
			}
		}
		classesFile=gh.getClassesFile();
		studentsFile=gh.getStudentsFile();
		String jsonString1 = FileUtils.readFileToString(classesFile,(String) null);
		String jsonString2 = FileUtils.readFileToString(studentsFile,(String) null);
		JSONParser parser = new JSONParser();
		JSONObject jsonClasses = (JSONObject) parser.parse(jsonString1);
		JSONObject jsonStudents = (JSONObject) parser.parse(jsonString2);
		
		//Parse classes
		ArrayList<Course> courseAL = parseClasses(jsonClasses);
		ArrayList<Student> studentAL = parseStudents(jsonStudents);

		
		// **************
		//Insert code for algorithm here
		
		
		ArrayList<Lecture> arrayLectures = ScheduleBuilder.getArrayLectures(courseAL);
		
		// Fill the possibleStudents and possibleLectures properties
		ScheduleBuilder.fillPossibleArrays(arrayLectures, studentAL);
		
		// Sort lectures and students
		ScheduleBuilder.sortLectures(arrayLectures);
		
		ArrayList<Student> sortedArrayOfStudents = new ArrayList<Student>();  // Create new array of sorted students
		sortedArrayOfStudents = ScheduleBuilder.sortStudents(studentAL);
		
		// Build Schedule 
		studentAL = ScheduleBuilder.assignCourses(arrayLectures, sortedArrayOfStudents);
		
		/*
		 * Outputs : 
		 * ArrayList<Course> courseAL
		 * ArrayList<Student> studentAL
		 * 
		 */
		
		
		//***************
		
		
		// TEST
		
		//print all students number for lectures
		/*
			//Get Lectures
		ArrayList<Lecture> testLecture = new ArrayList<Lecture>();
		testLecture = ScheduleBuilder.getArrayLectures(courseAL);
		
		System.out.print("Lectures : [ ");
		
		for (int i = 0; i < testLecture.size(); i++)
		{
			System.out.print(testLecture.get(i).students.size() + ", ");
		}
		
		System.out.print(" ]");
		
		// print all lecture number for students 
		System.out.print("Students : [ ");
		
		for (int i = 0; i < studentAL.size(); i++)
		{
			System.out.print(studentAL.get(i).lecturesTaken.size());
		}
		
		System.out.println(" ]");
		*/
		
		// TEST
		
		//Format JSON output
		//classes json object
		JSONObject classesObj = new JSONObject();
		String classesObjName = "classes";
		for(int i=0; i<courseAL.size(); i++){	//Iterate through all courses
			Course course = courseAL.get(i);
			JSONObject courseObj = new JSONObject();
			String courseObjName = course.Name;
			
			for(int j=0;j<course.occurrences.size();j++){	//Iterate through lectures
				Lecture lecture = course.occurrences.get(j);
				JSONObject lectureObj = new JSONObject();
				String lectureObjName = lecture.getNameIdentifier();
				
				for(int k=0; k<lecture.students.size();k++){	//Iterate through students
					Student student = lecture.students.get(k);
					JSONObject studentObj = new JSONObject();
					String studentObjName = "student"+k;
					
					String studentName = student.name;
					int studentID = student.ID;
					studentObj.put("id", studentID);
					studentObj.put("name", studentName);
					
					lectureObj.put(studentObjName, studentObj);
				}
				courseObj.put(lectureObjName, lectureObj);
			}
			classesObj.put(courseObjName, courseObj);
		}
		//students json object
		JSONObject studentsObj = new JSONObject();
		String studentsObjName = "students";
		for(int i = 0; i < studentAL.size(); i++){	//Iterate through students
			Student aStudent = studentAL.get(i);
			JSONObject aStudentObj = new JSONObject();
			String aStudentObjName = "student"+i;
			
			String studentName = aStudent.name;
			int studentID = aStudent.ID;
			String classesTaken = ""; //TODO
			for(int j = 0; j<aStudent.lecturesTaken.size(); j++){
				Lecture aLecture = aStudent.lecturesTaken.get(j);
				classesTaken+=aLecture.courseName+"-";
				classesTaken+=aLecture.getNameIdentifier();
				if(j != aStudent.lecturesTaken.size()-1){	//Add a comma if its not the last course
					classesTaken+=",";
				}
			}
			aStudentObj.put("id", studentID);
			aStudentObj.put("name", studentName);
			aStudentObj.put("classesTaken", classesTaken);
			
			studentsObj.put(aStudentObjName, aStudentObj);
		}
		
		JSONObject output = new JSONObject();
		output.put(classesObjName, classesObj);
		output.put(studentsObjName, studentsObj);
		
		//Offical output file
		File outputFile = new File("codejam-challenge.json");
		FileWriter outputStream = new FileWriter(outputFile, false);
		outputStream.write(output.toJSONString());
		outputStream.close();
		
		//Format another output file for JS
		File JSoutputFile = new File("jsdata.js");
		FileWriter JSoutputStream = new FileWriter(JSoutputFile, false);
		JSoutputStream.write("jsonobj ="+output.toJSONString());
		JSoutputStream.close();
		
		File htmlFile = new File("webInterface/index.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
		
	}
	
	
	/**
	 * 
	 * @param timeString
	 * @return time formated as hhmm, 24-h format
	 */
	private static int parseTimeString(String timeString){
		if(timeString.equals("NA")){
			return -1;
		}
		int offset = 0;
		if(timeString.contains("pm")){
			offset = 1200;
		}
		String trimmedTime;
		if(timeString.length() == 6){
			trimmedTime =  timeString.substring(0, 1) + timeString.substring(2, 4);
		}else{
			trimmedTime = timeString.substring(0, 2) + timeString.substring(3, 5);
		}
		int time = Integer.parseInt(trimmedTime)+offset;
		if(time >= 2400)
			time-=1200;	//12:30pm is still 12:30
		return time;
	}
	/**
	 * Parses a JSONObject for classes.json
	 * @param jsonClasses
	 * @return ArrayList of the courses objects
	 */
	private static ArrayList<Course> parseClasses(JSONObject jsonClasses){
		ArrayList<Course> returnedCourseAL = new ArrayList<Course>();
		JSONObject classesObj =  (JSONObject) jsonClasses.get("classes");
		for(Object classIDObj : classesObj.keySet()){		//ForEach course
			ArrayList<Lecture> lects = new ArrayList<Lecture>();
			String classID = classIDObj.toString();
			//System.out.println(classID);
			JSONObject aCourse = (JSONObject) classesObj.get(classID);
			String courseName = (String) aCourse.get("name");
			//System.out.println(" Name: "+courseName);
			JSONObject courseTimeSlotsObj = (JSONObject) aCourse.get("times");
			for(Object aTimeSlot : courseTimeSlotsObj.keySet()){		//ForEach time slots
				String timeSlotName = aTimeSlot.toString();
				JSONObject timeSlotObj = (JSONObject) courseTimeSlotsObj.get(timeSlotName);
				String day = (String) timeSlotObj.get("day");
				String start = (String) timeSlotObj.get("start");
				String end = (String) timeSlotObj.get("end");
				
				int cstart = parseTimeString(start);
				int cend = parseTimeString(end);
				if(cstart == -1 || cend == -1){		//Skip this iteration if invalid time, i.e. "NA"
					continue;
				}
				Slot slot = new Slot(day,cstart,cend);
				lects.add(new Lecture(slot,courseName));	//init with empty student ArrayList
			}
			returnedCourseAL.add(new Course(classID,courseName,lects));
		}
		return returnedCourseAL;
	}
	
	/**
	 * Parses a JSONObject for jsonStudents
	 * @param jsonStudents
	 * @return
	 */
	private static ArrayList<Student> parseStudents(JSONObject jsonStudents){
		ArrayList<Student> returnStudentsAL = new ArrayList<Student>();
		for(Object studentIDObj : jsonStudents.keySet()){		//ForEach studentID
			ArrayList<Slot> availAL = new ArrayList<Slot>();
			String studentIDStr = studentIDObj.toString();
			int studentID = Integer.parseInt(studentIDStr);
			JSONArray aStudent = (JSONArray) jsonStudents.get(studentIDStr);
			String name = (String) aStudent.get(0);
			JSONObject availabilitiesObj = (JSONObject) aStudent.get(1);
			for(Object availability: availabilitiesObj.keySet()){	//ForEach availabilities
				String availName = availability.toString();
				JSONObject anAvail = (JSONObject) availabilitiesObj.get(availName);
				String day = (String) anAvail.get("day");
				String start = (String) anAvail.get("start");
				String end = (String) anAvail.get("end");
				
				int cstart = parseTimeString(start);
				int cend = parseTimeString(end);
				if(cstart == -1 || cend == -1){		//Skip this iteration if invalid time, i.e. "NA"
					continue;
				}
				Slot slot = new Slot(day,cstart,cend);
				availAL.add(slot);
			}
			/*String nameAr[] = name.split(", ",2);
			String firstName = nameAr[0];
			String lastName = nameAr[1];*/
			Student student = new Student(studentID, name, availAL);
			returnStudentsAL.add(student);
		}
		return returnStudentsAL;
	}
	
}
