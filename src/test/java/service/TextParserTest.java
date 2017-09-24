package service;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import parser.service.TextParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextParserTest {

    @Test
    public void checkThatInputTextIsSplitIntoSentencesAndWords() throws Exception {
/*
        ParsedText parsedTextExpected = new ParsedText();
        parsedTextExpected
                .add(new SentenceSortedWords()
                        .add(new Word("a"))
                        .add(new Word("had"))
                        .add(new Word("lamb"))
                        .add(new Word("little"))
                        .add(new Word("Mary")))
                .add(new SentenceSortedWords()
                        .add(new Word("Aesop"))
                        .add(new Word("and"))
                        .add(new Word("called"))
                        .add(new Word("came"))
                        .add(new Word("for"))
                        .add(new Word("Peter"))
                        .add(new Word("the"))
                        .add(new Word("wolf")))
                .add(new SentenceSortedWords()
                        .add(new Word("Cinderella"))
                        .add(new Word("likes"))
                        .add(new Word("shoes")));
*/
//        String text = "Mary had a little lamb .\n\n Peter called for the wolf , and Aesop came .\n Cinderella likes shoes..";
        InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("input/small.in");
        InputStream expected = Thread.currentThread().getContextClassLoader().getResourceAsStream("expected/small.csv");

        ByteArrayOutputStream actual = new ByteArrayOutputStream();

        new TextParser().parse(input, actual);

        assertThat(new String (actual.toByteArray()), is(IOUtils.toString(expected)));
//        assertThat(parsedTextExpected).isEqualToComparingFieldByField(parsedTextActual);

    }


}
