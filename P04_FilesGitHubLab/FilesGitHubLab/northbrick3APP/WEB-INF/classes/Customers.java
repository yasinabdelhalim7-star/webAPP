import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class Customers extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
	}
	
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		String idStr = req.getParameter("country");
		
        toClient.println(Utils.header("List of Customers of " + idStr));
		
		PrintCustomer(toClient, connection, idStr);
		
        toClient.println(Utils.footer("List of Employees of " + idStr));
        toClient.close();
	}
		
	public static void PrintCustomer(PrintWriter toClient, Connection connection, String country) {
		
		//toClient.println("<title>Developed by Marribas<title>");
		toClient.println("<table border='1' id='clientes'>");
		toClient.println("<tr>");
		toClient.println("<td>Id</td>");
		toClient.println("<td>CompanyName</td>");
		toClient.println("<td>City</td>");
		Vector<CustomerData> customerList;
		customerList = CustomerData.getCustomerList(connection, country);
		for (int i=0; i<customerList.size(); i++){
			
			CustomerData customer = customerList.elementAt(i);
			System.out.println("aqui");
			toClient.println("<tr>");
			toClient.println("<td>" + customer.CustomerID + " </td>");
			toClient.println("<td>" + customer.CompanyName + " </td>");
			toClient.println("<td>" + customer.City + " </td>");
			toClient.println("<td><a href='CustomerInsert?id=" + customer.CustomerID + "'>CustomerEdit</a></td>");
            toClient.println("</tr>");
        }
		toClient.println("</table>");
		
		
		
		toClient.println("<form action='CustomerInsert' method='GET'>");
	   
        toClient.println("<input name='country' type='hidden' value='" + country + "'></input>");
		
		toClient.println("<table border='1' id='cienteInsert'>");
		toClient.println("</table>");
		
	    toClient.println("</form>");
        
		toClient.println("<script src=customers.js></script>");
	}
}