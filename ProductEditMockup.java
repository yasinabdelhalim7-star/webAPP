import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductData {
    String    productId;
    String productName;
    int    supplierId;
    String companyName;
    float    unitPrice;
	
	//------New---------------
	// Add new attributes in the class
	int categoryId;
	int quantityPerUnit;
	int unitsInStock;
	int unitsOnOrder;
	

	//-------------------------------------------
	
	
    ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
    }
	
	//------New---------------
	// New constructor with the new fields	
	  ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice, int catId, int quantity, int stock, int order) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
		
		this.categoryId    = catId;
        this.quantityPerUnit  = quantity;
        this.unitsInStock   = stock;
		this.unitsOnOrder =order;
        
			
    }
	
  //-----------------------------------------------------------------------------------------------
	
    public static Vector<ProductData> getProductList(Connection connection) {
        Vector<ProductData> vec = new Vector<ProductData>();
        String sql = "Select ProductId, ProductName, Products.SupplierId as SupplierId, CompanyName, UnitPrice FROM Products, Suppliers";
        sql += " WHERE Products.SupplierId = Suppliers.SupplierId";
        System.out.println("getProductList: " + sql);
        try {
            Statement statement=connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                ProductData product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    result.getString("CompanyName"),
                    Float.parseFloat(result.getString("UnitPrice"))
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    public static Vector<ProductData> getCategoryProductList(Connection connection, String id) {
        Vector<ProductData> vec = new Vector<ProductData>();
        String sql = "Select ProductId, ProductName, Products.SupplierId as SupplierId, CompanyName, UnitPrice FROM Products, Suppliers";
        sql += " WHERE Products.SupplierId = Suppliers.SupplierId AND CategoryID=?";
        System.out.println("getProductList: " + sql);
        try {
            PreparedStatement pstmt=connection.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                ProductData product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    result.getString("CompanyName"),
                    Float.parseFloat(result.getString("UnitPrice"))
                );
                vec.addElement(product);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProductList: " + sql + " Exception: " + e);
        }
        return vec;
    }
	
	
	
	
	
    public static ProductData getProduct(Connection connection, String id) {
        String sql = "Select ProductId, ProductName, SupplierId, UnitPrice FROM Products";
        sql += " WHERE ProductId=?";
        System.out.println("getProduct: " + sql);
        ProductData product = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    null,
                    Float.parseFloat(result.getString("UnitPrice"))
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProduct: " + sql + " Exception: " + e);
        }
        return product;
    }
    
	//---------------------New----------------------------------
	// New method to read all the fields from the database. I have changed the name to keep both methods
	
    public static ProductData getProduct2(Connection connection, String id) {
        String sql = "Select * FROM Products";
        sql += " WHERE ProductId=?";
        System.out.println("getProduct: " + sql);
        ProductData product = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                product = new ProductData(
                    result.getString("ProductId"),
                    result.getString("ProductName"),
                    Integer.parseInt(result.getString("SupplierId")),
                    null,
                    Float.parseFloat(result.getString("UnitPrice")),
					
					Integer.parseInt(result.getString("CategoryId")),
					Integer.parseInt(result.getString("QuantityPerUnit")), 
					Integer.parseInt(result.getString("UnitsInStock")),
					Integer.parseInt(result.getString("UnitsOnOrder"))
					
									
								

                );
				
				
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProduct: " + sql + " Exception: " + e);
        }
        return product;
    }
    
	//---------------------------------------------------------------------------------
	
	public static int updateProduct(Connection connection, ProductData product) {
        String sql ="UPDATE Products "
            + "SET ProductName = ?, SupplierId = ?, UnitPrice = ?"
            + " WHERE ProductId = ?";
        System.out.println("updateProduct: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,product.productName);
            stmtUpdate.setInt(2,product.supplierId);
            stmtUpdate.setFloat(3,product.unitPrice);
            stmtUpdate.setString(4,product.productId);
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateProduct: " + sql + " Exception: " + e);
        }
        return n;
    }
	
	// New-----------------------------------------------------
		public static int updateProduct2(Connection connection, ProductData product) {
        String sql ="UPDATE Products "
            + "SET ProductName = ?, SupplierId = ?, UnitPrice = ?, CategoryId=?, QuantityPerUnit=?,UnitsInStock=?, UnitsOnOrder=?"
            + " WHERE ProductId = ?";
        System.out.println("updateProduct: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,product.productName);
            stmtUpdate.setInt(2,product.supplierId);
            stmtUpdate.setFloat(3,product.unitPrice);
     
			
			stmtUpdate.setInt(4,product.categoryId);
            stmtUpdate.setInt(5,product.quantityPerUnit);
            stmtUpdate.setInt(6,product.unitsInStock);
            stmtUpdate.setInt(7,product.unitsOnOrder);
			
			
			stmtUpdate.setString(8,product.productId);
            
			
			n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateProduct: " + sql + " Exception: " + e);
        }
        return n;
    }
	//--------------------------------------------------------------------------------------
}
