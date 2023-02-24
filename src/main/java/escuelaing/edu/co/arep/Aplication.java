package escuelaing.edu.co.arep;

import escuelaing.edu.co.arep.sparkService.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Aplication {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Inicio la APP!");
        HttpServer server = HttpServer.getInstance();
        server.run(args);
    }
}
