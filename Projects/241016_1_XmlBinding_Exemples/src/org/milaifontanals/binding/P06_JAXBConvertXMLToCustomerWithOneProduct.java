package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P06_JAXBConvertXMLToCustomerWithOneProduct {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomerWithOneProduct.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C3CustomerWithOneProduct.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            C3CustomerWithOneProduct customer = (C3CustomerWithOneProduct) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
