/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 3 */
/* Project3 class: executes main method and deals with other
      methods that read the file, print messages, create storms
      sorts each line into an array */
/**************************************************************/

package com.projects.BSTStorm;

//imports

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project4
{
   /* Variables Used In Class */
   /* ALL UNCHANGEABLE VARIABLES ARE CAPITALIZED */
   private static final int MENU_SEARCH_NAME = 1;
   private static final int MENU_SEARCH_YEARS = 2;
   private static final int MENU_PRINT_ALL = 3;
   private static final int MENU_ADD_STORM = 4;
   private static final int MENU_DELETE_STORM = 5;
   private static final int MENU_EDIT_STORM = 6;
   private static final int MENU_EDIT_DISK_WRITE = 7;
   private static final int MENU_EDIT_REINITIALIZE= 8;
   private static final int MENU_EXIT = 9;
   
   /* Creates new scanner object */
   private static Scanner scanner = new Scanner(System.in);
   
   /**************************************************************/
   /* Method: printWelcomeMessage */
   /* Purpose: shows the user the welcome sign */
   /* Parameters: */
   /* String target: */
   /* Returns:*/
   /**************************************************************/
   
   private static void printWelcomeMessage()
   {
      System.out.println("Welcome to the CS-102 Storm Tracker Program.");
   }


   public static String getCurrentProductionDirectory() {
      final File f = new File(Project4.class.getProtectionDomain().getCodeSource().getLocation().getPath());
      String parentDir = f.getParent();
      System.out.println("production path is: " + parentDir);
      return parentDir;

   }
   /**************************************************************/
   /* Method: main */
   /* Purpose: runs the program */
   /* Parameters: String[] args */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public static void main(String[] args)
   {
      //Prints out the welcome message when starting the program
      printWelcomeMessage();
      
      //Makes a database object of the input file
      Database database = readDatabaseFile(new File(args[0]));
      //Database database = readDatabaseFile(new File(getCurrentProductionDirectory()+File.separator+"inputFile.txt"));
      
      //Instantiates the menu selection
      int menuSelection = 0;
      
      //While loop for the user to enter the desired command(s)
      while(menuSelection != MENU_EXIT)
      {
         //Makes the menuSelection equal to what the user selected
         menuSelection = getMenuSelection();
         
         //If the user wants to search for a Storm by the name
         if(menuSelection == MENU_SEARCH_NAME)
         {
            //Reads the storm name the user input
            String stormName = readStringFromKeyboard("\n" + "Enter Storm name prefix: ");
            //Uses the searchByName method to find said name
            database.searchByName(stormName);
         }
         
         //If the user wants to search for a Storm by the year
         else if(menuSelection == MENU_SEARCH_YEARS)
         {
            //Reads the storm year the user input
            int stormYear = readIntegerFromKeyboard("\n" + "Enter Storm year: ", 0);
            //Uses the searchByYear method to find said year
            database.searchByYear(stormYear);
         }
         
         //If the user wants to print all Storms in the input file
         else if(menuSelection == MENU_PRINT_ALL)
         {
            //Uses the printAll method to print out the Storms
            database.printAll();
         }
         
         //If the user wants to add a Storm to the input file
         else if(menuSelection == MENU_ADD_STORM)
         {
            //Uses createNewStorm method to create Storm and set it equal to a Storm object
            Storm storm = createNewStorm();
            
            //If the database contains the new Storm
            if(database.contains(storm))
            {
               System.out.println("Cannot add Storm. It already exists.");
            }
            
            //If the database doesn't already contain the new Storm
            else
            {
               //Uses the add method to add the new Storm to the file
               database.add(storm);
               System.out.println("Storm was added successfully.");
            }
            
         }
         
         //If the user wants to delete a Storm from the input file
         else if(menuSelection == MENU_DELETE_STORM)
         {
            System.out.println("\n" + "DELETING STORM:");
            
            //User needs to enter the Storm year and name they want to delete
            int year = readIntegerFromKeyboard("Enter year: ", 0);
            String name = readStringFromKeyboard("Enter Storm name: ");
            
            //Creates a new Storm object with the year and name the user input
            Storm storm = new Storm(year, name, "", "", "");
            
            //If the database has a Storm that contains the Storm name and year
            if(database.contains(storm))
            {
               //Make sure the user wants to delete the storm (can't be undone)
               boolean confirm = "Y".equalsIgnoreCase(readStringFromKeyboard("Are" +
                  " you sure you want to delete Storm? Y/N: "));
                  
               //If the user does the Storm is deleted from the file
               if(confirm)
               {
                  database.delete(storm);
               }
            }
            
            //If the user inputs a Storm that doesn't exist in the file
            else
            {
               System.out.println("Cannot delete. No such Storm found.");
            }
            
         }
         
         //If the user wants to edit a Storm in the input file
         else if(menuSelection == MENU_EDIT_STORM)
         {
            System.out.println("\n" + "EDITING STORM:");
            
            //User needs to enter the Storm year and name they want to edit
            int year = readIntegerFromKeyboard("Enter current Storm year: ", 0);
            String name = readStringFromKeyboard("Enter current Storm name: ");
            System.out.println("\n");
            
            //Creates a new Storm object with the year and name the user input
            Storm storm = new Storm(year, name, "", "", "");
            
            //If the database has a Storm that contains the Storm name and year 
            if(database.contains(storm))
            {
               //User needs to input the new data for the Storm
               int newYear = readIntegerFromKeyboard("Enter new Storm year: ", 0);
               String newName = readStringFromKeyboard("Enter new Storm name: ");
               String newStartDate = readStringFromKeyboard("Enter new start date: ");
               String newEndDate = readStringFromKeyboard("Enter new end date: ");
               String newStrength = readStringFromKeyboard("Enter new hurricane level: ");
               
               //Uses the edit method to read in the data the user input and change the Storm
               database.edit(storm, newYear, newName, newStartDate, newEndDate, newStrength);
            }

            
            //If the user inputs a Storm that doesn't exist in the file
            else
            {
               System.out.println("Cannot edit. No such Storm found.");
            }
            
         }else if (menuSelection==MENU_EDIT_DISK_WRITE){
            String fileName = readStringFromKeyboard("Please enter destination file name.");
            database.writeToFile(fileName);
            System.out.println("Writing storm database to file completed.");
         }else if(menuSelection == MENU_EDIT_REINITIALIZE){
            String fileName = readStringFromKeyboard("Please enter new file name for reinitialization of storm database using new data");
            File file = new File(fileName);
            if(!file.exists()){
               System.out.println("Specified file not found.");
            }else{
               database = readDatabaseFile(file);
               System.out.println("Storm database reinitialization completed.");
            }
         }
         
         //If the user inputs an invalid command
         else if(menuSelection != MENU_EXIT)
         {
            System.out.println("Invalid command. Try again.");
         }
         
      } //End of while loop

   } //End of main method
   
   /**************************************************************/
   /* Method: getMenuSelection */
   /* Purpose: shows the user the available commands and has the
         user make a choice */
   /* Parameters: */
   /* String target: */
   /* Returns: int -- the number the user chose */
   /**************************************************************/
    
   private static int getMenuSelection()
   {
      //Gives the user what choices they can make
      String message = "\n" + "Current Available Commands:" + "\n" +
         MENU_SEARCH_NAME + " --> Search for Storm Name" + "\n" +
         MENU_SEARCH_YEARS + " --> Search for Storm Year" + "\n" +
         MENU_PRINT_ALL + " --> Print All Storms" + "\n" +
         MENU_ADD_STORM + " --> Add Storm" + "\n" +
         MENU_DELETE_STORM + " --> Delete Storm" + "\n" +
         MENU_EDIT_STORM + " --> Edit Storm" + "\n" +
              MENU_EDIT_DISK_WRITE + " --> Write Storm Database to file" + "\n" +
              MENU_EDIT_REINITIALIZE + " --> Reinitialize Storm Database using new file" + "\n" +
         Project4.MENU_EXIT + " --> Exit" + "\n" +
         "Your choice? ";
      
      //Returns what the user chose to do in the program
      return readIntegerFromKeyboard(message, -1);
        
   } //End of getMenuSelection
   
   /**************************************************************/
   /* Method: readDatabaseFile */
   /* Purpose: reads the input file makes each line a Storm object */
   /* Parameters: File fileName */
   /* String target: */
   /* Returns: Database -- returns the database */
   /**************************************************************/

   private static Database readDatabaseFile(File fileName)
   {
      //Creates a new database object
      Database database = new Database();
      
      //Try scanning the input file
      try
      {
         //Reads the file name of the input file
         Scanner reader = new Scanner(fileName);
         
         /*While the file has another line, makes next line
           equal the the String line and parses the line into
           a Storm*/
         while(reader.hasNextLine())
         {
            String line = reader.nextLine();
            Storm storm = parseLineIntoStorm(line);
            
            //Adds the Storm into the database
            database.add(storm);
         }
         
      }
      
      //Catch a FileNotFoundException exception
      catch (FileNotFoundException exception)
      {
         exception.printStackTrace();
      }
      
      //Returns the database
      return database;
        
   } //End of readDatabaseFile
   
   /**************************************************************/
   /* Method: parseLineIntoStorm */
   /* Purpose: makes each line into a Storm object */
   /* Parameters: String line */
   /* String target: */
   /* Returns: Storm -- a Storm object */
   /**************************************************************/
   
   private static Storm parseLineIntoStorm(String line)
   {
      /*Makes each part of the array seperate from the next part
        by splitting the line up with tokens*/
      String[] tokens = line.split("/");
      
      //Creates the array of the nexly splitted parts
      String[] array = new String[5];
      
      /*For loop to make each part inbetween the tokens an index of
        the array*/
      for(int index = 0; index < tokens.length; index++)
      {
         array[index] = tokens[index];
      }
      
      //Creates new Storm object to equal the parsed array data
      Storm newStorm = new Storm(parseInteger(array[0], 0),
         array[1], parseDate(array[2]), parseDate(array[3]),
         parseStrength(array[4]));
      
      //Returns the Storm object
      return newStorm;
         
   } //End of parseLineIntoStorm
   
   /**************************************************************/
   /* Method: parseDate */
   /* Purpose: gets the date (start and/or end) and makes sure it's
         four characters long and not null the puts the date into
         the proper format */
   /* Parameters: String date */
   /* String target: */
   /* Returns: String -- the date (start and/or end) in the proper
         format */
   /**************************************************************/
   
   private static String parseDate(String date)
   {
      /*If either the startDate and/or endDate isn't nill and is
        fourbcharacters long then return the startDate and/or endDate
        in the proper format*/
      if(date != null && date.length() == 4)
      {
         return date.substring(0, 2) + "/" + date.substring(2, 4);
      }
      
      //Else return the startDate and/or endDate
      return date;
      
   } //End of parseDate
   
   /**************************************************************/
   /* Method: parseStrength */
   /* Purpose: gets the strength and makes sure it's one character
         long and not null the puts the strength into the proper
         format */
   /* Parameters: String strength */
   /* String target: */
   /* Returns: String -- the strength in the proper format */
   /**************************************************************/
   
   private static String parseStrength(String strength)
   {
      //Initializes zero
      String zero = "0";
      
      /*If the hurricane strength isn't null, the length equals one character,
        and the value of the strength equals zero then return that it's a level
        one hurricane or, a tropical storm*/
      if(strength != null && strength.length() == 1 && strength.equals(zero))
      {
         return "Tropical Storm";
      }
      
      /*If the hurricane strength isn't null, and the length equals one character
        then return the hurricane level and the value of the strength*/
      else if (strength != null && strength.length() == 1)
      {
         return "Hurricane Level " + strength.substring(0, 1);
      }
      
      //Else return the strength of the hurricane
      return strength;
      
   } //End of parseStrength
   
   /**************************************************************/
   /* Method: parseInteger */
   /* Purpose: gets the year and makes it an integer */
   /* Parameters: String year, int defaultValue */
   /* String target: */
   /* Returns: int -- the year or default value */
   /**************************************************************/
   
   private static int parseInteger(String year, int defaultValue)
   {
      //Try to return the year as an Integer
      try
      {
         return Integer.parseInt(year);
      }
      
      //Catch exception
      catch (Exception exception) {}
      
      //Return the set default value
      return defaultValue;
      
   } //End of parseInteger
   
   /**************************************************************/
   /* Method: createNewStorm */
   /* Purpose: creates a new Storm object when prompted */
   /* Parameters: */
   /* String target: */
   /* Returns: Storm -- new Storm object */
   /**************************************************************/
       
   private static Storm createNewStorm()
   {
      System.out.println("\n" + "CREATING NEW STORM:");
      
      //Reads the inputs from the user
      int year = readIntegerFromKeyboard("Enter year: ", 0);
      String name = readStringFromKeyboard("Enter Storm name: ");
      String startDate = readStringFromKeyboard("Enter start date: ");
      String endDate = readStringFromKeyboard("Enter end date: ");
      String strength = readStringFromKeyboard("Enter hurricane level: ");
      
      //Uses the read inputs and returns a new Storm object
      return new Storm(year, name, startDate, endDate, strength);
      
   } //End of createNewStorm
   
   /**************************************************************/
   /* Method: readStringFromKeyboard */
   /* Purpose: reads what the user inputed */
   /* Parameters: String message */
   /* String target: */
   /* Returns: String -- the next line */
   /**************************************************************/
   
   public static String readStringFromKeyboard(String message)
   {
      //Reads the input from the user as a String
      System.out.print(message);
      
      //Returns the next line
      return scanner.nextLine();
      
   } //End of readStringFromKeyboard
   
   /**************************************************************/
   /* Method: readIntegerFromKeyboard */
   /* Purpose: reads what the user inputed */
   /* Parameters: String message, int defaultValue */
   /* String target: */
   /* Returns: int -- the next line or the default value */
   /**************************************************************/
   
   public static int readIntegerFromKeyboard(String message, int defaultValue)
   {
      //Reads the input from the user
      System.out.print(message);
      
      //Try to make the line read into an Integer
      try
      {
         return Integer.parseInt(scanner.nextLine());
      }
      
      //Catch exception
      catch (Exception exception) {}
      
      //Returns the set default value
      return defaultValue;
      
   } //End of readIntegerFromKeyboard

}
