import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class GraphicHandler {
	  
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   private JLabel statusLabel1;
	   private JLabel statusLabel2;
	   private JPanel controlPanel;
	   private JPanel controlPanel2;
	   private File classes;
	   private File students;
	   public boolean promptfile;
	   public boolean chooseDone;
	   private JButton button;
	   public GraphicHandler(int row){
	      prepareGUI(row);
	      chooseDone = false;
	      promptfile = false;
	   }

	 

	   private void prepareGUI(int row){
	      mainFrame = new JFrame("Initialization");
	      mainFrame.setSize(400,300);
	      mainFrame.setLayout(new GridLayout(row, 1));
	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	           System.exit(0);
	         }        
	      });    
	      headerLabel = new JLabel("", JLabel.CENTER);        
	      statusLabel1 = new JLabel("",JLabel.CENTER);   
	      statusLabel2 = new JLabel("",JLabel.CENTER);    


	      statusLabel1.setSize(350,50);
	      statusLabel2.setSize(350,50);


	      controlPanel = new JPanel();
	      controlPanel.setLayout(new FlowLayout());
	      
	      controlPanel2 = new JPanel();
	      controlPanel2.setLayout(new FlowLayout());
	      mainFrame.add(headerLabel);
	      mainFrame.add(controlPanel);
	      if(row == 5){
	    	  button = new JButton("Save");
		      button.setEnabled(false);
		      button.setPreferredSize(new Dimension(150,40));
		      button.addActionListener(new ActionListener(){
		    	  public void actionPerformed(ActionEvent e)
		    	  {
		    		  if((GraphicHandler.this.classes) == null){
		    			  JOptionPane.showMessageDialog(null, "Please chose a file for the classes data", "Error",JOptionPane.ERROR_MESSAGE);
		    		  }else if((GraphicHandler.this.students) == null){
		    			  JOptionPane.showMessageDialog(null, "Please choose a file for the students data", "Error",JOptionPane.ERROR_MESSAGE);
		    		  }else{
		    			  chooseDone = true;
		    			  mainFrame.dispose();
		    		  }

		    	  }
		      });
		      controlPanel2.add(button);
		      mainFrame.add(statusLabel1);	 
		      mainFrame.add(statusLabel2);
		      mainFrame.add(controlPanel2);
		     
	      }
	      
	     
	    
	      mainFrame.setVisible(true);  
	   }
	   
	   public void promptFileChoose(){
		   headerLabel.setText("Do you want to choose new input files ?");
		   JButton yesbutton = new JButton("Yes");
		   JButton nobutton = new JButton("Use last input");
		   yesbutton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				promptfile = true;
				chooseDone = true;
				mainFrame.dispose();
			}  
		   });
		   nobutton.addActionListener(new ActionListener(){
			   public void actionPerformed(ActionEvent e) {
				   // TODO Auto-generated method stub
				   promptfile = false;
				   chooseDone = true;
				   mainFrame.dispose();
			   }  
		   });
		   controlPanel.add(yesbutton);
		   controlPanel.add(nobutton);
		   mainFrame.setVisible(true);
	   }

	   public void showFileChooser(){
	      headerLabel.setText("Please choose the input files"); 
	      final JFileChooser  fileDialog = new JFileChooser();
	      
	      FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON files (*.json)", "json");
	        // add filters
	      fileDialog.addChoosableFileFilter(jsonFilter);
	      fileDialog.setFileFilter(jsonFilter);
	      
	      JButton showFileDialogButton1 = new JButton("Open classes.json");
	      JButton showFileDialogButton2 = new JButton("Open students.json");
	      showFileDialogButton1.setPreferredSize(new Dimension(150,40));
	      showFileDialogButton2.setPreferredSize(new Dimension(150,40));
	      showFileDialogButton1.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            int returnVal = fileDialog.showOpenDialog(mainFrame);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	               GraphicHandler.this.classes = fileDialog.getSelectedFile();
	               statusLabel1.setText("Classes file selected : " 
	               + classes.getName());
	               if((GraphicHandler.this.classes) != null && (GraphicHandler.this.students) != null){
	            	   button.setEnabled(true);
	               }
	              
	            }
	            else{
	            	GraphicHandler.this.classes = null;
	               statusLabel1.setText("Open command cancelled" );           
	            }      
	         }
	      });
	      showFileDialogButton2.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            int returnVal = fileDialog.showOpenDialog(mainFrame);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	               GraphicHandler.this.students = fileDialog.getSelectedFile();
	               statusLabel2.setText("Students file selected : " 
	               + students.getName());
	               if((GraphicHandler.this.classes) != null && (GraphicHandler.this.students) != null){
	            	   button.setEnabled(true);
	               }
	            }
	            else{
	            	GraphicHandler.this.students = null;
	               statusLabel2.setText("Open command cancelled" );           
	            }      
	         }
	      });
	      controlPanel.add(showFileDialogButton1);
	      controlPanel.add(showFileDialogButton2);
	      mainFrame.setVisible(true);
	   }
	   
	   public File getClassesFile(){
		   return classes;
	   }
	   public File getStudentsFile(){
		   return students;
	   }
	}