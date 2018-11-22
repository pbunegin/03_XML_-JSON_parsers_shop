package service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Shop;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConverterJsonXml {
    Shop shop;

    public void convertXmlToJson() {
        Shop shop = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Shop.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<Shop> jb = unmarshaller.unmarshal(new StreamSource("src/xml_json/shop.xml"), Shop.class);
            shop = jb.getValue();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/xml_json/JsonFromXml.json"), shop);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void convertJsonToXml() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            shop = mapper.readValue(new File("src/xml_json/JsonFromXml.json"), Shop.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        JAXBContext jc = null;
        try {
            jc = JAXBContext.newInstance(Shop.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.marshal(shop, new File("src/xml_json/XmlFromJson.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
