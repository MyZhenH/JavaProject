import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public void getOrderHistory(int customer_id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, customer_id);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("customer_id"),
                        rs.getInt("order_id"),
                        rs.getDate("order_date"));

                System.out.println("\uD83E\uDDFE Order ID: " + order.getOrder_id());
                System.out.println("\uD83D\uDCC5 Order date: " + order.getOrder_date());
                System.out.println();
            }
            System.out.println();
        }
    }


    public boolean addProductsOnOrder(int customer_id, List<OrderProduct> orderProducts) throws SQLException {
        String sql = "INSERT INTO orders (customer_id, order_date) VALUES (?, DATETIME('now'))";
        //Create order in the orders table

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, customer_id);

            int createId = pstmt.executeUpdate(); //Get the generated order ID

            if (createId > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    System.out.println();
                    System.out.println("\uD83E\uDDFE Order id: " + orderId);

                    String orderSql = "INSERT INTO orders_products (order_id, product_id, " +
                            "quantity, unit_price) VALUES (?, ?, ?, ?)"; //Add products to orders_products
                    try (PreparedStatement orderProductStmt = conn.prepareStatement(orderSql)) {

                        for (OrderProduct orderProduct : orderProducts) {
                            orderProductStmt.setInt(1, orderId);
                            orderProductStmt.setInt(2, orderProduct.getProduct_id());
                            orderProductStmt.setInt(3, orderProduct.getQuantity());
                            orderProductStmt.setDouble(4, orderProduct.getUnit_price());

                            System.out.println("\uD83C\uDD94 Product ID: " + orderProduct.getProduct_id());
                            System.out.println("\uD83D\uDD22 Quantity: " + orderProduct.getQuantity());
                            System.out.println("\uD83D\uDD16 Price/unit: " + orderProduct.getUnit_price() + " SEK");
                            System.out.println();

                            orderProductStmt.executeUpdate();
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }


}
