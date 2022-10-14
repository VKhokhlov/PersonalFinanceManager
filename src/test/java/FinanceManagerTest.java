import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FinanceManagerTest {
    @Test
    @DisplayName("loadCategories: categories count")
    public void loadCategoriesCount() {
        FinanceManager financeManager = new FinanceManager();

        financeManager.loadCategories(Main.CATEGORIES_FILE_NAME);
        Map<String, String> categories = financeManager.getCategories();

        Assertions.assertEquals(8, categories.size());
    }

    @Test
    @DisplayName("loadCategories: exception")
    public void loadCategoriesException() {
        String fileName = "categories.csv";
        FinanceManager financeManager = new FinanceManager();

        Assertions.assertThrows(RuntimeException.class, () -> financeManager.loadCategories(fileName));
    }

    @ParameterizedTest
    @CsvSource({
            "тапки,     2022-02-08, 300,    одежда",
            "мыло,      2022-02-08, 2500,   быт",
            "курица,    2022-02-08, 200000, еда",
            "самолет,   2022-02-08, 200000, другое"
    })
    @DisplayName("addPurchase")
    public void addPurchase(String title, String date, int sum, String category) {
        Purchase purchase = new Purchase(title,
                LocalDate.parse(date),
                sum);

        FinanceManager financeManager = new FinanceManager(Main.CATEGORIES_FILE_NAME, Main.DEFAULT_CATEGORY);
        financeManager.addPurchase(purchase);

        List<Purchase> purchases = financeManager.getPurchases();

        Assertions.assertEquals(1, purchases.size());
        Assertions.assertEquals(category, purchases.get(0).getCategory());
    }

    @Test
    @DisplayName("getStatistics")
    public void getStatistics() {
        FinanceManager financeManager = new FinanceManager(Main.CATEGORIES_FILE_NAME, Main.DEFAULT_CATEGORY);
        Map<Purchase, Category> params = new LinkedHashMap<>();

        params.put(new Purchase("тапки", LocalDate.parse("2022-02-08"), 200), new Category("одежда", 200));
        params.put(new Purchase("булка", LocalDate.parse("2022-02-08"), 50), new Category("одежда", 200));
        params.put(new Purchase("курица", LocalDate.parse("2022-02-08"), 500), new Category("еда", 550));
        params.put(new Purchase("колбаса", LocalDate.parse("2022-02-08"), 500), new Category("еда", 1050));
        params.put(new Purchase("самолет", LocalDate.parse("2022-02-08"), 99999), new Category("другое", 99999));
        params.put(new Purchase("акции", LocalDate.parse("2022-02-08"), 150000), new Category("финансы", 150000));

        for (Map.Entry<Purchase, Category> entry: params.entrySet()) {
            financeManager.addPurchase(entry.getKey());
            Category currentCategory = financeManager.getStatistics().getMaxCategory();
            Category targetCategory = entry.getValue();
            Assertions.assertEquals(targetCategory.getCategory(), currentCategory.getCategory());
            Assertions.assertEquals(targetCategory.getSum(), currentCategory.getSum());
        }
    }
}
