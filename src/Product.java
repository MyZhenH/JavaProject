public class Product {
    private int product_id;
    private String name;
    private double price;
    private int stock_quantity;
    private String categoryName;


    public Product(String name, double price, int stock_quantity) {
        this.name = name;
        this.price = price;
        this.stock_quantity = stock_quantity;
    }

    public Product(String name, double price, int stock_quantity, String categoryName) {
        this.name = name;
        this.price = price;
        this.stock_quantity = stock_quantity;
        this.categoryName = categoryName;
    }

    public Product(String name, int price, int stock_quantity){
        this.name = name;
        this.price = price;
        this.stock_quantity = stock_quantity;
    }

    public Product(int product_id, double price){
        this.product_id = product_id;
        this.price = price;
    }

    public Product(int product_id, String name, double price, int stock_quantity){
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.stock_quantity = stock_quantity;

    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}

