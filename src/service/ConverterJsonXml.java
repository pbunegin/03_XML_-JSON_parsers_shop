package service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Shop;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;

public class ConverterJsonXml {
    public void convertXmlToJson(String shopXml, String shopJson) {
        Shop shop = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Shop.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<Shop> jb = unmarshaller.unmarshal(new StreamSource(shopXml), Shop.class);
            shop = jb.getValue();
        } catch (JAXBException e) {
            System.out.println("Ошибка JAXB при конвертации XML в JSON.\n Неудачная попытка демаршализации");;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(shopJson), shop);
        } catch (IOException e) {
            System.out.println("Ошибка JAXB при конвертации JSON в XML.\n" + e.getMessage());;
        }


    }

    public void convertJsonToXml(String jsonFromXml, String xmlFromJson) {
        Shop shop = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            shop = mapper.readValue(new File(jsonFromXml), Shop.class);

        } catch (JsonParseException | JsonMappingException e) {
            System.out.println("Ошибка Jackson при конвертации JSON в XML.\n Неудачная попытка демаршализации");;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }


        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Shop.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(shop, new File(xmlFromJson));
        } catch (JAXBException e) {
            System.out.println("Ошибка JAXB при конвертации JSON в XML");;
        }

    }
}
