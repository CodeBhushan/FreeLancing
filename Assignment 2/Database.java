

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Database class
 */
public class Database {

    private List<List<Storm>> storms;

    /**
     * Constructor
     */
    public Database() {
        this.storms = new LinkedList<>();
    }

    /**
     * Adds a storm
     * @param storm
     */
    public void add(Storm storm) {
        boolean added = false;

        /**
         * Look for a list with that year, if found
         * add it to that list
         */
        for(List<Storm> stormsInYear : storms) {
            int stormYear = stormsInYear.get(0).getYear();
            if(stormYear == storm.getYear()) {
                stormsInYear.add(storm);
                added = true;
            }
        }

        /**
         * If it wasn't added, create a new list for that year
         * and add the storm
         */
        if(!added) {
            List<Storm> stormList = new LinkedList<>();
            stormList.add(storm);
            this.storms.add(stormList);
        }
    }

    @Override
    public String toString() {
        /**
         * Make a string of storms
         */
        String str = "\n";
        if(storms.isEmpty()) {
            str = "No storms";
        }
        else {
            sortList();
            for(List<Storm> stormsInYear : storms) {
                for(Storm storm : stormsInYear) {
                    str += storm.toString() + "\n";
                }
            }
        }
        return str;
    }

    /**
     * Sort the storm by year
     */
    private void sortList() {
        Collections.sort(this.storms, new Comparator<List<Storm>>() {
            @Override
            public int compare(List<Storm> o1, List<Storm> o2) {
                int year1 = o1.get(0).getYear();
                int year2 = o2.get(0).getYear();
                return  year1 - year2;
            }
        });

        /**
         * Sort storms in each year by name
         */
        for(List<Storm> stormsInYear : storms) {
            Collections.sort(stormsInYear, new Comparator<Storm>() {
                @Override
                public int compare(Storm o1, Storm o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    /**
     * Prints all the storms
     */
    public void printAll() {
        System.out.println(this.toString());
    }

    /**
     * Searches a storm by name
     * @param stormName
     */
    public void searchByName(String stormName) {
        Database filtered = new Database();

        for(List<Storm> stormsInYear : storms) {
            for(Storm storm : stormsInYear) {
                if(storm.getName().toLowerCase().contains(stormName.toLowerCase())) {
                    filtered.add(storm);
                }
            }
        }
        if(filtered.isEmpty()) {
            System.out.println("Could not find any storms named \"" + stormName + "\"");
        }
        else {
            System.out.println(filtered.toString());
        }
    }

    /**
     * Check if database is empty
     * @return
     */
    private boolean isEmpty() {
        return this.storms.isEmpty();
    }

    /**
     * Search a storm by year
     * @param stormYear
     */
    public void searchByYear(int stormYear) {
        Database filtered = new Database();
        for(List<Storm> stormsInYear : storms) {
            for(Storm storm : stormsInYear) {
                if(storm.getYear() == stormYear) {
                    filtered.add(storm);
                }
            }
        }

        if(filtered.isEmpty()) {
            System.out.println("Could not find any storms in year \"" + stormYear + "\"");
        }
        else {
            System.out.println(filtered.toString());
        }
    }

    /**
     * Check if a storm exists
     * @param target
     * @return
     */
    public boolean contains(Storm target) {
        for(List<Storm> stormsInYear : storms) {
            for(Storm storm : stormsInYear) {
                if(storm.getYear() == target.getYear() && storm.getName().equalsIgnoreCase(target.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Delete a storm
     * @param target
     */
    public void delete(Storm target) {
        List<Storm> foundList = null;
        Storm foundStorm = null;
        for(List<Storm> stormsInYear : storms) {
            for(Storm storm : stormsInYear) {
                if(storm.getYear() == target.getYear() && storm.getName().equalsIgnoreCase(target.getName())) {
                    foundList = stormsInYear;
                    foundStorm = storm;
                }
            }
        }
        if(foundList != null) {
            foundList.remove(foundStorm);
            // also remove the list if we don't have anymore storms
            if(foundList.isEmpty()) {
                this.storms.remove(foundList);
            }
            System.out.println("Storm deleted.");
        }
    }
}
