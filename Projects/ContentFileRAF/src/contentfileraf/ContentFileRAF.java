/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package contentfileraf;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author Juan Antonio
 */
public class ContentFileRAF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Escritura en el fitxer;
        String fileName = "data_raf.bin";
        File f = new File(fileName);
        if(f.exists() && f.isFile() && !f.delete()){
            System.out.println("Error el fitxer existeis i no es pot eliminar, abroterm i sortim");
            return;
        }
        
        
        RandomAccessFile raf;
        
        try {
            //Append: true,  per començar al final, false per a eliminar el contingut;    
            raf = new RandomAccessFile(fileName, "rw"); 
        } catch (FileNotFoundException ex) {
            System.out.println("No s'ha pogut crear el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
       
        
        for(int i = 1; i <= 300; i++){
            try {
                raf.writeInt(i);
            } catch (IOException ex) {
                System.out.println("Error en enregistrar en el fitxer: "+fileName);
            }
        }
        
        
        try {
            raf.close();
        } catch (IOException ex) {
            System.out.println("No s'ha pogut tancar el fitxer: "+fileName);
            System.out.println(ex.getMessage());
            return;
        }
        
        //Lectura del fitxer;
        
        RandomAccessFile fos2;
        
        try {
            try {
                
                fos2 = new RandomAccessFile(fileName, "r");
                
                try{
                    while ( true ) {
                        System.out.println("Output2: " + fos2.readInt()); //Retorna exception quan acaba
                    }
                }catch(EOFException ex){} // final de bucle
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }
            
            fos2.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
