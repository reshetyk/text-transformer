package parser.service;

import java.text.BreakIterator;
import java.util.Iterator;
import java.util.Locale;

public class WordIterator implements Iterator<String>, Iterable<String>{
    private BreakIterator wordInstance;
    private String text;
    private int endWord;
    private int startWord;
    private String word;

    public WordIterator(String text, Locale locale) {
        wordInstance = BreakIterator.getWordInstance(locale);
        wordInstance.setText(text);
        startWord = wordInstance.first();
        endWord = wordInstance.next();
        this.text = text;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        if (endWord == BreakIterator.DONE)
            return false;

        word = text.substring(startWord, endWord).trim();
        while (!word.matches("[A-z]+")) {
            next();
            if (endWord == BreakIterator.DONE)
                return false;
            word = text.substring(startWord, endWord).trim();
        }
        return true;

    }

    @Override
    public String next() {
        startWord = endWord;
        endWord = wordInstance.next();
        return word;
    }
}
