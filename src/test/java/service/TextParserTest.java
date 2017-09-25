package service;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Ignore;
import org.junit.Test;
import parser.service.TextParser;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextParserTest {

    public static final String ENCODING = "UTF-8";

    private final InputStream input = getContextClassLoader().getResourceAsStream("input/small.in");

    private final TextParser textParser = new TextParser(ENCODING);

    @Ignore
    @Test
    public void testConvertingToCsv() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.csv")) {

            textParser.parse(input, actual);

            assertThat(actual.toString(ENCODING), is(IOUtils.toString(expected, ENCODING)));
        }
    }

    @Test
    public void testConvertingToXml() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.xml")) {

            textParser.parse(input, actual);

            XMLUnit.setIgnoreWhitespace(true);
            XMLUnit.setCompareUnmatched(true);

            XMLAssert.assertXMLEqual(IOUtils.toString(expected, ENCODING), actual.toString(ENCODING));
        }
    }

//    @Ignore
    @Test
    public void testConvertingLargeData() throws Exception {
        try (InputStream inputStream = getContextClassLoader().getResourceAsStream("input/large.in");
             OutputStream fos = new FileOutputStream("D:\\temp\\1.txt")) {

            textParser.parse(inputStream, fos);

        }
    }

    private static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
