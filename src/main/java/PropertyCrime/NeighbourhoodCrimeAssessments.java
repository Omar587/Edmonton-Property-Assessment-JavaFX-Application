package PropertyCrime;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class NeighbourhoodCrimeAssessments {


    /*propertyCrimeData Contains the whole dataset  */
    private List<NeighbourhoodCrimeAssessment> propertyCrimeData = new ArrayList<>();


    public List<NeighbourhoodCrimeAssessment> getAll() {
        if (propertyCrimeData.size() == 0) {
            generatePropertyData("src\\main\\java\\EPS_Neighbourhood_Criminal_Occurrences.csv");
        }

        return Collections.unmodifiableList(propertyCrimeData);
    }

    private void setPropertyData(List<NeighbourhoodCrimeAssessment> propertyData) {
        this.propertyCrimeData = propertyData;
    }


    /**
     * @param csvLine takes a single line from a csv file and turns elements of that line
     *                to represent a NeighbourhoodCrimeAssessment object.
     * @return returns a single NeighbourhoodCrimeAssessment object.
     */
    private NeighbourhoodCrimeAssessment parseCsvLine(String csvLine) {

        /*The indexes of the string csvLine represents each column of the csv file */
        String[] column = csvLine.split(",");

        String neighbourhood = column[0];
        String crimeDescriptor = column[1];

        NeighbourhoodCrimeOccurrences occurrences = new NeighbourhoodCrimeOccurrences((column[2]),
                column[3], Integer.parseInt(column[4]), Integer.parseInt(column[4]));

        return new NeighbourhoodCrimeAssessment(neighbourhood, crimeDescriptor, occurrences);

    }

    /**
     * takes the csv file and sets the propertyCrimeData list
     * @param file
     */

    private void generatePropertyData(String file) {


        try {
            Scanner  sc = new Scanner(Paths.get(file));
            List<NeighbourhoodCrimeAssessment> neighbourhoodCrimeList = new ArrayList<>();

            //Skip the first row which is the column names
            sc.nextLine();

            while (sc.hasNextLine()) {
                String csvLine = sc.nextLine();

                ;
                NeighbourhoodCrimeAssessment property = parseCsvLine(csvLine);
                neighbourhoodCrimeList.add(property);
            }

            setPropertyData(neighbourhoodCrimeList);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    /**
     * loops through the neighbourhoodCrimeAssessment list and maps crime descriptors with the occurrence count
     *
     * @return a Map<String, Integer> where String is the descriptors and Integer is the counts
     */


    public Map<String, Integer> crimeDescriptorsAndCount() {
        Map<String, Integer> mp = new HashMap<>();


        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String crimeDescriptor = neighbourhoods.getAll().get(i).getCrimeDescriptor();

            if (mp.containsKey(crimeDescriptor)) {
                int count = mp.get(crimeDescriptor);
                mp.put(crimeDescriptor, count + 1);
            } else {
                mp.put(crimeDescriptor, 1);
            }
        }

        return Collections.unmodifiableMap(mp);

    }


    /**
     *  loops through the neighbourhoodCrimeAssessment list and maps the neighbourhoods with the total crime that happened
     *
     * @return A MAP<String,Integer>  where string is neighbourhood, and Integer is the count of crimes
     */
    public Map<String, Integer> neighbourhoodsCrimeCount() {

        Map<String, Integer> mp = new HashMap<>();


        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String neighbourhood = neighbourhoods.getAll().get(i).getNeighbourhood();

            if (mp.containsKey(neighbourhood)) {
                int count = mp.get(neighbourhood);
                mp.put(neighbourhood, count + 1);
            } else {
                mp.put(neighbourhood, 1);
            }
        }

        return Collections.unmodifiableMap( sortMap(mp));
    }


    /**
     *  loops through the NeighbourhoodCrimeAssessment list and maps each year with how much crime happened in that year
     *
     * @return Map<String, Integer>  where string is the year and Integer is the count of crimes.
     */
    public Map<String, Integer> incidentsPerYear() {

        Map<String, Integer> mp = new TreeMap<>();


        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String years = neighbourhoods.getAll().get(i).getCrimeOccurrences().getReportedYear();

            if (mp.containsKey(years)) {
                int count = mp.get(years);
                mp.put(years, count + 1);
            } else {
                mp.put(years, 1);
            }
        }

        return Collections.unmodifiableMap(mp);

    }


    /**
     *  loops through the NeighbourhoodCrimeAssessment l list and maps each quarter with how much crime happened in that quarter
     *
     *
     * @return Map<String, Integer>  where string is the quarter and Integer is the count of crimes.
     */
    public Map<String, Integer> incidentsPerQuarter() {

        Map<String, Integer> mp = new TreeMap<>();

        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String quarter = neighbourhoods.getAll().get(i).getCrimeOccurrences().getReportedQuarter();

            if (mp.containsKey(quarter)) {
                int count = mp.get(quarter);
                mp.put(quarter, count + 1);
            } else {
                mp.put(quarter, 1);
            }
        }

        return Collections.unmodifiableMap(mp);

    }


    /**
     *  loops through the NeighbourhoodCrimeAssessment  list,
      map the years and the counts where that crime happened in that neighborhood
     *
     * @return Map<String, Integer>  where string is the quarter and Integer is the count of crimes.
     */
    public Map<String, Integer> crimeTypeByYearInNeighbourhood(String neighbourhood, String crimeDescriptor) {

        Map<String, Integer> mp = new TreeMap<>();

        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {

            String csvNeighbourhood = neighbourhoods.getAll().get(i).getNeighbourhood().toUpperCase();
            String csvCrimeType = neighbourhoods.getAll().get(i).getCrimeDescriptor().toUpperCase();

            if (csvNeighbourhood.contains(neighbourhood.toUpperCase()) && csvCrimeType.contains(crimeDescriptor.toUpperCase())) {

                String year = neighbourhoods.getAll().get(i).getCrimeOccurrences().getReportedYear();

                if (mp.containsKey(year)) {
                    int count = mp.get(year);
                    mp.put(year, count + 1);
                } else {
                    mp.put(year, 1);
                }
            }


        }
        return Collections.unmodifiableMap(mp);

    }


    /**
     * loops through the NeighbourhoodCrimeAssessment  list, counts how much a crime descriptor happened in edmonton every
     * year. The year is mapped to the crime descriptor.
     *
     * @param crimeDescriptor
     * @return Map<String, Integer> where string is the year and integer is the count
     */
    public Map<String, Integer> crimeTypePerYear(String crimeDescriptor) {
        Map<String, Integer> mp = new TreeMap<>();

        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String csvCrimeDescriptor = neighbourhoods.getAll().get(i).getCrimeDescriptor();

            if (csvCrimeDescriptor.equals(crimeDescriptor)) {
                String year = neighbourhoods.getAll().get(i).getCrimeOccurrences().getReportedYear();

                if (mp.containsKey(year)) {
                    int count = mp.get(year);
                    mp.put(year, count + 1);
                } else {
                    mp.put(year, 1);
                }
            }
        }

        return Collections.unmodifiableMap(mp);

    }


    /**
     *
     * @param neighbourhood desired neighbourhood
     *  loops through the NeighbourhoodCrimeAssessment list, the crimeDescriptors associated with the
     *  neighbourhood given is mapped with the count.
     *
     * @return  Map<String, Integer>  the String is crimeDescriptor, Integer is the count.
     */
    public Map<String, Integer> neighbourhoodCrimeType(String neighbourhood) {
        Map<String, Integer> mp = new HashMap<>();

        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            String csvNeighbourhood = neighbourhoods.getAll().get(i).getNeighbourhood();

            if (csvNeighbourhood.contains(neighbourhood.toUpperCase())) {
                String crimeDescriptor = neighbourhoods.getAll().get(i).getCrimeDescriptor();

                if (mp.containsKey(crimeDescriptor)) {
                    int count = mp.get(crimeDescriptor);
                    mp.put(crimeDescriptor, count + 1);
                } else {
                    mp.put(crimeDescriptor, 1);
                }
            }
        }

        return Collections.unmodifiableMap(mp);

    }


    /**
     * loops through the NeighbourhoodCrimeAssessment list, and the months are mapped to count where counts
     * are the count of crimes that happened in the month.
     *
     * @return Map<String, Integer>  String is the month, Integer count
     */

    public Map<String, Integer> totalIncidentsPerMonth() {

        Map<String, Integer> mp = new TreeMap<>();
        NeighbourhoodCrimeAssessments neighbourhoods = new NeighbourhoodCrimeAssessments();
        for (int i = 0; i < neighbourhoods.getAll().size(); i++) {
            int month = neighbourhoods.getAll().get(i).getCrimeOccurrences().getReportedMonth();
            String months =monthConverter().get(month);
            if (mp.containsKey(months)) {
                int count = mp.get(months);
                mp.put(months, count + 1);
            } else {
                mp.put(months, 1);
            }
        }

        return Collections.unmodifiableMap(sortMap(mp));
    }


    /**
     *
     *
     * @param neighbourhood Desired neighbourhood to search
     *
     * @return General statistics about neighbourhood such as their safety rank and how many crimes happened from
     * 2009-2019
     */

    public String neighbourhoodSafetyRank(String neighbourhood) {

        Map<String, Integer> mp = sortMap(neighbourhoodsCrimeCount());

        int count=0;

        for (String key: mp.keySet()){
            if (key.contains(neighbourhood.toUpperCase())){
                return "Neighbourhood " + neighbourhood + " is ranked number " + count + "\non most reported crimes out of" +
                        " " + mp.size() + " neighbourhoods.\n\n" +
                        "Total Crimes reported from 2009-2019 is " + mp.get(key);
            }
            count++;
        }

        return "";
    }




    /*******************************  *Helper methods  ***************************************** */

    /**
     *
     * @param mp takes a map
     *
     * @return LinkedHashMap in sorted order.
     */
    private Map<String, Integer> sortMap(Map<String, Integer> mp){
        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        mp.entrySet().stream()
                .sorted((key1, key2) -> -key1.getValue().compareTo(key2.getValue()))
                .forEach(k -> sortedMap.put(k.getKey(), k.getValue()));

        return sortedMap;


    }


    /**
     * if an integer key is given from 1-12 it will return the month
     * as a string.
     * @return
     */
    private Map<Integer,String>  monthConverter(){
        HashMap<Integer,String> map = new HashMap<>();

        map.put(1, "January");
        map.put(2, "February");
        map.put(3, "March");
        map.put(4, "April");
        map.put(5, "May");
        map.put(6, "June");
        map.put(7, "July");
        map.put(8, "August");
        map.put(9, "September");
        map.put(10, "October");
        map.put(11, "November");
        map.put(12, "December");

        return map;
    }




}









