package kz.kaspi.lab.calc.dtab;

import kz.kaspi.lab.calc.Calculation;
import kz.kaspi.lab.db.ConnectionManager;
import kz.kaspi.lab.db.utils.DBLogger;
import kz.kaspi.lab.error.Error;
import kz.kaspi.lab.json.doc.JSONDocument;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

public class DecisionTab extends Calculation {

    private static final Logger log = Logger.getLogger(DecisionTab.class);
    private static final String getDecisionParamsStmt = "select json_path, type, op, const, result from D_CALC_DEC inner join D_PAR on D_CALC_DEC.par = D_PAR.id where calc_id=? order by priority";
    public DecisionTab(int id, String name, int procId) {
        super(id, name, procId);
    }

    @Override
    public void eval(JSONDocument doc) throws Exception{
        log.info(getEvalLog());
        DBLogger.procLog(LocalDateTime.now(), getEvalLog());
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getDecisionParamsStmt)){
            pt.setInt(1, getId());
            ResultSet rs = pt.executeQuery();
            while (rs.next()){
                String jsonPath = rs.getString(1);
                String type = rs.getString(2);
                String op = rs.getString(3);
                String constVal = rs.getString(4);
                String result = rs.getString(5);
                if (type.equals("TEXT")){
                    String val = doc.getVal(jsonPath);
                    if (stringEval(op, val, constVal)){
                        doc.setVal("/" + getName(), result);
                        break;
                    }
                }
                if (type.equals("NUMBER")){
                    int val = Integer.parseInt(doc.getVal(jsonPath));
                    if (intEval(op, val, Integer.parseInt(constVal))){
                        doc.setVal("/" + getName(), result);
                        break;
                    }
                }
            }
        }
        DBLogger.procLog(LocalDateTime.now(), getEvalSuccessLog());
    }

    public boolean stringEval(String op, String first, String second) throws Exception{
        switch (op){
            case "==":
                return first.equals(second);
            case "!=":
                return !first.equals(second);
        }
        throw new Exception(Error._message("Неправильный оператор %s", op));
    }

    public boolean intEval(String op, int first, int second) throws Exception {
        switch (op){
            case "==":
                return first == second;
            case "!=":
                return first != second;
            case ">":
                return first > second;
            case "<":
                return first < second;
            case ">=":
                return first <= second;
            case "<=":
                return first >= second;
        }
        throw new Exception(Error._message("Неправильный оператор %s", op));
    }
}
