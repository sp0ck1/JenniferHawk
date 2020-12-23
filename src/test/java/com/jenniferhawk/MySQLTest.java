package com.jenniferhawk;

import com.jenniferhawk.database.JenDB;
import com.jenniferhawk.utils.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.sql.*;

public class MySQLTest {

    public static String username = "sp0ck1";
    public static String password = "AHawk15AB1rd";
    private static String url = "jdbc:mysql://db-jenniferhawk.cullqcykoe1e.us-east-2.rds.amazonaws.com:3306?useUnicode=true&characterEncoding=utf8";

    static Stopwatch stopwatch = new Stopwatch();

    public static void main(String args[]) throws SQLException, LoginException, InterruptedException {

        executeTest();
        double queryOne = stopwatch.getElapsedTime();
        System.out.println(queryOne);
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        System.out.println(stopwatch.getElapsedTime());
        executeTest();
        double queryTen = stopwatch.getElapsedTime();
        System.out.println(queryTen);

        System.out.println("Total time between first and tenth queries: " + Math.toIntExact(Math.round(queryTen) - Math.round(queryOne)));

    }


        /**
         * Begin standard user command queries
         */

    public static void executeTest() { // Retrieve the text of a command
        String result = "";
        String whereClause = "BSE";

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
        System.out.println(result);
    }


}
