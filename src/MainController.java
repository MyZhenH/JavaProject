import java.sql.SQLException;
import java.util.Scanner;

public class MainController {

    public void runMenu() throws SQLException{
        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println();
            System.out.println("------\uD83C\uDFE0 Main Menu ------");
            System.out.println("1. \uD83D\uDC64 Customer");
            System.out.println("2. \uD83D\uDCE6 Products");
            System.out.println("3. \uD83E\uDDFE Orders");
            System.out.println("0. ❌ Exit");
            System.out.println("-------------------------");
            System.out.println("Enter your choice: ");
            String select = scanner.nextLine();

            switch(select){
                case "0":
                    System.exit(0);
                    break;

                case "1":
                    CustomerController customerController = new CustomerController();
                    customerController.runMenu();
                    break;

                case "2":
                    ProductController productController = new ProductController();
                    productController.runMenu();
                    break;

                case "3":
                    OrderController orderController = new OrderController();
                    orderController.runMenu();
                    break;

            }
        }
    }
}
