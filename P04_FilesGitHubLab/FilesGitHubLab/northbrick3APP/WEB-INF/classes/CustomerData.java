import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerData {
    String CustomerID;
    String CompanyName;
	String City;
    
    CustomerData (String CustomerID, String CompanyName, String City) {
        this.CustomerID = CustomerID;
        this.CompanyName = CompanyName;
        this.City = City;
    }
	
    CustomerData (String CompanyName, String country) {
        this.CompanyName = CompanyName;
        this.City = country;
    }	
		
	
    public static Vector<CustomerData> getCustomerList(Connection connection, String country) {
        Vector<CustomerData> vec = new Vector<CustomerData>();
        String sql = "SELECT CustomerID, CompanyName, City FROM Customers WHERE Country=?";
        System.out.println("getCustomerList: " + sql + country);
		
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, country);
            ResultSet result = pstmt.executeQuery();
			
            while(result.next()) {
                CustomerData customer = new CustomerData(
                    result.getString("CustomerID"),
                    result.getString("CompanyName"),
                    result.getString("City")
                );
				
                vec.addElement(customer);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getCustomerList: " + sql + " Exception: " + e);
        }
        return vec;
    }
	
/*     public static CustomerData getCustomer(Connection connection, String id) {
        String sql = "SELECT EmployeeID, FirstName, LastName, Title FROM Employees";
        sql += " WHERE EmployeeID=?";
        System.out.println("getEmployee: " + sql);
        EmployeeData employee = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                employee = new EmployeeData(
					Integer.parseInt(result.getString("EmployeeID")),
                    result.getString("FirstName"),
                    result.getString("LastName"),
					result.getString("Title")
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getEmployee: " + sql + " Exception: " + e);
        }
        return employee;
    } */

    public static int insertCustomer(Connection connection, CustomerData customer) {
        String sql ="INSERT INTO Customers (CompanyName, City) "
            + "VALUES (?, ?, ?)";
        System.out.println("updateCustomer: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,customer.CompanyName);
			stmtUpdate.setString(2,customer.City);
			
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insertCustomer: " + sql + " Exception: " + e);
        }
        return n;
    }
}
