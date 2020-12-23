package com.jenniferhawk;/* Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.*/
/*
   DESCRIPTION
   The code sample shows how to use the DataSource API to establish a connection
   to the Database. You can specify properties with "setConnectionProperties".
   This is the recommended way to create connections to the Database.
   Note that an instance of oracle.jdbc.pool.OracleDataSource doesn't provide
   any connection pooling. It's just a connection factory. A connection pool,
   such as Universal Connection Pool (UCP), can be configured to use an
   instance of oracle.jdbc.pool.OracleDataSource to create connections and
   then cache them.

    Step 1: Enter the Database details in this file.
            DB_USER, DB_PASSWORD and DB_URL are required
    Step 2: Run the sample with "ant DataSourceSample"

   NOTES
    Use JDK 1.7 and above
   MODIFIED    (MM/DD/YY)
    nbsundar    02/17/15 - Creation
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.jenniferhawk.utils.Stopwatch;
import com.sun.tools.javac.util.List;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;

public class DataSourceSample {
    // The recommended format of a connection URL is the long format with the
    // connection descriptor.
    // final static String DB_URL= "jdbc:oracle:thin:@myhost:1521/myorcldbservicename";
    // For ATP and ADW - use the TNS Alias name along with the TNS_ADMIN when using 18.3 JDBC driver
    final static String DB_URL="jdbc:oracle:thin:@jenniferhawk_tp?TNS_ADMIN=C:/wallet_jenniferhawk";
    // In case of windows, use the following URL
    // final static String DB_URL="jdbc:oracle:thin:@wallet_dbname?TNS_ADMIN=C:\\Users\\test\\wallet_dbname";
    final static String DB_USER = "ADMIN";
    final static String DB_PASSWORD = "AH4wk15AB1rd";
    static Properties info = new Properties();
    static OracleDataSource ods;
    static Stopwatch stopwatch = new Stopwatch();

    public static void start() throws SQLException {
        info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
        info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);
        info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
        ods = new OracleDataSource();

        ods.setURL(DB_URL);
        ods.setConnectionProperties(info);
        ods.setImplicitCachingEnabled(false);

    }

    /*
     * The method gets a database connection using
     * oracle.jdbc.pool.OracleDataSource. It also sets some connection
     * level properties, such as,
     * OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH,
     * OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, etc.,
     * There are many other connection related properties. Refer to
     * the OracleConnection interface to find more.
     */
    public static void main(String args[]) throws SQLException {

        start();
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

        // With AutoCloseable, the connection is closed automatically.

    }

    public static void executeTest() throws SQLException {
        try {
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@jenniferhawk_tp?TNS_ADMIN=C:/wallet_jenniferhawk","ADMIN","AH4wk15AB1rd");
            Class.forName("oracle.jdbc.driver.OracleDriver");


            // Perform a database operation
            printEmployees(con);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
     * Displays first_name and last_name from the employees table.
     */
    public static void printEmployees(Connection connection) throws SQLException {
        // Statement and ResultSet are AutoCloseable and closed automatically.
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement
                    .executeQuery("select * from dual")) {

                while (resultSet.next())
                    System.out.println(resultSet.getString(1) + " "
                            );
            }
        }
    }
}

