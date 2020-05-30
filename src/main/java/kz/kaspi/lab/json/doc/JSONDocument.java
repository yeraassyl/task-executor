package kz.kaspi.lab.json.doc;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kz.kaspi.lab.error.Error;

public class JSONDocument {
    private final static int CONSTM1 = -1;
    private final static int CONST0 = 0;
    private final static int CONST1 = 1;

    private static final String ERROR_1 = "Код ошибки не может быть пустым";
    private static final String ERROR_2 = "Тип данных \"%s\" не поддерживается";
    private static final String ERROR_3 = "Тип данных не может быть пустым";
    private static final String ERROR_4 = "Узел \"%s\" не может быть добавлен к родительскому узлу \"%s\", поскольку это не контейнер, а \"%s\"";

    //Типы данных
    private static final String DT_TEXT = "VARCHAR";

    private static final String DOLAR = "$";
    private static final String LBRACKET = "\\[";
    private static final String RBRACKET = "\\]";
    private static final String DOTS = "[.]+";
    private static final String SLASHES = "[//]+";
    private static final String SLASH = "/";

    private static final String INDEX_PATTERN = "\\/\\d+";
    private static final String INDEX_SLASH = "/%d";

    private ObjectMapper mapper = null;

    private JsonNode root = null;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    public JSONDocument() {
        mapper = new ObjectMapper();
        root = mapper.createObjectNode();
    }

    public void clear() {
        writeLock.lock();
        try {
            root = mapper.createObjectNode();
        } finally {
            writeLock.unlock();
        }
    }

    public String getVal(String path, String dataType) throws Exception {
        return _getVal(path, dataType);
    }

    public String getVal(String path) throws Exception {
        if (_getVal(path, DT_TEXT) == null){
            return "";
        }
        return _getVal(path, DT_TEXT);
    }


    public int getCount(String path) throws Exception {
        readLock.lock();
        try {
            return _getCount(path);
        } finally {
            readLock.unlock();
        }
    }

    public String getText() throws Exception {
        readLock.lock();
        try {
            return root.toString();
        } finally {
            readLock.unlock();
        }
    }

    public void setVal(String path, String value) throws Exception {
        writeLock.lock();
        try {
            setNode(path, getJsonNode(value));
        } finally {
            writeLock.unlock();
        }
    }

    public void parse(String json) throws Exception {
        writeLock.lock();
        try {
            root = mapper.readTree(json);
        } finally {
            writeLock.unlock();
        }
    }

    private String changeIndexes(String path) throws Exception {
        int minus = CONST0;
        String out = path;
        Pattern p = Pattern.compile(INDEX_PATTERN);
        Matcher m = p.matcher(path);
        while(m.find()) {
            int pos = m.start() - minus;
            int index = Integer.parseInt(m.group().substring(CONST1));
            String srcStr = String.format(INDEX_SLASH, index);
            String dstStr = String.format(INDEX_SLASH, index - CONST1);
            String left = out.substring(CONST0, pos);
            String rigth = out.substring(pos + srcStr.length());
            out = left + dstStr + rigth;
            minus += (srcStr.length() - dstStr.length());
        }
        return out;
    }

    private String preparePath(String path) throws Exception {
        String out =
            path.replace(DOLAR, SLASH).
                replaceAll(LBRACKET, SLASH).
                replaceAll(RBRACKET, SLASH).
                replaceAll(DOTS, SLASH).
                replaceAll(SLASHES, SLASH)
            ;
        return SLASH.equals(out) ? out : changeIndexes(!out.endsWith(SLASH) ? out : out.substring(0, out.length() - CONST1));
    }

    private JsonNode getJsonNode(String value) {
        if (!value.isEmpty()) {
            return new TextNode(value);
        }
        return NullNode.getInstance();
    }

    private JsonNode checkParent(JsonPointer parentPointer, JsonNode parentNode, String nodeName) throws Exception {
        JsonNode res = parentNode;
        if (res.isMissingNode() || res.isNull()) {
            res = StringUtils.isNumeric(nodeName) ? mapper.createArrayNode() : mapper.createObjectNode();
            setNode(parentPointer, res);
        }
        return res;
    }

    private String getNodeName(JsonPointer pointer) throws Exception {
        return pointer.last().toString().substring(CONST1);
    }

    private void checkArray(ArrayNode parentNode, JsonNode node, String nodeName) throws Exception {
        int index = Integer.parseInt(nodeName);
        for (int i = parentNode.size(); i <= index;  i++) { parentNode.addObject(); }
        parentNode.set(index, node);
    }

    private void checkObject(ObjectNode parentNode, JsonNode node, String nodeName) throws Exception {
        parentNode.set(nodeName, node);
    }

    private void raiseUnknowing(JsonNode parentNode, String nodeName, JsonPointer parent) throws Exception {
        Error.raise(ERROR_4, nodeName, getNodeName(parent), parentNode.getNodeType().name());
    }

    private void checkNode(JsonPointer parent, JsonNode parentNode, JsonNode node, String nodeName) throws Exception {
        if (parentNode.isArray()) {
            checkArray((ArrayNode) parentNode, node, nodeName);
        } else if (parentNode.isObject()) {
            checkObject((ObjectNode) parentNode, node, nodeName);
        } else {
            raiseUnknowing(parentNode, nodeName, parent);
        }
    }

    private void setNode(JsonPointer parentPointer, JsonNode parentNode, JsonNode node, String nodeName) throws Exception {
        checkNode(parentPointer, checkParent(parentPointer, parentNode, nodeName), node, nodeName);
    }

    private void setNode(JsonPointer parentPointer, JsonPointer pointer, JsonNode node) throws Exception {
        setNode(parentPointer, root.at(parentPointer), node, getNodeName(pointer));
    }

    private void setNode(JsonPointer pointer, JsonNode node) throws Exception {
        setNode(pointer.head(), pointer, node);
    }

    private void setNode(String path, JsonNode node) throws Exception {
        String prepared = preparePath(path);
        if (!SLASH.equals(prepared)) {
            setNode(JsonPointer.compile(preparePath(path)), node);
        } else { root = node; }
    }

    private JsonNode getNode(String path) throws Exception {
        String prepared = preparePath(path);
        JsonNode node = !SLASH.equals(prepared) ? root.at(JsonPointer.compile(prepared)) : root;
        return !node.isMissingNode() && !node.isNull() ? node : null;
    }

    private int _getCount(String path) throws Exception {
        JsonNode node = getNode(path);
        return node != null ? node.isArray() ? node.size() : CONST1: CONSTM1;
    }

    private String readVal(String path, String dataType) throws Exception {
        JsonNode node = getNode(path);
        if (node != null) {
            if (!StringUtils.isEmpty(dataType)) {
                if (DT_TEXT.equals(dataType.trim().toUpperCase())) {
                    return node.asText();
                } else {
                    Error.raise(ERROR_2, dataType.trim().toUpperCase());
                }
            } else { Error.raise(ERROR_3); }
        }
        return null;
    }
    private String _getVal(String path, String dataType) throws Exception {
        readLock.lock();
        try {
            return readVal(path, dataType);
        } finally {
            readLock.unlock();
        }
    }
}
