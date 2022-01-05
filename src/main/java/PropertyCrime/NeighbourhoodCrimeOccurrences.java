package PropertyCrime;

import java.util.Objects;

public class NeighbourhoodCrimeOccurrences {
    private String reportedYear;
    private String reportedQuarter;
    private int reportedMonth;
    private int reportedFrequency;

    public NeighbourhoodCrimeOccurrences(String reportedYear, String reportedQuarter, int reportedMonth, int reportedFrequency) {
        this.reportedYear = reportedYear;
        this.reportedQuarter = reportedQuarter;
        this.reportedMonth = reportedMonth;
        this.reportedFrequency = reportedFrequency;
    }

    public String getReportedYear() {
        return reportedYear;
    }

    public void setReportedYear(String reportedYear) {
        this.reportedYear = reportedYear;
    }

    public String getReportedQuarter() {
        return reportedQuarter;
    }

    public void setReportedQuarter(String reportedQuarter) {
        this.reportedQuarter = reportedQuarter;
    }

    public int getReportedMonth() {
        return reportedMonth;
    }

    public void setReportedMonth(int reportedMonth) {
        this.reportedMonth = reportedMonth;
    }

    public int getReportedFrequency() {
        return reportedFrequency;
    }

    public void setReportedFrequency(int reportedFrequency) {
        this.reportedFrequency = reportedFrequency;
    }

    @Override
    public String toString() {
        return "CrimeOccurrences{" +
                "reportedYear=" + reportedYear +
                ", reportedQuarter=" + reportedQuarter +
                ", reportedMonth=" + reportedMonth +
                ", reportedFrequency=" + reportedFrequency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeighbourhoodCrimeOccurrences that = (NeighbourhoodCrimeOccurrences) o;
        return reportedYear == that.reportedYear && reportedQuarter == that.reportedQuarter && reportedMonth == that.reportedMonth && reportedFrequency == that.reportedFrequency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(reportedYear, reportedQuarter, reportedMonth, reportedFrequency);
    }
}
