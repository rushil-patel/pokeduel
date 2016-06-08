package connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import player.Player;
import pokemon.Pokemon;

//A class to manage a connection to the database
public class DBConnection {

    //Get a connection to the database
    public static Connection getConnection() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");

        String dbUser = "root";
        String dbPass = "admin";
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
        } catch (Exception ex) {
            System.err.println("Unable to close DBConnection.");
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {
        List<Pokemon> pokeList = new ArrayList<Pokemon>();
        pokeList = getAllPokeStatsAndMult();
        /*createUser("Rushil");
        createUser("Lion");
        checkUser("Rushil");
        checkUser("Seong");
        checkUser("Tiger");
        Player player = getUser("Seong");*/
    }

    /*Assumes the player already exists*/
    public static Player getUser(String username) throws Exception {
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet results = null;
        String query;
        Player player = null;
        
        query = "SELECT *\n"
                + "FROM Users U\n"
                + "WHERE U.username = '" + username + "'";
        
        try {
            statement = conn.createStatement();
            results = statement.executeQuery(query);
            while (results.next()) {
                player = new Player(results.getInt("id"), results.getString("username"));
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
              if (statement != null) {
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
        return player;
    }
    
    public static void createUser(String username) throws Exception {
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet results = null;
        String query;
        Player player;
        
        query = "INSERT INTO Users VALUES\n"
                + "   (NULL, '" + username + "', 0, 0)\n";
        
        try {
            statement = conn.createStatement();
            results = statement.executeQuery(query);
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
              if (statement != null) {
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
    
    public static boolean checkUser(String username) throws Exception {
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet results = null;
        String query;
        Boolean bool = false;
        
        query = "SELECT *\n"
                + "FROM Users U\n"
                + "WHERE U.username = '" + username + "'";
        
        try {
            statement = conn.createStatement();
            results = statement.executeQuery(query);
            
            if (!results.next()) {
                bool = false; /*empty set*/
            }
            else {
                bool = true; /*not empty set*/
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
              if (statement != null) {
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
        return bool;
    }
    
    public static List<Pokemon> getAllPokeStatsAndMult() throws Exception {
        /*Connectors*/
        Connection conn = getConnection();
        Statement statement = null;
        ResultSet results = null;
        
        /*Pokemon list, Pokemon*/
        List<Pokemon> pokemonList = new ArrayList<Pokemon>();
        Pokemon pokeAdd;
        
        /*Strings, arrays, scanners*/
        String query1, query2, mult, promptUser;
        String[] word;
        Scanner sc = new Scanner(System.in);
        int[] stat, type, resistance = new int[17];

        query1 = "SELECT\n"
                + "   P.id, P.name, PC.cost,\n"
                + "   PS.hp, PS.att, PS.def, PS.sAtt, PS.sDef, PS.speed, P.sprite,\n"
                + "   type1, type2, numTypes\n"
                + "FROM Pokemon P\n"
                + "   JOIN PokemonStats PS ON P.id = PS.pId\n"
                + "   JOIN \n"
                + "      (SELECT *\n"
                + "      FROM  (SELECT\n"
                + "               PT1.pId,\n"
                + "               T1.id AS type1,\n"
                + "               T1.name AS typeName1,\n"
                + "               T2.id AS type2,\n"
                + "               T2.name AS typeName2,\n"
                + "               '2' AS numTypes\n"
                + "            FROM PokemonType PT1\n"
                + "               JOIN PokemonType PT2 ON PT1.pId = PT2.pId\n"
                + "               JOIN Type T1 ON PT1.typeId = T1.id\n"
                + "               JOIN Type T2 ON PT2.typeId = T2.id\n"
                + "            WHERE T1.id < T2.id\n"
                + "            ORDER BY PT1.pId) X\n"
                + "      UNION ALL\n"
                + "         (SELECT\n"
                + "            PT1.pId,\n"
                + "            T1.id AS type1,\n"
                + "            T1.name AS typeName1,\n"
                + "            0 AS type2,\n"
                + "            0 AS typeName2,\n"
                + "            '1' AS numTypes\n"
                + "         FROM PokemonType PT1\n"
                + "            JOIN PokemonType PT2 ON PT1.pId = PT2.pId\n"
                + "            JOIN Type T1 ON PT1.typeId = T1.id\n"
                + "            JOIN Type T2 ON PT2.typeId = T2.id\n"
                + "         GROUP BY PT1.pId\n"
                + "         HAVING COUNT(*) = 1\n"
                + "         ORDER BY PT1.pId)\n"
                + "      ORDER BY pId) Y ON P.id = Y.pId\n"
                + "   JOIN PokemonCost PC ON P.id = PC.pId\n";

        query2 = "SELECT DISTINCT P.id, P.name,\n"
                + "GROUP_CONCAT(R.mult ORDER BY R.typeId) AS res\n"
                + "FROM Pokemon P\n"
                + "   JOIN Resistance R ON P.id = R.pId\n"
                + "GROUP BY P.id\n";
        
        try {
            //Get a statement from the connection
            //Execute the query
            statement = conn.createStatement();
            results = statement.executeQuery(query1);
            
            while (results.next()) {
                stat = new int[] {results.getInt("hp"), results.getInt("att"),
                   results.getInt("def"), results.getInt("sAtt"), 
                   results.getInt("sDef"), results.getInt("speed")};
                type = new int[] {results.getInt("type1"), results.getInt("type2")};
                pokeAdd = new Pokemon(results.getInt("id"), 
                        results.getString("name"), results.getInt("cost"), stat,
                        results.getString("sprite"), null, type, 
                        results.getInt("numTypes"), true);
                pokemonList.add(pokeAdd);
            }
            
            statement = conn.createStatement();
            results = statement.executeQuery(query2);

            while (results.next()) {
                mult = results.getString("res");
                word = mult.split(",");
                for (int i = 0; i < 17; i++) {
                    resistance[i] = Integer.parseInt(word[i].replace(".", ""));
                }
                pokemonList.get(results.getInt("id") - 1).resistances = resistance;
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
              if (statement != null) {
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
        return pokemonList;
    }
}