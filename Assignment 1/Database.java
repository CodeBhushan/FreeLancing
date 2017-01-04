/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 1 */
/* StormDatabase class: Contains methods that manipulate Storm
   objects */
/**************************************************************/

 
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.IOException;

public class Database
{   
   /*The following constants and variables are static because they are
     the same no matter what object we are dealing with */
   
   private static final int STORM_ARRAY_SIZE = 100;
   
   //It's static because it exists even without the creation of a
   //StormDatabase object
   private static Storm [] stormArray = new Storm [STORM_ARRAY_SIZE];
   
   private static int biggerStormArraySize = STORM_ARRAY_SIZE;
   
   private static int stormArrayIndex = 0;
   
   
   /**************************************************************/
   /* Method: insertData */
   /* Purpose: reads from the inputFile and stores the data into
      the proper arrays */
   /* Parameters: inputFile */
   /* String target: */
   /* Returns: Void */
   /**************************************************************/

   public static void insertData(File inputFile) throws Exception
   {
      Scanner fileScanner = new Scanner(inputFile).useDelimiter("/");
   
      while (fileScanner.hasNextLine())
      {
         //Read the input
         String inputString = fileScanner.nextLine();
         Scanner inputScanner = new Scanner(inputString).useDelimiter("/");
         String year = "";
         String stormName = "";
         String startDate = "";
         String endDate = "";
         String category = "";
         
         //Instantiate the object for try blocks in the switch
         Storm stormObject = null;
         try
         {
         
         //Read
         year = inputScanner.next();
         stormName = inputScanner.next();
         startDate = inputScanner.next();
         endDate = inputScanner.next();
         category = inputScanner.next(); 
         }
         catch (NoSuchElementException noSuchElem)
         {
                  stormObject = null;
         }
         
         char type = year.charAt(0);
         
          
         
         //Determine what kind of object needs to be created
         switch (type)
         {
            case '1':
               try {
                  
                  //If the storm name is not already in the array
                  if (!stormExists(stormName, year))
                  {
                     //Create a new Storm Object
                     stormObject = new Storm(year, stormName, startDate,
                        endDate, category);
                  }
               }
                           
               //Trash the data.
               //Make the object null to inilitialize it for the next try block
               catch (NoSuchElementException noSuchElem)
               {
                  stormObject = null;
               }
               
               try
               { 
                  //If the data is valid
                  if (stormObject.getValidity())
                  {
                     //Place the new stormObject object into the array
                     stormArray[stormArrayIndex] = stormObject;
                  }
               }
               
               //Happens when the size of the array is too small
               catch (ArrayIndexOutOfBoundsException stormArrayException)
               {
                  System.out.println("Array is too small.." +
                     "Fixing this by making it larger");
                  
                  //Increase size for stormArray by 20
                  biggerStormArraySize += 20;
                  
                  //Create a new temporary array to assist data transfer to new array
                  Storm [] stormArrayTemp = stormArray;
                  stormArray = new Storm [biggerStormArraySize];
                  
                  //copy elements from old stormArray (now "stormArrayTemp")
                  //to new and bigger stormArray
                  for (int i = 0; i < stormArrayTemp.length; i++)
                  {
                     stormArray[i] = stormArrayTemp[i];
                  }
                  
                  //Try again to place the new stormObject object into the array
                  stormArray[stormArrayIndex] = stormObject;
               } 
               
               catch (NullPointerException nullPointer)
               {
                  //This happens if the data is invalid and the object is null.
                  //In this case we skip right over it.
                  System.out.println("Dealing with a bad Storm item...");
               }
               
               //Increment the index of array   
               stormArrayIndex += 1;
               
               break;
            
            case '2':
               try {
                  
                  //If the storm name is not already in the array
                  if (!stormExists(stormName, year))
                  {
                     //Create a new Storm Object
                     stormObject = new Storm(year, stormName, startDate,
                        endDate, category);
                  }
               }
               
               //Trash the data.
               //Make the object null to inilitialize it for the next try block
               catch (NoSuchElementException noSuchElem)
               {
                  stormObject = null;
               }
               
               try
               { 
                  //If the data is valid
                  if (stormObject.getValidity())
                  {
                     //Place the new stormObject object into the array
                     stormArray[stormArrayIndex] = stormObject;
                  }
               }
               
               //Happens when the size of the array is too small
               catch (ArrayIndexOutOfBoundsException stormArrayException)
               {
                  System.out.println("Array is too small.." + 
                     "Fixing this by making it larger");
                  
                  //Increase size for stormArray by 20
                  biggerStormArraySize += 20;
                  
                  //Create a new temporary array to assist data transfer to new array
                  Storm [] stormArrayTemp = stormArray;
                  stormArray = new Storm [biggerStormArraySize];
                  
                  //copy elements from old stormArray (now "stormArrayTemp")
                  //to new and bigger stormArray
                  for (int i = 0; i < stormArrayTemp.length; i++)
                  {
                     stormArray[i] = stormArrayTemp[i];
                  }
                  
                  //Try again to place the new stormObject object into the array
                  stormArray[stormArrayIndex] = stormObject;
               } 
               
               catch (NullPointerException nullPointer)
               {
                  //This happens if the data is invalid and the object is null.
                  //In this case we skip right over it.
                  System.out.println("Dealing with a bad Storm item...");
               }
               
               //Increment the index of array   
               stormArrayIndex += 1;
               
               break;
            
            //Not a match to a year starting with '1' or '2'
            default:
            
               //Discard the rest of the line
               String invalidString = inputScanner.nextLine();
               
               break;
               
         //End of switch
         }
      
      //End of while loop   
      }  
      
   //End of insertData method
   }
   
