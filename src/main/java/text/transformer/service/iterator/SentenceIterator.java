package text.transformer.service.iterator;

import text.transformer.service.TextAnalyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Scanner;

public class SentenceIterator implements Iterator<String>, Iterable<String> {

    private Scanner scanner;
    private TextAnalyzer textAnalyzer;


    public SentenceIterator(InputStream inputStream, String encoding, TextAnalyzer textAnalyzer) throws IOException {
        this.textAnalyzer = textAnalyzer;
        this.scanner = new Scanner(inputStream, encoding);
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        StringBuilder sb = new StringBuilder();
        String charSeq;
        for (charSeq = scanner.next(); !textAnalyzer.isEndOfSentence(charSeq); charSeq = scanner.next()) {
            sb.append(" " + charSeq);
        }
        sb.append(" " + charSeq);

        return sb.toString();
    }


}
