public class Main {
    public static final int PORT = 8989;
    public static final String CATEGORIES_FILE_NAME = "categories.tsv";
    public static final String DATA_FILE_NAME = "data.bin";
    public static final String DEFAULT_CATEGORY = "другое";

    public static void main(String[] args) {
        try {
            FinanceManager financeManager = new FinanceManager(CATEGORIES_FILE_NAME, DEFAULT_CATEGORY);
            financeManager.loadPurchases(DATA_FILE_NAME);

            Server server = new Server(PORT, DATA_FILE_NAME, financeManager);
            server.start();
        } catch (RuntimeException e) {
            System.out.println("Runtime error: " + e.getMessage());
        }
    }
}