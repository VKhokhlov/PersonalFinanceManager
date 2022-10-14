public class Statistics {
    private final Category maxCategory;

    public Statistics(String category, int sum) {
        maxCategory = new Category(category, sum);
    }

    public Category getMaxCategory() {
        return maxCategory;
    }
}
