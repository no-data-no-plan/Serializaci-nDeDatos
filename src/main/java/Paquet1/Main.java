package Paquet1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Main {

    //SCANNER
    public static Scanner scan = new Scanner(System.in);

    // FILES
    private static final String DADES_JSON = "alumnes.json";
    private static final String FITXER_DAT = "alumnes.dat";
    private static final String FITXER_CSV = "alumnes.csv";
    private static final String FITXER_XML = "alumnes.xml";


    public static void main(String[] args){

        int opcio;
        do {
            System.out.println("\n--- GESTOR D'ALUMNES (SERIALITZACIÓ AMB JSON I XML) ---");
            System.out.println("LECTURA");
            System.out.println("1. Llegir l'arxiu DAT");
            System.out.println("2. Llegir l'arxiu CSV");
            System.out.println("3. Llegir l'arxiu XML");
            System.out.println("4. Llegir l'arxiu JSON");
            System.out.println("5. Llegir el json ordenat per categoría");
            System.out.println("CONVERSIÓ");
            System.out.println("6. Exportar alumnes a XML");


            System.out.println("0. Sortir");
            System.out.print("Opció: ");

            opcio = Integer.parseInt(scan.nextLine());

            switch (opcio) {
                case 1:
                    GestioDades.llegirDat(FITXER_DAT);
                    break;
                case 2:
                    GestioDades.llegirCsv(FITXER_CSV);
                    break;
                case 3:
                    GestioDades.llegirXml(FITXER_XML);
                    break;
                case 4:
                    GestioDades.llegirJson(DADES_JSON);
                    break;
                case 5:
                    GestioDades.llegirOrdenat(DADES_JSON);
                    break;
                case 6:
                    GestioDades.exportarAlumnes(FITXER_DAT, DADES_JSON, FITXER_CSV, FITXER_XML;


                case 0:
                    System.out.println("Fins que ens ensume'm de nou!");
                    break;
                default:
                    System.out.println("Opció no válida");
                    break;
            }
        }while (opcio != 0);

    }
}