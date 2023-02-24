package escuelaing.edu.co.arep;

import escuelaing.edu.co.arep.spring.Content;
import escuelaing.edu.co.arep.spring.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Content
public class InvokeService {

    @RequestMapping("/")
    public static String index() {
        return  getHeader() + getIndex();
    }

    @RequestMapping("404")
    public static String notFoundException() {
        return  getHeader() + get404();
    }

    public static String getHeader() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html \r\n" +
                "\r\n";
    }

    public static String getIndex() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/index.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }

    public static String get404() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/error404.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }
}
