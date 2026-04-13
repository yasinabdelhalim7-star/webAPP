import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CustomerInsert extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        String country = req.getParameter("country");
        CustomerData customer = new CustomerData(
                    req.getParameter("CompanyName"),
                    req.getParameter("City")
        );
        int n = CustomerData.insertCustomer(connection, customer);

        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		
        toClient.println(Utils.header("Customer updated"));
        Customers.PrintCustomer(toClient, connection, country);
        toClient.println(Utils.footer("Employee updated"));
		
        
        toClient.close();
    }
}