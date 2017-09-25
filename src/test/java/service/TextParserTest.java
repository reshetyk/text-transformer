package service;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import parser.service.TextParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TextParserTest {
    private final InputStream input = getContextClassLoader().getResourceAsStream("input/small.in");

    @Test
    public void testConvertingToCsv() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.csv")) {

            new TextParser().parse(input, actual);

            assertThat(new String(actual.toByteArray()), is(IOUtils.toString(expected)));
        }
    }

    @Test
    public void testConvertingToXml() throws Exception {
        try (ByteArrayOutputStream actual = new ByteArrayOutputStream();
             InputStream expected = getContextClassLoader().getResourceAsStream("expected/small.xml")) {

            new TextParser().parse(input, actual);

            XMLUnit.setIgnoreWhitespace(true);
            XMLUnit.setCompareUnmatched(true);

            String actualXml = new String(actual.toByteArray());
            String expectedXml = IOUtils.toString(expected);

            XMLAssert.assertXMLEqual(expectedXml, actualXml);
        }
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
