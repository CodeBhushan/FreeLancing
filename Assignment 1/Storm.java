/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 1 */
/* Storm class:  methods which deal with creating storm objects */
/**************************************************************/
 
public class Storm
{
   /* Instance variables */
   
   private String year;
   private String stormName;
   private String startDate;
   private String endDate;
   private String category;
   private Boolean isValid;

   /**************************************************************/
   /* Method: Storm */
   /* Purpose: constructor for Storm object */
   /* Parameters: year, stormName, startDate, endDate, category */
   /* String target: */
   /* Returns: */
   /**************************************************************/
   
   public Storm(String year, String stormName, String startDate, String endDate,
      String category)
   {
      this.year = year;
      this.stormName = stormName;
      this.startDate = startDate;
      this.endDate = endDate;
      this.category = category;
      
      //Validity of the data is determined upon object creation
      isValid = checkValidity();
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: getYear */
   /* Purpose: initializes the year */
   /* Parameters: */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public String getYear()
   {
      return year;
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: getStormName */
   /* Purpose: initializes the storm name */
   /* Parameters: */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public String getStormName()
   {
      return stormName;
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: getStartDate */
   /* Purpose: initializes the starting date */
   /* Parameters: */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public String getStartDate()
   {
      if (startDate != null && startDate.length() != 0)
      {
         String startOfStartDate = startDate.substring(0,2);
         String endOfStartDate = startDate.substring(2,4);
         String completeStartDate = startOfStartDate +
            "/" + endOfStartDate;
         return completeStartDate;
      }
      
      String startDateNoInfo = "No Start Date";
      return startDateNoInfo;
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: getEndDate */
   /* Purpose: initializes the ending date */
   /* Parameters: */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public String getEndDate()
   {
      //String zero = "";
      if (endDate != null && endDate.length() != 0)
      {
         String startOfEndDate = endDate.substring(0,2);
         String endOfEndDate = endDate.substring(2,4);
         String completeEndDate = startOfEndDate + "/" + endOfEndDate;
         return completeEndDate;
      }
      
      String endDateNoInfo = "No End Date";
      return endDateNoInfo;
      
   //End of method
   }
   
   /**************************************************************/
   /* Method: getCategory */
   /* Purpose: initializes the hurricane category */
   /* Parameters: */
   /* String target: */
   /* Returns: String */
   /**************************************************************/
   
   public String getCategory()
   {
      if (category != null && category.length() != 0)
      {
         String zero = "0";
         if (category.equals(zero))
         {
            String catZero = "Tropical Storm";
            return catZero;
         }
         else
         {
            String catOther = "Hurricane Level ";
            return catOther + category;
         }
      }
      String catNoInfo = "No Info";
      return catNoInfo;
   //End of method
   }
   
   /**************************************************************/
   /* Method: getValidity */
   /* Purpose: makes sure that the storm's valid */
   /* Parameters: */
   /* String target: */
   /* Returns: Boolean (true/false) */
   /**************************************************************/

   public Boolean getValidity()
   {
      return isValid;
   
   //End of method
   }
   
   /**************************************************************/
   /* Method: checkValidity */
   /* Purpose: Checks that the data is valid before the object is
      put into the array */
   /* Parameters: */
   /* String target: */
   /* Returns: Boolean (true/false) */
   /**************************************************************/

   private Boolean checkValidity()
   {
      if (year.length() == 4)
         return true;
      else
         return false;
   
   //End of method
   }
   
   public void print()
   {
      System.out.println(getYear() + ": " + getStormName() +
         " (" + getCategory() + "): (" + getStartDate() + ") - (" + 
         getEndDate() + ")");
   }

//End of class
}