package org.milaifontanals;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class Ex01_ComptarXML {

    public static void main(String[] args) {
        SAXBuilder builder = null;
        // Es suposa que tenim el fitxer books.xml en el nivell immediatament superior 
        // a la carpeta del projecte

        File xmlFile = new File(".." + File.separator + "books.xml");
        try {
            // Per validar un fitxer en el procés de càrrega, cal utilitzar un SAXBuilder amb instruccions
            // de validació, però si el fitxer a carregar no conté DOCTYPE, es produirà una excepció en la
            // validació. Per això creem el SAXBuilder amb validació o sense, segons el fitxer XML contingui
            // o no la cadena DOCTYPE i per saber-ho ens ajudem de FileUtils d'Apache Commons
            if (FileUtils.readFileToString(xmlFile, (String) null).contains("<!DOCTYPE")) {
                builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
            } else {
                builder = new SAXBuilder();
            }
        } catch (IOException ex) {
            System.out.println("Error en comprovar si el fitxer XML conté etiqueta DOCTYPE");
            System.out.println("Excepció: " + ex.getClass().getName());
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        try {
            Document document = builder.build(xmlFile);
            System.out.println("Document carregat. Nombre de fills: " + document.getContentSize());
            Element rootNode = document.getRootElement();

            int nAuthor = 0;
            int nTitle = 0;
            int nPrice = 0;
            int nYear = 0;
//            List<Element> list = rootNode.getChildren("book");
            // Alternativament, per què tots els fills de bookstore són "book"
            List<Element> list = rootNode.getChildren();        // Llibres - Element "book"
            int nreBooks = list.size();
            System.out.println("Qtat. Llibres: " + nreBooks);
            for (int i = 0; i < nreBooks; i++) {
                Element node = list.get(i);
                /* Llibre */

                List slist = node.getChildren("title");
                nTitle = nTitle + slist.size();
                slist = node.getChildren("author");
                nAuthor = nAuthor + slist.size();
                slist = node.getChildren("year");
                nYear = nYear + slist.size();
                slist = node.getChildren("price");
                nPrice = nPrice + slist.size();
            }
            System.out.println("Nre. elements \"title\": " + nTitle);
            System.out.println("Nre. elements \"author\": " + nAuthor);
            System.out.println("Nre. elements \"year\": " + nYear);
            System.out.println("Nre. elements \"price\": " + nPrice);
        } catch (IOException | JDOMException io) {
            System.out.println("Excepció: " + io.getClass().getName());
            System.out.println(io.getMessage());
        }
    }
}
