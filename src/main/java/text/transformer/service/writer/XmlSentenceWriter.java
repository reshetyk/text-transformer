package text.transformer.service.writer;

import org.codehaus.stax2.XMLOutputFactory2;
import org.codehaus.stax2.XMLStreamWriter2;
import text.transformer.domain.SentenceSortedWords;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamException;
import java.io.OutputStream;

public class XmlSentenceWriter implements SentenceWriter{
    private final String encoding;
    private final String version;
    private final XMLStreamWriter2 writer;
    private final Marshaller marshaller;

    public XmlSentenceWriter(String encoding, String version, OutputStream outputStream) throws JAXBException, XMLStreamException {
        this.encoding = encoding;
        this.version = version;
        this.writer = (XMLStreamWriter2) (XMLOutputFactory2.newFactory()).createXMLStreamWriter(outputStream, encoding);
        this.marshaller = JAXBContext.newInstance(SentenceSortedWords.class).createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        this.marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
    }

    public void writeStartDocument(String tagName) throws XMLStreamException {
        writer.writeStartDocument(version, encoding, true);
        writer.writeStartElement(tagName);
    }

    @Override
    public void before() {
        try {
            writeStartDocument("text");
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(SentenceSortedWords sentence) throws Exception {
        marshaller.marshal(sentence, writer);
    }

    @Override
    public void after() {
        try {
            writeEndDocument();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }
}
