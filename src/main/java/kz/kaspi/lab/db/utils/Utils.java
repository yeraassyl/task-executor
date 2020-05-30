package kz.kaspi.lab.db.utils;

import kz.kaspi.lab.error.Error;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

public class Utils {
    public static Properties readProperties(String filename) {
        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream(filename);
        try {
            props.load(stream);
        } catch (IOException e) {
            try {
                DBLogger.errLog(LocalDateTime.now(), e.getMessage());
            } catch (SQLException exception) {
                Error.printError(exception);
            }
            Error.printError(e);
        }
        return props;
    }

}