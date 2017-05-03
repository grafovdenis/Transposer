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
                String[] words = s.nextLine().split("[ ]+");
                Collections.addAll(row, words);
                if (row.size() > maxRowSize) maxRowSize = row.size();
                list.add(row);
            }

            String align = ((this.alignRight) || (width == 0)) ? "" : "-";
            String widthToStr = width == 0 ? "" : Integer.toString(width);
            for (int i = 0; i < maxRowSize; i++) {
                StringBuilder str = new StringBuilder();
                for (int j = 0; j < maxRowSize; j++) {
                    try {
                        String word = String.format("%" + align + widthToStr + "s", list.get(j).get(i));
                        if (cut)
                            word = word.substring(0, width);
                        str.append(word);
                        int len = (width != 0) ? width - word.length() + 1 : word.length();
                        if (j != maxRowSize - 1)
                            for (int n = 0; n < len; n++) {
                                str.append(" ");
                            }
                    } catch (IndexOutOfBoundsException ignored) {
                        int len = width != 0 ? width : 1;
                        if (j != maxRowSize - 1)
                            for (int n = 0; n < len + 1; n++) {
                                str.append(" ");
                            }
                    }
                }
                out.println(str);
            }
        }
        out.close();
    }
}