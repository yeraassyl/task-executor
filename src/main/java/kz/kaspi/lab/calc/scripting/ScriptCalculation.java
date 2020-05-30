package kz.kaspi.lab.calc.scripting;

import kz.kaspi.lab.calc.Calculation;

public abstract class ScriptCalculation extends Calculation {
    private String script;

    public ScriptCalculation(int id, String name, int procId, String script) {
        super(id, name, procId);
        this.script = script;
    }

    public String getScript(){
        return script;
    }

    public void setScript(String script){
        this.script = script;
    }
}
