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
        String expected = readFixture("expected.txt");
        String actual = Differ.generate(filePath1, filePath2);
        assertEquals(expected, actual.trim());
    }

    @Test
    public void testApp() throws Exception {
        String filePath1 = getFixturePath("file1.json").toString();
        String filePath2 = getFixturePath("file2.json").toString();
        
        int exitCode = new CommandLine(new App()).execute(filePath1, filePath2);
        
        assertEquals(0, exitCode);
        String expected = readFixture("expected.txt");
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
