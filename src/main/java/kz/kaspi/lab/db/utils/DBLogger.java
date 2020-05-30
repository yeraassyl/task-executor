package kz.kaspi.lab.db.utils;

import kz.kaspi.lab.db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DBLogger {
    private static final String extLogStmt = "insert into ext_log values (?, ?, ?, ?)";
    private static final String procLogStmt = "insert into proc_log values (?, ?)";
    private static final String errLogStmt = "insert into err_log values (?, ?)";
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void extLog(LocalDateTime receiveDate, String input, LocalDateTime responseDate, String output) throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(extLogStmt)){
            pt.setString(1, formattedDate(receiveDate));
            pt.setString(2, input);
            pt.setString(3, formattedDate(responseDate));
            pt.setString(4, output);
            pt.executeUpdate();
        }
    }

    public static void procLog(LocalDateTime date, String message) throws SQLException{
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(procLogStmt)){
            pt.setString(1, formattedDate(date));
            pt.setString(2, message);
            pt.executeUpdate();
        }
    }

    public static void errLog(LocalDateTime date, String message) throws SQLException{
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(errLogStmt)){
            pt.setString(1, formattedDate(date));
            pt.setString(2, message);
            pt.executeUpdate();
        }
    }

    private static String formattedDate(LocalDateTime date){
        return date.format(format);
    }

}
