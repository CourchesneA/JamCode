# JamCode
# Team Name: "Choose your team name (Required)"
*Project by*
*Anthony Courchesne*
*and*
*Samuel Beaubien*


**Description**

Our code is in mainly Java. We parse the JSON files into objects to run the algorithms, then parse back to JSON. We then read the output file within an HTML page and display the appropriate schedule. Everything displayed in a browser is happening locally (exept maybe to fetch a library), nothing is hosted.

**Instructions**
* Clone or download the repo
* Navigate to the Executable folder
* Execute the Jar by double clicking or or by the command line (java -jar Scheduler.jar)
* Emergency procedure: import the project in eclipse (load existing project) and run it


**Step-By-Step procedure**
* Run the file
* Select yes to be prompted to choose your input files, then navigate to the file containing the student avaailabilities.
* Do the same for the file that contains the courses hours
* Click save. The algorithm will execute within the next micro-seconds
* A web page is automatically opened in your favorite browser. We recommand chrome, but anything should do. (You can open the file manually to try other browser: webInterface/index.html
* Choose your status: Teacher or student
* If you are a teacher, enther the course you are teaching. For students, enter your ID
* Look at your schedule ! 
* If you are a teacher, you are also able to click event to see the student registered in this lecture

**Features**

* Login as a student with your ID and see you course schedule
* Login as a teacher with you course name and see your lectures and its registered students by clicking it
* ~~Export calendar as iCal~~(Not enough time to implement)
