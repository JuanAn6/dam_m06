package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P02_JAXBConvertXMLToCustomer {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomer.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C1Customer.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            C1Customer customer = (C1Customer) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
