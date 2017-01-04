/**************************************************************/
/* Alyx Heilig */
/* Login ID: heil4438 */
/* CS-102, Fall 2016 */
/* Programming Assignment 1 */
/* StormManager class: main method */
/**************************************************************/

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.io.*;
import java.io.IOException;

public class Project1
{
   
   /**************************************************************/
   /* Method: main */
   /* Purpose: excutes all methods from StormDatabase and Storm */
   /* Parameters: args */
   /* String target: excutes the program */
   /* Returns: Void */
   /**************************************************************/
   
   public static void main(String [] args) throws Exception
   {
      /* Initialize variables */
      
      //The exit code for the program
      final int SENTINEL = 9;
      
      //Reads user commands
      Scanner userInput = new Scanner(System.in);
      
      //Welcome text is put here so it appears before any exception handling text
      System.out.println("Welcome to the CS-102 Storm Database Manager!");
      
      //Read input file and store data
      File inputFile = null;
      try
      {
         //define the input file
         inputFile = new File(args[0]);
      }
      catch(ArrayIndexOutOfBoundsException exception)
      {
         //Happens when there is no file in the input argument
         System.out.println("Please select an input file to use.");
         System.exit(0);
      }
      Database dbObject = new Database();
      
     try {         
         dbObject.insertData(inputFile);
      } //end try
     catch (NoSuchElementException noSuchElem) {
         System.out.println("Exception: " + noSuchElem);
      }
      
      //Initialize and define the command variable
      int command = 0;
      
      // While loop to make sure that the program exits when it is supposed to
      while (command != SENTINEL)
      {
         // Prompt user for input
         System.out.print("\nPlease type in a command:\n" + "1 --> Search for" +
            " Storm \n" + "2 --> Search for Year \n" + "3 --> Print all Storms" +
            "\n9 --> Exit\n" + "Your choice: ");
         try
         {
            //User inputs the commmand
            command = userInput.nextInt();
         }
         // Happens when the user input is not an integer
         catch (InputMismatchException exception)
         {
            System.out.println("Error: Command must be an integer.");
            userInput.next();
            //Go back to the beginning of the while loop
            continue;
         }      
            
         //Perform the appropriate task as per user command
         switch (command)
         {
            case 1:
               //User inputs storm name
               System.out.print("Enter first initial of storm: ");
               String stormToSearch = userInput.next().toUpperCase();
               //Print all storms with that starting letter
               dbObject.searchForStorm(stormToSearch);
               break;
            case 2:
               //User inputs year
               System.out.print("Enter the year: ");
               String yearToSearch = userInput.next().toUpperCase();
               //Print all storms with that year
               dbObject.searchForYear(yearToSearch);
               break;
            case 3:
               //Print all storms
               dbObject.printAllStorms();
               break;
            case 9:
               //Exits program
               System.out.println("Thank you for using the Storm Manager!");
               break;
            default:
               //User inputs a different number than indicated above
               System.out.println("You did not enter a correct command. " +
                  "Please try again. ");
               break;
               
         //End of switch
         }
         
      //End of while loop
      }
      
   //End of main method
   }
   
//End of class
}