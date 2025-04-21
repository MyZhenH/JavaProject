public class OrderProduct {
    private int order_id;
    private int product_id;
    private int quantity;
    private double unit_price;

    public OrderProduct(int order_id, int product_id, int quantity, double unit_price) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public OrderProduct(int product_id, int quantity, double unit_price) {
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public OrderProduct(int product_id, int quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
