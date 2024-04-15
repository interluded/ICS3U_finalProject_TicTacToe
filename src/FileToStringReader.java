import java.io.FileReader;
import java.io.IOException;

public class FileToStringReader {

    public static void main(String[] args) {
        FileToStringReader reader = new FileToStringReader();
        String fileContents = reader.readFileToString("/Users/marcuskongjika/IdeaProjects/filetester/src/coins.txt");
        System.out.println("File Contents:");
        System.out.println(fileContents);
    }

    public String readFileToString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(filePath)) {
            int character;
            while ((character = reader.read()) != -1) {
                contentBuilder.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
