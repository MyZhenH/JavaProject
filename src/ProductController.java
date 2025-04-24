import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {

    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("-------------\uD83D\uDED2 Product Menu ------------");
            System.out.println("1. \uD83D\uDCE6 Get all products");
            System.out.println("2. \uD83D\uDD0D Search product");
            System.out.println("3. \uD83D\uDD0D\uD83C\uDD94 Search product by category ID");
            System.out.println("4. \uD83D\uDD0D Search product by category name");
            System.out.println("5. \uD83D\uDEE0\uFE0F Update price");
            System.out.println("6. \uD83D\uDEE0\uFE0F Update stock quantity");
            System.out.println("7. ➕ Add a new product");
            System.out.println("8. \uD83C\uDFE0 Go to main menu");
            System.out.println("0. ❌ Exit");
            System.out.println("-----------------------------------------");
            System.out.println("Enter your choice: ");
            String select = scanner.nextLine();

            switch (select) {
                case "0":
                    SystemUtils.exit();
                    break;

                case "1":
                    getAllProducts();
                    break;

                case "2":
                    searchProductsByName(scanner);
                    break;

                case "3":
                    searchProductsByCategoryId(scanner);
                    break;

                case "4":
                    searchProductsByCategoryName(scanner);
                    break;

                case "5":
                    updatePrice(scanner);
                    break;

                case "6":
                    updateStockQuantity(scanner);
                    break;

                case "7":
                    addProduct(scanner);
                    break;

                case "8":
                    return;
            }
        }
    }

    private void addProduct(Scanner scanner) throws SQLException {
        System.out.println("Enter the name of the product:");
        String productName = scanner.nextLine();

        System.out.println("Enter the price:");
        double price = Double.parseDouble(scanner.nextLine());

        while (price <= 0) {
            System.out.println("Invalid price! (must be greater than 0)");
            System.out.println("Enter price:");
            price = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.println("Enter stock quantity:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        while (quantity <=0) {
            System.out.println("Invalid stock quantity! (must be greater than 0)");
            System.out.println("Enter stock quantity:");
            quantity = scanner.nextInt();
            scanner.nextLine();
        }
        productService.insertProduct(productName, price, quantity);
    }

    private void updateStockQuantity(Scanner scanner) throws SQLException {
        System.out.println("Enter product ID:");
        int productId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new stock quantity:");
        int newStockQuantity = scanner.nextInt();
        scanner.nextLine();

        while (newStockQuantity <=0) {
            System.out.println("Invalid stock quantity! (must be greater than 0)");
            System.out.println("Enter stock quantity:");
            newStockQuantity = scanner.nextInt();
            scanner.nextLine();
        }
        boolean success = productService.updateQuantity(productId, newStockQuantity);
        System.out.println(success ? "✅ Stock quantity updated" : "❌ Stock quantity could not be updated!");
    }

    private void updatePrice(Scanner scanner) throws SQLException {
        System.out.println("Enter product ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the new price:");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        while (newPrice <= 0) {
            System.out.println("Invalid price! (must be greater than 0)");
            System.out.println("Enter price:");
            newPrice = scanner.nextDouble();
            scanner.nextLine();
        }

        boolean updateSuccess = productService.updatePrice(id, newPrice);
        System.out.println(updateSuccess ? "✅ Price updated" : "❌ Price could not be updated!");
    }

    private void searchProductsByCategoryName(Scanner scanner) throws SQLException {
        System.out.println("Search by category:");
        String category = scanner.nextLine();
        productService.searchProductsByCategory(category);
    }

    private void searchProductsByCategoryId(Scanner scanner) throws SQLException {
        System.out.println("Enter category ID:");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        productService.searchProductByCategoryId(categoryId);
    }

    private void searchProductsByName(Scanner scanner) throws SQLException {
        System.out.println("Search for a product:");
        String name = scanner.nextLine();
        productService.searchProductByName(name);
    }

    private void getAllProducts() throws SQLException {
        ArrayList<Product> products = productService.getAllProduct();
        for (Product p : products) {
            System.out.println("\uD83D\uDCE6 Name: " + p.getName());
            System.out.println("\uD83D\uDD16 price: " + p.getPrice());
            System.out.println("\uD83D\uDD22 Stock quantity: " + p.getStock_quantity());
            System.out.println();
        }
    }
}
