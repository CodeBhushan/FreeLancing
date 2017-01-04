/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 3 */
/* Storm class: methods which deal with creating storm objects */
/**************************************************************/

package com.projects.BSTStorm;

public class Storm implements Comparable<Storm> {
   /* Variables Used In Class */
   private int year;
   private String name;
   private String startDate;
   private String endDate;
   private String strength;
   
   /**************************************************************/
   /* Method: Storm (constructor) */
   /* Purpose: constructor for Storm object */
   /* Parameters: year, name, startDate, endDate, strength */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public Storm(int year, String name, String startDate, String endDate,
                 String strength)
   {
      this.year = year;
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
      this.strength = strength;
      
   } //End of Storm
   
   /**************************************************************/
   /* Method: toString (override) */
   /* Purpose: to create the proper format when the startDate,
         endDate, and/or strength have no information and to create
         the correct format of for output of the file */
   /* Parameters: */
   /* String target: */
   /* Returns: String -- the file input in the correct format */
   /**************************************************************/

  @Override
   public String toString()
   {
      /*if the startDate isn't null and the length isn't zero otherwise
        startDate equals no information*/
      String start = (this.startDate != null && this.startDate.length() > 0)
         ? this.startDate : "(No Start Date)";
      
      /*if the endDate isn't null and the length isn't zero otherwise
        endtDate equals no information*/
      String end = (this.endDate != null && this.endDate.length() > 0)
         ? this.endDate : "(No End Date)";
      
      /*if the strength isn't null and the length isn't zero otherwise
        strength equals no information*/
      String strength = (this.strength != null && this.strength.length() > 0)
         ? this.strength : "No Hurricane Info";
      
      //returns the proper format of the input file to the output
      return String.format("%d: %s (%s): %s - %s", year, name, strength, start, end);
      
   } //End of toString
   
   /**************************************************************/
   /* Method: getYear */
   /* Purpose: initializes the year */
   /* Parameters: */
   /* String target: */
   /* Returns: int year */
   /**************************************************************/

   public int getYear()
   {
      return year;
   }
   
   /**************************************************************/
   /* Method: getName */
   /* Purpose: initializes the storm name */
   /* Parameters: */
   /* String target: */
   /* Returns: String name */
   /**************************************************************/
   
   public String getName()
   {
      return name;
   }
   
   /**************************************************************/
   /* Method: getStartDate */
   /* Purpose: initializes the starting date */
   /* Parameters: */
   /* String target: */
   /* Returns: String startDate */
   /**************************************************************/
   
   public String getStartDate()
   {
      return startDate;
   }
   
   /**************************************************************/
   /* Method: getEndDate */
   /* Purpose: initializes the ending date */
   /* Parameters: */
   /* String target: */
   /* Returns: String endDate */
   /**************************************************************/
   
   public String getEndDate()
   {
      return endDate;
   }
   
   /**************************************************************/
   /* Method: getStrength */
   /* Purpose: initializes the hurricane strength level */
   /* Parameters: */
   /* String target: */
   /* Returns: String strength */
   /**************************************************************/
   
   public String getStrength()
   {
    return strength;
   }
   
   /**************************************************************/
   /* Method: setYear */
   /* Purpose: sets a value to the storm year */
   /* Parameters: int year -- makes new year equal the year */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public void setYear(int year)
   {
      this.year = year;
   }
   
   /**************************************************************/
   /* Method: setName */
   /* Purpose: sets a value to the storm name */
   /* Parameters: String name -- makes new name equal the name */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public void setName(String name)
   {
      this.name = name;
   }
   
   /**************************************************************/
   /* Method: setStartDate */
   /* Purpose: sets a value to the storm starting date */
   /* Parameters: String startDate -- makes new startDate equal
         the startDate */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public void setStartDate(String startDate)
   {
      this.startDate = startDate;
   }
   
   /**************************************************************/
   /* Method: setEndDate */
   /* Purpose: sets a value to the storm ending date */
   /* Parameters: String endDate -- makes new endDate equal the 
         endDate */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public void setEndDate(String endDate)
   {
      this.endDate = endDate;
   }
   
   /**************************************************************/
   /* Method: setStrength */
   /* Purpose: sets a value to the storm hurricane strength level */
   /* Parameters: String strength -- makes new strength equal the
         strength */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public void setStrength(String strength)
   {
      this.strength = strength;
   }

    @Override
    public int compareTo(Storm o) {
        int diff = this.year - o.getYear();
        if(diff == 0) {
            return this.name.compareToIgnoreCase(o.getName());
        }
        return diff;
    }
}
