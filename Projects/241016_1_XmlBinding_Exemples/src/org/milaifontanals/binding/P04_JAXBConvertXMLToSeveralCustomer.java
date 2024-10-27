package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class P04_JAXBConvertXMLToSeveralCustomer {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            // En cas que el fitxer XML contingui informació de validació i es vulgui
            // comprovar en efectuar el parse, cal haver avisat la factoria:
            dbFactory.setValidating(true);
            // En aquest cas, com que el document XML no conté informació de validació
            // apareix un missatge d'advertència (no genera excepció)
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            dBuilder.setErrorHandler(null);  // Per a omitir cap avís de la validació
                                             //doncs en aquest cas, el document XML no disposa de <!DOCTYPE>
            /* Si no es desactiva, cal invocar setErrorHandler assignant un objecte d'una classe que implementi 
             la interfície ErrorHandler, en la qual cal haver indicat què fer davant qualsevol dels tipus 
             d'error possibles */

            File file = new File("_fileSeveralCustomers.xml");

            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();     // Eliminar nodes buits

            JAXBContext jaxbContext = JAXBContext.newInstance(C1Customer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            NodeList llista = doc.getElementsByTagName("customer");
            int q = llista.getLength();
            int i;
            for (i = 0; i < q; i++) {
                C1Customer customer = (C1Customer) jaxbUnmarshaller.unmarshal(llista.item(i));
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
