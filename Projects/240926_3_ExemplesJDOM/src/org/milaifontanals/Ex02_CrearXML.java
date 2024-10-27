package org.milaifontanals;

import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Ex02_CrearXML {

    public static void main(String[] args) {
        try {
            Element arrel = new Element("bookstore");
//            arrel.setAttribute(new Attribute("param", "hao"));
            // Equivalent:
            arrel.setAttribute("param", "hao");

            Document doc = new Document(arrel);

            Element book = new Element("book");
            book.setAttribute(new Attribute("category", "cooking"));
            Element title = new Element("title").setText("Everyday Italian");
            title.setAttribute("lang", "en");
            book.addContent(title);
            book.addContent(new Element("author").setText("Giada de Laurentiis"));
            book.addContent(new Element("year").setText("2005"));
            book.addContent(new Element("price").setText("30.00"));
            doc.getRootElement().addContent(book);
            // Equivalent:
//            arrel.addContent(book);

            book = new Element("book");
            book.setAttribute(new Attribute("category", "children"));
            title = new Element("title").setText("Harry Potter");
            title.setAttribute("lang", "en");
            book.addContent(title);
            book.addContent(new Element("author").setText("J K. Rowling"));
            book.addContent(new Element("year").setText("2005"));
            book.addContent(new Element("price").setText("29.99"));
            doc.getRootElement().addContent(book);

            book = new Element("book");
            book.setAttribute(new Attribute("category", "web"));
            title = new Element("title").setText("XQuery Kick Start");
            title.setAttribute("lang", "en");
            book.addContent(title);
            book.addContent(new Element("author").setText("James McGovern"));
            book.addContent(new Element("author").setText("Per Bothner"));
            book.addContent(new Element("author").setText("Kurt Cagle"));
            book.addContent(new Element("author").setText("James Linn"));
            book.addContent(new Element("author").setText("Vaidyanathan Nagarajan"));
            book.addContent(new Element("year").setText("2003"));
            book.addContent(new Element("price").setText("49.99"));
            doc.getRootElement().addContent(book);

            book = new Element("book");
            book.setAttribute(new Attribute("category", "web"));
            book.setAttribute(new Attribute("cover", "paperback"));
            title = new Element("title").setText("Learning XML");
            title.setAttribute("lang", "en");
            book.addContent(title);
            book.addContent(new Element("author").setText("Erik T. Ray"));
            book.addContent(new Element("year").setText("2003"));
            book.addContent(new Element("price").setText("39.95"));
            doc.getRootElement().addContent(book);

            // Tenim el document en memòria...
            // En cas que calgui inserir etiqueta <!DOCTYPE> al document
//            doc.setDocType(new DocType("nomArrel", "nomFitxer.dtd"));
            // Per mostrar-lo per pantalla o enregistrar-lo a disc...
            XMLOutputter out = new XMLOutputter();
            out.setFormat(Format.getPrettyFormat());    // deixar l'XML "ben formatat"

            // Per enviar-lo cap pantalla
            out.output(doc, System.out);
            // Per enviar-lo a fitxer
            out.output(doc, new FileWriter("books_creat.xml"));
            System.out.println("Procés finalitzat!");
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
    }
}
