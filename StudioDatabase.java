/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studiodatabase;

import java.sql.*;

/**
 *
 * @author Brett
 */
public class StudioDatabase {

    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/AlbumProduction";
    static final String USER = "Brett";
    static final String PASS = "hello";

    public static void main(String[] args) {
        // TODO code application logic here
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);
            
            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            
            
            
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }

  
    }
}


/*

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
  



    try{
      //STEP 2: Register JDBC driver
      Class.forName("org.apache.derby.jdbc.ClientDriver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);



      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT au_id, au_fname, au_lname, phone FROM Authors";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         String id  = rs.getString("au_id");
         String phone = rs.getString("phone");
         String first = rs.getString("au_fname");
         String last = rs.getString("au_lname");

         //Display values
         System.out.print("ID: " + id);
         System.out.print(", First: " + first);
         System.out.print(", Last: " + last);
         System.out.println(", Phone: " + phone);
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end FirstExample
*/
