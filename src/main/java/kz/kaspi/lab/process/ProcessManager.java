package kz.kaspi.lab.process;
import kz.kaspi.lab.calc.Calculation;
import kz.kaspi.lab.process.utils.ProcessUtils;
import org.apache.log4j.Logger;

import java.util.List;

public class ProcessManager {

    public static final Logger log = Logger.getLogger(ProcessManager.class);

    public static String execute(String procCode, String json) throws Exception {
        log.info("Executing process " + procCode);
        int procId = ProcessUtils.getProcId(procCode);
        Process process = new Process(procId, ProcessUtils.parse(json));
        List<Calculation> calculations = process.getCalculations();
        for (Calculation calc: calculations) {
            calc.eval(process.getDoc());
        }
        return process.getDoc().getVal(process.getFlagJsonPath());
    }
}
