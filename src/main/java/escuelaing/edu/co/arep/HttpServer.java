package escuelaing.edu.co.arep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import escuelaing.edu.co.arep.spring.RequestMapping;
import escuelaing.edu.co.arep.spring.SpringPersonal;
import org.json.JSONArray;
import org.json.JSONObject;


public class HttpServer {
    private OutputStream outputStream;
    private static  HttpServer _instance = new HttpServer();

    private HashMap<String, Method> methods = new HashMap<>();

    public void run(String[] args) throws IOException, ClassNotFoundException {

        SpringPersonal spring = new SpringPersonal();
        ArrayList<String> classes = spring.getClassComponent(new ArrayList<>(), ".");
        for (String className: classes) {
            Class c = Class.forName(className);
            for (Method m: c.getMethods()) {
                if (m.isAnnotationPresent(RequestMapping.class)){methods.put(m.getAnnotation(RequestMapping.class).value(), m);}
            }
        }
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");System.exit(1);
        }

        boolean running = true;
        while(running) {
            Boolean indicator = true;
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()
                    ));
            String inputLine, outputLine, ruta = "/simple", call = "GET";

            outputStream = clientSocket.getOutputStream();
            //if(inputLine.contains("info?title=")){
            //    String[] res = inputLine.split("title=");
            //    nombrePelicula = (res[1].split("HTTP")[0]).replace(" ", "");
            //}
            while ((inputLine = in.readLine()) != null) {

                if (indicator) {
                    ruta = inputLine.split(" ")[1];
                    call = inputLine.split(" ")[0];
                    indicator = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            //if (ruta.startsWith("/apps/")) {
            //    outputLine = ruta.substring(5);
            //    if (Service.requests.containsKey(outputLine)){
            //        outputLine = Service.requests.get(outputLine).getResponse();
            //    }
            //} else if (!nombrePelicula.equals("")) {
            //    String response = Conection.busqueda(nombrePelicula, "http://www.omdbapi.com/?t=" + nombrePelicula + "&apikey=62c22013");
            //    outputLine ="HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<br>" + "<table border=\" 1 \"> \n " + organizacion(response)+ "    </table>";
            //}else {
            //    outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + content();
            //}
            if (Objects.equals(call, "GET")) {
                try {
                    if (methods.containsKey(ruta)) {
                        outputLine = (String) methods.get(ruta).invoke(null);
                    } else {
                        outputLine = (String) methods.get("404").invoke(null);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                outputLine = "HTTP/1.1 200 OK\r\n" + "Content-type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta charset=\"UTF-8\">" + "<title>404</title>\n" + "</head>" + "<body>" + "Esto es una prueba" + "</body>" + "</html>";
            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    public static String content(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Info</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Información de la película a buscar</h1>\n" +
                "<form action=\"/search\">\n" +
                "    <label for=\"name\">Inserte nombre:</label><br>\n" +
                "    <input type=\"text\" id=\"name\" name=\"name\" value=\"Guardians of the Galaxy Vol. 2\"><br><br>\n" +
                "    <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n" +
                "</form>\n" +
                "<div id=\"capture\"></div>\n" +
                "\n" +
                "<script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"capture\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/info?title=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n" +
                "</body>\n" +
                "</html>";
    }
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
    /**
     * organiza la informacion
     * @param inf hace visible el contenido de manera organizada
     * @return informacion de la busqueda
     */
    private static String organizacion(String inf){

        HashMap<String,String> informacion = new HashMap<String, String>();
        HashMap<String,String> alma = new HashMap<String, String>();

        String organizar = "Información de la película:" + "<br>" + "<tr> \n";
        JSONArray jsonArray = new JSONArray(inf);

        for (int i=0; i<jsonArray.length();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            for (String key: object.keySet()) {
                String k;
                k = key.toString();
                informacion.put(key.toString(), object.get(key).toString());
                //System.out.println(k);
                //System.out.println(jsonArray.getJSONObject(i));
            }

        }
        //System.out.println(dict.keySet());


        for (String keys: informacion.keySet()){
            String value = informacion.get(keys);
            alma.put(keys, value);
            organizar += "<br>" + keys + ": " + value + "<br>";
            organizar += "<tr> \n";
        }
        return organizar;
    }
    //public static String ejecucion(String serviceName) throws IOException {
    //  String body, header;
    // if (memory.containsKey(serviceName) ) {
    //   Rest rs = memory.get(serviceName);
    //  header = rs.getHeader();
    // body = rs.getResponse();
    //} else {
    //  Rest rs = memory.get("/noEncontrado");
    //header = rs.getHeader();
    //body = rs.getResponse();}

    //return header + body;}
    public static HttpServer getInstance() {
        return _instance;
    }


}