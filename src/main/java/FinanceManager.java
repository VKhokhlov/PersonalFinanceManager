import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (purchases.size() == 0) {
            return null;
        }

        Purchase lastPurchase = purchases.get(purchases.size() - 1);

        Stream<Purchase> maxStream = purchases.stream();
        Stream<Purchase> maxYearStream = purchases.stream().filter(p -> p.cmpYear(lastPurchase));
        Stream<Purchase> maxMonthStream = purchases.stream().filter(p -> p.cmpMonth(lastPurchase));
        Stream<Purchase> maxDayStream = purchases.stream().filter(p -> p.cmpDay(lastPurchase));

        Category maxCategory = MaxCategory(maxStream);
        Category maxYearCategory = MaxCategory(maxYearStream);
        Category maxMonthCategory = MaxCategory(maxMonthStream);
        Category maxDayCategory = MaxCategory(maxDayStream);

        return new Statistics(maxCategory, maxYearCategory, maxMonthCategory, maxDayCategory);
    }

    public Category MaxCategory(Stream<Purchase> purchaseStream) {
        Map.Entry<String, Integer> maxCategory = purchaseStream
                .collect(Collectors.toMap(Purchase::getCategory, Purchase::getSum, Integer::sum))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get();

        return new Category(maxCategory.getKey(), maxCategory.getValue());
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    @SuppressWarnings("unchecked")
    public void loadPurchases(String dataFileName) {
        File file = new File(dataFileName);

        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream out = new ObjectInputStream(new FileInputStream(file))) {
            purchases = (List<Purchase>) out.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("File load error: " + dataFileName + " (" + e.getMessage() + ")");
        }
    }

    public void savePurchases(String dataFileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dataFileName))) {
            out.writeObject(purchases);
        } catch (IOException e) {
            throw new RuntimeException("File save error: " + dataFileName);
        }
    }
}
