import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

    public ArrayList<Customer> getAllCustomer() throws SQLException {
        return customerRepository.getAll();
    }

    public boolean insertCustomer(String name, String email, String password) throws SQLException{
        return customerRepository.insertCustomer(name, email, password);
    }

    public boolean updateEmail(int customer_id, String email) throws SQLException{
        return customerRepository.updateEmail(customer_id, email);
    }

    public boolean deleteCustomer(int id) throws SQLException{
        return customerRepository.deleteCustomer(id);
    }
}
