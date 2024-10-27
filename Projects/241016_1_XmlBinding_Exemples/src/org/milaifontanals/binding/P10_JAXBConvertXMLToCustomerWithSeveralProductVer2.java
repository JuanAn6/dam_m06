package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P10_JAXBConvertXMLToCustomerWithSeveralProductVer2 {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomerWithSeveralProductVer2.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C5CustomerWithSeveralProductVer2.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            C5CustomerWithSeveralProductVer2 customer = (C5CustomerWithSeveralProductVer2) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
            customer.visualitzar(System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
