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

package com.projects.BSTStorm;

//imports

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Database {

    List<BST<Storm>> storms;//linked list to store all BSTs year wise
    //List<StormListNode> storms;

    /**
     *
     * @param strength
     * @return
     */
    private static String encodeStrength(String strength)//converts strength from detail format to short-format which just have numerical characters
    {
        if(strength==null){
            return "";
        }
      /*If the hurricane strength isn't null, the length equals one character,
        and the value of the strength equals zero then return that it's a level
        one hurricane or, a tropical storm*/
        else if(strength.equals("Tropical Storm"))
        {
            return "0";
        }

      /*If the hurricane strength isn't null, and the length equals one character
        then return the hurricane level and the value of the strength*/
        else if (strength.contains("Hurricane Level"))
        {
            String[] strings = strength.split(" ");
            return strings[strings.length-1];
        }
        else{
            System.out.println("some unknown error happened during data manipulation.");
            return "";
        }



    } //End of encodeStrength

    private static String parseDate(String date) {//converts date from AB/CD format to ABCD format
        if (date == null)
            return "";
        else {
            String[] strings = date.split("/");
            if(strings.length==0){
                return "";
            }
            else if(strings.length==1){
                System.out.println(date);
                System.out.println("some error");
                return "";
            }
            return strings[0] + strings[1];
        }
    }

    public void writeStringForOutputFile(BufferedWriter out) throws IOException {//for writing database to given buffered outputstream
           sortList();
            for (BST<Storm> storm : this.storms) {//looping each BST present in the linked list
                writeNodeWiseToFile(storm.getRoot(),out);
            }
    }

    private void writeNodeWiseToFile(BSTNode<Storm> root,BufferedWriter out) throws IOException {//write given BST data in preorder fashion to given outstream
        if(root==null)return;
        //processing current node
        out.write(""+root.data.getYear()+"/"+root.data.getName()+"/"+parseDate(root.data.getStartDate())+"/"+parseDate(root.data.getEndDate())+"/"+encodeStrength(root.data.getStrength()));
        out.newLine();
        //processing left child
        writeNodeWiseToFile(root.left,out);
        //processing right child
        writeNodeWiseToFile(root.right,out);
    }

    public void writeToFile(String fileName) {//function for writing database to file with specified filename.
        byte[] buffer;
        BufferedWriter out = null;
        try  {
            out = new BufferedWriter(new FileWriter(fileName));//getting output stream corresponding to specified filename
            writeStringForOutputFile(out);
            out.flush();//flushing data to file ultimately
        } catch (IOException e) {
            System.out.println("File Read Error");
            e.printStackTrace();
        } finally {
            if(out!=null)
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

  /*  class StormListNode{
        public int year;
        public BST<Storm> storms;
    }*/



    /**************************************************************/
   /* Method: Database (constructor) */
   /* Purpose: constructor for making Storms part of a linked list */
   /* Parameters: */
   /* String target: */
   /* Returns: */

    /**************************************************************/

    public Database() {
        this.storms = new LinkedList<BST<Storm>>();
    }

    /**************************************************************/
   /* Method: stortList */
   /* Purpose: sort the list of Storms by year */
   /* Parameters: */
   /* String target: */
   /* Returns: */
    /**************************************************************/

//   private void sortList()
//   {
//      //Sort the Storms by comparing the storms of two Storm objects
//      Collections.sort(this.storms, new Comparator<Storm>()
//      {
//
//         /*********************************************************/
//         /* Method: compare */
//         /* Purpose: Compare two Storm storms */
//         /* Parameters: Storm object1, Storm object2 */
//         /* String target: */
//         /* Returns: int -- the smaller year */
//         /*********************************************************/
//
//         @Override
//         public int compare(Storm object1, Storm object2)
//         {
//            //Initializes the first and second year
//            int year1 = object1.getYear();
//            int year2 = object2.getYear();
//            //Initializes the difference of the two storms
//            int diff = year1 - year2;
//
//            //If there isn't a difference, compare the Storm names instead
//            if (diff == 0)
//            {
//               return object1.getName().compareTo(object2.getName());
//            }
//
//            //Returns the smaller year
//            return diff;
//
//         } //End of compare
//
//      }); //End of Collection
//
//   } //End of sortList

    /**************************************************************/
   /* Method: searchByName */
   /* Purpose: search the list for a Storm name */
   /* Parameters: stormName */
   /* String target: */
   /* Returns: */

    /**************************************************************/

    public void searchByName(String stormName) {
        //Created a database object called filtered
        Database filtered = new Database();
        Storm foundStorm = null;
        for(BST<Storm> tree : this.storms) {//looping over each BST present in linkedlist
            /*BSTNode<Storm> current = tree.getRoot();
            while(current != null) {
                Storm storm = current.data;
                if(storm.getName().toLowerCase().contains(stormName.toLowerCase())) {
                    filtered.add(storm);
                }
                current = current.
            }*/
            BSTNode<Storm> bstNode = ((BSTNode<Storm>)(tree.searchNode(new Storm(0,stormName,"","",""))));//find BSTNode with specified stormName
            if(bstNode!=null) {
                foundStorm = bstNode.data;
                filtered.add(foundStorm);
            }

        }

      /*If the filtered database is empty say that there aren't any Storms
        with the name*/
        if (filtered.isEmpty()) {
            System.out.println("Could not find any storms named \"" +
                    stormName + "\"");
        }

        //Else put the filtered database into the proper format
        else {
            System.out.println(filtered.toString());
        }

    } //End of searchByName






    void populateFilteredDatabase(BSTNode<Storm> node, Database filtered) {//put current node data to filtered database and do inorder traversal for complete tree
        //inorder traversal for sorted output
        if (node == null) return;
        populateFilteredDatabase(node.left, filtered);
        filtered.add(node.data);
        populateFilteredDatabase(node.right, filtered);

    }

    /**************************************************************/
   /* Method: searchByYear */
   /* Purpose: search the list for a Storm year */
   /* Parameters: stormYear */
   /* String target: */
   /* Returns: */

    /**************************************************************/

    public void searchByYear(int stormYear) {
        //Created a database object called filtered
        Database filtered = new Database();

        //For loop for when a Storm object is the Storm object
        for (BST<Storm> stormTree : this.storms) {
            //If the Storm year equals the stormYear then add to filtered
            if (!stormTree.isEmpty() && stormTree.getRoot().data.getYear() == stormYear) {
                //filtered.add(storm);
                populateFilteredDatabase(stormTree.getRoot(),filtered);//put all the stroms from current BST to filtered Database
            }
        }
      
      /*If the filtered database is empty say that there aren't any Storms
        with the year*/
        if (filtered.isEmpty()) {
            System.out.println("Could not find any storms in year \"" + stormYear + "\"");
        }

        //Else put the filtered database into the proper format
        else {
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

    public boolean contains(Storm target) {
        /*//For loop for when a Storm object is the Storm object
        for (BST storm : storms) {
         *//*If the Storm year equals the target year and the Storm
           name equals the target name while ignoring the capitalization
           then return true*//*
            BSTNode current = storm.getRoot();
            while(current != null) {

            }
            if (storm.getYear() == target.getYear() &&
                    storm.getName().equalsIgnoreCase(target.getName())) {
                return true;
            }
        }

        //Else return false
        return false;*/


        BST<Storm> bst = findBSTByYear(target.getYear());
        if(bst==null){
            System.out.println("no storm with target year found.");
            return false;
        }
        BSTNode<Storm> bstNode = bst.searchNode(target);


        if(bstNode !=null){
            return true;
        }else{
            System.out.println("no storm found with target name.");
            return false;
        }

    } //End of contains

    /**************************************************************/
   /* Method: add */
   /* Purpose: adds the Storm object the the list */
   /* Parameters: Storm storm */
   /* String target: */
   /* Returns: */

    /**************************************************************/

    public void add(Storm storm) {
        int year = storm.getYear();
        BST<Storm> tree = findBSTByYear(year);
        if(tree!=null) {//BST corresponding to given year already exists, so just insert new storm into it
            tree.insert(storm);
        }else{//BST doesn't exists, so create new BST
            //creating new BST with Comparator as argument so that arrangement of nodes(having unique storm at each node) in BST occurs according to dictionary ordering of names of the storm
            BST<Storm> newTree = new BST<Storm>(new Comparator<Storm>() {
                @Override
                public int compare(Storm storm1, Storm storm2) {
                    if(storm1.getName().compareToIgnoreCase(storm2.getName())>0) return 1;//less than case
                    if(storm1.getName().compareToIgnoreCase(storm2.getName())<0) return -1;
                    return 0;
                }
            });
            this.storms.add(newTree);//add new BST to linked list
            newTree.insert(storm);//insert given storm to new BST
        }
    }

    private BST<Storm> findBSTByYear(int year) {//find BST from linked list which has the storms of given year
        for (BST<Storm> tree : storms) {//looping over all BSTs present in linkedlist
            if (!tree.isEmpty()) {
                int stormYear = tree.getRoot().data.getYear();
                if (stormYear == year) {
                    return tree;
                }
            }
        }
        return null;//no BST found with given year
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
                     String newEndDate, String newStrength) {
        Storm storm = null;;
        //For loop for when a Storm object is the Storm object
         BST<Storm> bst = findBSTByYear(target.getYear());
        if(bst==null){
            System.out.println("no storm with target year found.");
            return;
        }
        BSTNode<Storm> bstNode = bst.searchNode(target);
      /*  for (Storm storm : storms) {
         *//*If the Storm object year is equal to the target year and the Storm object
           name is equal to the target name while ignoring the capitalization
           then set a new year, name, start date, end date, and hurricane strength*//*
            if (storm.getYear() == target.getYear() &&
                    storm.getName().equalsIgnoreCase(target.getName())) {
                storm.setYear(newYear);
                storm.setName(newName);
                storm.setStartDate(newStartDate);
                storm.setEndDate(newEndDate);
                storm.setStrength(newStrength);

                //Says the Storm is editted successfully (YAY!!!)
                System.out.println("Storm edited successfully");
            }

        }*/

           if(bstNode !=null){
               storm = bstNode.data;
           }else{
               System.out.println("no storm found with target name.");
               return;
           }
        if (storm.getYear() == newYear && storm.getName()==newName) {//condition due to the constraint of ordering of Storm by name in BST and grouping of storm on basis of year
            storm.setYear(newYear);
            storm.setName(newName);
            storm.setStartDate(newStartDate);
            storm.setEndDate(newEndDate);
            storm.setStrength(newStrength);
        } else {//if new storm has different year and name; then it should be delted and inserted again so as to take care of year wise entry in linked list
            delete(storm);
            add(new Storm(newYear, newName, newStartDate, newEndDate, newStrength));
        }

        //Says the Storm is editted successfully (YAY!!!)
        System.out.println("Storm edited successfully");

    } //End of edit

    /**************************************************************/
   /* Method: delete */
   /* Purpose: deletes a specified Storm object */
   /* Parameters: Storm target */
   /* String target: */
   /* Returns: */

    /**************************************************************/

    public void delete(Storm target) {
        //Initialize foundStorm
        Storm foundStorm = null;

        //For loop for when a Storm object is the Storm object
    //    for (BST<Storm> storm : storms) {
         /*If the Storm object year is equal to the target year and the Storm object
           name is equal to the target name while ignoring the capitalization
           then the storm was found*/


           /* if (storm.getYear() == target.getYear() &&
                    storm.getName().equalsIgnoreCase(target.getName())) {
                foundStorm = storm;
            }
*/

     //   }
        BST<Storm> yearTree = findBSTByYear(target.getYear());

      /*If the found BST  object isn't null then remove the Storm say the Storm
        is gone*/
        if (yearTree != null) {
            yearTree.delete(/*foundTreeNode*/target);
            if(yearTree.isEmpty()){//if there is no node present in BST, then remove BST from linked list
                storms.remove(yearTree);
            }

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

    private boolean isEmpty() {
        return this.storms.isEmpty();
    }

    String addBSTToString(BSTNode<Storm> node, String string) {//convert BSTNode data to string, add it it given string and return new string
        //inorder traversal for sorted output
        if (node == null) return string;
        //processing left child
        string = addBSTToString(node.left, string);
        //processing current node
        string += node.data.toString() + "\n";
        //processing right child
        string = addBSTToString(node.right, string);
        //return modified new string
        return string;
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
    public String toString() {
        //Starting String is on a new line
        String string = "\n";

        //If the file is empty
        if (storms.isEmpty()) {
            string = "No storms";
        }

        //If the file isn't empty
        else {
            //Sort the list
            sortList();
         
         /*For when there's a Storm add the object to the String
           and start a new line*/
            for (BST<Storm> storm : this.storms) {//loop over each BST to get the data in string format
              //  string += storm.toString() + "\n";
               string = addBSTToString(storm.getRoot(),string);//appending current BST tree in string format
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

    public void printAll() {
        System.out.println(this.toString());
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
        Collections.sort(this.storms, new Comparator<BST<Storm>>()
        {

            /*********************************************************/
         /* Method: compare */
         /* Purpose: Compare two BST on the basis of year */
         /* Parameters: BST<Storm> object1, BST<Storm> object2 */
         /* String target: */
         /* Returns: int -- the smaller year */
            /*********************************************************/

            @Override
            public int compare(BST<Storm> object1, BST<Storm> object2)
            {
                //Initializes the first and second year
                int year1 = object1.getRoot().data.getYear();
                int year2 = object2.getRoot().data.getYear();
                //Initializes the difference of the two years
                int diff = year1 - year2;

                //If there isn't a difference, compare the Storm names instead
                if (diff == 0)
                {
                    System.out.println("some error because list is having more than one tree for same year");
                    return 0;
                }

                //Returns the smaller year
                return diff;

            } //End of compare

        }); //End of Collection

    } //End of sortList

}
