package text.transformer.service.iterator;

import text.transformer.service.TextAnalyzer;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public class WordIterator implements Iterator<String>, Iterable<String>{

    private TextAnalyzer textAnalyzer;
    private Queue<String> words;

    public WordIterator(String text, TextAnalyzer textAnalyzer) {
        this.textAnalyzer = textAnalyzer;
        words = parseIntoCleanWords(text);
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return !words.isEmpty();
    }

    @Override
    public String next() {
        return words.poll();
    }

    private Queue<String> parseIntoCleanWords(String charSeq) {
        return stream(charSeq.split("[\\s,\\(\\):;\\[\\]\\{\\}]"))
                .filter(word -> isNotBlank(clean(word)))
                .map(word -> textAnalyzer.isShortening(word) ? word.trim() : clean(word))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private String clean(String word) {
        return word.trim()
                .replaceAll("\\p{Punct}$|^\\p{Punct}", "")
                .replaceAll("’", "'")
                .replace((char) 8217, (char) 39);
    }

}
