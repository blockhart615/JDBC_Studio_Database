/*  Brett Lockhart, James Dinh
    JDBC Project    
    10/19/2015
*/
package studiodatabase;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Brett and James
 */
public class StudioDatabase {

    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static final String DB_URL = "jdbc:derby://localhost:1527/AlbumProduction";
    static final String USER = "Brett";
    static final String PASS = "hello";

    public static void main(String[] args) {
        //Create Connection and Statement Objects
        Connection conn;
        Statement stmt;
        Scanner input = new Scanner(System.in);
        String keepGoing = "y";

        try {
            // Load the driver and connect to the database
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Create a menu for user
            while (keepGoing.toLowerCase().charAt(0) == 'y') {

                System.out.println("What would you like to do?"
                        + "\n 1) List all album Titles"
                        + "\n 2) List data from a specific album"
                        + "\n 3) Insert a new Album"
                        + "\n 4) Insert a new studio and update all albums published"
                        + " by one studio to be published by the new studio"
                        + "\n 5) Remove an album");
                int choice = input.nextInt();
                input.nextLine();

                stmt = conn.createStatement();
                String sql;   // will contain the SQL commands
                ResultSet rs;   // contains the results of the queries
                // The data fields of the tables
                String albumTitle = "", dateRecorded = "",
                        length = "", genre = "", groupName = "";
                String studioName = "", studioAddress = "", studioOwner = "",
                        studioPhone = "";
                // The following 5 variables will be the SQL used to
                // validate input, in the case where more than one album
                // has the same name, and therefore, needs the group name
                String albumCountSQL = "SELECT count(album_title) AS"
                        + "\"Number of Albums\""
                        + "FROM Album Where album_title = ?";
                String albumGroupNameSQL = "SELECT group_name "
                        + "FROM Album Where album_title = ?";
                String addGroupNameSQL = " AND group_name = ?";
                PreparedStatement pstmt;
                int numOfAlbums;
                int counter;

                switch (choice) {
                    case 1:
                        //list all album titles
                        counter = 0;
                        sql = "SELECT album_title FROM Album";
                        rs = stmt.executeQuery(sql);

                        //get all album titles
                        while (rs.next()) {
                            if (counter % 5 == 0 && counter != 0) {
                                System.out.println();
                            }
                            //Retrieve by column name
                            albumTitle = rs.getString("ALBUM_TITLE");
                            //Display values
                            System.out.print(albumTitle + "\t");
                            counter++;
                        }
                        System.out.println();
                        break;

                    case 2:
                        //list all data of a specific album
                        System.out.print("Which album do you want data for? ");
                        albumTitle = input.nextLine();
                        sql = "SELECT * FROM Album WHERE album_title = ?";

                        // Check to see if more than one album with the same
                        // name exists
                        pstmt = conn.prepareStatement(albumCountSQL);
                        pstmt.setString(1, albumTitle);

                        // Use a query to find the number of albums
                        rs = pstmt.executeQuery();
                        numOfAlbums = 0;
                        if (rs.next()) 
                            numOfAlbums = rs.getInt("Number of Albums");                        

                        // if more than one album exists with the same name
                        // Ask the user which group's album to delete
                        if (numOfAlbums > 1) {
                            sql += addGroupNameSQL;
                            pstmt = conn.prepareStatement(albumGroupNameSQL);
                            pstmt.setString(1, albumTitle);
                            System.out.println("Which group's album would you like to view: ");
                            rs = pstmt.executeQuery();

                            // Display the groups that have produced
                            // an album with the specified name
                            while (rs.next()) {
                                System.out.print(rs.getString("group_name") + "\t");
                            }
                            System.out.println(); // makes it look nicer
                            groupName = input.nextLine();
                        }

                        // Prepare the query
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, albumTitle);

                        // If there are  duplicate album names,
                        // group_name will differentiate
                        if (numOfAlbums > 1) 
                            pstmt.setString(2, groupName);
                        
                        // Retrieve data fields
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            albumTitle = rs.getString("ALBUM_TITLE");
                            dateRecorded = rs.getString("DATE_RECORDED");
                            length = rs.getString("LENGTH");
                            genre = rs.getString("GENRE");
                            studioName = rs.getString("STUDIO_NAME");
                            groupName = rs.getString("GROUP_NAME");
                        }
                        
                        // Output the data fields
                        System.out.println("Album Title: " + albumTitle
                                + "\nDate Recorded: " + dateRecorded
                                + "\nLength: " + length
                                + "\nGenere: " + genre
                                + "\nStudio Name: " + studioName
                                + "\nGroup Name: " + groupName);
                        break;

                    case 3:
                        //insert a new album
                        System.out.print("Enter Album Title: ");
                        albumTitle = input.nextLine();
                        System.out.print("Enter the date recorded (yyyy-mm-dd): ");
                        dateRecorded = input.nextLine();
                        System.out.print("Enter the album length: (hh:mm:ss)");
                        length = input.nextLine();
                        System.out.print("Enter the Genre: ");
                        genre = input.nextLine();
                        System.out.print("Enter Studio name where album was recorded: ");
                        studioName = input.nextLine();
                        System.out.print("Enter the group name: ");
                        groupName = input.nextLine();
                        sql = "INSERT INTO Album VALUES ('"
                                + albumTitle + "', '"
                                + dateRecorded + "', '"
                                + length + "', '"
                                + genre + "', '"
                                + studioName + "', '"
                                + groupName + "')";
                        stmt.executeUpdate(sql);
                        break;

                    case 4:
                        // insert a new studio
                        System.out.println("Enter studio's name: ");
                        studioName = input.nextLine();
                        System.out.print("Enter the studio's address: ");
                        studioAddress = input.nextLine();
                        System.out.print("Enter the studio's owner: ");
                        studioOwner = input.nextLine();
                        System.out.print("Enter the studio's phone number: ");
                        studioPhone = input.nextLine();
                        sql = "INSERT INTO RecordingStudio VALUES ('"
                                + studioName + "', '"
                                + studioAddress + "', '"
                                + studioOwner + "', '"
                                + studioPhone + "')";
                        stmt.executeUpdate(sql);

                        //Asks if user wants to transfer albums
                        System.out.println("You can transfer albums that were"
                                + " recorded at one studio to your new studio. "
                                + "Would you like to make the transfer? (y/n)");
                     
                        //update all albums published by one studio 
                        //to be published by the new studio
                        if (input.nextLine().toLowerCase().charAt(0) == 'y') {
                            System.out.println("Which studio's data would you"
                                    + " like transferred: ");
                            
                            // Display the studios excluding the newest one
                            sql = "SELECT studio_name FROM RecordingStudio"
                                    + " WHERE NOT studio_name = '"
                                    + studioName + "'";
                            rs = stmt.executeQuery(sql);
                            counter = 0;
                            while (rs.next()) {
                                if (counter % 5 == 0 && counter != 0)
                                    System.out.println();
                                System.out.print(rs.getString("studio_name") + "\t");
                                counter++;
                            }
                            
                            // Receive user input
                            String studioTransfer = input.nextLine();

                            // Change SQL for the update
                            sql = "UPDATE Album SET studio_name = '" + studioName + "' "
                                    + "WHERE studio_name = '" + studioTransfer + "'";
                            stmt.executeUpdate(sql);
                        }
                        break;

                    case 5:
                        // Remove an album from the database
                        sql = "DELETE FROM Album WHERE album_title = ?";

                        System.out.println("Which album would you like removed: ");
                        albumTitle = input.nextLine();

                        // Check to see if more than one album with the same
                        // name exists
                        pstmt = conn.prepareStatement(albumCountSQL);
                        pstmt.setString(1, albumTitle);
                        rs = pstmt.executeQuery();

                        // Use a query to find the number of albums
                        numOfAlbums = 0;
                        if (rs.next()) 
                            numOfAlbums = rs.getInt("Number of Albums");
                        
                        // if more than one album exists with the same name
                        // Ask the user which group's album to delete
                        if (numOfAlbums > 1) {
                            sql += addGroupNameSQL;
                            pstmt = conn.prepareStatement(albumGroupNameSQL);
                            pstmt.setString(1, albumTitle);
                            System.out.println("Which group's album would you "
                                    + "like deleted: ");
                            rs = pstmt.executeQuery();

                            // Display the list of groups that have produced
                            // an album with the specified name
                            counter = 0;
                            while (rs.next()) {
                                if (counter % 5 == 0 && counter != 0) 
                                    System.out.println();
                                System.out.print(rs.getString("group_name") + "\t");
                                counter++;
                            }
                            System.out.println();
                            groupName = input.nextLine();
                        }

                        // Prepare the statement for DELETE
                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, albumTitle);

                        // If there are  duplicate album names,
                        // group_name will differentiate
                        if (numOfAlbums > 1)
                            pstmt.setString(2, groupName);

                        // if the DELETE returns 1, that means 1 row was
                        // affected (in this case, deleted) which means
                        // it went as expected
                        // If 0 is returned, no rows were deleted
                        int numRowsDeleted = pstmt.executeUpdate();
                        if (numRowsDeleted == 0) 
                            System.out.println("Album does not exist");
                        else 
                            System.out.println("Album removed");
                        
                        break;
                }

                System.out.print("Would you like to continue? ");
                keepGoing = input.nextLine();
            }

        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.toString());
        } catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }
    }
}
