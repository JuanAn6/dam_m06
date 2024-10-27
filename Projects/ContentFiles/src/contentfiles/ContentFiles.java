/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contentfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Juan Antonio
 */
public class ContentFiles {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Escritura en el fitxer;
        String fileName = "data.bin";
        FileOutputStream fos;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            fos = new FileOutputStream(fileName, false); 
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        ByteBuffer buffer = ByteBuffer.allocate(4);
        
        for(int i = 1; i <= 300; i++){
            buffer.clear();
            buffer.putInt(i);
            try {    
                fos.write(buffer.array());
            } catch (IOException ex) {
                System.out.println("Error en enregistrar en el fitxer: "+fileName);
            }
        }
        
        
        try {
            fos.close();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut tancar el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        //Lectura del fitxer;
        
        FileInputStream fis;
        
        try {
            try {
                fis = new FileInputStream(fileName);
                int size = fis.available();
                System.out.println("File size: "+size);
                
                byte[] fileOutput = new byte[4];
                
                while ( fis.read(fileOutput) != -1 ) {
                    ByteBuffer bufferExit = ByteBuffer.wrap(fileOutput);
                    int number = bufferExit.getInt();
                    System.out.println("Output2: " + number);
                }
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            
            fis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
