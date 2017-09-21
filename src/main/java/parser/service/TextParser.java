package parser.service;

import parser.domain.ParsedText;
import parser.domain.Sentence;
import parser.domain.Word;

import java.io.IOException;
import java.util.Locale;

public class TextParser {

    public ParsedText parse(String text) throws IOException {
        ParsedText parsedText = new ParsedText();

        SentenceIterator sentenceIterator = new SentenceIterator(text, Locale.ENGLISH);

        sentenceIterator.forEach(s -> {
            Sentence sentence = new Sentence();
            System.out.println("sentence=" + s);

            WordIterator wordIterator = new WordIterator(s, Locale.ENGLISH);
            wordIterator.forEach(w -> {
                System.out.println("word=" + w);
                sentence.add(new Word(w));
            });
            parsedText.add(sentence);

        });

        return parsedText;

    }
}
