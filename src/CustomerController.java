import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerController {

    CustomerService customerService = new CustomerService();
    CustomerRepository customerRepository = new CustomerRepository();

    public void runMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("--------- \uD83D\uDC64 Kundmeny ----------");
            System.out.println("1. \uD83D\uDC65 Hämta alla kunder");
            System.out.println("2. \uD83D\uDC64\uD83C\uDD94 Hämta en kund efter id");
            System.out.println("3. ➕ Lägg in en ny kund");
            System.out.println("4. \uD83D\uDCE7 Uppdatera email");
            System.out.println("5. \uD83D\uDDD1\uFE0F Radera kund");
            System.out.println("6. \uD83C\uDFE0 Gå till huvudmeny");
            System.out.println("0. ❌ Avsluta");
            System.out.println("------------------------------------------");
            System.out.println("Ange ditt val: ");
            String select = scanner.nextLine();

            switch (select) {
                case "0":
                    SystemUtils.exit();
                    break;

                case "1":
                    getAllCustomer();
                    break;

                case "2":
                    getCustomerById(scanner);
                    break;

                case "3":
                    addCustomer(scanner);
                    break;

                case "4":
                    updateCustomerEmail(scanner);
                    break;

                case "5":
                    deleteCustomer(scanner);
                    break;

                case "6":
                    return;

            }
        }
    }

    private void deleteCustomer(Scanner scanner) throws SQLException {
        System.out.println("Ange kund id som ska raderas:");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();
        boolean deleteSuccess = customerService.deleteCustomer(idToDelete);
        System.out.println(deleteSuccess ? "✅ Kund raderad" : " ❌ Kund hittades ej!");
        System.out.println();
    }

    private void updateCustomerEmail(Scanner scanner) throws SQLException {
        System.out.println("Ange id på den kund som ska updateras:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Ange ny email:");
        String newEmail = scanner.nextLine();

        //Validering på att email måste innehålla @
        while (!newEmail.contains("@")) {
            System.out.println("Ogiltig email!");
            System.out.println("Ange ny email:");
            newEmail = scanner.nextLine();
        }

        boolean success = customerService.updateEmail(customerId, newEmail);
        System.out.println(success ? " ✅ Kundens email är uppdaterad" : " ❌ Kund hittades ej!");
        System.out.println();

    }

    //Lägga till en kund
    private void addCustomer(Scanner scanner) throws SQLException {
        System.out.println("Ange ett namn:");
        String name = scanner.nextLine();

        System.out.println("Ange en email:");
        String email = scanner.nextLine();

        //Validering på att email måste innehålla @
        while (!email.contains("@")) {
            System.out.println("Ogiltig email!");
            System.out.println("Ange ny email:");
            email = scanner.nextLine();
        }

        System.out.println("Ange ett lösenord:");
        String password = scanner.nextLine();

        boolean addSuccess = customerService.insertCustomer(name, email, password);
        System.out.println(addSuccess ? " ✅ Kund tillagd" : " ❌ Ett fel uppstod");
        System.out.println();
    }

    private void getCustomerById(Scanner scanner) throws SQLException {
        System.out.println("Ange ID:");
        int id = scanner.nextInt();
        scanner.nextLine();
        Customer customer = customerRepository.getCustomerById(id);
        System.out.println("👤 " + customer.getName());
        System.out.println("\uD83D\uDCE7 " + customer.getEmail());
        System.out.println();
    }

    private void getAllCustomer() throws SQLException {
        ArrayList<Customer> customers = customerService.getAllCustomer();
        for (Customer c : customers) {
            System.out.println(" \uD83C\uDD94  KundId: " + c.getCustomerId());
            System.out.println(" \uD83D\uDC64 Namn: " + c.getName());
            System.out.println(" \uD83D\uDCE7 Email: " + c.getEmail());
            System.out.println();
        }
    }
}
