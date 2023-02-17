package escuelaing.edu.co.arep;

import java.io.IOException;

public interface Rest {

    public String getHeader();


    public String getResponse() throws IOException;
}
