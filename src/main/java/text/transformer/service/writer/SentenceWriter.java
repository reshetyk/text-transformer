package text.transformer.service.writer;

import text.transformer.domain.SentenceSortedWords;

public interface SentenceWriter {

    void before() ;

    void write(SentenceSortedWords sentence) throws Exception;

    void after();
}
