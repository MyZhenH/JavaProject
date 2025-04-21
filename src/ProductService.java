import java.sql.SQLException;
import java.util.ArrayList;

public class ProductService {
    ProductRepository productRepository = new ProductRepository();

    public ArrayList<Product> getAllProduct() throws SQLException {
        return productRepository.getAll();
    }

    public void searchProductByName(String name) throws SQLException{
        productRepository.searchProductByName(name);
    }

    public void searchProductByCategoryId(int categoryId) throws SQLException{
        productRepository.searchProductByCategoryId(categoryId);
    }

    public boolean updateQuantity(int productId, int stockQuantity) throws SQLException{
       return productRepository.updateQuantity(productId, stockQuantity);
    }

    public boolean updatePrice(int productId, double price) throws SQLException{
        return productRepository.updatePrice(productId, price);
    }

    public void insertProduct(String name, double price, int stock_quantity) throws SQLException{
        productRepository.insertProduct(name,price,stock_quantity);
    }

    public void searchProductsByCategory(String categoryName) throws SQLException{
        productRepository.searchProductsByCategory(categoryName);
    }

    public int getStockQuantity(int productId)throws SQLException{
        return productRepository.getStockQuantity(productId);
    }


}
