package Paquet1;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "alumnes")
@XmlAccessorType(XmlAccessType.FIELD)
public class DadesXml {

    @XmlElement(name = "alumne")
    private List<Alumne> alumnes = new ArrayList<>();  // ← Usa la clase Alumne

    public DadesXml() {}

    public List<Alumne> getAlumnes() {
        return alumnes;
    }

    public void setAlumnes(List<Alumne> alumnes) {
        this.alumnes = alumnes;
    }
}