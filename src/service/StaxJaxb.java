package service;

import entity.Category;
import entity.Product;
import entity.Shop;
import entity.Subcategory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StaxJaxb {

    public void parse(String shopXml) throws XMLStreamException, JAXBException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(shopXml));

        Shop shop = null;
        while (xsr.hasNext()) {
            int event = xsr.nextTag();
            if (event == XMLStreamReader.END_ELEMENT && xsr.getLocalName().equals("shop"))
                break;
            if (event == XMLStreamReader.START_ELEMENT) {
                if (xsr.getLocalName().equals("shop"))
                    shop = new Shop();
                if (xsr.getLocalName().equals("category")) {
                    Category category = new Category();
                    if (shop != null)
                        shop.getCategories().add(category);
                }
                if (xsr.getLocalName().equals("categoryName")) {
                    List<Category> categories = null;
                    if (shop != null)
                        categories = shop.getCategories();
                    if (categories != null)
                        categories.get(categories.size() - 1).setCategoryName(xsr.getElementText());
                }
                if (xsr.getLocalName().equals("subcategory")) {
                    JAXBContext jc = JAXBContext.newInstance(Subcategory.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    JAXBElement<Subcategory> jb = unmarshaller.unmarshal(xsr, Subcategory.class);
                    List<Category> categories = null;
                    if (shop != null) {
                        categories = shop.getCategories();
                        categories.get(categories.size() - 1).getSubcategories().add(jb.getValue());
                    }
                }
            }
        }
        if (shop != null) {
            for (Category category : shop.getCategories()) {
                System.out.println(category.getCategoryName());
                for (Subcategory subcategory : category.getSubcategories()) {
                    System.out.println("\t\t" + subcategory.getSubcategoryName());
                    for (Product product : subcategory.getProducts())
                        System.out.println("\t\t\t\t" + product);
                }
            }
        }
        xsr.close();
    }

    public void validation(String shopXml, String shopXsd) throws XMLStreamException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(shopXml));

        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new File(shopXsd));
            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(xsr));
            System.out.println("XML валидный");
        } catch (SAXException e) {
            System.out.println("XML НЕ ВАЛИДНЫЙ");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        xsr.close();
    }
}
