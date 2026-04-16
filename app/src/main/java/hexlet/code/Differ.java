package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public final class Differ {

    private Differ() {
    }

    public static String generate(String filePath1, String filePath2) throws Exception {
        return generate(filePath1, filePath2, "stylish");
    }

    public static String generate(String filePath1, String filePath2, String format) throws Exception {
        Path path1 = Paths.get(filePath1).toAbsolutePath().normalize();
        Path path2 = Paths.get(filePath2).toAbsolutePath().normalize();

        String content1 = Files.readString(path1);
        String content2 = Files.readString(path2);

        String format1 = getFileExtension(filePath1);
        String format2 = getFileExtension(filePath2);

        Map<String, Object> map1 = Parser.parse(content1, format1);
        Map<String, Object> map2 = Parser.parse(content2, format2);

        List<Map<String, Object>> diff = Tree.build(map1, map2);

        return Formatter.render(diff, format);
    }

    private static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf('.');
        return index == -1 ? "" : filePath.substring(index + 1);
    }
}
