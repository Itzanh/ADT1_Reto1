/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itzanhuerta.ad_reto1;

import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author Itzan
 */
public class App {

    private static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("*****\n"
                    + "1.- Organizar por mes i año\n"
                    + "2.- Leer i mostrar coordenadas\n"
                    + "3.- Organizar las carpetas por cuidad\n"
                    + "4-. Reducir tamaño\n"
                    + "5.- Marca de agua\n"
                    + "6.- Salir");
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch (opcion) {
                case 1: {
                    opcionOrganizar();
                    break;
                }
                case 2: {
                    opcionCoordenadas();
                    break;
                }
                case 3: {
                    opcionPorCuidad();
                    break;
                }
                case 4: {
                    opcionComprimir();
                    break;
                }
                case 5: {
                    opcionMarcaDeAgua();
                    break;
                }
                case 6: {
                    break;
                }
                default: {
                    System.out.println("ERROR");
                }
            }
        } while (opcion != 6);
    }

    /**
     * Metode que pregunta a l'usuari per un directori usant interficie grafica.
     *
     * @return
     */
    private static File seleccionarDirectori(String titol) {
        String rutaCarpeta;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(titol);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;

    }

    private static File seleccionarDirectori() {
        return seleccionarDirectori("Introduce la carpeta");
    }

    private static File seleccionarFitxer(String titol, boolean esGuardar) {
        String rutaCarpeta;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(titol);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (esGuardar) {
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                return chooser.getSelectedFile();
            }
        }
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;

    }

    private static File seleccionarFitxer() {
        return seleccionarFitxer("Introduce el fichero", false);
    }

    /**
     * Pregunta per una carpeta i la organitza per subcarpetes per fecha YYYYMM.
     * P. Ej. 201910
     */
    private static void opcionOrganizar() {
        File carpeta = seleccionarDirectori();
        if (carpeta == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        OrganizarCarpeta novaOrg = new OrganizarCarpeta(carpeta);
        novaOrg.organitzarCarpetaPerFecha();
    }

    private static void opcionComprimir() {
        System.out.println("Introdueix la compresio de la imatge. 0.0 -> 1.0");
        double qualitat = entrada.nextDouble();
        System.out.println("Inrtodueix un fitxer per interficie grafica...");
        File fitxerEntrada = seleccionarFitxer("Introdueix el fitxer original", false);
        if (fitxerEntrada == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        // guardar la imatge processada
        File fitxerEixida = seleccionarFitxer("Guardar el fitxer processat", false);
        if (fitxerEixida == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        ProcessarImatge.comprimirImatge(fitxerEntrada, fitxerEixida, qualitat);
    }

    private static void opcionMarcaDeAgua() {
        File fitxerEntrada = seleccionarFitxer("Introdueix el fitxer original", false);
        if (fitxerEntrada == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        File fitxerMarcaAigua = seleccionarFitxer("Introdueix el fitxer de marca de aigua", false);
        if (fitxerMarcaAigua == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        // guardar la imatge processada
        File fitxerEixida = seleccionarFitxer("Guardar el fitxer processat", false);
        if (fitxerEixida == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        ProcessarImatge.afegirMarcaDeAgua(fitxerEntrada, fitxerMarcaAigua, fitxerEixida);
    }

    /**
     * Imprimeix per pantalla uuna llista de noms de fitxers i coordenades GPS
     * corresponents
     */
    private static void opcionCoordenadas() {
        File carpeta = seleccionarDirectori();
        if (carpeta == null) {
            System.out.println("Se ha cancelat");
            return;
        }
        for (File fitxer : carpeta.listFiles()) {
            if (fitxer.isFile()) {
                Coordenades c = Coordenades.coordenadesDesdeImatge(fitxer);
                if (c.teCoordenades()) {
                    System.out.print("NOMBRE: " + fitxer.getName() + ". COORDENADAS ");
                    System.out.println(c);
                }
            }
        }
    }

    /**
     * pregunta per una carpeta, extrau les metadades de GPS dels fitxers que
     * conte, busca la ciutat o poble a la que perteneixen i les ordena en una
     * jerarquia de carpetes
     */
    private static void opcionPorCuidad() {
        Coordenades cord;
        String posGPS;
        File carpeta = seleccionarDirectori();
        if (carpeta == null) {
            System.out.println("Se ha cancelat");
            return;
        }

        OrganizarCarpeta org = new OrganizarCarpeta(carpeta);
        org.organitzarCarpetaPerGPS();
    }

}
