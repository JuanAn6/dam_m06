/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package activitatsfitxers;

import java.io.File;
import static java.io.File.pathSeparator;
import static java.io.File.pathSeparatorChar;
import static java.io.File.separator;
import java.io.IOException;

/**
 *
 * @author Juan Antonio
 */
public class ActivitatsFitxers {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.print(pathSeparator+", "+separator+ "\n");
        
        String t[] = {"X:\\programes\\pepe.txt", "C:\\Windows", "C:\\Windows\\regedit.exe", "C:\\fontanals.txt", "D:\\fontanals.txt"};
        
        for(String s : t){
            System.out.print("\nRuta: "+s+", ");
            File f = new File(s);
            if(f.exists()){
                System.out.print("Existeix, ");
                if( f.isFile()){
                    System.out.print("Es un fitxer, ");
                }else{
                    System.out.print("Es un directori, ");
                }
            }else{
                System.out.print("No existeix");
//                try{
//                    f.createNewFile();
//                    System.out.print("S'ha creat el fitxer: "+s);
//                }catch (IOException e){
//                    System.out.println("No s'ha pogut crear");
//                    System.out.println(e.getMessage());
//                }
            }
            
        }

        System.out.print("\n");
        
    }
    
}
