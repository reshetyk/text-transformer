package text.transformer;

import text.transformer.service.TextAnalyzer;
import text.transformer.service.TextTransformer;
import text.transformer.service.writer.CsvSentenceWriter;
import text.transformer.service.writer.SentenceWriter;
import text.transformer.service.writer.XmlSentenceWriter;

public class Launcher {

    public static final String ENCODING = "UTF-8";
    public static final String XML_ARG = "xml";
    public static final String CSV_ARG = "csv";

    public static void main(String[] args) throws Exception {

        validateArgs(args);

        SentenceWriter sentenceWriter = null;

        if (args[0].equalsIgnoreCase(CSV_ARG)) {
            sentenceWriter = new CsvSentenceWriter(System.out, ", ", ENCODING);
        } else if (args[0].equalsIgnoreCase(XML_ARG)) {
            sentenceWriter = new XmlSentenceWriter(ENCODING, "1.0", System.out);
        }

        TextTransformer textTransformer = new TextTransformer(sentenceWriter, new TextAnalyzer(), ENCODING);

        textTransformer.transform(System.in);
    }

    private static void validateArgs(String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase(XML_ARG) && !args[0].equalsIgnoreCase(CSV_ARG))
            throw new RuntimeException("Please set java arg '" + XML_ARG + "' or '" + CSV_ARG + "'!");
    }
}
