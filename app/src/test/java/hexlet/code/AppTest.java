package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;
import picocli.CommandLine;

public final class AppTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath);
    }

    public static Stream<Object[]> data() {
        return Stream.of(
            new Object[] {"file1.json", "file2.json", "stylish", "expected_stylish.txt"},
            new Object[] {"file1.json", "file2.json", "plain", "expected_plain.txt"},
            new Object[] {"file1.json", "file2.json", "json", null},
            new Object[] {"file1.json", "file2.json", null, "expected_stylish.txt"},
            new Object[] {"file1.yml", "file2.yml", "stylish", "expected_stylish.txt"},
            new Object[] {"file1.yml", "file2.yml", "plain", "expected_plain.txt"},
            new Object[] {"file1.yml", "file2.yml", "json", null},
            new Object[] {"file1.yml", "file2.yml", null, "expected_stylish.txt"}
        );
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testGenerate(String file1, String file2, String format, String expectedFile) throws Exception {
        String filePath1 = getFixturePath(file1).toString();
        String filePath2 = getFixturePath(file2).toString();

        String actual;
        if (format == null) {
            actual = Differ.generate(filePath1, filePath2);
        } else {
            actual = Differ.generate(filePath1, filePath2, format);
        }

        if ("json".equals(format)) {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.List<java.util.Map<String, Object>> actualList = mapper.readValue(actual,
                    new com.fasterxml.jackson.core.type.TypeReference<>() { });
            assertTrue(actualList.size() > 0);
        } else {
            String expected = readFixture(expectedFile);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testParserException() {
        try {
            Parser.parse("content", "unknown");
        } catch (Exception e) {
            assertEquals("Unknown format: 'unknown'", e.getMessage());
        }
    }

    @Test
    public void testNoExtension() {
        String filePath1 = getFixturePath("file1").toString();
        String filePath2 = getFixturePath("file2").toString();
        try {
            Differ.generate(filePath1, filePath2);
        } catch (Exception e) {
            assertEquals("Unknown format: ''", e.getMessage());
        }
    }

    @Test
    public void testFormatterException() {
        try {
            Formatter.render(new java.util.ArrayList<>(), "unknown");
        } catch (Exception e) {
            assertEquals("Unknown format: 'unknown'", e.getMessage());
        }
    }

    @Test
    public void testApp() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();

        int exitCode = new CommandLine(new App()).execute(filePath1, filePath2);

        assertEquals(0, exitCode);
        String expected = readFixture("expected_stylish.txt");
        assertTrue(outputStreamCaptor.toString().contains(expected));
    }

    @Test
    public void testHelp() {
        int exitCode = new CommandLine(new App()).execute("-h");
        assertEquals(0, exitCode);
        assertTrue(outputStreamCaptor.toString().contains("Usage: gendiff"));
    }

    @Test
    public void testVersion() {
        int exitCode = new CommandLine(new App()).execute("-V");
        assertEquals(0, exitCode);
        assertTrue(outputStreamCaptor.toString().contains("gendiff 1.0"));
    }
}
