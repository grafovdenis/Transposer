package transpose;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


import java.io.*;
import java.util.Objects;

public class TransposerLauncher {
    @Option(name = "-file", metaVar = "file", usage = "Задаёт имя входного файла")
    private File inFile;

    @Option(name = "-o", metaVar = "ofile", usage = "Задаёт имя выходного файла")
    private String outFile;

    @Option(name = "-a", metaVar = "width", usage = "Каждое слово занимет num символов, остальное-пробелы")
    private int width;

    @Option(name = "-t", metaVar = "cut", usage = "Если слово не влезает, то его обрезаем до нужного размера")
    private boolean cut;

    @Option(name = "-r", usage = "Выравниваем по правой границе")
    private boolean alignRight;

    public static void main(String[] args) {
        new TransposerLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
            if (Objects.deepEquals(width, null) && (cut || alignRight)) width = 10;
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java - jar transpose.jar -a width -t cut -r -o ofile -file");
            parser.printUsage(System.err);
            return;
        }
        Transposer transpose = new Transposer(width, alignRight, cut);
        try {
            InputStream input = inFile != null ? new FileInputStream(inFile) : new BufferedInputStream(System.in);
            OutputStream output = outFile != null ? new FileOutputStream(outFile) : new BufferedOutputStream(System.out);
            transpose.transpose(input, output);
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }
}