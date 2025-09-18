package com.jenniferhawk.database;

import com.jenniferhawk.Bot;
import com.jenniferhawk.n64mania.N64Game;
import lombok.SneakyThrows;
//import oracle.jdbc.driver.OracleDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO: Update Jendb pass

public class JenDB {
    public static String username = Bot.configuration.getDatabase().get("username");
    public static String password = Bot.configuration.getDatabase().get("password");
    private static String url = Bot.configuration.getDatabase().get("url");
    private static Logger LOG = LoggerFactory.getLogger(JenDB.class);
    private static String driverForName = "oracle.jdbc.driver.OracleDriver";
    private static BasicConnectionPool connectionPool;

    static {
        try {
            connectionPool = BasicConnectionPool.create(url,username,password);
            // Does this need to exist? Test on remote server
         //   DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Begin standard user command queries
     */

    public static String queryHer(String whereClause) { // Retrieve the text of a command
        String result = "";

        try {

            Connection con = connectionPool.getConnection();

            PreparedStatement stmt = con.prepareStatement("select * from ADMIN.COMMANDS WHERE COMMAND = ? ");
            stmt.setString(1,whereClause);
            ResultSet rs = stmt.executeQuery();
//                ResultSet testSet = stmt.executeQuery("select * from ADMIN.COMMANDS WHERE COMMAND = '" + "dab"+"'");stmt.executeUpdate("DROP/**/TABLE/**/ADMIN.TAPIOCA");stmt.executeQuery("SELECT * from ADMIN.COMMANDS WHERE COMMAND = '"+ "cya" + "'");
            while (rs.next())
                result = rs.getString(2);
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
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



    @SneakyThrows
    public static void addToHer(String Command, String Text, String Author) { //Add to Jennifer's commands list

        try {

            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO COMMANDS (COMMAND, TEXT, AUTHOR) VALUES (?,?,?)");


            System.out.println("Attempting to add command: " + Text);

            //stmt.execute("SET NAMES utf8mb4"); // This is the only way the database will accept UNICODE characters like
            stmt.setString(1,Command);
            stmt.setString(2,Text);
            stmt.setString(3,Author);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            //System.out.print ("There was an error when inserting.");
            //e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            if (e.getMessage().contains("ORA-00001: unique constraint (ADMIN.COMMANDS_PK) violated")) { // If command cannot be inserted because it already exists, update the record
                System.err.println("That one already exists. Attempting an update...");
                try {

                    Connection con = connectionPool.getConnection();
                    PreparedStatement stmt = con.prepareStatement("UPDATE ADMIN.COMMANDS SET TEXT = ? WHERE COMMAND = ? ");

                    stmt.setString(1,Text);

                    stmt.setString(2,Command);
                    // This was necessary in a MySQL database in order to display emoji characters.
                    // Retaining as comment in case of later database migration.
                    // stmt.execute("SET NAMES utf8mb4");

                    stmt.executeUpdate();

                    connectionPool.releaseConnection(con);
                } catch (SQLException f) {
                    System.out.println("There was still an error.");
                    // e.printStackTrace(System.err);

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

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM ADMIN.COMMANDS WHERE COMMAND = '" + whereClause + "'");
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println("There was an error when deleting.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());
        }
    }

    public static void addPun(String pun, String text, String author) { //Add to Jennifer's puns list
        try {

            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO ADMIN.puns(PUN, TEXT, AUTHOR) VALUES (?,?,?)");


            System.out.println("Attempting to add pun: " + text);
            // stmt.execute("SET NAMES utf8mb4"); // This is the only way the database will accept UNICODE characters in MySQL
            stmt.setString(1,pun);
            stmt.setString(2,text);
            stmt.setString(3,author);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            //System.out.print ("There was an error when inserting.");
            //e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

            if ((((SQLException) e).getErrorCode()) == 1062) { // If command cannot be inserted because it already exists, update the record
                System.err.println("That pun already exists. Attempting an update...");
                try {

                    Connection con = connectionPool.getConnection();
                    PreparedStatement stmt = con.prepareStatement("UPDATE ADMIN.puns SET TEXT = ? WHERE PUN = ? ");
                    stmt.setString(1,text);
                    stmt.setString(2,pun);
                    stmt.execute("SET NAMES utf8mb4");
                    stmt.executeUpdate();
                    connectionPool.releaseConnection(con);
                } catch (SQLException f) {
                    System.out.println("There was still a pun error.");
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException) f).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException) f).getErrorCode());

                    System.err.println("Message: " + f.getMessage());
                }

            }
        }
    }

    public static String getPun() { // Retrieve the text of a command
        String result = "";
        Random random = new Random();
        System.out.println("Getting pun.");
        List<String> punList = new ArrayList<>();
        try {

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT TEXT FROM ADMIN.puns");
            while (rs.next()) {
                punList.add(rs.getString(1));
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {

            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());
        }
        int id = random.nextInt(punList.size());
        result = punList.get(id);
        return result;
    }

    // TODO: Make the forbidden table a view of all commands where sp0ck1 is the author + all commands listed in timed_commands
    public static boolean checkQuery(String whereClause) { // If query is on the list of forbidden commands, return true
        boolean tf;
        String result = "";
        try {

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from ADMIN.forbidden WHERE COMMAND = '" + whereClause + "'");
            while (rs.next())
                result = rs.getString(2);
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
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
        System.out.println("Getting timed message from timed_commands.");
        List<String> commandList = new ArrayList<>();
        try {

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT TEXT FROM ADMIN.timed_commands WHERE TIMER = 1");
            while (rs.next()) {
                commandList.add(rs.getString(1));
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {

            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());
        }
        int id = random.nextInt(commandList.size());
        result = commandList.get(id);
        return result;
    }

    private static String pokeFacts() {
        String pokeFact = null;
        Random random = new Random();
        int FactID = random.nextInt(231);
        try {
            System.out.println("PokeFact sent.");
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select ADMIN.pokefacts.FACT from ADMIN.pokefacts WHERE FactID = " + FactID);
            while (rs.next()) {
                pokeFact = rs.getString("FACT");

            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
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

    public static N64Game rolln64() { // Return a random N64 game from the n64_games table

        Random random = new Random();
        N64Game n64Game = new N64Game();
        try {
            System.out.println("rolln64 command fired.");
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            List<Integer> idList = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery("SELECT GAMEID FROM N64_REMAINING");

            while (resultSet.next()) { idList.add(resultSet.getInt(1)); }

            int GameID = idList.get(random.nextInt(idList.size())); // Get one of the entries in the list of GameIDs

            ResultSet rs = stmt.executeQuery("SELECT * FROM N64_REMAINING WHERE GAMEID = " + GameID);

            while (rs.next()) {
                n64Game.setId(rs.getString("GAMEID"))
                        .setTitle(rs.getString("GAME"))
                        .setGenre(rs.getString("GENRE"))
                        .setRaceURL("URL");
                //TODO: .setURLType if www.speedrunslive, type = SRL, if www.racetime, type = RACETIME
                //  and rename .setSrlURL to .setRaceResultsURL

            }
            connectionPool.releaseConnection(con);

        } catch (SQLException e) {
            System.out.println("There was an exception, chief.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return n64Game;
    }
    /**
     * Returns an unplayed n64 game with the given arguments taken into account
     *
     * @param   args  An array of argument strings. Should be only the arguments, i.e. {"--nosports", "--nofighting",}
     *
     * @return  An as yet unplayed <code>N64Game</code>.
     */
    public static N64Game rolln64(String[] args) {
        StringBuilder initialQuery = new StringBuilder();
        String genreNotLike = " AND LOWER (GENRE) NOT LIKE ";
        Random random = new Random();
        N64Game n64Game = new N64Game();
        try {
            System.out.println("rolln64 command fired.");
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            List<Integer> idList = new ArrayList<>();
            initialQuery.append("SELECT GAMEID FROM N64_REMAINING WHERE 1 = 1");

            // Argument string should include --
            // Example: If the command, !rolln64 --nosports --nofighting, is sent,
            for (String argument : args) {
                if (argument.substring(2,4).equalsIgnoreCase("no")) {
                    initialQuery
                            .append(genreNotLike).append("'%")
                            .append(argument.substring(argument.indexOf("no") + 2).toLowerCase())
                            .append("%'");
                }

            }
            ResultSet resultSet = stmt.executeQuery(initialQuery.toString());

            while (resultSet.next()) { idList.add(resultSet.getInt(1)); }

            int GameID = idList.get(random.nextInt(idList.size())); // Get one of the entries in the list of GameIDs

            ResultSet rs = stmt.executeQuery("SELECT * FROM N64_REMAINING WHERE GAMEID = " + GameID);

            while (rs.next()) {
                n64Game.setId(rs.getString("GAMEID"))
                        .setTitle(rs.getString("GAME"))
                        .setGenre(rs.getString("GENRE"))
                        .setRaceURL("URL");
            }
            connectionPool.releaseConnection(con);

        } catch (SQLException e) {
            System.out.println("There was an exception, chief.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return n64Game;


    }

    public static N64Game rollRunback() { // Return a random N64 game from the n64_results table

        Random random = new Random();
        N64Game n64Game = new N64Game();
        n64Game.setCompleted(true);

        try {

            LOG.info("rollRunback command fired.");

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            List<Integer> idList = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery("SELECT GAMEID FROM N64_RESULTS");

            while (resultSet.next()) { idList.add(resultSet.getInt(1)); }

            int GameID = idList.get(random.nextInt(idList.size())); // Get one of the entries in the list of GameIDs

            ResultSet rs = stmt.executeQuery("SELECT * FROM N64_RESULTS WHERE GAMEID = " + GameID);

            while (rs.next()) {
                n64Game.setId(rs.getString("GAMEID"))
                        .setTitle(rs.getString("GAME"))
                        .setWinner(rs.getString("FIRST"))
                        .setRaceURL(rs.getString("URL"));
                        //.setGenre(rs.getString("GENRE"));
            }
            connectionPool.releaseConnection(con);

        } catch (SQLException e) {

            LOG.error("SQL Exception while attempting to fetch a runback game.");
            e.printStackTrace(System.err);
            LOG.error("SQLState: " +
                    e.getSQLState());

            LOG.error("Error Code: " +
                    e.getErrorCode());

            LOG.error("Message: " + e.getMessage());


        }
        return n64Game;
    }


    public static N64Game getGameInfo(Integer GameID) {

        N64Game n64Game = new N64Game();
        try {
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from N64_GAMES WHERE GAMEID = " + GameID);
            while (rs.next()) {
                n64Game.setTitle(rs.getString("GAME"))
                        .setYear(Integer.parseInt(rs.getString("YEAR")))
                        .setDeveloper(rs.getString("DEVELOPER"))
                        .setPublisher(rs.getString("PUBLISHER"))
                        .setRegion(rs.getString("REGION"))
                        .setGenre(rs.getString("GENRE"));
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println("There was an exception, captain.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return n64Game;
    } // for lookup command

    public static N64Game getGameName(Integer GameID) {

        N64Game n64Game = new N64Game();
        try {
            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select GAME from N64_GAMES WHERE GAMEID = " + GameID);
            while (rs.next()) {
                n64Game.setTitle(rs.getString("GAME"));
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println("There was an exception when retrieving the game name.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
        return n64Game;
    } // for lookup command

    public static String[] N64Current() {
        String[] result = new String[2];

        try {

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from N64_CURRENT");
            while (rs.next()) {
                result[0] = rs.getString("GameID");
                result[1] = rs.getString("GAME");
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println("There was an exception, captain.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }

        return result;
    } // for base n64mania commmand

    public static void N64UpdateCurrent(String Runner, String Column) { // Insert first/second/third/fourth/fifth place and runner's name

        try {

            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("UPDATE N64_CURRENT SET "+Column+" = ?");
            stmt.setString(1,Runner);

            System.out.println(Column + " place: "+ Runner);
            stmt.executeUpdate(); // + " where GameID = TRUE");
            connectionPool.releaseConnection(con);

        } catch (SQLException e) {
            System.out.println("Runner may not have been inserted.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
    } // to add first, second, third etc place

    public static String setNewN64Game(Integer GameID) { // Get game name by Integer GameID and insert into n64_current
        String game = "";
        String year = "";
        int count = 0;
        try {

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT GAME, YEAR FROM N64_GAMES WHERE GAMEID = " + GameID);
            while (rs.next()) {
                game = rs.getString("GAME");
                year = rs.getString("YEAR");
            }
            System.out.println("Game is "+game+". Year is: "+year+", GameID is: "+GameID);
            PreparedStatement ps = con.prepareStatement("INSERT INTO N64_CURRENT(GAMEID,GAME) VALUES(?,?)");
            ps.setInt(1,GameID);
            ps.setString(2,game);
            ps.executeUpdate();

            System.out.println("New game is: " + game);

            ResultSet rc = stmt.executeQuery("SELECT COUNT(GAME)+1 AS C FROM N64_RESULTS");
            while (rc.next()) {
                count = rc.getInt("C");
            }
            System.out.println("Writing " + GameID +" "+game+" "+year+" "+count);
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
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


        try { Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from N64_CURRENT");
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
                    "INSERT INTO N64_RESULTS(GAMEID, GAME, FIRST, SECOND, THIRD, FOURTH, FIFTH, URL) VALUES( ?,?,?,?,?,?,?,?)");

            preparedStatement.setInt(1,GameID[0]);
            preparedStatement.setString(2,result[0]);
            preparedStatement.setString(3,result[1]);
            preparedStatement.setString(4,result[2]);
            preparedStatement.setString(5,result[3]);
            preparedStatement.setString(6,result[4]);
            preparedStatement.setString(7,result[5]);
            preparedStatement.setString(8,result[6]);

            preparedStatement.executeUpdate();

            int deleted = stmt.executeUpdate("DELETE FROM N64_CURRENT WHERE GAMEID = " + GameID[0]);
            connectionPool.releaseConnection(con);
            if ( deleted > 0 ) { System.out.println(deleted + " records deleted.");} else {System.out.println("Nothing was deleted!");}

        } catch(SQLException e){
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

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            int deleted = stmt.executeUpdate("DELETE FROM N64_CURRENT WHERE TRUE");
            connectionPool.releaseConnection(con);
            if ( deleted > 0 ) { LOG.info(deleted + " records deleted.");} else {LOG.info("Nothing was deleted!");}
        } catch(SQLException e){
            System.out.println("Error when clearing results from n64_current table.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }
    }

    public static String[] N64Lookup(String lookupPhrase) { // Return the closest match based on input
        String[] result = new String[2];
        try {
            System.out.println("Lookup phrase: "+lookupPhrase);
            Connection con = connectionPool.getConnection();
            lookupPhrase = lookupPhrase
                    .replace("!", "!!")
                    .replace("_", "!_")
                    .replace("[", "![");
            PreparedStatement stmt = con.prepareStatement("select GAMEID, GAME from N64_GAMES WHERE UPPER(GAME) LIKE UPPER(?)");
            stmt.setString(1,"%"+lookupPhrase+"%");

            ResultSet rs = stmt.executeQuery();
            System.out.println(stmt);
            while (rs.next()) {
                result[0] = rs.getString("GameID");
                result[1] = rs.getString("GAME");
            }
            connectionPool.releaseConnection(con);
            System.out.println("result[0] is " + result[0] + " and result[1] is " + result[1]);
        } catch (SQLException e) {
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

            Connection con = connectionPool.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select GameID from ADMIN.n64_current");
            while (rs.next()) {
                gameID = rs.getInt("GameID");
                System.out.println(gameID);
            }
            ResultSet rt = stmt.executeQuery("SELECT GAME, YEAR FROM ADMIN.n64_games WHERE GameID = " + gameID);
            while (rt.next()) {
                game = rt.getString("GAME");
                System.out.println(game);
                year = rt.getString("YEAR");
                System.out.println(year);
            }
            ResultSet rc = stmt.executeQuery("SELECT COUNT(GAME)+1 AS C FROM ADMIN.n64_results");
            while (rc.next()) {
                count = rc.getInt("C");
            }
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {
            System.out.println("There was an exception, James.");
            e.printStackTrace(System.err);
            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());


        }

    }


    public static void addUEBSUnit(String unit) {
        try {
            System.out.println("Trying to add unit " + unit);
            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO UEBS_UNITS (UNIT) VALUES (?)");
            stmt.setString(1,unit);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {

            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

        }
    }

    public static void insertRole(String roleId, String roleName, String roleEmoteId) {
        try {
            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement( "INSERT INTO ROLES (ROLE_ID, ROLE_NAME, ROLE_EMOTE) VALUES(?, ?, ?)" );
            stmt.setString(1, roleId);
            stmt.setString(2, roleName);
            stmt.setString(3, roleEmoteId);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ArrayList<String> getRoleMessages() {
        ArrayList<String> resultList = new ArrayList<>();

        try {

                Connection con = connectionPool.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT MSG_ID FROM ROLE_MESSAGES");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    resultList.add(rs.getString("MSG_ID"));
                }
                connectionPool.releaseConnection(con);
            return resultList;
            } catch (SQLException e) {

                System.err.println("SQLState: " +
                        ((SQLException) e).getSQLState());

                System.err.println("Error Code: " +
                        ((SQLException) e).getErrorCode());

                System.err.println("Message: " + e.getMessage());

            }
        return null;
    }

    public static void addRoleMessage(String messageId) {
        try {
            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("INSERT INTO ROLE_MESSAGES (MSG_ID) VALUES (?)");
            stmt.setString(1, messageId);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {

            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

        }
    }

    public static String getReactionEmoteIdForMessageId(String msgId) {
        try {
            Connection con = connectionPool.getConnection();
            PreparedStatement stmt = con.prepareStatement("SELECT ROLE_ID FROM ROLE_MESSAGES WHERE MSG_ID = ?");
            stmt.setString(1, msgId);
            stmt.executeUpdate();
            connectionPool.releaseConnection(con);
        } catch (SQLException e) {

            System.err.println("SQLState: " +
                    ((SQLException) e).getSQLState());

            System.err.println("Error Code: " +
                    ((SQLException) e).getErrorCode());

            System.err.println("Message: " + e.getMessage());

        }
        return null; //TODO: Finish this method
    }

            private static Statement createStatement() throws SQLException {
        Connection con = connectionPool.getConnection();
        return con.createStatement();
        // TODO: Integrate a way to release connections
    }
}

