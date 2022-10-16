import java.util.Objects;

public class Category {
    private String category;
    private int sum;

    public Category(String category, int sum) {
        this.category = category;
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return sum == that.sum && category.equals(that.category);
    }

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                ", sum=" + sum +
                '}';
    }
}
