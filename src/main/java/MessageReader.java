import java.io.BufferedReader;
import java.io.IOException;

public class MessageReader {
    public static String read(BufferedReader in) throws IOException {
        StringBuilder stringBuilder = new StringBuilder().append(in.readLine());

        while (in.ready()) {
            stringBuilder.append("\n").append(in.readLine());
        }

        return stringBuilder.toString();
    }
}
