

/**
 * Storm class
 */
public class Storm {

    private int year;
    private String name;
    private String startDate;
    private String endDate;
    private int strength;

    /**
     * Constructor
     * @param year
     * @param name
     * @param startDate
     * @param endDate
     * @param strength
     */
    public Storm(int year, String name, String startDate, String endDate, int strength) {
        this.year = year;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.strength = strength;
    }

    @Override
    public String toString() {
        String start = (this.startDate != null && this.startDate.length() > 0) ? this.startDate : "(no start)";
        String end = (this.endDate != null && this.endDate.length() > 0) ? this.endDate : "(no end)";

        return String.format("%d: %s: %s - %s", year, name, start, end);
    }

    /**
     * Gets the name
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the year
     * @return
     */
    public int getYear() {
        return year;
    }

}
