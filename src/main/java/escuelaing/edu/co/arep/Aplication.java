package escuelaing.edu.co.arep;

import java.io.IOException;

public class Aplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HttpServer server = HttpServer.getInstance();
        server.run(args);
    }
}
