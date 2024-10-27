/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package org.milaifontanals.biblioteca.persistence;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.FileWriter;
import org.milaifontanals.biblioteca.Biblioteca;
import org.milaifontanals.biblioteca.BibliotecaAL;
import org.milaifontanals.biblioteca.BibliotecaTS;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;

/**
 *
 * @author Juan Antonio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BibliotecaAL b = new BibliotecaAL("Juan");
        
        b.afegirFitxa(new FitxaLlibre("2222222222", "El Quixot", "Planeta", "0000010020"));
        b.afegirFitxa(new FitxaRevista("1111111111", "Tiempo", 2000, 15));
        b.afegirFitxa(new FitxaRevista("3333333333", "Mundo Científico", 2010, 3));
        
        //System.out.println(b);
        
        FitxaLlibre fl = new FitxaLlibre("2222222222", "El Quixot", "Planeta", "0000010020");
        FitxaRevista fr = new FitxaRevista("1111111111", "Tiempo", 2000, 15);


   
        try {
            /*
            FileWriter file = new FileWriter("_fileLlibre.xml");
            
            JAXBContext jaxbContext = JAXBContext.newInstance(FitxaLlibre.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(fl, file);     // Envia a fitxer
            jaxbMarshaller.marshal(fl, System.out);// Envia a consola

            
            
            File fileRevista = new File("_fileRevista.xml");
            JAXBContext jaxbContextRevista = JAXBContext.newInstance(FitxaRevista.class);
            Marshaller jaxbMarshallerRevista = jaxbContextRevista.createMarshaller();

            // output pretty printed
            jaxbMarshallerRevista.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshallerRevista.marshal(fr, fileRevista);     // Envia a fitxer
            jaxbMarshallerRevista.marshal(fr, System.out);// Envia a consola

            
            */
            
            
            
            
            FileWriter fileB = new FileWriter("_fileLlibreria.xml");
            fileB.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            fileB.write("<!DOCTYPE library SYSTEM \"../library.dtd\">\n");
            
            JAXBContext jaxbContextB = JAXBContext.newInstance(
                    Biblioteca.class, 
                    FitxaLlibre.class, 
                    FitxaRevista.class);
            Marshaller jaxbMarshallerB = jaxbContextB.createMarshaller();

            // output pretty printed
            jaxbMarshallerB.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshallerB.setProperty(Marshaller.JAXB_FRAGMENT, true);
            
            jaxbMarshallerB.marshal(b, fileB);     // Envia a fitxer
            jaxbMarshallerB.marshal(b, System.out);// Envia a consola
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

/*
@XmlID //Camps que siguin unics

varios roots

lista de bibliotecas


*/