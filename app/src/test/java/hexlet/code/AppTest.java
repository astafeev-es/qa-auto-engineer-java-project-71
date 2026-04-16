package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import picocli.CommandLine;

public class AppTest {
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
        return Files.readString(filePath).trim();
    }

    @Test
    public void testGenerate() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();
        String expected = readFixture("expected_stylish.txt");
        String actual = Differ.generate(filePath1, filePath2);
        assertEquals(expected, actual.trim());
    }

    @Test
    public void testGeneratePlain() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();
        String expected = readFixture("expected_plain.txt");
        String actual = Differ.generate(filePath1, filePath2, "plain");
        assertEquals(expected, actual.trim());
    }

    @Test
    public void testGenerateJson() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();
        String actual = Differ.generate(filePath1, filePath2, "json");

        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        java.util.List<java.util.Map<String, Object>> actualList = mapper.readValue(actual,
                new com.fasterxml.jackson.core.type.TypeReference<java.util.List<java.util.Map<String, Object>>>() { });

        assertTrue(actualList.size() > 0);
        assertEquals("chars1", actualList.get(0).get("key"));
    }

    @Test
    public void testGenerateYaml() throws Exception {
        // Create YAML fixtures for complex structures
        String filePath1 = getFixturePath("file1.yml").toString();
        String filePath2 = getFixturePath("file2.yml").toString();
        String expected = readFixture("expected_stylish.txt");
        String actual = Differ.generate(filePath1, filePath2);
        assertEquals(expected, actual.trim());
    }

    @Test
    public void testParserException() {
        try {
            Parser.parse("content", "unknown");
        } catch (Exception e) {
            assertEquals("Unknown format: unknown", e.getMessage());
        }
    }

    @Test
    public void testNoExtension() {
        String filePath1 = getFixturePath("file1").toString();
        String filePath2 = getFixturePath("file2").toString();
        try {
            Differ.generate(filePath1, filePath2);
        } catch (Exception e) {
            assertEquals("Unknown format: ", e.getMessage());
        }
    }

    @Test
    public void testFormatterException() {
        try {
            Formatter.render(new java.util.ArrayList<>(), "unknown");
        } catch (Exception e) {
            assertEquals("Unknown format: unknown", e.getMessage());
        }
    }

    @Test
    public void testStylishException() {
        java.util.List<java.util.Map<String, Object>> diff = new java.util.ArrayList<>();
        java.util.Map<String, Object> node = new java.util.HashMap<>();
        node.put("key", "v");
        node.put("type", "unknown");
        diff.add(node);
        try {
            hexlet.code.formatters.Stylish.render(diff);
        } catch (Exception e) {
            assertEquals("Unknown node type: unknown", e.getMessage());
        }
    }

    @Test
    public void testPlainException() {
        java.util.List<java.util.Map<String, Object>> diff = new java.util.ArrayList<>();
        java.util.Map<String, Object> node = new java.util.HashMap<>();
        node.put("key", "v");
        node.put("type", "unknown");
        diff.add(node);
        try {
            hexlet.code.formatters.Plain.render(diff);
        } catch (Exception e) {
            assertEquals("Unknown node type: unknown", e.getMessage());
        }
    }

    @Test
    public void testApp() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();

        int exitCode = new CommandLine(new App()).execute(filePath1, filePath2);

        assertEquals(0, exitCode);
        String expected = readFixture("expected_stylish.txt");
        assertTrue(outputStreamCaptor.toString().trim().contains(expected));
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
