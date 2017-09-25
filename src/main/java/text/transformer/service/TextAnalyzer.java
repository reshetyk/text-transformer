package text.transformer.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.io.FileUtils.readFileToString;

public class TextAnalyzer {

    private static final File DEFAULT_VOCABULARY = getFile("shortening-vocabulary.txt");

    private List<String> shortenings;

    public TextAnalyzer() {
        this(DEFAULT_VOCABULARY, "UTF-8");
    }

    public TextAnalyzer(File shorteningVocabularyFile, String encoding) {
        this.shortenings = parseShortenings(shorteningVocabularyFile, encoding);
    }

    public boolean isShortening(String word) {
        return shortenings.contains(word.trim());
    }

    public boolean isEndOfSentence(String word) {
        word = word.trim();
        return word.matches(".*[?!.]+\\s*") && !isShortening(word);
    }


    private List<String> parseShortenings(File shorteningVocabularyFile, String encoding) {
        try {
            return Arrays.asList(readFileToString(shorteningVocabularyFile, encoding).split("\\s*,\\s*"));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read vocabulary file", e);
        }
    }

    private static File getFile(String name)  {
        try {
            return new File(Thread.currentThread().getContextClassLoader().getResource(name).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
