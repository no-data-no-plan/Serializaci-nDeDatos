package Paquet1;

import java.util.List;

public class DadesJson {
    private List<Alumne> alumnes;  // ← Usa la clase Alumne

    public DadesJson() {}

    public List<Alumne> getAlumnes() {
        return alumnes;
    }

    public void setAlumnes(List<Alumne> alumnes) {
        this.alumnes = alumnes;
    }
}