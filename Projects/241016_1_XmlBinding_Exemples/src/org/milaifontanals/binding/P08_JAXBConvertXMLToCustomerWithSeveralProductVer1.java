package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P08_JAXBConvertXMLToCustomerWithSeveralProductVer1 {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomerWithSeveralProductVer1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C4CustomerWithSeveralProductVer1.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            C4CustomerWithSeveralProductVer1 customer = (C4CustomerWithSeveralProductVer1) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
            customer.visualitzar(System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
