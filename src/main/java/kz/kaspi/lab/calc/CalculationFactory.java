package kz.kaspi.lab.calc;

import kz.kaspi.lab.calc.dtab.DecisionTab;
import kz.kaspi.lab.calc.scripting.groovy.GroovyCalculation;
import kz.kaspi.lab.calc.scripting.sql.SQLCalculation;
import org.apache.log4j.Logger;

public class CalculationFactory {
    private static final Logger log = Logger.getLogger(CalculationFactory.class);

    public static Calculation getCalculation(int id, int type, String name, int procId, String script){
        if (type == 1){
            return new GroovyCalculation(id, name, procId, script);
        } else if (type == 2){
            return new SQLCalculation(id, name, procId, script);
        } else {
            return new DecisionTab(id, name, procId);
        }
    }
}
