package escuelaing.edu.co.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Crea la conexión a la api de las películas
 */
public class Conection {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static String busqueda(String nombre, String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int answer = con.getResponseCode();
        System.out.println("GET Response Code :: " + answer);

        if (answer == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer ans = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                ans.append(inputLine);
            }
            in.close();

            String finaly = "["+ans.toString()+"]" ;
            return  finaly;
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return "Failed";
    }
}
