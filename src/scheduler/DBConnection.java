package scheduler;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Opens a connection with the database.
 */
public class DBConnection {

    private static final String dBName="WJ06yKt";
    private static final String dBUrl="jdbc:mysql://wgudb.ucertify.com:3306/" + dBName;
    private static final String username="U06yKt";
    private static final String password="53688907697";
    private static final String driver="com.mysql.jdbc.Driver";
    public static Connection connection;

    /**
     * Makes a connection with the database.
     * @throws Exception Throws Exception if there is an error connecting to the database.
     */
    public static void makeConnection()throws Exception
    {
        Class.forName(driver);
        connection=(Connection) DriverManager.getConnection(dBUrl,username,password);

    }

    /**
     * Closes the database connection.
     * @throws Exception Throws Exception if there is an error closing the database connection.
     */
    public static void closeConnection()throws Exception{
        connection.close();

    }
}

