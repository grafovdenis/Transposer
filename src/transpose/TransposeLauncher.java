package transpose;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class TransposeLauncher {
    @Option(name = "file", metaVar = "file", usage = "Задаёт имя входного файла")
    private String file;

    @Option(name = "-o", metaVar = "ofile", required = true, usage = "Задаёт имя выходного файла")
    private String outFile;

    @Option(name = "-a", metaVar = "num", required = true, usage = "Каждое слово занимет num символов, остальное-пробелы")
    private String num;

    @Option(name = "-t", usage = "Если слово не влезает, то его обрезаем до нужного размера")
    private String split;

    @Option(name = "-r", usage = "Выравниваем по правой границе")
    private String alignRight;

    public static void main(String[] args) {
        new TransposeLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java - jar transpose.jar -a num -t -t -o ofile file");
            parser.printUsage(System.err);
        }
    }
}
