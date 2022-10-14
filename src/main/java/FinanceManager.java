import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FinanceManager {
    private Map<String, String> categories;
    private List<Purchase> purchases;
    private String defaultCategory;

    public FinanceManager() {
    }

    public FinanceManager(String categoriesFileName, String defaultCategory) {
        this.purchases = new ArrayList<>();
        this.defaultCategory = defaultCategory;

        loadCategories(categoriesFileName);
    }

    public void loadCategories(String categoriesFileName) throws RuntimeException {
        File file = new File(categoriesFileName);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            categories = bufferedReader
                    .lines()
                    .map(line -> line.split("\t"))
                    .collect(Collectors.toMap(line -> line[0], line -> line[1]));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + categoriesFileName);
        } catch (IOException e) {
            throw new RuntimeException("File read error: " + categoriesFileName);
        }
    }

    public void addPurchase(Purchase purchase) {
        String category = categories.get(purchase.getTitle());
        purchase.setCategory(category == null ? defaultCategory : category);
        purchases.add(purchase);
    }

    public Statistics getStatistics() {
        Map.Entry<String, Integer> maxCategory = purchases.stream()
                .collect(Collectors.toMap(Purchase::getCategory, Purchase::getSum, Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();

        return new Statistics(maxCategory.getKey(), maxCategory.getValue());
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }
}
