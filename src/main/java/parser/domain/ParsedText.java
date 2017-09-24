package parser.domain;

import java.util.ArrayList;
import java.util.List;

public class ParsedText {

    private List<SentenceSortedWords> sentences = new ArrayList ();

    public ParsedText add(SentenceSortedWords sentence) {
        sentences.add(sentence);
        return this;
    }

    @Override
    public String toString() {
        return "ParsedText{" +
                "sentences=" + sentences +
                '}';
    }
}
