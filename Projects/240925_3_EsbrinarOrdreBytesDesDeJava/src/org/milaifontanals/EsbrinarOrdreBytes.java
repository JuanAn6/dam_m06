/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals;

import java.nio.ByteOrder;

/**
 *
 * @author Usuari
 */
public class EsbrinarOrdreBytes {

    public static void main(String[] args) {
        ByteOrder byteOrder = ByteOrder.nativeOrder();
        System.out.println("L'ordre de bytes en aquesta CPU és "+byteOrder);   //LITTLE_ENDIAN
        System.out.println("Java sempre treballa amb "+ByteOrder.BIG_ENDIAN+" independent de la CPU");
    }
}
