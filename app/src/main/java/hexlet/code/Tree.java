package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedHashMap;
import java.util.Objects;

public final class Tree {
    private Tree() {
    }
    public static List<Map<String, Object>> build(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> keys = new TreeSet<>(map1.keySet());
        keys.addAll(map2.keySet());

        List<Map<String, Object>> result = new ArrayList<>();

        for (String key : keys) {
            Map<String, Object> node = new LinkedHashMap<>();
            node.put("key", key);

            if (!map1.containsKey(key)) {
                node.put("type", "added");
                node.put("newValue", map2.get(key));
            } else if (!map2.containsKey(key)) {
                node.put("type", "deleted");
                node.put("oldValue", map1.get(key));
            } else if (Objects.equals(map1.get(key), map2.get(key))) {
                node.put("type", "unchanged");
                node.put("oldValue", map1.get(key));
            } else {
                node.put("type", "changed");
                node.put("oldValue", map1.get(key));
                node.put("newValue", map2.get(key));
            }
            result.add(node);
        }

        return result;
    }
}
