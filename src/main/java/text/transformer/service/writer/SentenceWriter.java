package text.transformer.service.writer;

import text.transformer.domain.SentenceSortedWords;

public interface SentenceWriter {

    void startWriting() ;

    void write(SentenceSortedWords sentence, int sentenceNum) throws Exception;

    void endWriting();

    void finalizeWriting ();


}
