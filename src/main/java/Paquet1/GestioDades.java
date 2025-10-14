package Paquet1;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
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
            } catch (EOFException eof) {

            }
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
            JAXBContext context = JAXBContext.newInstance(Alumne.class, ArrayList.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(Alumne, new File("alumnes.xml"));

            llistaAlumnes.add(Alumne);

        } catch (JAXBException e) {
            throw new RuntimeException(e);
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
    // LLEGIR DES DE JSON
    public static void llegirJson(String nomFitxer) {
        try {
            System.out.println("\n--- LLISTA D'ALUMNES (JSON) ---");
            List<Alumne> llistaProductes = jsonEnArraylist(nomFitxer);

            for (Alumne p : llistaProductes) {
                System.out.println(p);
            }

        } catch (JSONException e) {
            e.printStackTrace();
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


}