/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itzanhuerta.ad_reto1;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Itzan
 */
public class OrganizarCarpeta {

    private File carpeta;

    public OrganizarCarpeta(File carpeta) {
        this.carpeta = carpeta;
    }

    public File getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(File carpeta) {
        this.carpeta = carpeta;
    }

    /**
     * organitza una carpeta per subcarpetes utilitzant les feches dels archui i
     * creant subdirectoris amb noms seguint el format YYYYMM i moguent els
     * fitxers a la subcarpeta correcponent
     */
    public void organitzarCarpetaPerFecha() {
        for (File fitxer : carpeta.listFiles()) {
            if (fitxer.isFile()) {
                // carregar la fecha, i concatenarla de manera que siga el nom 
                // de la carpeta que el te que contenir
                Date ultimaModif = new Date(fitxer.lastModified());
                Calendar c = Calendar.getInstance();
                c.setTime(ultimaModif);
                String nomCarpetaContenedora = c.get(Calendar.YEAR) + "" + (c.get(Calendar.MONTH) + 1);
                // comprovar si existeix
                File carpetaContenedora = new File(carpeta, nomCarpetaContenedora);
                if (!carpetaContenedora.exists()) {
                    carpetaContenedora.mkdir();
                }
                // moure el fitxer
                try {
                    fitxer.renameTo(new File(carpetaContenedora, fitxer.getName()));
                } catch (Exception ex) {
                    System.out.println("ERROR al moure el fitxer de directori: " + ex.getMessage());
                }
            }
        }
    }

    public void organitzarCarpetaPerGPS() {
        Coordenades cord;
        String posGPS;

        for (File fitxer : this.carpeta.listFiles()) {
            if (fitxer.isFile()) {
                cord = Coordenades.coordenadesDesdeImatge(fitxer);
                if (cord != null) {
                    posGPS = cord.getCoordenadesFormatejades();
                    String ciutat = GeoApp.getNomCuitat(posGPS);
                    if (ciutat != null) {
                        System.out.println(ciutat);
                        File carpetaContenedora = new File(this.carpeta, ciutat);
                        if (!carpetaContenedora.exists()) {
                            carpetaContenedora.mkdir();
                        }
                        fitxer.renameTo(new File(carpetaContenedora, fitxer.getName()));
                    }
                }
            }
        }
    }
}
