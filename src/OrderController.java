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
            System.out.println("------------ \uD83D\uDCE6 Ordermeny --------------");
            System.out.println("1. \uD83E\uDDFE Se orderhistorik");
            System.out.println("2. ➕ Lägg order");
            System.out.println("3. \uD83C\uDFE0 Gå till huvudmeny");
            System.out.println("0. ❌ Avsluta");
            System.out.println("-------------------------------------");
            System.out.println("Ange ditt val: ");
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

    //Lägga order
    private void addOrder(Scanner scanner) throws SQLException {
        System.out.println("Ange kundens id:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        List<OrderProduct> orderProducts = new ArrayList<>();


        boolean addProduct = true; //Förutsätter att kunden vill lägga till produkt
        while (addProduct) {
            boolean checkProductValid = false; //Kontrollera produkten

            while (!checkProductValid) {
                System.out.println("Ange produktens id:");
                int productId = scanner.nextInt();
                scanner.nextLine();

                //Kontrollera om det finns tillräckligt med produkter
                int stockQuantity = productService.getStockQuantity(productId); //Antal produkter i lager
                int quantity = 0;
                boolean checkProductQuantity = false; // Kontrollera lagersaldo

                //Om lagersaldo är <=0
                if (stockQuantity <= 0) {
                    System.out.println("⚠\uFE0F Produkten är slut");
                    continue;
                }


                while (!checkProductQuantity) {
                    System.out.println("Ange antal:");
                    quantity = scanner.nextInt();
                    scanner.nextLine();

                    // Validering - inte skriva in negativ tal
                    if (quantity <= 0) {
                        System.out.println("ogiltig svar! får inte vara mindre än 1");

                        // Validering ifall kunden vill lägga fler produkt, än vad som finns i lager
                    } else if (quantity > stockQuantity) {
                        System.out.println("⚠\uFE0F Finns inte tillräckligt i lager!" + " Antal produkt i lager: "
                                + stockQuantity);
                    } else {
                        double unitPrice = orderService.getUnitPrice(productId); // Produktens pris
                        OrderProduct orderProduct = new OrderProduct(productId, quantity, unitPrice);
                        orderProducts.add(orderProduct);

                        checkProductQuantity = true;
                        checkProductValid = true;
                    }
                }


                //Fråga om kunden vill lägga till fler produkter
                String answer;
                do {
                    System.out.println("Vill du lägga till fler produkter? (ja/nej)");
                    answer = scanner.nextLine().toLowerCase();
                    if (!answer.equals("ja") && !answer.equals("nej")) {
                        System.out.println("❌ Ogiltigt svar, ange ja eller nej");
                    }
                } while (!answer.equals("ja") && !answer.equals("nej"));

                if (answer.equals("nej")) {
                    addProduct = false; //Kunden vill ej lägga till fler produkter

                    boolean orderSuccess = orderService.addProductOnOrder(customerId, orderProducts);//Ordern läggs till
                    orderService.updateStockQuantity(orderProducts); //Uppdatera lagersaldo i databasen


                    //Totalpriset
                    double totalPrice = orderService.totalPrice(orderProducts); //Räkna ihop totalpriset
                    String totalPriceToString = orderService.totalPriceToString(totalPrice); //Returnera/omvandla
                    // totalpriset till en sträng med två decimaler.

                    System.out.println("\uD83D\uDCB0 Totaltpris: " + totalPriceToString + " kr");
                    System.out.println(orderSuccess ? "✅ Ordern har skapats" : " ❌ Order kunde ej skapas!");

                }
            }
        }
    }

    private void getOrderHistory(Scanner scanner) throws SQLException {
        System.out.println("Ange kundens id:");
        int customerId = scanner.nextInt();
        scanner.nextLine();
        orderService.getOrderHistory(customerId);
    }

}
