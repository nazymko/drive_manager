package rt.util;



import java.io.IOException;
import java.io.InputStream;

/**
 * User: Andrew
 */
public class IO {
    public static String readStream(InputStream in) throws IOException {
        int read = 0;
        byte buff[] = new byte[1024 * 8];
        StringBuilder result = new StringBuilder();
        while ((read = in.read(buff)) != -1) {
            result.append(new String(buff, 0, read));

        }
        return result.toString();

    }
}
