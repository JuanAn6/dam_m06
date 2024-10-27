/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.proves;

import java.util.List;
import org.milaifontanals.FleetListPersistence;
import org.milaifontanals.FleetListPersistenceException;
import org.milaifontanals.data.Fleet;


/**
 *
 * @author Usuari
 */
public class P04_ProvaLoadJAXB {

    public static void main(String[] args) {
        System.out.println("ProvaLoadJAXB");
        // Per comprovar recuperació a partir de fitxer generat per P03:
        List<Fleet> ll = null;
        try{
            ll = FleetListPersistence.loadFleetJAXB("llistaFlotes.xml");
        } catch (FleetListPersistenceException ex) {
            System.out.println("Error en carregar el xml");
            ex.printStackTrace();
        }
        // Per comprovar recuperació a partir de fitxer d'exemple facilitat:
        //List<Fleet> ll = FleetListPersistence.loadFleetJAXB("exempleLlistaFlotes.xml");
        System.out.println("Llista recuperada:");
        for (Fleet f : ll) {
            System.out.println("\t" + f);
        }
    }
}
