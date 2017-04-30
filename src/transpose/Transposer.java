package transpose;

import java.io.*;
import java.util.*;

public class Transposer {

    private int width;

    private boolean alignRight;

    private boolean cut;

    public Transposer(int width, boolean alignRight, boolean cut) {
        this.width = width;
        this.alignRight = alignRight;
        this.cut = cut;
    }

    public void transpose(InputStream in, OutputStream outputStream) throws IOException {
        PrintWriter out = new PrintWriter(outputStream);
        try (Scanner s = new Scanner(in)) {
            List<List<String>> list = new ArrayList<>();
            int maxRowSize = 0;
            while (s.hasNextLine()) {
                List<String> row = new ArrayList<>();
                Collections.addAll(row,(s.nextLine()).split("[ ]+"));
                if (row.size() > maxRowSize) maxRowSize = row.size();
                list.add(row);
            }
            List<String> result = new ArrayList<>();
            for (int i = 0; i < maxRowSize; i++) {
                StringBuilder string = new StringBuilder();
                for (int j = 0; j < maxRowSize; j++) {
                    try {
                        String word = list.get(j).get(i);
                        if (cut)
                            string.append(word.substring(0, width)).append(" ");
                        else string.append(word).append(" ");
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                result.add(string.toString());
            }
            int aligner = list.size();
            String al = "%" + (aligner * 2 - 1) + "s";
            for (int i = 0; i < result.size(); i++) {
                String str = result.get(i).trim();
                if (alignRight) out.write(String.format(al, str));
                else out.write(str);
                if (i != result.size() - 1)
                    out.write("\n");
            }
        }
        out.close();
    }
}