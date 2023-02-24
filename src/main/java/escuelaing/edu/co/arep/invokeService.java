package escuelaing.edu.co.arep;

import escuelaing.edu.co.arep.mySpark.RequestMapping;
import escuelaing.edu.co.arep.mySpark.Rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Rest
public class invokeService {

    @RequestMapping("404")
    public static String notFoundException() {
        return  getHeader() + getResponseNotFound();
    }

    public static String getHeader() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html \r\n" +
                "\r\n";
    }

    public static String getResponseNotFound() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/error404.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }
}