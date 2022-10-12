public class Main {
    public static final int PORT = 8989;
    public static final String CATEGORIES_FILE_NAME = "categories.tsv";
    public static final String DEFAULT_CATEGORY = "другое";

    public static void main(String[] args) {
        try {
            FinanceManager financeManager = new FinanceManager(CATEGORIES_FILE_NAME, DEFAULT_CATEGORY);
            Server server = new Server(PORT, financeManager);
            server.start();
        } catch (RuntimeException e) {
            System.out.println("Runtime error: " + e.getMessage());
        }
    }
}