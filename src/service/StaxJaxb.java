package service;

import entity.Category;
import entity.Product;
import entity.Shop;
import entity.Subcategory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.util.List;

public class StaxJaxb {
    public void parse() throws XMLStreamException, JAXBException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource("src/xml_json/shop.xml");
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);
        Shop shop = null;
        while (xsr.hasNext()) {
            int event = xsr.nextTag();
            if (event==XMLStreamReader.END_ELEMENT && xsr.getLocalName().equals("shop"))
                break;
            if (event == XMLStreamReader.START_ELEMENT) {
                if (xsr.getLocalName().equals("shop"))
                    shop = new Shop();
                if (xsr.getLocalName().equals("category")) {
                    Category category = new Category();
                    shop.getCategories().add(category);
                }
                if (xsr.getLocalName().equals("categoryName")){
                    List<Category> categories = shop.getCategories();
                    categories.get(categories.size()-1).setCategoryName(xsr.getElementText());

                }
                if (xsr.getLocalName().equals("subcategory")) {
                    JAXBContext jc = JAXBContext.newInstance(Subcategory.class);
                    Unmarshaller unmarshaller = jc.createUnmarshaller();
                    JAXBElement<Subcategory> jb = unmarshaller.unmarshal(xsr, Subcategory.class);
                    List<Category> categories = shop.getCategories();
                    categories.get(categories.size()-1).getSubcategories().add(jb.getValue());
                }
            }
        }
        for (Category category: shop.getCategories()){
            System.out.println(category.getCategoryName());
            for (Subcategory subcategory: category.getSubcategories()){
                System.out.println("\t\t" + subcategory.getSubcategoryName());
                for (Product product: subcategory.getProducts())
                    System.out.println("\t\t\t\t" + product);
            }
        }
        xsr.close();
    }
}
