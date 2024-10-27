/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.milaifontanals.biblioteca;

/**
 *
 * @author Usuari
 */
public class FitxaException extends RuntimeException {

    private char motiu;     // (R)eferencia / (T)itol / (I)SBN / (A)ny / (N)um / (O)rdre / Data(C)reació / Data (M)odificacio
    private String referencia;
    private String titol;
    private String isbn;
    private Integer any;
    private Integer num;
    private Integer ordre;

    public FitxaException(String message, char motiu) {
        super(message);
        switch (motiu) {
            case 'C':
            case 'M':
                break;
            default:
                throw new RuntimeException("Creació incorrecta de FitxaException: motiu " + motiu);
        }
        this.motiu = motiu;
    }

    public FitxaException(String message, char motiu, String valor) {
        super(message);
        switch (motiu) {
            case 'R':
                referencia = valor;
                break;
            case 'T':
                titol = valor;
                break;
            case 'I':
                isbn = valor;
                break;
            default:
                throw new RuntimeException("Creació incorrecta de FitxaException: motiu " + motiu + " - valor: " + valor);
        }
        this.motiu = motiu;
    }

    public FitxaException(String message, char motiu, int valor) {
        super(message);
        switch (motiu) {
            case 'A':
                any = valor;
                break;
            case 'N':
                num = valor;
                break;
            case 'O':
                ordre = valor;
                break;
            default:
                throw new RuntimeException("Creació incorrecta de FitxaException: motiu " + motiu + " - valor: " + valor);
        }
        this.motiu = motiu;
    }

    public FitxaException(String message, Throwable cause, char motiu, String valor) {
        super(message, cause);
        switch (motiu) {
            case 'R':
                referencia = valor;
                break;
            case 'T':
                titol = valor;
                break;
            case 'I':
                isbn = valor;
                break;
            default:
                throw new RuntimeException("Creació incorrecta de FitxaException: motiu " + motiu + " - valor: " + valor);
        }
        this.motiu = motiu;
    }

    public FitxaException(String message, Throwable cause, char motiu, int valor) {
        super(message, cause);
        switch (motiu) {
            case 'A':
                any = valor;
                break;
            case 'N':
                num = valor;
                break;
            case 'O':
                ordre = valor;
                break;
            default:
                throw new RuntimeException("Creació incorrecta de FitxaException: motiu " + motiu + " - valor: " + valor);
        }
        this.motiu = motiu;
    }

    public char getMotiu() {
        return motiu;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getTitol() {
        return titol;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAny() {
        return any;
    }

    public Integer getNum() {
        return num;
    }

    public Integer getOrdre() {
        return ordre;
    }

}
