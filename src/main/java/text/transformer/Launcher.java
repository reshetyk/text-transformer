package text.transformer;

import text.transformer.service.TextAnalyzer;
import text.transformer.service.TextTransformer;
import text.transformer.service.writer.CsvSentenceWriter;

public class Launcher {

    public static void main(String[] args) throws Exception {

        TextTransformer textTransformer = new TextTransformer(
                new CsvSentenceWriter(System.out, ", "),
                new TextAnalyzer(), "UTF-8"
        );

        textTransformer.transform(System.in);
    }
}
