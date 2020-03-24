package com.jenniferhawk.features;



import com.jenniferhawk.Bot;
import com.jenniferhawk.layout.FileWriters;

import java.io.IOException;
import java.sql.*;
import java.util.Random;


public class JenDB {
    public static String username = Bot.configuration.getDatabase().get("username");
    public static String password = Bot.configuration.getDatabase().get("password");
    private static String url = Bot.configuration.getDatabase().get("url");


    /**
     * Begin standard user command queries
     */

    public static String queryHer(String whereClause) { // Retrieve the text of a command
        String result = "";

            try {
                Connection con = DriverManager.getConnection(url, username, password);
                Class.forName("com.mysql.jdbc.Driver");
                PreparedStatement stmt = con.prepareStatement("select * from JenniferHawk.COMMANDS WHERE COMMAND = ? ");
                stmt.setString(1,whereClause);
                ResultSet rs = stmt.executeQuery();
//                ResultSet testSet = stmt.executeQuery("select * from JenniferHawk.COMMANDS WHERE COMMAND = '" + "dab"+"'");stmt.executeUpdate("DROP/**/TABLE/**/JenniferHawk.TAPIOCA");stmt.executeQuery("SELECT * from JenniferHawk.COMMANDS WHERE COMMAND = '"+ "cya" + "'");
                while (rs.next())
                    result = rs.getString(2);
                 con.close();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("There was an exception, chief.");
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());


        }
            return result;
        }



    public static void addToHer(String Command, String Text, String Author) { //Add to Jennifer's commands list
        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO JenniferHawk.COMMANDS(COMMAND, TEXT, AUTHOR) VALUES (?,?,?)");

            System.out.println("Attempting to add this: " + Text);
            System.out.println("Attempting to add command: " + Text);
            stmt.execute("SET NAMES utf8mb4"); // This is the only way the database will accept UNICODE characters like 
            stmt.setString(1,Command);
            stmt.setString(2,Text);
            stmt.setString(3,Author);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print ("There was an error when inserting.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            if ((((SQLException) e).getErrorCode()) == 1062) { // If command cannot be inserted because it already exists, update the record
                System.err.println("That one already exists. Attempting an update...");
                try {

                    Connection con = DriverManager.getConnection(url, username, password);
                    Class.forName("com.mysql.jdbc.Driver");
                    PreparedStatement stmt = con.prepareStatement("UPDATE JenniferHawk.COMMANDS SET TEXT = ? WHERE COMMAND = ? ");
                    stmt.setString(1,Text);
                    stmt.setString(2,Command);
                    stmt.execute("SET NAMES utf8mb4");
                    stmt.executeUpdate();
                    con.close();
                } catch (SQLException | ClassNotFoundException f) {
                    System.out.println("There was still an error.");
                    e.printStackTrace(System.err);
                    assert f instanceof SQLException;
                    System.err.println("SQLState: " +
                            ((SQLException) f).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException) f).getErrorCode());

                    System.err.println("Message: " + f.getMessage());
                }

            }
        }
    }

    public static void deleteFromHer(String whereClause) { // Delete a command. Only accessible by sp0ck1
        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM JenniferHawk.COMMANDS WHERE COMMAND = '" + whereClause + "'");
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an error when deleting.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());
        }
    }

    public static boolean checkQuery(String whereClause) { // If query is on the list of forbidden commands, return true
        boolean tf;
        String result = "";
        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from JenniferHawk.forbidden WHERE COMMAND = '" + whereClause + "'");
            while (rs.next())
                result = rs.getString(2);
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an exception, chief.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        tf = !result.equals("");
        return tf;
    }

    public static String getTimedMessage() {
        String result = "";
        Random random = new Random();
        System.out.println("Timed message called.");
        try {

            Connection con = DriverManager.getConnection(url, username,password);
            Class.forName("com.mysql.jdbc.Driver");
            int id = random.nextInt(5) + 1;
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT TEXT FROM JenniferHawk.timed_commands WHERE ID = " + id);
            while (rs.next()) {
                result = rs.getString(1);
            System.out.println("Sending timed message: " + result);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an error when toggling.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());
        }
        return result;
    }

    public static String pokeFacts() {
        String pokeFact = null;
        Random random = new Random();
        int FactID = random.nextInt(231);
        try {
            System.out.println("PokeFact sent.");
                 Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select JenniferHawk.pokefacts.FACT from JenniferHawk.pokefacts WHERE FactID = " + FactID);
            while (rs.next()) {
                pokeFact = rs.getString("FACT");

            }
                con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was a problem with your pokeFact.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return pokeFact;
    }

    public static String getPokeFact() {
        return pokeFacts();
    }

    /**
     *
     * Begin N64 Commands
     *
     */

    public static String[] rolln64() { // Return a random N64 game from the n64_games table
        String[] result = new String[3];
        Random random = new Random();

        try {
            System.out.println("rolln64 command fired.");
                 Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            boolean sports = true;
            while (sports) {
                int GameID = random.nextInt(304);
                ResultSet rs = stmt.executeQuery("select * from JenniferHawk.n64_games WHERE GameID = " + GameID);
                while (rs.next()) {
                    result[0] = rs.getString("GameID");
                    result[1] = rs.getString("GAME");
                    result[2] = rs.getString("GENRE");
                }

                if (!result[2].toLowerCase().contains("sports")) { // If result[2] does not contains "sports", we're good. Sports is false
                    sports = false;
                } else { // If result[2] DOES contains "sports", we're not good and sports is true. The loop should repeat.
                    System.out.print("GENRE is: " + result[2]+ "\n"); sports = true;
                }
                }
                    con.close();

        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an exception, chief.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return result;
    }

    public static String[] n64Info(Integer GameID) {
        String[] result = new String[7];
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from JenniferHawk.n64_games WHERE GameID = " + GameID);
            while (rs.next()) {
                result[0] = rs.getString("GAME");
                result[1] = rs.getString("YEAR");
                result[2] = rs.getString("DEVELOPER");
                result[3] = rs.getString("PUBLISHER");
                result[4] = rs.getString("REGION");
                result[5] = rs.getString("GENRE");
            }
        con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an exception, captain.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return result;
    }

    public static String[] N64Current() {
        String[] result = new String[2];

        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from JenniferHawk.n64_current");
            while (rs.next()) {
                result[0] = rs.getString("GameID");
                result[1] = rs.getString("GAME");
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an exception, captain.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }

        return result;
    }

    public static void N64UpdateCurrent(String Runner, String Column) { // Insert first/second/third/fourth/fifth place and runner's name

        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement stmt = con.prepareStatement("UPDATE JenniferHawk.n64_current SET "+Column+" = ?");
            stmt.setString(1,Runner);

            System.out.println(Column + " place: "+ Runner);
            stmt.executeUpdate(); // + " where GameID = TRUE");
            con.close();

            FileWriters writer = new FileWriters();
            writer.writeN64PlaceToFile(Column,Runner); //Update stream layout  (Move to layout package?)
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println("Runner may not have been inserted.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
    }

    public static String setNewN64Game(Integer GameID) { // Get game name by Integer GameID and insert into n64_current
        String game = "";
        String year = "";
        int count = 0;
        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GAME, YEAR FROM JenniferHawk.n64_games WHERE GameID = " + GameID);
            while (rs.next()) {
                game = rs.getString("GAME");
                year = rs.getString("YEAR");
            }
            System.out.println("Game is "+game+". Year is: "+year+", GameID is: "+GameID);
            PreparedStatement ps = con.prepareStatement("INSERT INTO JenniferHawk.n64_current(GameID,GAME) VALUES(?,?)");
            ps.setInt(1,GameID);
            ps.setString(2,game);
            ps.executeUpdate();

            System.out.println("New game is: " + game);

            ResultSet rc = stmt.executeQuery("SELECT COUNT(GAME)+1 AS C FROM JenniferHawk.n64_results");
            while (rc.next()) {
                count = rc.getInt("C");
            }
            System.out.println("Writing " + GameID +" "+game+" "+year+" "+count);
            FileWriters writer = new FileWriters();
            writer.writeN64InfoToFile(GameID,game,year, count);
            con.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            System.out.println("Game may not have been inserted.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());



        }
        return game;
    }

    public static void N64Complete() {
        String[] result = new String[8];
        int[] GameID = new int[1];
        FileWriters writer = new FileWriters();

        try {
            writer.clearN64Layout();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try { Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from JenniferHawk.n64_current");
            while (rs.next()) {
                GameID[0] = rs.getInt("GameID");
                result[0] = rs.getString(2);
                result[1] = rs.getString("FIRST");
                result[2] = rs.getString("SECOND");
                result[3] = rs.getString("THIRD");
                result[4] = rs.getString("FOURTH");
                result[5] = rs.getString("FIFTH");
                result[6] = rs.getString("URL");
            }

            System.out.printf("GameID[0] is %s, result[0] is %s, result[1] is %s, result[2] is %s, result[3] is %s, result[4] is %s, result[5] is %s, result[6] is %s",GameID[0],result[0],result[1],result[2],result[3],result[4],result[5],result[6]);

            if (result[6] == null) {result[6] = "";}
            PreparedStatement preparedStatement = con.prepareStatement(
                    "INSERT INTO JenniferHawk.n64_results(GameID, GAME, FIRST, SECOND, THIRD, FOURTH, FIFTH, URL) VALUES( ?,?,?,?,?,?,?,?)");

            preparedStatement.setInt(1,GameID[0]);
            preparedStatement.setString(2,result[0]);
            preparedStatement.setString(3,result[1]);
            preparedStatement.setString(4,result[2]);
            preparedStatement.setString(5,result[3]);
            preparedStatement.setString(6,result[4]);
            preparedStatement.setString(7,result[5]);
            preparedStatement.setString(8,result[6]);

            preparedStatement.executeUpdate();

            int deleted = stmt.executeUpdate("DELETE FROM JenniferHawk.n64_current WHERE GameID = " + GameID[0]);
                    con.close();
                    if ( deleted > 0 ) { System.out.println(deleted + " records deleted.");} else {System.out.println("Nothing was deleted!");}

        } catch(SQLException | ClassNotFoundException e){
                System.out.println("Error when inserting results into n64_results table.");
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());


            }
        }

    /**
     * Delete the current game and metadata from n64_current table
     */

    public static void N64Clear() {

        try {

            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Statement stmt = con.createStatement();
            int deleted = stmt.executeUpdate("DELETE FROM JenniferHawk.n64_current WHERE TRUE");
            con.close();
            if ( deleted > 0 ) { System.out.println(deleted + " records deleted.");} else {System.out.println("Nothing was deleted!");}
        } catch(SQLException | ClassNotFoundException e){
            System.out.println("Error when clearing results from n64_current table.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
    }

    public static String[] N64Lookup(String Lookup) { // Return the closest match based on input
        String[] result = new String[2];
        try {
System.out.println("Lookup phrase: "+Lookup);
            Connection con = DriverManager.getConnection(url, username, password);
            Class.forName("com.mysql.jdbc.Driver");
            Lookup = Lookup
                    .replace("!", "!!")
                    .replace("_", "!_")
                    .replace("[", "![");
            PreparedStatement stmt = con.prepareStatement("select GameID, GAME from JenniferHawk.n64_games WHERE GAME LIKE ?");
            stmt.setString(1,"%"+Lookup+"%");

            ResultSet rs = stmt.executeQuery();
            System.out.println(stmt);
            while (rs.next()) {
                result[0] = rs.getString("GameID");
                result[1] = rs.getString("GAME");
            }
            con.close();
    System.out.println("result[0] is " + result[0] + " and result[1] is " + result[1]);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an exception in the lookup, chief.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return result;
    }

    private static void n64UpdateOBS() throws IOException {
            int gameID = 0;
            String game = "";
            String year = "";
            int count = 0;
            try {

                       Connection con = DriverManager.getConnection(url, username, password);
                Class.forName("com.mysql.jdbc.Driver");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select GameID from JenniferHawk.n64_current");
                while (rs.next()) {
                    gameID = rs.getInt("GameID");
                    System.out.println(gameID);
                }
                ResultSet rt = stmt.executeQuery("SELECT GAME, YEAR FROM JenniferHawk.n64_games WHERE GameID = " + gameID);
                while (rt.next()) {
                    game = rt.getString("GAME");
                    System.out.println(game);
                    year = rt.getString("YEAR");
                    System.out.println(year);
                }
                ResultSet rc = stmt.executeQuery("SELECT COUNT(GAME)+1 AS C FROM JenniferHawk.n64_results");
                while (rc.next()) {
                    count = rc.getInt("C");
                }
            con.close();
            } catch (SQLException | ClassNotFoundException e) {
                System.out.println("There was an exception, James.");
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());


            }
        FileWriters writer = new FileWriters();
        writer.writeN64InfoToFile(gameID,game,year, count);
        }
    }


