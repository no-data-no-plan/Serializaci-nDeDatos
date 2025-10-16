package Paquet1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.xml.bind.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Paquet1.Main.*;

public class GestioDades {


        /*
      #################################
     ###### PASSAR A ARRAYLISTS ######
    #################################
    */

    //PASSAR LES DADES DEL "DAT" A UN ARRAYLIST
    public static ArrayList<Alumne> datEnArraylist(String nomFitxer) {
        ArrayList<Alumne> llistaAlumnes = new ArrayList<>();

        try {
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null) {
                System.out.println("No s'ha trobat el fitxer DAT: " + nomFitxer);
                return llistaAlumnes;
            }
            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                while (true) {
                    Alumne a = (Alumne) ois.readObject();
                    llistaAlumnes.add(a);
                }
            } catch (EOFException eof) {}
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error llegint el fitxer DAT: " + e.getMessage());
        }
        return llistaAlumnes;
    }

    //PASSAR LES DADES DEL "CSV" A UN ARRAYLIST
    public static ArrayList<Alumne> csvEnArraylist(String nomFitxer) {
        ArrayList<Alumne> llistaAlumnes = new ArrayList<>();

        try {
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null) {
                System.out.println("No s'ha trobat el fitxer CSV: " + nomFitxer);
                return llistaAlumnes;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String linia;
            boolean primeraLinia = true;

            while ((linia = br.readLine()) != null) {
                if (primeraLinia) {
                    primeraLinia = false;
                    if (linia.toLowerCase().contains("nom") || linia.toLowerCase().contains("cognom")) {
                        continue;
                    }
                }
                String[] parts = linia.split(",");
                if (parts.length >= 4) {
                    try {
                        String nom = parts[0].trim();
                        String cognom = parts[1].trim();
                        int edat = Integer.parseInt(parts[2].trim());
                        double nota = Double.parseDouble(parts[3].trim());
                        llistaAlumnes.add(new Alumne(nom, cognom, edat, nota));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parsejant la línia: " + linia);
                    }
                } else {
                    System.err.println("Línia al CSV amb format erroni: " + linia);
                }
            }
            br.close();
            System.out.println("S'han afegit " + llistaAlumnes.size() + " alumnes del CSV.");
        } catch (IOException e) {
            System.err.println("Error llegint el fitxer CSV: " + e.getMessage());
        }
        return llistaAlumnes;
    }

    //PASSAR LES DADES DEL "XML" A UN ARRAYLIST
    public static ArrayList<Alumne> xmlEnArraylist(String nomFitxer) {
        ArrayList<Alumne> llistaAlumnes = new ArrayList<>();
        try {
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null){
                System.out.println("No s'ha trobat el fitxer: " + nomFitxer);
                System.out.println("Tornant llista buida...");
                return llistaAlumnes;
            }

            //Es passa l'estructura de con es tracten les dades a DadesXml al 'context'
            JAXBContext context = JAXBContext.newInstance(DadesXml.class);

            //Es crea un Unmarshaller per llegir dades, marshaller és per escriure
            //També li si passa el context per saber com están organitzades les dades
            Unmarshaller unmarshaller = context.createUnmarshaller();

            //Es crea un objecte dadesXml que conté tots els objectes dins l'arrel del xml
            DadesXml dadesXml = (DadesXml) unmarshaller.unmarshal(is);

            //Obtener la lista de alumnos del objeto y añadirlos al arraylist.
            for (Alumne a : dadesXml.getAlumnes()) {
                llistaAlumnes.add(a);
            }

        } catch (JAXBException e) {
            System.err.println("Error llegint el fitxer XML: " + e.getMessage());
        }
        return llistaAlumnes;
    }

    //PASSAR LES DADES DEL "JSON" A UN ARRAYLIST
    public static ArrayList<Alumne> jsonEnArraylist(String nomFitxer){

        ArrayList<Alumne> llista = new ArrayList<>();

        try{
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null){
                System.out.println("No s'ha trobat el fitxer: " + nomFitxer);
                System.out.println("Tornant llista buida...");
                return llista;
            }
            // PARTE VÁLIDA PARA AMBAS LIBRERIAS, ORG I GSON

            //Contingut en text pla de tot l'arxiu JSON
            String contingut = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            //Objecte JSON que conté tot l'objecte productes senser
            JSONObject json = new JSONObject(contingut);
            //Array amb l'objecte 'productes' desglossat en 'producte'
            JSONArray llistaJson = json.getJSONArray("alumnes");

            for(int i = 0; i < llistaJson.length(); i++){
                //Objecte JSON per poder iterar cada atribut de cada objecte de l'array
                JSONObject objecte = llistaJson.getJSONObject(i);
                String nom = objecte.getString("nom");
                String cognom = objecte.getString("cognom");
                int edat = objecte.getInt("edat");
                Double nota = objecte.getDouble("nota");

                llista.add(new Alumne(nom, cognom, edat, nota));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return llista;
    }


       /*
      #################################
     ###### LECTURA DE FITXERS ######
    ###############################
    */

   // LLEGIR DES DE DAT
   public static void llegirDat(String nomFitxer) {
       System.out.println("\n--- LLISTA D'ALUMNES (DAT) ---");
       ArrayList<Alumne> llistaAlumnes = datEnArraylist(nomFitxer);
       for(Alumne a : llistaAlumnes){
           System.out.println(a);
       }
   }
    // LLEGIR DES DE CSV
    public static void llegirCsv(String nomFitxer) {
        System.out.println("\n--- LLISTA D'ALUMNES (CSV) ---");
        ArrayList<Alumne> llistaAlumnes = csvEnArraylist(nomFitxer);
        for(Alumne a : llistaAlumnes){
            System.out.println(a);
        }
    }
    public static void llegirXml(String nomFitxer) {
        System.out.println("\n--- LLISTA D'ALUMNES (XML) ---");
        List<Alumne> llistaProductes = xmlEnArraylist(nomFitxer);
        for (Alumne p : llistaProductes) {
            System.out.println(p);
        }
    }
    // LLEGIR DES DE JSON
    public static void llegirJson(String nomFitxer) {
        System.out.println("\n--- LLISTA D'ALUMNES (JSON) ---");
        List<Alumne> llistaProductes = jsonEnArraylist(nomFitxer);
        for (Alumne p : llistaProductes) {
            System.out.println(p);
        }
    }

    // LLEGIR DES DE JSON ORDENAT PER CATEGORÍES
    public static void llegirOrdenat(String nomFitxer) {

        System.out.println("Selecciona per quina categoría vols llistar els alumnes:");
        System.out.println("1. Nom");
        System.out.println("2. Cognom");
        System.out.println("3. Edat");
        System.out.println("4. Nota");

        try{
            List<Alumne> llista = jsonEnArraylist(nomFitxer);
            int opcio = Integer.parseInt(scan.nextLine());

            //Ordenem la llista de productes al switch depenent del que vulgui l'usuari
            switch (opcio){
                case 1:
                    llista.sort(Comparator.comparing(Alumne::getNom));
                    break;
                case 2:
                    llista.sort(Comparator.comparing(Alumne::getCognom));
                    break;
                case 3:
                    llista.sort(Comparator.comparingInt(Alumne::getEdat).reversed());
                    break;
                case 4:
                    llista.sort(Comparator.comparingDouble(Alumne::getNota).reversed());
                    break;
                default:
                    System.out.print("Opció errònia..\n");

            }
            if (opcio < 1 || opcio > 4){
                System.out.println("Sortint");
            }else{
                for (Alumne p : llista) {
                    System.out.println(p);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
       /*
      ###############################################
     ###### IMPORTAR FITXERS A FORMAT DIFERENT######
    ###############################################
    */


    public static void exportarAlumnes(String fitxerDat, String fitxerJson, String fitxerCsv, String fitxerXml) throws JAXBException {
        System.out.println("Selecciona el format d'origen de les dades:");
        System.out.println("1. DAT");
        System.out.println("2. CSV");
        System.out.println("3. JSON");

        ArrayList<Alumne> llistaImport = null;
        int opcio = Integer.parseInt(scan.nextLine());
        if(opcio == 1){
            llistaImport = datEnArraylist(fitxerDat);
        }else if(opcio == 2){
            llistaImport = csvEnArraylist(fitxerCsv);
        }else if(opcio == 3){
            llistaImport = jsonEnArraylist(fitxerJson);
        }
        try{
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(fitxerXml);
            JAXBContext context = JAXBContext.newInstance(DadesXml.class);

            //Es crea un Unmarshaller per llegir dades, marshaller és per escriure
            //També li si passa el context per saber com están organitzades les dades
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            for (Alumne a : llistaImport){
                marshaller.marshal((Object) a, is);
            }

        }catch (JAXBException e){
            System.out.println("Error exportant les dades.");
        }

    }
}