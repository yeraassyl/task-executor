package kz.kaspi.lab.calc.scripting.utils;

import kz.kaspi.lab.error.Error;
import kz.kaspi.lab.process.Process;
import kz.kaspi.lab.process.utils.ProcessUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ScriptUtils {
    private static final Logger log = Logger.getLogger(ScriptUtils.class);
    private static final String relativeScriptPath = "scripts/";

    public static String parseScript(String filename) throws Exception{
        log.info("PARSING SCRIPT " + filename);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(relativeScriptPath + filename);
        StringBuilder sb = new StringBuilder();
        assert url != null;
        try (BufferedReader reader = new BufferedReader(new FileReader(url.getPath()))){
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }


    public static String formatScript(String script, Map<String, String> params) {
        for (String key: params.keySet()){
            script = script.replaceFirst(String.format("#%s#", key), params.get(key));
        }
        return script;
    }

    public static String getFormattedScript(String scriptPath, Process proc) throws Exception {
        log.info("Getting formatted script of" + scriptPath);
        String script = "";
        if (scriptPath != null) {
            script = parseScript(scriptPath);
            List<String> params = ProcessUtils.parseParams(script);
            Map<String, String> parameters = ProcessUtils.parseValues(params, proc);
            script = formatScript(script, parameters);
        }
        return script;
    }
}
