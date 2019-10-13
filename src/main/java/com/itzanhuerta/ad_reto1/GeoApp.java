package com.itzanhuerta.ad_reto1;

import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;

public class GeoApp {

    public final static Logger log = Logger.getLogger("OpenStreeMapUtils");

    private static GeoApp instance = null;
    private JSONParser jsonParser;

    public GeoApp() {
        //jsonParser = new JSONParser();
    }

    public static GeoApp getInstance() {
        if (instance == null) {
            instance = new GeoApp();
        }
        return instance;
    }

    private static String enviaPeticioGET(String urlEntrada) throws Exception {
        // obrir una connexio HTTP
        StringBuilder str = new StringBuilder();
        URL url = new URL(urlEntrada);
        Scanner entrada = new Scanner(url.openStream());

        // guardar linea per linea el resuñtat
        while (entrada.hasNext()) {
            str.append(entrada.nextLine());
        }

        // tornar la web descarregada
        return str.toString();
    }

    public static String getNomCuitat(String coordinades) {
        // inicialitzacions
        StringBuilder query = new StringBuilder();
        String[] split = coordinades.split(" ");
        String res;

        if (split.length == 0) {
            return null;
        }

        // generar la URL de la peticio.
        query.append("https://nominatim.openstreetmap.org/search?q=");

        // junsat substituint el ' ' per '+'
        for (int i = 0; i < split.length; i++) {
            query.append(split[i]);
            if (i < (split.length - 1)) {
                query.append("+");
            }
        }
        // parametres GET
        query.append("&format=json&addressdetails=1");

        // enviar la peticio
        String queryResult = null;
        try {
            queryResult = enviaPeticioGET(query.toString().replace("'", "%27").replace("°", "%C2%B0").replace("\"", "%22"));
        } catch (Exception e) {
        }

        // acabar si no se ha rebut res
        if (queryResult == null) {
            return null;
        }

        // Extraure les dades de JSON, segons si existeix la etiqueta 'city' o 'town'
        // town
        if (queryResult.indexOf(",\"city\":") < 0) {
            String tallat = queryResult.substring(queryResult.indexOf(",\"town\":") + 9);
            res = tallat.substring(0, tallat.indexOf("\""));
        } else {
            // city
            String tallat = queryResult.substring(queryResult.indexOf(",\"city\":") + 9);
            res = tallat.substring(0, tallat.indexOf("\""));
        }

        // tornar el resultat
        return res;
    }
}
