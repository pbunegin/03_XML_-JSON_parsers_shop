package service;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class Main {
    public static void main(String[] args) {
        try {
            new StaxJaxb().parse();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }



    }
}
