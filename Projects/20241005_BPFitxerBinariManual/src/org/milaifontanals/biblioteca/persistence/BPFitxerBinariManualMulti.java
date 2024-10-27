/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.milaifontanals.biblioteca.persistence;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.milaifontanals.biblioteca.Fitxa;
import org.milaifontanals.biblioteca.FitxaLlibre;
import org.milaifontanals.biblioteca.FitxaRevista;
import org.milaifontanals.biblioteca.IBiblioteca;

/**
 * Capa de persistència de Biblioteca en fitxer binari.<BR>
 * Fitxer de configuració compatible amb els mètodes store i load de la classe
 * Properties de Java i amb continguts obligatoris: <BR>
 * - fitxer: Nom de fitxer binari on s'emmagatzema la biblioteca. <BR>
 * - classeBiblioteca: Nom de classe Biblioteca a generar en la càrrega des de
 * fitxer. <BR>
 * Nom per defecte del fitxer de propietats: BPFitxerBinariManual.properties
 *
 * @author Isidre Guixà
 */
public class BPFitxerBinariManualMulti {

    private String nomFitxer;
    private String nomClasseBiblioteca;

    /**
     * Constructor que cerca fitxer de configuració amb nom
     * BPFitxerBinariManual.properties
     */
    public BPFitxerBinariManualMulti() {
        this("BPFitxerBinariManual.properties");
    }

    /**
     * Constructor que cerca fitxer de configuració indicat per paràmetre.
     *
     * @param nomFitxerConfiguracio Nom del fitxer de configuració a usar
     */
    public BPFitxerBinariManualMulti(String nomFitxerConfiguracio) {
        try {
            if (nomFitxerConfiguracio == null || nomFitxerConfiguracio.length() == 0) {
                throw new BPFitxerBinariManualException("S'ha passat fitxer de configuració null o buit");
            }
            
            Properties p = new Properties();
            
            p.load(new FileReader(nomFitxerConfiguracio));
            
            nomFitxer = p.getProperty("fitxer");
            nomClasseBiblioteca = p.getProperty("classeBiblioteca");
            
            if(nomFitxer == null || nomFitxer.length() == 0 || nomClasseBiblioteca == null || nomClasseBiblioteca.length() == 0){
                throw new BPFitxerBinariManualException("Error en el format de les propietats");
            }
                    
        } catch (Exception ex) {
            throw new BPFitxerBinariManualException("Error en carregar el fitxer de configuracio");
        }
        
    }

