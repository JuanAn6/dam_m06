package activitatsfitxers;

import java.io.File;
import static java.io.File.pathSeparator;
import static java.io.File.separator;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Antonio
 */
public class Activitat2 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        File f = new File("D:\\fontanals.txt");
        //if(f.exists()){
            try{
                if(f.delete() ){
                    System.out.print("Eliminat correctament");
                }else{
                    System.out.print("Error al eliminar");
                }
            }catch(SecurityException e){
                 System.out.println("No tens permis");
            }
            
//        }else{
//            System.out.print("No existeix");
//        }

        System.out.print("\n");
        
    }
    
}
