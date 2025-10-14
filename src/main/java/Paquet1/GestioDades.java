package Paquet1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static ArrayList<AlumneDat> datEnArraylist(String nomFitxer) {
        ArrayList<AlumneDat> llistaAlumnes = new ArrayList<>();

        try {
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null) {
                System.out.println("No s'ha trobat el fitxer DAT: " + nomFitxer);
                return llistaAlumnes;
            }

            ObjectInputStream ois = new ObjectInputStream(is);
            try {
                while (true) {
                    AlumneDat a = (AlumneDat) ois.readObject();
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
    public static ArrayList<AlumneCsv> csvEnArraylist(String nomFitxer) {
        ArrayList<AlumneCsv> llistaAlumnes = new ArrayList<>();

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
                        llistaAlumnes.add(new AlumneCsv(nom, cognom, edat, nota));
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


    //PASSAR LES DADES DEL "JSON" A UN ARRAYLIST
    public static ArrayList<AlumneJson> jsonEnArraylist(String nomFitxer){

        ArrayList<AlumneJson> llista = new ArrayList<>();

        try{
            InputStream is = GestioDades.class.getClassLoader().getResourceAsStream(nomFitxer);
            if (is == null){
                System.out.println("No s'ha trobat el fitxer: " + nomFitxer);
                System.out.println("Tornant llista buida...");
                return llista;
            }
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

                llista.add(new AlumneJson(nom, cognom, edat, nota));
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
       ArrayList<AlumneDat> llistaAlumnes = datEnArraylist(nomFitxer);
       for(AlumneDat a : llistaAlumnes){
           System.out.println(a);
       }
   }
    // LLEGIR DES DE CSV
    public static void llegirCsv(String nomFitxer) {
        System.out.println("\n--- LLISTA D'ALUMNES (CSV) ---");
        ArrayList<AlumneCsv> llistaAlumnes = csvEnArraylist(nomFitxer);
        for(AlumneCsv a : llistaAlumnes){
            System.out.println(a);
        }
    }
    // LLEGIR DES DE JSON
    public static void llegirJson(String nomFitxer) {
        try {

            List<AlumneJson> llistaProductes = jsonEnArraylist(nomFitxer);

            for (AlumneJson p : llistaProductes) {
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
            List<AlumneJson> llista = jsonEnArraylist(nomFitxer);
            int opcio = Integer.parseInt(scan.nextLine());

            //Ordenem la llista de productes al switch depenent del que vulgui l'usuari
            switch (opcio){
                case 1:
                    llista.sort(Comparator.comparing(AlumneJson::getNom));
                    break;
                case 2:
                    llista.sort(Comparator.comparing(AlumneJson::getCognom));
                    break;
                case 3:
                    llista.sort(Comparator.comparingInt(AlumneJson::getEdat).reversed());
                    break;
                case 4:
                    llista.sort(Comparator.comparingDouble(AlumneJson::getNota).reversed());
                    break;
                default:
                    System.out.print("Opció errònia..\n");

            }
            if (opcio < 1 || opcio > 4){
                System.out.println("Sortint");
            }else{
                for (AlumneJson p : llista) {
                    System.out.println(p);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


}