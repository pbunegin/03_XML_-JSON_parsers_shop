package service;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

public class Main {
    private static final String SHOP_XML = "src/xml_json/shop.xml";
    private static final String SHOP_XSD = "src/xml_json/shop.xsd";
    private static final String JSON_FROM_XML = "src/xml_json/JsonFromXml.json";
    private static final String XML_FROM_JSON = "src/xml_json/XmlFromJson.xml";

    public static void main(String[] args) {
        try {
            StaxJaxb staxJaxb = new StaxJaxb();
            staxJaxb.validation(SHOP_XML,SHOP_XSD);
            staxJaxb.parse(SHOP_XML);
        } catch (XMLStreamException | JAXBException e) {
            System.out.println(e.getMessage());
        }

        ConverterJsonXml converter = new ConverterJsonXml();
        converter.convertXmlToJson(SHOP_XML, JSON_FROM_XML);
        converter.convertJsonToXml(JSON_FROM_XML,XML_FROM_JSON);

    }
}
