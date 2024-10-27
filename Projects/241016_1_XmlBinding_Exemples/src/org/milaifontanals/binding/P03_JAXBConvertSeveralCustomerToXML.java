package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

public class P03_JAXBConvertSeveralCustomerToXML {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
            Document doc = dbuilder.newDocument();
            Element arrel = doc.createElement("customers");
            doc.appendChild(arrel);

            JAXBContext jaxbContext = JAXBContext.newInstance(C1Customer.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            for (int i = 0; i < 10; i++) {
                C1Customer customer = new C1Customer();
                customer.setId(100 + i);
                customer.setAge(30 + i);
                customer.setName("Nom del customer " + i);
                jaxbMarshaller.marshal(customer, arrel);     // Enviem l'element Customer al document XML
            }
            // Enregistrar el contingut a disc
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("_fileSeveralCustomers.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");    // Provoca salt de línia però NO INDENTA
                // La propietat OutputKeys.INDENT segueix les recomanancions de W3C respecte les propietats
                // que ha de facilitar la classe Transformer, però NO INDENTA... Només provoca salt de línia.
            transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");   
                // La propietat indent-amount específica de la classe org.apache.xalan.transformer.TransformerImpl
                // que és la implementació de la classe abstracta javax.xml.transform.Transformer que incorpora
                // Java, permet indicar el nombre d'espais en blanc de la indentació.
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            // En cas que es vulgui afegir l'etiqueta DOCTYPE, cal:
//            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"nomFitxer");
            transformer.transform(source, result);
            System.out.println("Procés finalitzat");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
