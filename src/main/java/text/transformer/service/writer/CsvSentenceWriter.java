package text.transformer.service.writer;

import org.apache.commons.io.IOUtils;
import text.transformer.domain.SentenceSortedWords;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvSentenceWriter implements SentenceWriter {
    final private String delimiter;
    final private OutputStream outputStream;
    final private File tempFile;
    final private FileOutputStream tempOutputStream;
    final private String prefixSentence = "Sentence ";
    final private String prefixWord = "Word ";
    final private String encoding;
    private int maxWords;

    public CsvSentenceWriter(OutputStream outputStream, String delimiter, String encoding) throws IOException {
        this.outputStream = outputStream;
        this.delimiter = delimiter;
        this.encoding = encoding;
        this.tempFile = File.createTempFile("text-transformer", "csv");
        this.tempOutputStream = new FileOutputStream(tempFile);
    }

    @Override
    public void startWriting() {
    }

    @Override
    public void write(SentenceSortedWords sentence, int sentenceNum) throws Exception {
        maxWords = Math.max(maxWords, sentence.countWords());
        writeToTempFile(toCsv(sentence, sentenceNum).getBytes(encoding));
    }

    @Override
    public void endWriting() {
        try {
            //writing header at the end because we need to know count of words in all sentences
            writeHeader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void finalizeWriting() {
        try {
            if (tempOutputStream != null) {
                tempOutputStream.close();
            }
            if (tempFile.exists())
                tempFile.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String toCsv(SentenceSortedWords sentence, int sentenceNum) {
        String joinedWords = sentence.getWords().stream().collect(Collectors.joining(delimiter)) + "\n";
        return prefixSentence + sentenceNum + delimiter + joinedWords;
    }

    private void writeHeader() throws IOException {
        String joined = delimiter + IntStream.rangeClosed(1, maxWords)
                .mapToObj(i -> prefixWord + i)
                .collect(Collectors.joining(delimiter)) + "\n";

        //write first line
        outputStream.write(joined.getBytes());

        //append all other content
        IOUtils.copyLarge(new FileInputStream(tempFile), outputStream);

        outputStream.flush();
    }

    private void writeToTempFile(byte[] bytes) throws IOException {
        tempOutputStream.write(bytes);
        tempOutputStream.flush();
    }

}
