/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filereaderfilewriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Character.toChars;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan Antonio
 */
public class FileReader2 {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String name_file = "dades.txt";
        File f = new File(name_file);
        
        try {
            FileReader fr = new FileReader(f);
            
            String readed = "";
            
            int c ;
            try {
                
                while(( c = fr.read()) != -1){
                    readed += (char) c ;
                }
                
                System.out.println("Llegit: "+readed);
    
            } catch (IOException ex) {
                System.out.println("Error: "+ex.getMessage());
                return;
            }
            
            
            String []array = readed.split("\s");
               
            
            for(String s:array){
                String value = "";
                
                switch(s.charAt(0)){
                    case 'B':
                        value = "byte";
                        break;
                    case 'S':
                        value = "short";
                        break;
                    case 'I':
                        value = "int";
                        break;
                    case 'F':
                        value = "float";
                        break;
                    case 'L':
                        value = "long";
                        break;
                    case 'D':
                        value = "double";
                        break;       
                }
                
                System.out.println("Valor de tipus "+value+": "+ s.substring(1));
                
                
            }
            
            fr.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: "+ex.getMessage());
            return;
        } catch (IOException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
        
        
    }
}
