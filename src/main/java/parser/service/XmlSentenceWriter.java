package parser.service;

import parser.domain.SentenceSortedWords;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;

public class XmlSentenceWriter {
    private final String encoding;
    private final String version;
    private final XMLStreamWriter writer;
    private final Marshaller marshaller;

    public XmlSentenceWriter(String encoding, String version, OutputStream outputStream) throws JAXBException, XMLStreamException {
        this.encoding = encoding;
        this.version = version;
//        this.writer = XMLOutputFactory.newFactory().createXMLStreamWriter(outputStream, encoding);
        this.writer = XMLOutputFactory.newFactory().createXMLStreamWriter(outputStream, encoding);
        this.marshaller = JAXBContext.newInstance(SentenceSortedWords.class).createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
    }

    public void writeStartDocument(String tagName) throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
        writer.writeStartElement(tagName);
    }

    public void writeSentence(SentenceSortedWords sentence) throws XMLStreamException, JAXBException {
        marshaller.marshal(sentence, writer);
    }

    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
        writer.flush();
        writer.close();
    }
}
