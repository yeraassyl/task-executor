package kz.kaspi.lab.process.utils;
import kz.kaspi.lab.db.ConnectionManager;
import kz.kaspi.lab.json.doc.JSONDocument;
import kz.kaspi.lab.process.Process;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kz.kaspi.lab.error.Error;

public class ProcessUtils {

    private static final String getJsonStmt = "select json_path from D_PAR where name=? and proc=?";
    private static final String getProcIdStmt = "select id from D_PROC where code=?";

    private static final Logger log = Logger.getLogger(ProcessUtils.class);

    public static List<String> parseParams(String script) throws Exception{
        String regex = "#(.*?)#";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(script);
        List<String> params = new ArrayList<>();
        while (matcher.find()){
            params.add(matcher.group(1));
        }
        return params;
    }

    public static Map<String, String> parseValues(List<String> params, Process proc) throws Exception{
        log.info("Parsing values for parameters of process " + proc.getProcId());
        Map<String, String> parameters = new HashMap<>();
        JSONDocument doc = proc.getDoc();
        for (String param: params) {
            String jsonPath = getJsonPath(param, proc.getProcId());
            String val = null;
            val = doc.getVal(jsonPath);
            if (val == null){
                val = "";
            }
            parameters.put(param, val);
        }
        return parameters;
    }

    private static String getJsonPath(String paramName, int procId) throws Exception {
        log.info("Getting jsonPath for " + paramName);
        String path = "";
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getJsonStmt)) {
            pt.setString(1, paramName);
            pt.setInt(2, procId);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                path = rs.getString(1);
            } else {
                Error.raise("JSON path для %s не существует", paramName);
            }
        }
        return path;
    }

    public static JSONDocument parse(String json) throws Exception{
        JSONDocument document = new JSONDocument();
        document.parse(json);
        return document;
    }

    public static int getProcId(String procCode) throws Exception{
        int val = 0;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getProcIdStmt)){
            pt.setString(1, procCode);
            ResultSet rs = pt.executeQuery();
            if (rs.next()){
                val = rs.getInt(1);
            } else {
                Error.raise("Нет процесса по коду %s", procCode);
            }
        }
        return val;
    }
}
