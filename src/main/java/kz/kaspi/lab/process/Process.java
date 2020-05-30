package kz.kaspi.lab.process;

import kz.kaspi.lab.calc.Calculation;
import kz.kaspi.lab.calc.CalculationFactory;
import kz.kaspi.lab.calc.scripting.utils.ScriptUtils;
import kz.kaspi.lab.db.ConnectionManager;
import kz.kaspi.lab.json.doc.JSONDocument;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Process {

    private static final Logger log = Logger.getLogger(Process.class);
    private final int procId;
    private final JSONDocument doc;
    private static final String getCalcStmt =
            "select D_CALC.id, D_CALC.name, D_CALC.type, D_CALC.script " +
                    "from D_CALC inner join D_PROC_CALC on D_CALC.id=D_PROC_CALC.calc and D_PROC_CALC.proc=?";

    private static final String getFlagStmt =
            "select json_path from D_PAR inner join D_CALC DC on D_PAR.name = DC.name inner join D_PROC_CALC DPC on DC.id = DPC.calc where DPC.proc=? and flag=1";

    public Process(int procId, JSONDocument doc){
        this.procId = procId;
        this.doc = doc;
    }

    public int getProcId() {
        return procId;
    }

    public JSONDocument getDoc() {
        return doc;
    }

    public List<Calculation> getCalculations() throws Exception {
        log.info("Getting calculations of process" + procId);
        List<Calculation> calculations = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getCalcStmt)) {
            pt.setInt(1, procId);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int type = rs.getInt(3);
                String script = ScriptUtils.getFormattedScript(rs.getString(4), this);
                calculations.add(CalculationFactory.getCalculation(id, type, name, procId, script));
            }
        }
        return calculations;
    }

    public String getFlagJsonPath() throws SQLException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement pt = connection.prepareStatement(getFlagStmt)){
            pt.setInt(1, getProcId());
            ResultSet rs = pt.executeQuery();
            if (rs.next()){
                return rs.getString(1);
            } else {
                throw new SQLException("Не найден терминируюший флаг для процесса " + getProcId());
            }
        }
    }
}
