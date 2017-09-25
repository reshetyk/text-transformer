package text.transformer.service.writer;

import org.apache.commons.io.IOUtils;
import text.transformer.domain.SentenceSortedWords;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvSentenceWriter implements SentenceWriter {
    private String delimiter;
    private int maxWords;
    private OutputStream outputStream;
    private Path temp;
    private FileOutputStream fos;
    private String prefix;

    public CsvSentenceWriter(OutputStream outputStream, String delimiter) throws IOException {
        this.outputStream = outputStream;
        this.delimiter = delimiter;
        temp = Files.createTempFile("text-transformer", "csv");
        fos = new FileOutputStream(temp.toFile());
    }

    @Override
    public void before() {

    }

    @Override
    public void write (SentenceSortedWords sentence) throws Exception {
        maxWords = Math.max(maxWords, sentence.countWords());
        writeToTempFile(toCsv(sentence, prefix).getBytes());
//        outputStream.flush();
    }

    @Override
    public void after() {
        try {
            writeHeader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToTempFile(byte[] bytes) throws IOException {
        fos.write(bytes);
    }

    private String toCsv(SentenceSortedWords sentence, String prefix) {
        String joinedWords = sentence.getWords().stream().collect(Collectors.joining(delimiter)) + "\n";
        return prefix + delimiter + joinedWords;
    }

    public void writeHeader () throws IOException {
        String joined = delimiter + IntStream.rangeClosed(1, maxWords)
                .mapToObj(i -> "Word " + i)
                .collect(Collectors.joining(delimiter)) + "\n";
        outputStream.write(joined.getBytes());
        IOUtils.copyLarge(new FileInputStream(temp.toFile()), outputStream);
        //todo delete temp file
    }


}
