package kz.kaspi.lab.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import kz.kaspi.lab.db.utils.DBLogger;
import kz.kaspi.lab.db.utils.Utils;
import kz.kaspi.lab.error.Error;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class ConnectionManager {

    private static final DataSource dataSource;
    private static final String DRIVER_NAME;
    private static final String URL;
    private static final String UNAME;
    private static final String PWD;

    private final Logger log = Logger.getLogger(ConnectionManager.class);
    private final String ERROR_1 = "Невозможно получить соединение";

    static {
        Properties prop = Utils.readProperties("db.properties");

        DRIVER_NAME = prop.getProperty("db.driverClassName");
        URL = prop.getProperty("db.url");
        UNAME = prop.getProperty("db.username");
        PWD = prop.getProperty("db.password");
        dataSource = setUpDataSource();
    }

    private static DataSource setUpDataSource(){

        ComboPooledDataSource ds = new ComboPooledDataSource();
        try{
            ds.setDriverClass(DRIVER_NAME);
        } catch (PropertyVetoException e) {
            try {
                DBLogger.errLog(LocalDateTime.now(), e.getMessage());
            } catch (SQLException exception) {
                Error.printError(exception);
            }
            Error.printError(e);
        }
        ds.setJdbcUrl(URL);
        ds.setUser(UNAME);
        ds.setPassword(PWD);
        ds.setInitialPoolSize(5);
        ds.setMinPoolSize(5);
        ds.setAcquireIncrement(5);
        ds.setMaxPoolSize(20);
        return ds;
    }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }
}