    public void saveBiblioteca(IBiblioteca b) {
        if (b == null) {
            throw new BPFitxerBinariManualException("S'ha passat biblioteca null");
        }
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(nomFitxer));
        } catch (FileNotFoundException ex) {
            throw new BPFitxerBinariManualException("Error en crear el fitxer " + nomFitxer, ex);
        }
        try {
            dos.writeUTF(b.getNom());
            dos.writeInt(Fitxa.getComptador());
            Iterator<Fitxa> it = b.recuperarFitxesOrdenadesPerReferencia();
            while (it.hasNext()) {
                Fitxa f = it.next();
                if (f instanceof FitxaLlibre) {
                    saveFitxaLlibre(f, dos);
                } else if (f instanceof FitxaRevista) {
                    saveFitxaRevista(f, dos);
                } else {
                    throw new BPFitxerBinariManualException("Mètode saveBiblioteca no preparat per "
                            + "enregristrar objecte de la classe "
                            + f.getClass().getName());
                }
            }
        } catch (IOException ex) {
            throw new BPFitxerBinariManualException("Error en enregistrar en el fitxer " + nomFitxer, ex);
        } finally {
            try {
                dos.close();
            } catch (IOException ex) {
                throw new BPFitxerBinariManualException("Error en tancar el fitxer " + nomFitxer, ex);
            }
        }
    }

    public IBiblioteca loadBiblioteca() {
        
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(nomFitxer));
        } catch (FileNotFoundException ex) {
            throw new BPFitxerBinariManualException("Error en obertura de fitxer " + nomFitxer, ex);
        }
        try {
            IBiblioteca b = null;
            try {
                Constructor c = Class.forName(nomClasseBiblioteca).getConstructor(String.class);
                b = (IBiblioteca) c.newInstance(dis.readUTF()); // Segona dada: nom de la biblioteca
            } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                throw new BPFitxerBinariManualException("Error en crear l'objecte " + nomClasseBiblioteca, ex);
            }
            // En aquest punt la biblioteca "b" ja està creada.
            // Ara toca recuperar el comptador
            Fitxa.setComptador(dis.readInt());
            // Ara toca recuperar les fitxes
            try {
                while (true) {
                    char tipusFitxa = dis.readChar();
                    if (tipusFitxa == 'R') {
                        b.afegirFitxa(loadFitxaRevista(dis));
                    } else if (tipusFitxa == 'L') {
                        b.afegirFitxa(loadFitxaLlibre(dis));
                    } else {
                        throw new BPFitxerBinariManualException("Mètode loadBiblioteca no preparat per "
                                + "recuoperar objecte de tipus " + tipusFitxa);
                    }
                }
            } catch (EOFException ex) {

            }
            return b;
        } catch (IOException ex) {
            throw new BPFitxerBinariManualException("Error en recuperar del fitxer " + nomFitxer, ex);
        } finally {
            try {
                dis.close();
            } catch (IOException ex) {
                throw new BPFitxerBinariManualException("Error en tancar el fitxer " + nomFitxer, ex);
            }
        }
    }

    private static void saveFitxaLlibre(Fitxa f, DataOutputStream dos) throws IOException {
        dos.writeChar('L');
        saveFitxa(f, dos);
        FitxaLlibre fl = (FitxaLlibre) f;
        if (fl.getEditorial() == null) {
            dos.writeChar('N');
        } else {
            dos.writeChar('*');
            dos.writeUTF(fl.getEditorial());
        }
        dos.writeUTF(fl.getIsbn());
    }

    private static void saveFitxaRevista(Fitxa f, DataOutputStream dos) throws IOException {
        dos.writeChar('R');
        saveFitxa(f, dos);
        FitxaRevista fr = (FitxaRevista) f;
        dos.writeInt(fr.getAny());
        dos.writeInt(fr.getNum());
    }

    private static void saveFitxa(Fitxa f, DataOutputStream dos) throws IOException {
        dos.writeUTF(f.getReferencia());
        dos.writeUTF(f.getTitol());
        if (f.getEsDeixa() == null) {
            dos.writeChar('N');
        } else {
            dos.writeChar('*');
            dos.writeBoolean(f.getEsDeixa());
        }
        dos.writeInt(f.getOrdre());
        dos.writeLong(f.getDataCreacio().getTime());
        if (f.getDataModificacio() == null) {
            dos.writeChar('N');
        } else {
            dos.writeChar('*');
            dos.writeLong(f.getDataModificacio().getTime());
        }
    }

    private static FitxaRevista loadFitxaRevista(DataInputStream dis) throws IOException {
        String referencia = dis.readUTF();
        String titol = dis.readUTF();
        Boolean esDeixa;
        char marca = dis.readChar();
        if (marca == '*') {
            esDeixa = dis.readBoolean();
        } else {
            esDeixa = null;
        }
        int ordre = dis.readInt();
        Date dataCreacio = new Date(dis.readLong());
        Date dataModificacio;
        marca = dis.readChar();
        if (marca == '*') {
            dataModificacio = new Date(dis.readLong());
        } else {
            dataModificacio = null;
        }
        int any = dis.readInt();
        int num = dis.readInt();
        return new FitxaRevista(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio, any, num);
    }

    private static FitxaLlibre loadFitxaLlibre(DataInputStream dis) throws IOException {
        String referencia = dis.readUTF();
        String titol = dis.readUTF();
        Boolean esDeixa;
        char marca = dis.readChar();
        if (marca == '*') {
            esDeixa = dis.readBoolean();
        } else {
            esDeixa = null;
        }
        int ordre = dis.readInt();
        Date dataCreacio = new Date(dis.readLong());
        Date dataModificacio;
        marca = dis.readChar();
        if (marca == '*') {
            dataModificacio = new Date(dis.readLong());
        } else {
            dataModificacio = null;
        }
        marca = dis.readChar();
        String editorial;
        if (marca == '*') {
            editorial = dis.readUTF();
        } else {
            editorial = null;
        }
        String isbn = dis.readUTF();
        return new FitxaLlibre(referencia, titol, esDeixa, ordre, dataCreacio, dataModificacio, editorial, isbn);
    }
}
