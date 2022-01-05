package PropertyCrime;


import java.util.Objects;

/**
 * Years with the most crime
 *
 * Neighbourhood with the most crimes
 *
 * Neighbourhood and crime descriptor example Robbery + Oxford
 *
 * Most crimes done in the city in the city
 *
 * Rank years with the most crime
 *
 * Use Bar Chart to summarize the data
 *
 * Use the Predicate interface minimize loops
 */

public class NeighbourhoodCrimeAssessment {
   private String neighbourhood;
   private String crimeDescriptor;
   private NeighbourhoodCrimeOccurrences crimeOccurrences;

    public NeighbourhoodCrimeAssessment(String neighbourhood, String crimeDescriptor, NeighbourhoodCrimeOccurrences crimeOccurrences) {
        this.neighbourhood = neighbourhood;
        this.crimeDescriptor = crimeDescriptor;
        this.crimeOccurrences = crimeOccurrences;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCrimeDescriptor() {
        return crimeDescriptor;
    }

    public void setCrimeDescriptor(String crimeDescriptor) {
        this.crimeDescriptor = crimeDescriptor;
    }

    public NeighbourhoodCrimeOccurrences getCrimeOccurrences() {
        return crimeOccurrences;
    }

    public void setCrimeOccurrences(NeighbourhoodCrimeOccurrences crimeOccurrences) {
        this.crimeOccurrences = crimeOccurrences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighbourhoodCrimeAssessment that = (NeighbourhoodCrimeAssessment) o;
        return Objects.equals(neighbourhood, that.neighbourhood) && Objects.equals(crimeDescriptor, that.crimeDescriptor) && Objects.equals(crimeOccurrences, that.crimeOccurrences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbourhood, crimeDescriptor, crimeOccurrences);
    }


    @Override
    public String toString() {
        return "NeighbourhoodCrimeAssessment{" +
                "neighbourhood='" + neighbourhood + '\'' +
                ", crimeDescriptor='" + crimeDescriptor + '\'' +
                ", crimeOccurrences=" + crimeOccurrences +
                '}';
    }
}