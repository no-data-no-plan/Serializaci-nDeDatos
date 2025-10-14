package Paquet1;

import java.io.Serializable;
public class Alumne implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nom;
    private String cognom;
    private int edat;
    private double nota;

    //Constructor
    public Alumne(String nom, String cognom, int edat, double nota) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
        this.nota = nota;
    }

    // Getter i setters
    public String getNom(){
        return nom;
    }
    public void setNom(String nom){
        this.nom = nom;
    }

    public String getCognom(){
        return cognom;
    }
    public void setCognom(String cognom){
        this.cognom = cognom;
    }
    public int getEdat(){
        return edat;
    }
    public void setEdat(int edat){
        this.edat = edat;
    }
    public Double getNota(){
        return nota;
    }
    public void setNota(double nota){
        this.nota = nota;
    }

    //Vista impressió de l'objecte
    @Override
    public String toString(){
        return "Alumne:\n\tNom: "+ nom + "\n\tCognom: "+ cognom +
                "\n\tEdat: "+ edat +"\n\tNota: "+ nota;

    }
}
