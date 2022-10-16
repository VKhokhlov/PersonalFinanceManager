import java.io.Serializable;
import java.time.LocalDate;

public class Purchase implements Serializable {
    private String category;
    private final String title;
    private final LocalDate date;
    private final int sum;

    public Purchase(String title, LocalDate date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public boolean cmpYear(Purchase purchase) {
        LocalDate date = purchase.getDate();
        return this.date.getYear() == date.getYear();
    }

    public boolean cmpMonth(Purchase purchase) {
        LocalDate date = purchase.getDate();
        return this.date.getYear() == date.getYear() && this.date.getMonth() == date.getMonth();
    }

    public boolean cmpDay(Purchase purchase) {
        LocalDate date = purchase.getDate();
        return this.date.equals(date);
    }
}
