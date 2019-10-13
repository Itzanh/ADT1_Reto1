/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itzanhuerta.ad_reto1;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Itzan
 */
public class Coordenades {

    private char latitudeRef;
    private String latitude;
    private char longitudeRef;
    private String longitude;

    public Coordenades() {
    }

    public Coordenades(char latitudeRef, String latitude, char longitudeRef, String longitude) {
        this.latitudeRef = latitudeRef;
        this.latitude = latitude;
        this.longitudeRef = longitudeRef;
        this.longitude = longitude;
    }

    public char getLatitudeRef() {
        return latitudeRef;
    }

    public void setLatitudeRef(char latitudeRef) {
        this.latitudeRef = latitudeRef;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public char getLongitudeRef() {
        return longitudeRef;
    }

    public void setLongitudeRef(char longitudeRef) {
        this.longitudeRef = longitudeRef;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean teCoordenades() {
        return (this.latitude != null) && (this.longitude != null);
    }

    /**
     * Extrau les coordinades de les metadades de un fitxer determinat.
     *
     * @param fitxer
     * @return
     */
    public static Coordenades coordenadesDesdeImatge(File fitxer) {
        Coordenades c = new Coordenades();
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(fitxer);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    /*System.out.format("[%s] - %s = %s",
                            directory.getName(), tag.getTagName(), tag.getDescription());*/
                    switch (tag.getTagName()) {
                        case "GPS Latitude Ref": {
                            c.setLatitudeRef(tag.getDescription().charAt(0));
                            break;
                        }
                        case "GPS Latitude": {
                            c.setLatitude(tag.getDescription());
                            break;
                        }
                        case "GPS Longitude Ref": {
                            c.setLongitudeRef(tag.getDescription().charAt(0));
                            break;
                        }
                        case "GPS Longitude": {
                            c.setLongitude(tag.getDescription());
                            break;
                        }
                    }
                }
            }
        } catch (ImageProcessingException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        return c;
    }

    /**
     * Torna les coordenades formatejades per 0º 0' 0" N 0º 0' 0" W
     *
     * @return
     */
    public String getCoordenadesFormatejades() {
        StringBuilder str = new StringBuilder();
        str
                .append(this.latitude).append(" ").append(this.latitudeRef).append(" ")
                .append(this.longitude).append(" ").append(this.longitudeRef);
        return str.toString();
    }

    /**
     * Torna les coordenades formatejades per Latit: 0º 0' 0" N. Longit: 0º 0'
     * 0" W
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str
                .append("Latit: ").append(this.latitude).append(" ").append(this.latitudeRef)
                .append(". Longit: ").append(this.longitude).append(" ").append(this.longitudeRef);
        return str.toString();
    }

}
