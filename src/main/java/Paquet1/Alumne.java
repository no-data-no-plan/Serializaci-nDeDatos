package Paquet1;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "alumne")
@XmlAccessorType(XmlAccessType.FIELD)
public class Alumne implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String nom;

    @XmlElement
    private String cognom;

    @XmlElement
    private int edat;

    @XmlElement
    private double nota;

    // Constructor vacío (obligatorio para JAXB y Gson)
    public Alumne() {}

    // Constructor completo
    public Alumne(String nom, String cognom, int edat, double nota) {
        this.nom = nom;
        this.cognom = cognom;
        this.edat = edat;
        this.nota = nota;
    }

    // Getters y Setters
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public int getEdat() {
        return edat;
    }
    public void setEdat(int edat) {
        this.edat = edat;
    }

    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Alumne:\n\tNom: " + nom + "\n\tCognom: " + cognom +
                "\n\tEdat: " + edat + "\n\tNota: " + nota;
    }
}