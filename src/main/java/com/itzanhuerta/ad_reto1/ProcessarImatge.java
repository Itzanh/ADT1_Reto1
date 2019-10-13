/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itzanhuerta.ad_reto1;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 *
 * @author Itzan
 */
public class ProcessarImatge {

    public static void comprimirImatge(File fitxerEntrada, File fitxerEixida, double qualitat) {
        try {
            Thumbnails.of(fitxerEntrada)
                    .scale(1)
                    .outputQuality(qualitat)
                    .toFile(fitxerEixida);
        } catch (IOException ex) {
            System.out.println("Se ha produir un error de entrada/eixida processant la imatge.");
        }
    }

    public static void afegirMarcaDeAgua(File fitxerEntrada, File fitxerMarcaAigua, File fitxerEixida) {
        try {
            Thumbnails.of(fitxerEntrada)
                    .scale(1)
                    .watermark(Positions.CENTER, ImageIO.read(fitxerMarcaAigua), 0.5f)
                    .outputQuality(1.0)
                    .toFile(fitxerEixida);
        } catch (IOException ex) {
            System.out.println("Se ha produir un error de entrada/eixida processant la imatge.");
        }
    }
}
