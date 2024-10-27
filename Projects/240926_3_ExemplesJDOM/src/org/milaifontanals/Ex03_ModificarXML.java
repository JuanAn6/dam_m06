package org.milaifontanals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.jdom2.input.sax.XMLReaders;

public class Ex03_ModificarXML {

    public static void main(String[] args) {
        try {
            // Fitxer original "books.xml" en nivell superior
            String filepathInitial = System.getProperty("user.dir") + File.separator + ".." + File.separator + "books.xml";
            String filepathFinal = "books_modificat.xml";
            SAXBuilder builder = null;
            File xmlFile = new File(filepathInitial);
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

            Document doc = (Document) builder.build(xmlFile);
            Element rootNode = doc.getRootElement();

            List<Element> llibres = rootNode.getChildren("book");
            for (int i = 0; i < llibres.size(); i++) {
                Element book = llibres.get(i);
                // Modificar l'atribut "category"
                // Com que sabem que TOTS tenen atribut "category" no pregunto si getAttribute retorna null
                String valor = book.getAttribute("category").getValue();
                book.getAttribute("category").setValue(valor.toUpperCase());

                // Afegir un nou element "editorial"
                Element editorial = new Element("editorial").setText("");
                book.addContent(editorial);

                // Actualitzar el preu
                // Com que sabem que TOTS tenen fill "price" no pregunto si getChild retorna null
                float preu;
                preu = Float.parseFloat(book.getChild("price").getValue());
                book.getChild("price").setText(String.valueOf(arrodonir(preu * 1.02, 2)));

                // Eliminar l'element year
                book.removeChild("year");

                // Afegir atribut "sex" als autors
                List<Element> listAutors = book.getChildren("author");
                Iterator<Element> iteAutors = listAutors.iterator();
                while (iteAutors.hasNext()) {
                    Element autor = iteAutors.next();
                    autor.setAttribute("sex", "?");
                }
                // Eliminar atribut "lang" als title
                book.getChild("title").removeAttribute("lang");
            }

            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat((Format.getPrettyFormat()).setEncoding("ISO-8859-1"));
            xmlOutput.output(doc, new FileWriter(filepathFinal));

            // xmlOutput.output(doc, System.out);
            System.out.println("Arxiu actualitzat!");
        } catch (IOException io) {
            io.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
    }

    private static double arrodonir(double numero, int numDecimals) {
        long ll = (long) Math.pow(10, numDecimals);
        return Math.rint(numero * ll) / ll;
    }
}
