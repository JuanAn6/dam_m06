package org.milaifontanals.binding;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class P12_JAXBConvertXMLToCustomerAmbConversionsEspecialsVer1 {

    public static void main(String[] args) {
        try {
            File file = new File("_fileCustomerAmbConversionsEspecialsVer1.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(C6CustomerAmbConversionsEspecialsVer1.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            C6CustomerAmbConversionsEspecialsVer1 customer = (C6CustomerAmbConversionsEspecialsVer1) jaxbUnmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
