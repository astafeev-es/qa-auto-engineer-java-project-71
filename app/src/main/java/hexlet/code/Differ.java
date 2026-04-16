package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;
import java.util.StringJoiner;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        Path path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filePath2).toAbsolutePath().normalize();

        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        String format1 = getFileExtension(filePath1);
        String format2 = getFileExtension(filePath2);

        Map<String, Object> map1 = Parser.parse(content1, format1);
        Map<String, Object> map2 = Parser.parse(content2, format2);

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

    private static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return index == -1 ? "" : filePath.substring(index + 1);
    }
}
