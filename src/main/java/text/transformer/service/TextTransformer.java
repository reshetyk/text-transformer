package text.transformer.service;

import text.transformer.domain.SentenceSortedWords;
import text.transformer.service.iterator.SentenceIterator;
import text.transformer.service.iterator.WordIterator;
import text.transformer.service.writer.SentenceWriter;

import java.io.InputStream;

public class TextTransformer {

    private SentenceWriter sentenceWriter;
    private TextAnalyzer textAnalyzer;
    private String encoding;

    public TextTransformer(SentenceWriter sentenceWriter, TextAnalyzer textAnalyzer, String encoding) {
        this.sentenceWriter = sentenceWriter;
        this.textAnalyzer = textAnalyzer;
        this.encoding = encoding;
    }

    public void transform(InputStream inputStream) throws Exception {
        try {
            sentenceWriter.startWriting();

            int sentenceNum = 0;
            SentenceIterator sentenceIterator = new SentenceIterator(inputStream, encoding, textAnalyzer);
            while (sentenceIterator.hasNext()) {
                SentenceSortedWords sentence = new SentenceSortedWords();

                WordIterator wordIterator = new WordIterator(sentenceIterator.next(), textAnalyzer);
                while (wordIterator.hasNext()) {
                    sentence.add(wordIterator.next());
                }

                sentenceWriter.write(sentence, ++sentenceNum);
            }
            sentenceWriter.endWriting();

        } finally {
            sentenceWriter.finalizeWriting();
        }
    }
}
