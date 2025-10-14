package Paquet1;

import java.io.Serializable;
public class AlumneDat implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nom;
    private String cognom;
    private int edat;
    private double nota;

    public AlumneDat(String nom, String cognom, int edat, double nota) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
        this.nota = nota;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom() {
        return cognom;
    }
    public int getEdat() {
        return edat;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    //Vista impressió de l'objecte
    @Override
    public String toString(){
        return "Alumne:\n\tNom: "+ nom + "\n\tCognom: "+ cognom +
                "\n\tEdat: "+ edat +"\n\tNota: "+ nota;

    }
}

