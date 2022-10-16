import java.util.Objects;

public class Statistics {
    private final Category maxCategory;
    private final Category maxYearCategory;
    private final Category maxMonthCategory;
    private final Category maxDayCategory;

    public Statistics(Category maxCategory, Category maxYearCategory, Category maxMonthCategory, Category maxDayCategory) {
        this.maxCategory = maxCategory;
        this.maxYearCategory = maxYearCategory;
        this.maxMonthCategory = maxMonthCategory;
        this.maxDayCategory = maxDayCategory;
    }

    public Category getMaxCategory() {
        return maxCategory;
    }

    public Category getMaxYearCategory() {
        return maxYearCategory;
    }

    public Category getMaxMonthCategory() {
        return maxMonthCategory;
    }

    public Category getMaxDayCategory() {
        return maxDayCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(maxCategory, that.maxCategory) && Objects.equals(maxYearCategory, that.maxYearCategory) && Objects.equals(maxMonthCategory, that.maxMonthCategory) && Objects.equals(maxDayCategory, that.maxDayCategory);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "maxCategory=" + maxCategory +
                ", maxYearCategory=" + maxYearCategory +
                ", maxMonthCategory=" + maxMonthCategory +
                ", maxDayCategory=" + maxDayCategory +
                '}';
    }
}
