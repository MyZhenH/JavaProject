import java.sql.SQLException;
import java.util.List;

public class OrderService {
    OrderRepository orderRepository = new OrderRepository();
    ProductRepository productRepository = new ProductRepository();

    public void getOrderHistory(int customer_id) throws SQLException {
        orderRepository.getOrderHistory(customer_id);
    }

    public boolean addProductOnOrder(int customerId, List<OrderProduct> orderProducts) throws SQLException {
        return orderRepository.addProductsOnOrder(customerId, orderProducts);
    }

    public double getUnitPrice(int product_id) throws SQLException {
        return productRepository.getPrice(product_id);
    }


    public double totalPrice(List<OrderProduct> orderProducts) {
        double total = 0.0;

        for (OrderProduct orderProduct : orderProducts) {
            total += (orderProduct.getUnit_price() * orderProduct.getQuantity());
        }
        return total;
    }

    public String totalPriceToString(double totalPrice) {
        return String.format("%.2f", totalPrice);

    }

    public void updateStockQuantity(List<OrderProduct> orderProducts) throws SQLException {

        for (OrderProduct orderProduct : orderProducts) {
            int productId = orderProduct.getProduct_id();
            int quantity = orderProduct.getQuantity();

            int stockQuantity = productRepository.getStockQuantity(productId); //Hämta lagersaldo

            int newStockQuantity = (stockQuantity - quantity);

            productRepository.updateStock(newStockQuantity, productId);
        }
    }

}


