package kz.kaspi.lab.error;

import org.apache.commons.lang3.StringUtils;

public final class Error {
    private static final String ERROR_1 = "Код ошибки не может быть пустым";

    public static String printError(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }

    public static void raise(String code, Object... args) throws Exception { throw new Exception(_message(code, args)); }

    private static String checkCode(String code) throws Exception {
        if (StringUtils.isEmpty(code)) { raise(ERROR_1); }
        return code.trim().toUpperCase();
    }

    public static String format(String code, Object... args) throws Exception { return String.format(code, args); }

    public static String _message(String code, Object ... args) throws Exception { return format(checkCode(code), args); }
}
