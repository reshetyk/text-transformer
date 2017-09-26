package service;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import text.transformer.service.TextAnalyzer;
import text.transformer.service.TextTransformer;
import text.transformer.service.writer.CsvSentenceWriter;
import text.transformer.service.writer.XmlSentenceWriter;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextTransformerTest {

    private static final String ENCODING = "UTF-8";
    private InputStream largeInput;
    private InputStream smallInput;

    @Before
    public void setUp() throws Exception {
        largeInput = getContextClassLoader().getResourceAsStream("input/large.in");
        smallInput = getContextClassLoader().getResourceAsStream("input/small.in");
    }

    @Test
    public void testConvertingToCsv() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.csv")) {

            TextTransformer textTransformer = new TextTransformer(
                    new CsvSentenceWriter(actual, ", ", ENCODING),
                    new TextAnalyzer(), ENCODING
            );

            textTransformer.transform(smallInput);

            assertThat(actual.toString(ENCODING), is(IOUtils.toString(expected, ENCODING)));
        }
    }

    @Test
    public void testConvertingToXml() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.xml")) {

            TextTransformer textTransformer = new TextTransformer(
                    new XmlSentenceWriter(ENCODING, "1.0", actual),
                    new TextAnalyzer(), ENCODING
            );

            textTransformer.transform(smallInput);

            XMLUnit.setIgnoreWhitespace(true);
            XMLUnit.setCompareUnmatched(true);

            XMLAssert.assertXMLEqual(IOUtils.toString(expected, ENCODING), actual.toString(ENCODING));
        }
    }

    /* In 'build.gradle' define jvmArgs '-Xmx32m' */

    @Test
    public void testConvertingLargeDataXml() throws Exception {
        File tempFile = File.createTempFile("text-transformer-test", "large");
        try (OutputStream fos = new FileOutputStream(tempFile)) {

            TextTransformer textTransformer = new TextTransformer(
                    new XmlSentenceWriter(ENCODING, "1.0", fos),
                    new TextAnalyzer(),
                    ENCODING
            );

            textTransformer.transform(largeInput);

            assertThat(tempFile.length() > 0, is(true));

        } finally {
            if (tempFile.exists())
                tempFile.delete();
        }

    }

    @Test
    public void testConvertingLargeDataCvs() throws Exception {
        File tempFile = File.createTempFile("text-transformer-test", "large");
        try (OutputStream fos = new FileOutputStream(tempFile)) {

            TextTransformer textTransformer = new TextTransformer(
                    new CsvSentenceWriter(fos, ", ", ENCODING),
                    new TextAnalyzer(),
                    ENCODING
            );

            textTransformer.transform(largeInput);

            assertThat(tempFile.length() > 0, is(true));

        } finally {
            if (tempFile.exists())
                tempFile.delete();
        }

    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
