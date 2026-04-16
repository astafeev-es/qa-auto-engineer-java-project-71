package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;
import java.util.StringJoiner;

public class Differ {

    private Differ() {
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Path path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filePath2).toAbsolutePath().normalize();

        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map1 = mapper.readValue(content1, new TypeReference<Map<String, Object>>() { });
        Map<String, Object> map2 = mapper.readValue(content2, new TypeReference<Map<String, Object>>() { });

        Set<String> keys = new TreeSet<>(map1.keySet());
        keys.addAll(map2.keySet());

        StringJoiner sj = new StringJoiner("\n", "{\n", "\n}");

        for (String key : keys) {
            if (map1.containsKey(key) && !map2.containsKey(key)) {
                sj.add("  - " + key + ": " + map1.get(key));
            } else if (!map1.containsKey(key) && map2.containsKey(key)) {
                sj.add("  + " + key + ": " + map2.get(key));
            } else if (Objects.equals(map1.get(key), map2.get(key))) {
                sj.add("    " + key + ": " + map1.get(key));
            } else {
                sj.add("  - " + key + ": " + map1.get(key));
                sj.add("  + " + key + ": " + map2.get(key));
            }
        }

        return sj.toString();
    }
}
