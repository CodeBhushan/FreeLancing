

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class with main method
 */
public class Application {

    private static final int MENU_SEARCH_NAME = 1;
    private static final int MENU_SEARCH_YEARS = 2;
    private static final int MENU_PRINT_ALL = 3;
    private static final int MENU_ADD_STORM = 4;
    private static final int MENU_DELETE_STORM = 5;
    private static final int MENU_EXIT = 9;

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main, needed to run the program
     * @param args
     */
    public static void main(String[] args) {
        printWelcomeMessage();

        Database database = readDatabaseFile("inputFile.txt");

        int menuSelection = -MENU_SEARCH_NAME;

        /**
         * ask for menu selection until user exists
         */
        while(menuSelection != MENU_EXIT) {
            menuSelection = getMenuSelection();

            if(menuSelection == MENU_SEARCH_NAME) {
                String stormName = readStringFromKeyboard("\nEnter storm name prefix: ");
                database.searchByName(stormName);
            }
            else if(menuSelection == MENU_SEARCH_YEARS) {
                int stormYear = readIntegerFromKeyboard("\nEnter storm year: ", 0);
                database.searchByYear(stormYear);
            }
            else if(menuSelection == MENU_PRINT_ALL) {
                database.printAll();
            }
            else if(menuSelection == MENU_ADD_STORM) {
                Storm storm = createNewStorm();
                if(database.contains(storm)) {
                    System.out.println("Cannot add storm. It already exists.");
                }
                else {
                    database.add(storm);
                    System.out.println("Storm was added successfully.");
                }
            }
            else if(menuSelection == MENU_DELETE_STORM) {
                System.out.println("\nDeleting storm:");
                int year = readIntegerFromKeyboard("Enter year: ", 0);
                String name = readStringFromKeyboard("Enter name: ");
                Storm storm = new Storm(year, name, "", "", 0);
                if(database.contains(storm)) {
                    boolean confirm = "Y".equalsIgnoreCase(readStringFromKeyboard("Are you sure you want to delete storm? Y/N: "));
                    if(confirm) {
                        database.delete(storm);
                    }
                }
                else {
                    System.out.println("Cannot delete. No such storm found.");
                }

            }
            else if(menuSelection != MENU_EXIT) {
                System.out.println("Invalid command. Try again.");
            }
        }

    }

    /**
     * Creates a new storm
     * @return
     */
    private static Storm createNewStorm() {
        System.out.println("\nCreating new storm:");
        int year = readIntegerFromKeyboard("Enter year: ", 0);
        String name = readStringFromKeyboard("Enter name: ");
        String startDate = readStringFromKeyboard("Enter start date: ");
        String endDate = readStringFromKeyboard("Enter end date: ");
        int strength = readIntegerFromKeyboard("Enter strength: ", 0);

        return new Storm(year, name, startDate, endDate, strength);
    }

    /**
     * Reads a string from user input
     * @param message
     * @return
     */
    private static String readStringFromKeyboard(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Reads the file with the storms
     * @param fileName
     * @return
     */
    private static Database readDatabaseFile(String fileName) {
        Database database = new Database();

        try {
            Scanner reader = new Scanner(new File(fileName));
            while(reader.hasNextLine()) {
                String line = reader.nextLine();
                Storm storm = parseLineIntoStorm(line);
                database.add(storm);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return database;
    }

    /**
     * Converts a line into a storm object
     * @param line
     * @return
     */
    private static Storm parseLineIntoStorm(String line) {
        String[] tokens = line.split("/");
        String[] array = new String[5];
        for(int i=0; i<tokens.length; i++) {
            array[i] = tokens[i];
        }
        return new Storm(parseInteger(array[0], 0),
                        array[1],
                        parseDate(array[2]),
                        parseDate(array[3]),
                        parseInteger(array[4], 0));
    }

    /**
     * Parses the from the file
     * @param str
     * @return
     */
    private static String parseDate(String str) {
        if(str != null && str.length() == 4) {
            return str.substring(0, 2) + "/" + str.substring(3);
        }
        return str;
    }

    /**
     * Parses an integer value
     * @param str
     * @param defaultValue
     * @return
     */
    private static int parseInteger(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        }
        catch (Exception e) {}
        return defaultValue;
    }

    /**
     * Shows the menu and gets user selection
     * @return
     */
    private static int getMenuSelection() {
        String message = "\nCurrent available commands:\n" +
                MENU_SEARCH_NAME + " --> Search for a storm name\n" +
                MENU_SEARCH_YEARS + " --> Search for a year\n" +
                MENU_PRINT_ALL + " --> Print all storms\n" +
                MENU_ADD_STORM + " --> Add new storm\n" +
                MENU_DELETE_STORM + " --> Delete a storm\n" +
                Application.MENU_EXIT + " --> Exit\n" +
                "Your choice? ";

        return readIntegerFromKeyboard(message, -1);
    }

    /**
     * Reads an integer from user input
     * @param message
     * @param defaultValue
     * @return
     */
    private static int readIntegerFromKeyboard(String message, int defaultValue) {
        System.out.print(message);
        try {
            return Integer.parseInt(scanner.nextLine());
        }
        catch (Exception e) {}
        return defaultValue;
    }

    /**
     * Prints a welcome message
     */
    private static void printWelcomeMessage() {
        System.out.println("Welcome to the CS-102 Storm Tracker Program.");
    }
}
