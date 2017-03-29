package transpose;

import java.io.*;

public class Transpose {

    private final String charsetInput;

    private final String charsetOutput;

    public Transpose(String charsetInput, String charsetOutput) {
        this.charsetInput = charsetInput;
        this.charsetOutput = charsetOutput;
    }

    public int transposer(InputStream in, OutputStream out) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(in, charsetInput)) {
            try (OutputStreamWriter writer = new OutputStreamWriter(out, charsetOutput)) {
                int sym = reader.read();
                int count = 0;
                while (sym != -1) {
                    writer.write(sym);
                    count++;
                    sym = reader.read();
                }
                return count;
            }
        }
    }
}
