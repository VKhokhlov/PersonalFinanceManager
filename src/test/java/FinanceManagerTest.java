import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

    private static Map<Purchase, Statistics> getStatisticsPackParams(String[] stringParams) {
        Map<Purchase, Statistics> params = new LinkedHashMap<>();

        for (String line:stringParams) {
            String values[] = line.split(",");

            Category maxCategory = new Category(values[3], Integer.parseInt(values[4]));
            Category maxYearCategory = new Category(values[5], Integer.parseInt(values[6]));
            Category maxMonthCategory = new Category(values[7], Integer.parseInt(values[8]));
            Category maxDayCategory = new Category(values[9], Integer.parseInt(values[10]));

            Statistics statistics = new Statistics(maxCategory, maxYearCategory, maxMonthCategory, maxDayCategory);
            Purchase purchase = new Purchase(values[0], LocalDate.parse(values[1]), Integer.parseInt(values[2]));

            params.put(purchase, statistics);
        }

        return params;
    }

    private static Stream<Arguments> getStatisticsParams() {
        // 1 - purchase.title
        // 2 - purchase.date
        // 3 - purchase.sum
        // 4 - maxCategory.category
        // 5 - maxCategory.sum
        // 6 - maxYearCategory.category
        // 7 - maxYearCategory.sum
        // 8 - maxMonthCategory.category
        // 9 - maxMonthCategory.sum
        // 10 - maxDayCategory.category
        // 11 - maxDayCategory.sum
        String testCases1[] = {
                "тапки,2022-02-08,100,одежда,100,одежда,100,одежда,100,одежда,100",
                "шапка,2022-02-08,100,одежда,200,одежда,200,одежда,200,одежда,200",
                "булка,2022-02-08,20,одежда,200,одежда,200,одежда,200,одежда,200",
                "курица,2022-02-08,500,еда,520,еда,520,еда,520,еда,520",
                "самолет,2022-02-08,1000,другое,1000,другое,1000,другое,1000,другое,1000",
                "танк,2020-01-01,500,другое,1500,другое,500,другое,500,другое,500",
                "булка,2020-01-02,20,другое,1500,другое,500,другое,500,еда,20",
                "булка,2020-01-02,20,другое,1500,другое,500,другое,500,еда,40",
                "сухарики,2020-01-03,600,другое,1500,еда,640,еда,640,еда,600"
        };

        String testCases2[] = {
                "булка,2022-02-08,20,еда,20,еда,20,еда,20,еда,20",
                "тапки,2022-02-10,500,одежда,500,одежда,500,одежда,500,одежда,500",
                "булка,2022-02-08,20,одежда,500,одежда,500,одежда,500,еда,40",
        };

        return Stream.of(
                Arguments.of(getStatisticsPackParams(testCases1)),
                Arguments.of(getStatisticsPackParams(testCases2))
        );
    }

    @ParameterizedTest
    @DisplayName("getStatistics")
    @MethodSource("getStatisticsParams")
    public void getStatistics(Map<Purchase, Statistics> params) {
        FinanceManager financeManager = new FinanceManager(Main.CATEGORIES_FILE_NAME, Main.DEFAULT_CATEGORY);

        for (Map.Entry<Purchase, Statistics> entry: params.entrySet()) {
            financeManager.addPurchase(entry.getKey());

            Statistics statistics = financeManager.getStatistics();
            Statistics expectedStatistics = entry.getValue();

            Assertions.assertEquals(statistics, expectedStatistics);
        }
    }
}
