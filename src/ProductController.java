import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {

    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("------------\uD83D\uDED2 Produktmeny --------------");
            System.out.println("1. \uD83D\uDCE6 Hämtar alla produkter");
            System.out.println("2. \uD83D\uDD0D Sök produkt");
            System.out.println("3. \uD83D\uDD0D\uD83C\uDD94 Sök produkt efter kategori id");
            System.out.println("4. \uD83D\uDD0D Sök produkt efter kategorinamn");
            System.out.println("5. \uD83D\uDEE0\uFE0F Uppdatera pris");
            System.out.println("6. \uD83D\uDEE0\uFE0F Uppdatera lagersaldo");
            System.out.println("7. ➕ Lägg till en ny produkt");
            System.out.println("8. \uD83C\uDFE0 Gå till huvudmeny");
            System.out.println("0. ❌ Avsluta");
            System.out.println("---------------------------------------");
            System.out.println("Ange ditt val: ");
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
        System.out.println("Ange namnet på produkten:");
        String productName = scanner.nextLine();

        System.out.println("Ange priset:");
        double price = Double.parseDouble(scanner.nextLine());

        while (price <= 0) {
            System.out.println("Ogiltig pris! (får ej vara mindre än 1)");
            System.out.println("Ange pris:");
            price = scanner.nextDouble();
            scanner.nextLine();
        }

        System.out.println("Ange lagersaldo:");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        while (quantity <=0) {
            System.out.println("Ogiltig lagersaldo! (får ej vara mindre än 1)");
            System.out.println("Ange lagersaldo:");
            quantity = scanner.nextInt();
            scanner.nextLine();
        }
        productService.insertProduct(productName, price, quantity);
    }

    private void updateStockQuantity(Scanner scanner) throws SQLException {
        System.out.println("Ange produktens id:");
        int productId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ange den nya lagersaldo:");
        int newStockQuantity = scanner.nextInt();
        scanner.nextLine();

        while (newStockQuantity <=0) {
            System.out.println("Ogiltig lagersaldo! (får ej vara mindre än 1)");
            System.out.println("Ange lagersaldo:");
            newStockQuantity = scanner.nextInt();
            scanner.nextLine();
        }
        boolean success = productService.updateQuantity(productId, newStockQuantity);
        System.out.println(success ? "✅ Lagersaldo är uppdaterad" : "❌ Lagersaldo kunde ej uppdateras!");
    }

    private void updatePrice(Scanner scanner) throws SQLException {
        System.out.println("Ange produktens id:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ange den nya priset:");
        double newPrice = scanner.nextDouble();
        scanner.nextLine();

        while (newPrice <= 0) {
            System.out.println("Ogiltig pris! (får ej vara mindre än 1)");
            System.out.println("Ange pris:");
            newPrice = scanner.nextDouble();
            scanner.nextLine();
        }

        boolean updateSuccess = productService.updatePrice(id, newPrice);
        System.out.println(updateSuccess ? "✅ Priset är uppdaterad" : "❌ Priset kunde ej uppdateras!");
    }

    private void searchProductsByCategoryName(Scanner scanner) throws SQLException {
        System.out.println("Sök efter kategori:");
        String category = scanner.nextLine();
        productService.searchProductsByCategory(category);
    }

    private void searchProductsByCategoryId(Scanner scanner) throws SQLException {
        System.out.println("Ange kategori id:");
        int categoryId = scanner.nextInt();
        scanner.nextLine();
        productService.searchProductByCategoryId(categoryId);
    }

    private void searchProductsByName(Scanner scanner) throws SQLException {
        System.out.println("Sök efter en produkt:");
        String name = scanner.nextLine();
        productService.searchProductByName(name);
    }

    private void getAllProducts() throws SQLException {
        ArrayList<Product> products = productService.getAllProduct();
        for (Product p : products) {
            System.out.println("\uD83D\uDCE6 Namn: " + p.getName());
            System.out.println("\uD83D\uDD16 price: " + p.getPrice());
            System.out.println("\uD83D\uDD22 Stock quantity: " + p.getStock_quantity());
            System.out.println();
        }
    }
}
