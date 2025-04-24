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
            System.out.println("--------- \uD83D\uDC64 Customer Menu ----------");
            System.out.println("1. \uD83D\uDC65 Get all customers");
            System.out.println("2. \uD83D\uDC64\uD83C\uDD94 Get a customer by ID");
            System.out.println("3. ➕ add a new customer");
            System.out.println("4. \uD83D\uDCE7 Update email");
            System.out.println("5. \uD83D\uDDD1\uFE0F Delete customer");
            System.out.println("6. \uD83C\uDFE0 Go to main menu");
            System.out.println("0. ❌ Exit");
            System.out.println("-------------------------------------");
            System.out.println("Enter your choice: ");
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
        System.out.println("Enter the customer ID to delete:");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();
        boolean deleteSuccess = customerService.deleteCustomer(idToDelete);
        System.out.println(deleteSuccess ? "✅ Customer deleted" : " ❌ Customer not found!");
        System.out.println();
    }

    private void updateCustomerEmail(Scanner scanner) throws SQLException {
        System.out.println("Enter the ID of the customer to update:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the new email:");
        String newEmail = scanner.nextLine();

        //Validation to ensure the email contains '@' and "."
        while (!newEmail.contains("@") || !newEmail.contains(".")) {
            System.out.println("Invalid email!");
            System.out.println("Enter the new email:");
            newEmail = scanner.nextLine();
        }

        boolean success = customerService.updateEmail(customerId, newEmail);
        System.out.println(success ? " ✅ Customer's email is updated" : " ❌ Customer not found!");
        System.out.println();

    }

    private void addCustomer(Scanner scanner) throws SQLException {
        System.out.println("Enter a name:");
        String name = scanner.nextLine();

        System.out.println("Enter an email:");
        String email = scanner.nextLine();

        //Validation to ensure the email contains '@' and "."
        while (!email.contains("@") || !email.contains(".")) {
            System.out.println("Ogiltig email!");
            System.out.println("Ange ny email:");
            email = scanner.nextLine();
        }

        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        boolean addSuccess = customerService.insertCustomer(name, email, password);
        System.out.println(addSuccess ? " ✅ Customer added" : " ❌ An error occurred");
        System.out.println();
    }

    private void getCustomerById(Scanner scanner) throws SQLException {
        System.out.println("Enter ID:");
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
            System.out.println(" \uD83C\uDD94 CustomerID: " + c.getCustomerId());
            System.out.println(" \uD83D\uDC64 Name: " + c.getName());
            System.out.println(" \uD83D\uDCE7 Email: " + c.getEmail());
            System.out.println();
        }
    }
}
