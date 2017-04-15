package transpose;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Transposer {

    private int width;

    private boolean alignRight;

    private boolean cut;

    public Transposer(int width, boolean alignRight, boolean cut) {
        this.width = width;
        this.alignRight = alignRight;
        this.cut = cut;
    }

    public String transpose(InputStream in, OutputStream outputStream) throws IOException {
        try (Scanner s = new Scanner(in).useDelimiter("\\A")) {
            String lines = s.hasNext() ? s.next() : "";
            lines = lines.replace("[", "").replace("]", "");
            PrintWriter out = new PrintWriter(outputStream);

            List<List<String>> list = new ArrayList<>();
            for (String x : lines.split(", ")) {
                List<String> row = new ArrayList<>();
                Collections.addAll(row, x.split("[ ]+"));
                list.add(row);
            }
            int maxRowSize = 0;
            for (int n = 0; n < list.size(); n++) {
                if (list.get(n).size() >= maxRowSize) maxRowSize = list.get(n).size();
            }
            List<List<String>> result = new ArrayList<>();
            for (int i = 0; i < maxRowSize; i++) {
                List<String> row = new ArrayList<>();
                for (int j = 0; j < maxRowSize; j++) {
                    try {
                        row.add(list.get(j).get(i));
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                result.add(row);
            }
            int size;
            for (int i = 0; i < result.size(); i++) {
                for (int j = 0; j < result.get(i).size(); j++) {
                    try {
                        if (cut) size = width;
                        else size =  result.get(i).get(j).length();
                        if (j != result.get(i).size() - 1)
                            out.write(result.get(i).get(j) + " ", 0, size);
                        else out.write(result.get(i).get(j),0, size);
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
                out.println();
            }
            out.close();
        }
        return "";
    }

    public String transpose(String inputName, String outputName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(inputName)) {
            try (FileOutputStream outputStream = new FileOutputStream(outputName)) {
                return transpose(inputStream, outputStream);
            }
        }
    }
}
