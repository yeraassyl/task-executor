package kz.kaspi.lab.calc.scripting.groovy;

import javafx.util.Pair;
import kz.kaspi.lab.calc.scripting.ScriptCalculation;
import kz.kaspi.lab.db.utils.DBLogger;
import kz.kaspi.lab.error.Error;
import kz.kaspi.lab.json.doc.JSONDocument;
import org.apache.log4j.Logger;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.LocalDateTime;

public class GroovyCalculation extends ScriptCalculation {
    private static final Logger log = Logger.getLogger(GroovyCalculation.class);
    private static final ScriptEngineManager manager = new ScriptEngineManager();

    public GroovyCalculation(int id, String name, int procId, String script){
        super(id, name, procId, script);
    }

    @Override
    public void eval(JSONDocument doc) throws Exception {
        log.info(getEvalLog());
        DBLogger.procLog(LocalDateTime.now(), getEvalLog());
        ScriptEngine engine = manager.getEngineByName("groovy");
        Pair<String, String> pair = getCalcResultParam();
        try {
            Object result = engine.eval(getScript());
            String jsonPath = pair.getKey();
            String type = pair.getValue();
            doc.setVal(jsonPath, (String) result);
            log.info(doc.getText());
        } catch (Exception e){
            String errMessage = Error._message("Невозможно выполнить по причине:\n %s", e.getMessage());
            Error.raise(errMessage);
            DBLogger.errLog(LocalDateTime.now(), errMessage);
        }
        log.info(getEvalSuccessLog());
        DBLogger.procLog(LocalDateTime.now(), getEvalSuccessLog());
    }

}
