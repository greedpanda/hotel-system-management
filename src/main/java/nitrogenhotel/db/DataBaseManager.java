package nitrogenhotel.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import nitrogenhotel.db.exceptions.databasemanager.CloseConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.ConnectionException;
import nitrogenhotel.db.exceptions.databasemanager.CouldNotSetupDataBaseException;
import nitrogenhotel.db.exceptions.databasemanager.DataBaseDriverException;
import nitrogenhotel.db.exceptions.databasemanager.SchemaNotFoundException;
import nitrogenhotel.utils.config.ConfigLoader;
import nitrogenhotel.utils.config.ConfigModel;
import org.tinylog.Logger;

/**
 * This class will be responsible of establishing the connection with MYSQL and it will also pass
 * the connection to whoever needs to contact the database. Other responsibilities include: 1.
 * Creating the database if it does not exist. 2. Building the tables from an SQL file (.sql). 3.
 * Optional: inserting dummy data for testing from a file.
 */
public class DataBaseManager {

  private static final String JdbcDriver = "com.mysql.cj.jdbc.Driver";
  private static final String script = "test_creation.sql";
  static Connection conn = null;
  private static String DbUrl = "jdbc:mysql://localhost?useTimezone=true&serverTimezone=UTC";
  private static String User = "root";
  private static String Pass = "root";
  private static String Schema = "NitrogenHotel";

  /**
   * This method is used to establish the connection to the database, it is to be called at the
   * beginning of the program.
   */
  public static void startConnection(String dbUrl, String schema, String user, String pass)
      throws ConnectionException, SchemaNotFoundException, DataBaseDriverException,
      CloseConnectionException, CouldNotSetupDataBaseException {

    ConfigModel config = ConfigLoader.getInstance();

    DataBaseManager.DbUrl = dbUrl;
    DataBaseManager.Schema = schema;
    DataBaseManager.User = user;
    DataBaseManager.Pass = pass;

    // closes any previous connections.
    closeConnection();

    /* Setup the driver and connect to the db */
    try {
      Class.forName(JdbcDriver);

      // The connection to be used throughout the package.
      conn = DriverManager.getConnection(dbUrl, user, pass);

      try (Statement st = conn.createStatement()) {

        Logger.debug("Attempting to connect to database " + schema);

        if (config.getDevelopment()) {

          // setup the database.
          setupSchema();

          // inserting data for testing.
          insertDataFromScript("test_insertion.sql");
        } else {

          // if database exists, use it.
          st.execute("USE " + Schema); // if the schema exists, use it.
          Logger.debug("database " + Schema + " found");
        }

      } catch (SQLException e) {
        throw new SchemaNotFoundException("Could not use the schema " + schema, e);
      } catch (IOException e) {
        throw new CouldNotSetupDataBaseException("Could not setup the schema " + schema, e);
      }
    } catch (SQLException e) {
      throw new ConnectionException("Could not connect to the database " + schema, e);
    } catch (ClassNotFoundException e) {
      throw new DataBaseDriverException("Could not locate the database driver " + JdbcDriver, e);
    }
  }

  /**
   * Used to connect to the database internally.
   *
   * @throws ConnectionException            when an error occurs while connecting to the db.
   * @throws CloseConnectionException       when an error occurs while closing the connection.
   * @throws DataBaseDriverException        when the db driver is not found.
   * @throws SchemaNotFoundException        when the schema is not found.
   * @throws CouldNotSetupDataBaseException when the database could not be set up.
   */
  public static void startConnection()
      throws ConnectionException, CloseConnectionException, DataBaseDriverException,
      SchemaNotFoundException, CouldNotSetupDataBaseException {
    ConfigModel config = ConfigLoader.getInstance();
    DataBaseManager.DbUrl =
        "jdbc:mysql://" + config.getHost() + ":" + config.getPort()
            + "?useTimezone=true&serverTimezone=UTC";
    DataBaseManager.User = config.getUsername();
    DataBaseManager.Pass = config.getPassword();
    DataBaseManager.startConnection(DbUrl, Schema, User, Pass);
  }

  /**
   * Returns the established connection. This method returns the connection that was established
   * when the program was launched. It is to be used by any method/class in the db package who wants
   * to communicate directly to the database through a PreparedStatement.
   */
  public static Connection getConnection() {
    return conn;
  }

