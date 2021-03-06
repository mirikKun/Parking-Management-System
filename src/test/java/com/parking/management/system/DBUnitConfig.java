package com.parking.management.system;

import com.parking.management.system.dao.AccountDao;

import com.parking.management.system.dao.AdminDao;
import com.parking.management.system.dao.FloorDao;
import com.parking.management.system.dao.ParkingDao;
import com.parking.management.system.dao.PaymentDao;
import com.parking.management.system.dao.SpotDao;
import com.parking.management.system.dao.TicketDao;
import com.parking.management.system.dao.VehicleDao;
import com.parking.management.system.dao.FloorDao;
import com.parking.management.system.utils.ConnectionProvider;
import com.parking.management.system.utils.TablesCreator;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class DBUnitConfig extends DBTestCase {

    protected IDatabaseTester tester;
    private final Properties properties;
    protected IDataSet beforeData;
    protected ConnectionProvider connectionProvider = new ConnectionProvider();
    protected AccountDao accountDao = new AccountDao(connectionProvider);
    protected AdminDao adminDao = new AdminDao(connectionProvider);
    protected FloorDao floorDao = new FloorDao(connectionProvider);
    protected ParkingDao parkingDao = new ParkingDao(connectionProvider);
    protected PaymentDao paymentDao = new PaymentDao(connectionProvider);
    protected SpotDao spotDao = new SpotDao(connectionProvider);
    protected TicketDao ticketDao = new TicketDao(connectionProvider);
    protected VehicleDao vehicleDao = new VehicleDao(connectionProvider);

    @Before
    public void setUp() throws Exception {
        tester = new JdbcDatabaseTester(properties.getProperty("driver"),
                properties.getProperty("url"),
                properties.getProperty("user"),
                properties.getProperty("password"));
    }

    public DBUnitConfig(String name) throws Exception {
        super(name);
        properties = new Properties();
        URL dataBasePropertyFile = this.getClass().getClassLoader().getResource("config.properties");
        try {
            String pathToPropertyFile = dataBasePropertyFile.getPath();
            FileReader fileReader = new FileReader(pathToPropertyFile);
            properties.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, properties.getProperty("driver"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, properties.getProperty("url"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, properties.getProperty("user"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, properties.getProperty("password"));
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "");

        ConnectionProvider connectionProvider = new ConnectionProvider();
        TablesCreator tablesCreator = new TablesCreator(connectionProvider);
        tablesCreator.createTables();
    }

    @Override
    protected IDataSet getDataSet() {
        return beforeData;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

}
