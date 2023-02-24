package escuelaing.edu.co.arep;

import escuelaing.edu.co.arep.mySpark.Rest;
import escuelaing.edu.co.arep.mySpark.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Rest
public class invokeResource {
    @RequestMapping("/html")
    public static String get1() {
        return  getHtml() + getPageHtml();
    }

    public static String getPageHtml() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/index.html"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }

    @RequestMapping("/css")
    public static String get2() {
        return  getHeader() + geCss();
    }

    public static String geCss() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/index.css"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }

    @RequestMapping("/app")
    public static String get3() {
        return  getHeader() + getScript();
    }

    public static String getScript() {
        byte[] file;
        try{
            file = Files.readAllBytes(Paths.get("src/main/resources/script.js"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return new String(file);
    }


    public static String getHtml() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: text/html \r\n" +
                "\r\n";
    }
    public static String getHeader() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: application/json \r\n" +
                "\r\n";
    }












}