  /**
   * This method is used to setup the Database. It will: 1- drop the tables if it is called and they
   * already exist. 2- create the database Schema in the mysql server. 3- create the tables in the
   * database using an sql file.
   *
   * @throws SQLException Thrown for sql errors.
   */
  public static void setupSchema() throws SQLException, IOException {

    Logger.debug("Setting up the Schema " + Schema);

    try {

      // dropping current schema to avoid duplicates.
      dropCurrentSchema();

      // creating the database schema.
      createSchema(Schema);

      // creating the tables in the schema.
      createTables();

    } catch (SQLException | FileNotFoundException e) {
      e.printStackTrace();
      Logger.error("setupSchema has failed to prepare the database");
    }
  }

  /**
   * This method drops the database schema if it already exists.
   */
  private static void dropCurrentSchema() {

    try (PreparedStatement st = conn.prepareStatement("DROP DATABASE IF EXISTS " + Schema)) {
      st.execute();
    } catch (SQLException e) {
      Logger.error("Could not drop database " + Schema);
    }
  }

  /**
   * This method creates the schema in the sql server. It also sets the server to use it.
   *
   * @param schema The name of the schema to be created.
   * @throws SQLException thrown for sql errors.
   */
  @SuppressFBWarnings(value = "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE",
      justification = "Static class, already handled")
  private static void createSchema(String schema) throws SQLException {

    Logger.debug("Attempting to create and use Schema " + schema);
    try (Statement stmnt = conn.createStatement()) {
      stmnt.execute("CREATE SCHEMA " + schema);
      stmnt.execute("USE " + schema);
      DataBaseManager.Schema = schema;
    }
  }

  /**
   * This method takes an SQL script with tables to be created and creates them in the database.
   *
   * @throws FileNotFoundException thrown if file cannot be found.
   * @throws SQLException          thrown for sql errors.
   */
  private static void createTables() throws IOException, SQLException {

    File creationScript =
        new File(
            Paths.get("./src/main/java/nitrogenhotel/db/scripts/")
                .toAbsolutePath()
                .normalize()
                .toString()
                + File.separatorChar
                + DataBaseManager.script);

    Statement st = conn.createStatement();

    // separate each query using the scanner.
    Scanner creationQueries = new Scanner(creationScript, StandardCharsets.UTF_8);
    creationQueries.useDelimiter(";[\\\\r\\\\n]*");
    String query;

    // iterate through the queries and execute them.
    while (creationQueries.hasNext()) {
      query = creationQueries.next();
      if (st != null && !query.isEmpty()) {
        st.execute(query);
      }
    }

    // close scanner and release statement.
    creationQueries.close();
    if (st != null) {
      st.close();
    }
  }

  /**
   * This method is to be used internally to insert dummy data for test purposes or to provide some
   * initial data.
   *
   * @param script The PATH to the file that contains the insertions queries.
   * @throws SQLException thrown for sql errors.
   */
  private static void insertDataFromScript(String script) throws SQLException {

    File dataScript =
        new File(
            Paths.get("./src/main/java/nitrogenhotel/db/scripts/")
                .toAbsolutePath()
                .normalize()
                .toString()
                + File.separatorChar
                + script);

    Statement st = conn.createStatement();

    // separate the queries.
    try {
      Scanner dataInsertionQueries = new Scanner(dataScript, StandardCharsets.UTF_8);
      dataInsertionQueries.useDelimiter(";[\\r\\n]{2,}");
      String query;

      // insert all the data.
      while (dataInsertionQueries.hasNext()) {
        query = dataInsertionQueries.next();
        if (st != null && !query.isEmpty()) {
          st.execute(query);
        }
      }

      // close everything.
      dataInsertionQueries.close();
      if (st != null) {
        st.close();
      }

    } catch (IOException e) {
      e.printStackTrace();
      Logger.error("Could not insert test data from script " + script);
    }
  }

  /**
   * This method closes the connection to the database server. To be called when the program is
   * going to be closed. I.e, it should be called in the method that will close the program.
   */
  public static void closeConnection() throws CloseConnectionException {
    try {
      if (conn != null) {
        conn.close();
        conn = null;
      }
    } catch (SQLException e) {
      throw new CloseConnectionException("Could not close the connection to " + Schema, e);
    }
  }
}
