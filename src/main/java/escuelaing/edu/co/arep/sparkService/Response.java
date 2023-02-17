package escuelaing.edu.co.arep.sparkService;

public class Response {
    private String extent;
    private String body;
    private String ruta;


    public String getHeader() {
        return "HTTP/1.1 200 \r\n" +
                "Content-Type: " + extent + " \r\n" +
                "\r\n";
    }

    public String getResponse() {
        return getHeader() + getBody();
    }
    public String getBody() {
        return body;
    }

    public void setRoute(String ruta) {
        this.ruta = ruta;
    }

    public void setExtent(String type) {
        this.extent = type;
    }
    public void setFile(String body) {
        this.body = body;
    }
}
