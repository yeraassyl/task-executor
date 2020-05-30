package kz.kaspi.lab.calc.scripting.sql;

import javafx.util.Pair;
import kz.kaspi.lab.calc.scripting.ScriptCalculation;
import kz.kaspi.lab.db.ConnectionManager;
import kz.kaspi.lab.db.utils.DBLogger;
import kz.kaspi.lab.error.Error;
import kz.kaspi.lab.json.doc.JSONDocument;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;

public class SQLCalculation extends ScriptCalculation {

    private static final Logger log = Logger.getLogger(SQLCalculation.class);
    public SQLCalculation(int id, String name, int procId, String script){
        super(id, name, procId, script);
    }

    public String evalDml() throws Exception{
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getScript())){
            pt.executeUpdate();
        }
        return "Успешно";
    }

    public Pair<String, String> evalSimple() throws Exception{
        Pair<String, String> result;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getScript())){
            ResultSet rs = pt.executeQuery();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            String val = "";
            if (rs.next()){
                val = rs.getString(1);
            }
            result = new Pair<>(rsMetaData.getColumnName(1), val);
        }
        return result;
    }

//    public List<String> evalSingle(){
//
//    }
//
//    public List<List<String>> evalMulti(){
//
//    }

    @Override
    public void eval(JSONDocument doc) throws Exception {
        log.info(getEvalLog());
        DBLogger.procLog(LocalDateTime.now(), getEvalLog());
        Pair<String, String> pair = getCalcResultParam();
        String jsonPath = pair.getKey();
        String type = pair.getValue();
        if (type.equals("DML")){
            doc.setVal(jsonPath, evalDml());
        } else if (type.equals("SIMPLE")){
            Pair<String, String> pathToValue = evalSimple();
            doc.setVal(String.format("%s/%s", jsonPath, pathToValue.getKey()), pathToValue.getValue());
        }
        log.info(doc.getText());
        log.info(getEvalSuccessLog());
        DBLogger.procLog(LocalDateTime.now(), getEvalSuccessLog());
    }
}
