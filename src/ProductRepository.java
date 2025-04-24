import java.sql.*;
import java.util.ArrayList;

public class ProductRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public ArrayList<Product> getAll() throws SQLException {

        ArrayList<Product> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) {

            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));
                products.add(product);
            }
        }
        return products;
    }

    public void searchProductByName(String name) throws SQLException{
        String sql = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setString(1, "%" + name + "%"); //wildcard

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));

                System.out.println("\uD83D\uDCE6 Produkt: " + product.getName());
                System.out.println("\uD83D\uDD16 Pris: " + product.getPrice());
                System.out.println("\uD83D\uDD22 Lagersaldo: " + product.getStock_quantity());
                System.out.println();
            }
            System.out.println();
        }
    }

        public void searchProductByCategoryId(int categoryId) throws SQLException{
        String sql = "SELECT * FROM products " +
                "INNER JOIN products_categories ON products.product_id = products_categories.product_id " +
                "INNER JOIN categories ON categories.category_id = products_categories.category_id " +
                "WHERE categories.category_id = ?";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, categoryId);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    Product product = new Product(rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity"));

                    System.out.println("\uD83C\uDD94 Produkt id: " + product.getProduct_id());
                    System.out.println("\uD83D\uDCE6 Namn: " + product.getName());
                    System.out.println("\uD83D\uDD16 Pris: " + product.getPrice());
                    System.out.println("\uD83D\uDD22 Lagersaldo: " + product.getStock_quantity());
                    System.out.println();
                }
                System.out.println();
            }
        }

        public void searchProductsByCategory(String categoryName) throws SQLException {
            String sql = "SELECT products.*, categories.name AS category_name FROM products  " +
                    "JOIN products_categories ON products.product_id = products_categories.product_id " +
                    "JOIN categories ON categories.category_id = products_categories.category_id " +
                    "WHERE categories.name LIKE ?";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, "%" + categoryName + "%");

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    String category = rs.getString("category_name");

                    Product product = new Product(rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity"));

                    System.out.println("Kategori: " + category);
                    System.out.println("\uD83D\uDCE6 Produkt: " + product.getName());
                    System.out.println("\uD83D\uDD16 Pris: " + product.getPrice() + " kr");
                    System.out.println("\uD83D\uDD22 Lagersaldo: " + product.getStock_quantity());
                    System.out.println();
                }
            }
        }

        //Uppdatera quantity manuellt
        public boolean updateQuantity(int productId, int stockQuantity) throws SQLException{
        String sql = "UPDATE products SET stock_quantity = ?  WHERE product_id = ?";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setInt(1, stockQuantity);
                pstmt.setInt(2, productId);

                return pstmt.executeUpdate() > 0;
            }
        }

        public boolean updatePrice(int productId, double price) throws SQLException{
        String sql = "UPDATE products SET price = ? WHERE product_id = ?";
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setDouble(1, price);
                pstmt.setInt(2, productId);

                return pstmt.executeUpdate() > 0;
            }
        }

        public void insertProduct(String name, double price, int stock_quantity)throws SQLException{
            String sql = "INSERT INTO products (name, price, stock_quantity) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)){

                pstmt.setString(1, name);
                pstmt.setDouble(2, price);
                pstmt.setInt(3, stock_quantity);

                pstmt.execute();
            }
        }

    public double getPrice(int product_id) throws SQLException {
        String sql = "SELECT price FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, product_id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("price");
            }
            return 0.0;
        }
    }

    public int getStockQuantity(int productId) throws SQLException {
        String sql = "SELECT stock_quantity FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock_quantity");
            }
            return 0;
        }
    }

        //Uppdatera lagersaldo
        public void updateStock(int quantity, int productId) throws SQLException {
            String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, quantity);
                pstmt.setInt(2, productId);

                pstmt.executeUpdate();
            }
        }

}







