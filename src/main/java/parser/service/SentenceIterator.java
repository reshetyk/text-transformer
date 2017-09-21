package parser.service;

import java.text.BreakIterator;
import java.util.Iterator;
import java.util.Locale;

public class SentenceIterator implements Iterator<String>, Iterable<String>{
    private String text;
    private Locale locale;

    private BreakIterator sentenceInstance;
    private int endSentence;
    private int startSentence;

    public SentenceIterator(String text, Locale locale) {
        sentenceInstance = BreakIterator.getSentenceInstance(locale);
        sentenceInstance.setText(text);
        startSentence = sentenceInstance.first();
        endSentence = sentenceInstance.next();
        this.text = text;
        this.locale = locale;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return endSentence != BreakIterator.DONE;
    }

    @Override
    public String next() {
        String sentence = text.substring(startSentence, endSentence).trim();
        startSentence = endSentence;
        endSentence = sentenceInstance.next();
        return sentence;
    }
}
