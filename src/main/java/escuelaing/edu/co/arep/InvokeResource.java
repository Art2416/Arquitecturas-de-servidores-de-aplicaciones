package escuelaing.edu.co.arep;

import escuelaing.edu.co.arep.spring.Content;
import escuelaing.edu.co.arep.spring.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Content
public class InvokeResource {
    @RequestMapping("/html")
    public static String Html() {
        return  getHtml() + getRHtml();
    }
    public static String getRHtml() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/index.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }
    @RequestMapping("/css")
    public static String Css() {
        return  getHeader() + getCss();
    }
    public static String getCss() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/index.css"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }
    @RequestMapping("/app")
    public static String Js() {
        return  getHeader() + getJs();
    }
    public static String getJs() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/public/script.js"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }


    public static String getHeader() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: application/json \r\n" +
                "\r\n";
    }

    public static String getHtml() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html \r\n" +
                "\r\n";
    }







}
