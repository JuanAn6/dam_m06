/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filereaderfilewriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Antonio
 */
public class FileReaderFileWriter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner scn = new Scanner(System.in);
        
        System.out.println("B-byte | S-short | I-int | F-float | L-long | D-double");
        System.out.println("Introdueix les dades en format X9999 (X lletra y 9999 dada) ($ per sortir):  ");
        
        
        String text = new String();
        String entrada = "";
        
        while (!entrada.equals("$")){
            entrada = scn.next();
            if (!entrada.equals("$")) {
                text += entrada+" ";
            }
        }
        
        System.out.println("Resultat: "+text);
        
        scn.close();
        
        String name_file = "dades.txt";
        File f = new File(name_file);
        
        try {
            FileWriter fw = new FileWriter(f);
            
            fw.write(text);
            
            fw.close();
        } catch (IOException ex) {
            System.out.println("Error: "+ ex.getMessage());
        }
        
        
    }
    
}
