package reminator.RemiBot.utils;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IOUtils {
    public static String readAsString(InputStream inputStream) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            while ((line = reader.readLine()) != null) {
                strBuilder.append(line.trim()).append("\n");
            }
        } catch (IOException ignored) {
        }
        inputStream.close();
        return strBuilder.toString();
    }

    public static List<String> readLines(File f) throws IOException {
        return new ArrayList<String>(Arrays.asList(readAsString(new FileInputStream(f)).split("\n")));
    }

    public static void writeLines(File f, List<String> lines) throws FileNotFoundException {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(f))) {
            for (String line : lines) {
                writer.println(line);
            }
        }
    }
}