   // Input arguments are the data that was input by the user
   // Check to see if the data is valid, and if so, add the player to the database.
   /*public void addStorm(String year, String stormName, String startDate, String endDate, String category)
   {
      if (!stormList.stormExists(stormName, year) && year != null && year.length() == 4)
      { 
         //If the data is valid and if the playerID is not already in the array
         Storm stormObject = new Storm(year, stormName, startDate, endDate, category); //Create a new TennisPlayer Object
         stormList.add(stormObject); // Add the player into the database
      }
      else { System.out.println("Sorry, storm data is not valid."); } // The player data is not valid
   }*/
   
   /**************************************************************/
   /* Method: printAllStorms */
   /* Purpose: print out all data of storms in propre format */
   /* Parameters: */
   /* String target: */
   /* Returns: System.out.println */
   /**************************************************************/
  
   public static void printAllStorms()
   {
      for (int i = 0; i < stormArray.length; i++)
      {        
         if (stormArray[i] != null)
         {
            stormArray[i].print();
         
         //End of if statement
         }
      
      //End of for loop
      }
   
   //End of method
   }   

   /**************************************************************/
   /* Method: stormExists */
   /* Purpose: makes sure the storm exists */
   /* Parameters: newStormName */
   /* String target: name of storm */
   /* Returns: Boolean (true/false) */
   /**************************************************************/   

   public static Boolean stormExists(String newName, String newYear)
   {
      for (int i = 0; i < stormArray.length; i++)
      {
         if (stormArray[i] != null)
         {
            if (newName.equals((stormArray[i].getStormName())) && newYear.equals(stormArray[i].getYear()))
               //If the storm already exists
               return true;
         
         //End of if statement
         }
      
      //End of for loop
      }
      return false;
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: searchForStorm */
   /* Purpose: searchs for a specified storm */
   /* Parameters: stormName */
   /* String target: the name of a storm */
   /* Returns: String */
   /**************************************************************/

   public static String searchForStorm(String stormName)
   {
      String returnString = "";
      for (int i = 0; i < stormArray.length; i++)
      {
         //First if statement
         //Search for matching storm(s) in stormArray using stormName
         if (stormArray[i] != null)
         {
            //Second if statement
            if (stormArray[i].getStormName().charAt(0) == stormName.charAt(0))
            {
               returnString = stormArray[i].getStormName();
               //Storm stormObject = PrintStormObject(returnString);
               System.out.println(stormArray[i].getYear() + ": " +
               stormArray[i].getStormName() + " (" + stormArray[i].getCategory() +
               "): (" + stormArray[i].getStartDate() + ") - (" + 
               stormArray[i].getEndDate() + ")");
            //End of second if statement
            }
         
         //End of first if statement
         }
      
      //End of for loop
      }
      //The storm has not been found
      if (returnString.equals("") ) {
      String print = "Storm name not found.";
      System.out.println("Storm name not found.");
      return print; }
      return "";
   //End of method
   }
   
   public static Storm PrintStormObject(String stormName)
   {
      //String returnString = "";
      for (int i = 0; i < stormArray.length; i++)
      {
         //First if statement
         //Search for matching storm(s) in stormArray using stormName
         if (stormArray[i] != null)
         {
            //Second if statement
            if (stormArray[i].getStormName().equals(stormName))
            {
               return stormArray[i];
               
            
            //End of second if statement
            }
         
         //End of first if statement
         }
      
      //End of for loop
      }
      //The storm has not been found
      return null;

   }
   
   /**************************************************************/
   /* Method: searchForYear */
   /* Purpose: searches for a specified year */
   /* Parameters: year */
   /* String target: a year of a storm */
   /* Returns: String */
   /**************************************************************/
   
   public static String searchForYear(String year)
   {
      String returnString = "";
      for (int i = 0; i < stormArray.length; i++)
      {
         //First if statement
         //Search for matching year(s) in stormArray using stormName
         if (stormArray[i] != null)
         {
            //Second if statement
            if (stormArray[i].getYear().equals(year))
            {
               returnString = stormArray[i].getYear();
               //Storm stormObject = PrintStormYear(returnString);
               System.out.println(stormArray[i].getYear() + ": " +
               stormArray[i].getStormName() + " (" + stormArray[i].getCategory() +
               "): (" + stormArray[i].getStartDate() + ") - (" + 
               stormArray[i].getEndDate() + ")");
            
            //End of second if statement
            }
         
         //End of first if statement
         }
      
      //End of for loop
      }
      //The year has not been found
      return year;
   
   //End of method
   }
   
    public static Storm PrintStormYear(String stormYear)
   {
      //String returnString = "";
      for (int i = 0; i < stormArray.length; i++)
      {
         //First if statement
         //Search for matching storm(s) in stormArray using stormName
         if (stormArray[i] != null)
         {
            //Second if statement
            if (stormArray[i].getYear().equals(stormYear))
            {
               return stormArray[i];
               
            
            //End of second if statement
            }
         
         //End of first if statement
         }
      
      //End of for loop
      }
      //The storm has not been found
      return null;

   }


//End of class
}