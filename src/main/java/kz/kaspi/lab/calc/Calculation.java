package kz.kaspi.lab.calc;

import javafx.util.Pair;
import kz.kaspi.lab.db.ConnectionManager;
import kz.kaspi.lab.error.Error;
import kz.kaspi.lab.json.doc.JSONDocument;

import javax.script.ScriptException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Calculation {
    private final int id;
    private String name;
    private final int procId;
    private static final String getCalcParamStmt = "select json_path, type from D_PAR where name=? and proc=?";

    public Calculation(int id, String name, int procId){
        this.id = id;
        this.name = name;
        this.procId = procId;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getProcId(){
        return procId;
    }

    public abstract void eval(JSONDocument doc) throws Exception;

    public Pair<String, String> getCalcResultParam() throws Exception {
        Pair<String, String> pair = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getCalcParamStmt)) {
            pt.setString(1, name);
            pt.setInt(2, procId);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                pair = new Pair<>(rs.getString(1), rs.getString(2));
            } else {
                Error.raise("Не удалось получить данные, название калькуляции %s, ID процесса %d", name, procId);
            }
        }
        return pair;
    }

    public String getEvalLog(){
        return String.format("Evaluating calculation %s of process %d", getName(), getProcId());
    }

    public String getEvalSuccessLog(){
        return String.format("Successful evaluation %s of process %d", getName(), getProcId());
    }
}
