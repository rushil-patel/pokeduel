package pokeduelserver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//A class to manage a connection to the database
public class DBConnection {
    
    //Get a connection to the database
    public static Connection getConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        
        String dbUser = "root";
        String dbPass = "seongbo";
        String host = "localhost";
        String dbName = "pokedb";
        
        String dbUrl = String.format("jdbc:mysql://%s/%s?autoReconnect=true",
                host, dbName);
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
    
    //Close the connection
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        }
        catch (Exception ex) {
            System.err.println("Unable to close DBConnection.");
            System.exit(1);
        }
    }
    
    public static void main(String[] args) throws Exception {
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet results = null;
        
        String query = "SELECT * FROM PokemonStats";
        
        try {
            //Get a statement from the connection
            statement = conn.createStatement();
            
            //Execute the query
            results = statement.executeQuery(query);
            
            while (results.next()) {
                String last = results.getString(1);
                String first = results.getString("pId");
                int room = results.getInt(3);
                
                System.out.println(String.format("%s, %s -- %d", 
                        last, first, room));
            }
        }
        catch (SQLException sqlEx) {
            System.err.println("Error doing query: " + sqlEx);
            sqlEx.printStackTrace(System.err);
        }
        finally {
            try {
                if (results != null) {
                    results.close();
                    results = null;
                }
                if (statement!= null) {
                    statement.close();
                    statement = null;
                }
            }
            catch (Exception ex) {
                System.err.println("Error closing query: " + ex);
                ex.printStackTrace(System.err);
            }
            
            close(conn);
        }
    }
}
