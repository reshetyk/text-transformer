package service;

import org.junit.Test;
import parser.domain.ParsedText;
import parser.domain.Sentence;
import parser.domain.Word;
import parser.service.TextParser;

import static org.assertj.core.api.Assertions.assertThat;

public class TextParserTest {

    @Test
    public void checkThatInputTextIsSplitIntoSentencesAndWords() throws Exception {

        ParsedText parsedTextExpected = new ParsedText();
        parsedTextExpected
                .add(new Sentence()
                        .add(new Word("a"))
                        .add(new Word("had"))
                        .add(new Word("lamb"))
                        .add(new Word("little"))
                        .add(new Word("Mary")))
                .add(new Sentence()
                        .add(new Word("Aesop"))
                        .add(new Word("and"))
                        .add(new Word("called"))
                        .add(new Word("came"))
                        .add(new Word("for"))
                        .add(new Word("Peter"))
                        .add(new Word("the"))
                        .add(new Word("wolf")))
                .add(new Sentence()
                        .add(new Word("Cinderella"))
                        .add(new Word("likes"))
                        .add(new Word("shoes")));

        String text = "Mary had a little lamb .\n\n Peter called for the wolf , and Aesop came .\n Cinderella likes shoes..";
        ParsedText parsedTextActual = new TextParser().parse(text);

        assertThat(parsedTextExpected).isEqualToComparingFieldByField(parsedTextActual);

    }


}
