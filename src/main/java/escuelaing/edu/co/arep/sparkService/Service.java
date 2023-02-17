package escuelaing.edu.co.arep.sparkService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Service {
    public static Map<String, Response> requests = new HashMap<>();

    public static void get(String route, Ruta ruta){
        Request request = new Request();
        Response response = new Response();
        ruta.way(request, response);

        System.out.println(ruta);

        response.setFile(getFile(route));
        response.setRoute(route);



        requests.put(route, response);
    }

    public static String getFile(String ruta) {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/" + ruta));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }
}
