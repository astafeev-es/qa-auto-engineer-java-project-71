package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;
import java.util.List;
import java.util.Map;

public final class Formatter {
    private Formatter() {
    }
    public static String render(List<Map<String, Object>> diff, String format) throws Exception {
        switch (format) {
            case "stylish":
                return Stylish.render(diff);
            case "plain":
                return Plain.render(diff);
            case "json":
                return Json.render(diff);
            default:
                throw new IllegalArgumentException("Unknown format: '" + format + "'");
        }
    }
}
