package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.Map;

public final class Parser {

    private Parser() {
    }

    public static Map<String, Object> parse(String content, String format) throws Exception {
        ObjectMapper mapper = getObjectMapper(format);
        return mapper.readValue(content, new TypeReference<Map<String, Object>>() { });
    }

    private static ObjectMapper getObjectMapper(String format) {
        if (format.equalsIgnoreCase("json")) {
            return new ObjectMapper();
        } else if (format.equalsIgnoreCase("yml") || format.equalsIgnoreCase("yaml")) {
            return new YAMLMapper();
        }
        throw new IllegalArgumentException("Unknown format: '" + format + "'");
    }
}
