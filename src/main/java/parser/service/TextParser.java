package parser.service;

import parser.domain.SentenceSortedWords;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class TextParser {

    private static List<String> shortenings = asList("Mr.", "Mrs.", "Ms.", "Prof.", "Dr.", "Gen.", "Rep.", "Sen.", "St.",
            "Sr.", "Jr.", "Ph.", "Ph.D.", "M.D.", "B.A.", "M.A.", "D.D.", "D.D.S.",
            "B.C.", "b.c.", "a.m.", "A.M.", "p.m.", "P.M.", "A.D.", "a.d.", "B.C.E.", "C.E.",
            "i.e.", "etc.", "e.g.", "al.");

    public void parse(InputStream inputStream, OutputStream outputStream) throws Exception {
        Scanner wordScanner = new Scanner(inputStream);

//        XmlSentenceWriter xmlWriter = new XmlSentenceWriter("UTF-8", "1.0", outputStream);
//        xmlWriter.writeStartDocument("text");

        CsvSentenceWriter csvWriter = new CsvSentenceWriter(outputStream, ", ");
        String charSeq;
        int sentenceNum = 0;
        while (wordScanner.hasNext()) {
            SentenceSortedWords sentence = new SentenceSortedWords();
            for (charSeq = wordScanner.next(); !isEndOfSentence(charSeq); charSeq = wordScanner.next()) {
                parseIntoCleanWords(charSeq, sentence);
            }
            parseIntoCleanWords(charSeq, sentence);
//            xmlWriter.writeSentence(sentence);
            csvWriter.write(sentence, "Sentence " + ++sentenceNum);
        }
        csvWriter.writeHeader();
//        xmlWriter.writeEndDocument();

        outputStream.flush();

    }

    private static void parseIntoCleanWords(String charSeq, SentenceSortedWords sentence) {
        sentence.addOriginal(charSeq);
        Arrays.stream(charSeq.split("[,\\(\\):;\\[\\]\\{\\}]")).forEach(word -> {
            if (isNotBlank(clean(word)))
                sentence.add(isShortening(word) ? word.trim() : clean(word));
        });

    }

    private static String clean(String word) {
        return word
                .trim()
                .replaceAll("\\p{Punct}$|^\\p{Punct}", "")
                .replace("’", "'");

    }

    private static boolean isEndOfSentence(String word) {
        word = word.trim();
        return word.matches(".*[?!.]+\\s*") && !isShortening(word);
    }

    private static boolean isShortening(String word) {
        return shortenings.contains(word);
    }
}
