package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class Stylish {
    private Stylish() {
    }
    public static String render(List<Map<String, Object>> diff) {
        StringJoiner sj = new StringJoiner("\n", "{\n", "\n}");

        for (Map<String, Object> node : diff) {
            String key = (String) node.get("key");
            String type = (String) node.get("type");

            switch (type) {
                case "added":
                    sj.add("  + " + key + ": " + node.get("newValue"));
                    break;
                case "deleted":
                    sj.add("  - " + key + ": " + node.get("oldValue"));
                    break;
                case "unchanged":
                    sj.add("    " + key + ": " + node.get("oldValue"));
                    break;
                case "changed":
                    sj.add("  - " + key + ": " + node.get("oldValue"));
                    sj.add("  + " + key + ": " + node.get("newValue"));
                    break;
                default:
                    throw new RuntimeException("Unknown node type: " + type);
            }
        }

        return sj.toString();
    }
}
