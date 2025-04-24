import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {

    OrderService orderService = new OrderService();
    ProductService productService = new ProductService();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("--------\uD83D\uDCE6 Order Menu -------");
            System.out.println("1. \uD83E\uDDFE View order history");
            System.out.println("2. ➕ Place order");
            System.out.println("3. \uD83C\uDFE0 Go to main menu");
            System.out.println("0. ❌ Exit");
            System.out.println("-----------------------------");
            System.out.println("Enter your choice: ");
            String select = scanner.nextLine();

            switch (select) {
                case "0":
                    SystemUtils.exit();
                    break;

                case "1":
                    getOrderHistory(scanner);
                    break;

                case "2":
                    addOrder(scanner);
                    break;

                case "3":
                    return;

            }
        }
    }

    private void addOrder(Scanner scanner) throws SQLException {
        System.out.println("Enter customer ID:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        List<OrderProduct> orderProducts = new ArrayList<>();

        boolean addProduct = true; //Assume the customer wants to add a product
        while (addProduct) {
            boolean checkProductValid = false; //Check if the product is available

            while (!checkProductValid) {
                System.out.println("Enter the product ID:");
                int productId = scanner.nextInt();
                scanner.nextLine();

                //Check if there is enough stock
                int stockQuantity = productService.getStockQuantity(productId); //Number of products in stock
                int quantity = 0;
                boolean checkProductQuantity = false; //Check stock quantity

                if (stockQuantity <= 0) {
                    System.out.println("⚠\uFE0F The product is out of stock");
                    continue;
                }

                while (!checkProductQuantity) {
                    System.out.println("Enter quantity:");
                    quantity = scanner.nextInt();
                    scanner.nextLine();

                    //Validation - do not allow negative numbers
                    if (quantity <= 0) {
                        System.out.println("Invalid input! Must be greater than 0");

                        //Validation if the customer wants to add more products than what is available in stock
                    } else if (quantity > stockQuantity) {
                        System.out.println("⚠\uFE0F Not enough stock available! " + " Stock quantity: "
                                + stockQuantity);
                    } else {
                        double unitPrice = orderService.getUnitPrice(productId); //Product price
                        OrderProduct orderProduct = new OrderProduct(productId, quantity, unitPrice);
                        orderProducts.add(orderProduct);

                        checkProductQuantity = true;
                        checkProductValid = true;
                    }
                }

                //Ask if the customer wants to add more products
                String answer;
                do {
                    System.out.println("Do you want to add more products? (yes/no)");
                    answer = scanner.nextLine().toLowerCase();
                    if (!answer.equals("yes") && !answer.equals("no")) {
                        System.out.println("❌ Invalid answer, please enter yes or no");
                    }
                } while (!answer.equals("yes") && !answer.equals("no"));

                if (answer.equals("no")) {
                    addProduct = false; //Customer does not want to add more products

                    boolean orderSuccess = orderService.addProductOnOrder(customerId, orderProducts);//Place the order
                    orderService.updateStockQuantity(orderProducts); //Update stock quantity in the database

                    //Total price
                    double totalPrice = orderService.totalPrice(orderProducts); //Calculate total price
                    String totalPriceToString = orderService.totalPriceToString(totalPrice);
                    //convert total price to a string with two decimal places

                    System.out.println("\uD83D\uDCB0 Total price: " + totalPriceToString + " SEK");
                    System.out.println(orderSuccess ?
                            "✅ The order has been created" : " ❌ Order could not be created!");

                }
            }
        }
    }

    private void getOrderHistory(Scanner scanner) throws SQLException {
        System.out.println("Enter customer ID:");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        orderService.getOrderHistory(customerId);
    }

}
