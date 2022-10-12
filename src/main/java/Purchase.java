import java.util.Date;

public class Purchase {
    private String category;
    private String title;
    private Date date;
    private int sum;

    public Purchase(String title, Date date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
