package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class Plain {

    private Plain() {
    }

    public static String render(List<Map<String, Object>> diff) {
        StringJoiner sj = new StringJoiner("\n");

        for (Map<String, Object> node : diff) {
            String key = (String) node.get("key");
            String type = (String) node.get("type");

            switch (type) {
                case "added":
                    sj.add("Property '" + key + "' was added with value: " + stringify(node.get("newValue")));
                    break;
                case "deleted":
                    sj.add("Property '" + key + "' was removed");
                    break;
                case "changed":
                    sj.add("Property '" + key + "' was updated. From " + stringify(node.get("oldValue"))
                            + " to " + stringify(node.get("newValue")));
                    break;
                case "unchanged":
                    break;
                default:
                    throw new RuntimeException("Unknown node type: " + type);
            }
        }

        return sj.toString();
    }

    private static String stringify(Object value) {
        if (value instanceof Map || value instanceof List) {
            return "[complex value]";
        }
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}
