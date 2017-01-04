/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 3 */
/* Database class: methods which deal with making the database
      a linked list, adding, deleting, editing a storm, printing
      and sorting all storms, checking if file is empty, and
      searching for storm year or storm name */
/**************************************************************/

package part3;

//imports
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Database
{
   /* Variable Used In Class */
   private List<Storm> storms;
   
   /**************************************************************/
   /* Method: Database (constructor) */
   /* Purpose: constructor for making Storms part of a linked list */
   /* Parameters: */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public Database() 
   {
      this.storms = new LinkedList<>();
   }
   
   /**************************************************************/
   /* Method: stortList */
   /* Purpose: sort the list of Storms by year */
   /* Parameters: */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   private void sortList()
   {
      //Sort the Storms by comparing the years of two Storm objects
      Collections.sort(this.storms, new Comparator<Storm>()
      {
         
         /*********************************************************/
         /* Method: compare */
         /* Purpose: Compare two Storm years */
         /* Parameters: Storm object1, Storm object2 */
         /* String target: */
         /* Returns: int -- the smaller year */
         /*********************************************************/
         
         @Override
         public int compare(Storm object1, Storm object2)
         {
            //Initializes the first and second year
            int year1 = object1.getYear();
            int year2 = object2.getYear();
            //Initializes the difference of the two years
            int diff = year1 - year2;
            
            //If there isn't a difference, compare the Storm names instead
            if (diff == 0)
            {
               return object1.getName().compareTo(object2.getName());
            }
            
            //Returns the smaller year
            return diff;
            
         } //End of compare
         
      }); //End of Collection
      
   } //End of sortList
   
   /**************************************************************/
   /* Method: searchByName */
   /* Purpose: search the list for a Storm name */
   /* Parameters: stormName */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void searchByName(String stormName)
   {
      //Created a database object called filtered
      Database filtered = new Database();
      
      //For loop for when a Storm object is the Storm object
      for (Storm storm : this.storms)
      {
         /*If the storm name of the Storm contains the stormName add the storm
           to the filtered database*/
         if (storm.getName().toLowerCase().contains(stormName.toLowerCase()))
         {
            filtered.add(storm);
         }
      }
      
      /*If the filtered database is empty say that there aren't any Storms
        with the name*/
      if (filtered.isEmpty())
      {
         System.out.println("Could not find any storms named \"" +
                            stormName + "\"");
      }
      
      //Else put the filtered database into the proper format 
      else
      {
         System.out.println(filtered.toString());
      }
      
   } //End of searchByName
   
   /**************************************************************/
   /* Method: searchByYear */
   /* Purpose: search the list for a Storm year */
   /* Parameters: stormYear */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void searchByYear(int stormYear)
   {
      //Created a database object called filtered
      Database filtered = new Database();
      
      //For loop for when a Storm object is the Storm object
      for (Storm storm : this.storms)
      {
         //If the Storm year equals the stormYear then add to filtered
         if (storm.getYear() == stormYear)
         {
            filtered.add(storm);
         }
      }
      
      /*If the filtered database is empty say that there aren't any Storms
        with the year*/ 
      if (filtered.isEmpty())
      {
         System.out.println("Could not find any storms in year \"" +
                            stormYear + "\"");
      }
      
      //Else put the filtered database into the proper format 
      else
      {
         System.out.println(filtered.toString());
      }
      
   } //End of searchByYear
   
   /**************************************************************/
   /* Method: contains */
   /* Purpose: makes sure the list has a target Storm object */
   /* Parameters: Storm target */
   /* String target: */
   /* Returns: boolean -- true if there/false if not */
   /**************************************************************/
   
   public boolean contains(Storm target)
   {
      //For loop for when a Storm object is the Storm object
      for (Storm storm : storms)
      {
         /*If the Storm year equals the target year and the Storm
           name equals the target name while ignoring the capitalization
           then return true*/ 
         if (storm.getYear() == target.getYear() &&
             storm.getName().equalsIgnoreCase(target.getName()))
         {
            return true;
         }
      }
      
      //Else return false
      return false;
      
   } //End of contains
   
   /**************************************************************/
   /* Method: add */
   /* Purpose: adds the Storm object the the list */
   /* Parameters: Storm storm */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void add(Storm storm)
   {
      this.storms.add(storm);
   }
   
   /**************************************************************/
   /* Method: edit */
   /* Purpose: edits a Storm object giving a Storm a new year, name,
         startDate, endDate, and strength */
   /* Parameters: Storm target, int newYear, String newName,
         String newStartDate, String newEndDate, String newStrength */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void edit(Storm target, int newYear, String newName, String newStartDate,
                    String newEndDate, String newStrength)
   {
      //For loop for when a Storm object is the Storm object
      for (Storm storm : storms)
      {
         /*If the Storm object year is equal to the target year and the Storm object
           name is equal to the target name while ignoring the capitalization
           then set a new year, name, start date, end date, and hurricane strength*/
         if (storm.getYear() == target.getYear() &&
             storm.getName().equalsIgnoreCase(target.getName()))
         {
            storm.setYear(newYear);
            storm.setName(newName);
            storm.setStartDate(newStartDate);
            storm.setEndDate(newEndDate);
            storm.setStrength(newStrength);
            
            //Says the Storm is editted successfully (YAY!!!)
            System.out.println("Storm edited successfully");
         }
         
      }
      
   } //End of edit
   
   /**************************************************************/
   /* Method: delete */
   /* Purpose: deletes a specified Storm object */
   /* Parameters: Storm target */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void delete(Storm target)
   {
      //Initialize foundStorm
      Storm foundStorm = null;
      
      //For loop for when a Storm object is the Storm object
      for (Storm storm : storms)
      {
         /*If the Storm object year is equal to the target year and the Storm object
           name is equal to the target name while ignoring the capitalization
           then the storm was found*/
         if (storm.getYear() == target.getYear() &&
             storm.getName().equalsIgnoreCase(target.getName()))
         {
            foundStorm = storm;
         }
      }
      
      /*If the found Storm object isn't null then remove the Storm say the Storm
        is gone*/
      if (foundStorm != null)
      {
         storms.remove(foundStorm);
         
         System.out.println("Storm deleted.");
      }
      
   } //End of delete
   
   /**************************************************************/
   /* Method: isEmpty */
   /* Purpose: checks to see if the list is empty */
   /* Parameters: */
   /* String target: */
   /* Returns: boolean -- true if empty/false if not */
   /**************************************************************/
   
   private boolean isEmpty()
   {
      return this.storms.isEmpty();
   }
   
   /**************************************************************/
   /* Method: toString (override) */
   /* Purpose: to create a String of Storms to be sorted if it isn't
         empty */
   /* Parameters: */
   /* String target: */
   /* Returns: String -- String of Storms */
   /**************************************************************/
   
   @Override
   public String toString()
   {
      //Starting String is on a new line
      String string = "\n";
      
      //If the file is empty
      if (storms.isEmpty())
      {
         string = "No storms";
      }
      
      //If the file isn't empty
      else
      {
         //Sort the list
         sortList();
         
         /*For when there's a Storm add the object to the String
           and start a new line*/
         for (Storm storm : this.storms) 
         {
            string += storm.toString() + "\n";
         }
         
      }
      
      //Return the string of Storm objects
      return string;
      
   } //End of toString
   
   /**************************************************************/
   /* Method: printAll */
   /* Purpose: print the list */
   /* Parameters: */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void printAll()
   {
      System.out.println(this.toString());
   }
   
}
