import java.io.*;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException {
        try(var server = new ServerSocket(8080)) {
            System.out.printf("Server started on %s%n", 8080);

            while(true) {
                try(var request = server.accept()) {

                    printRequest(request.getInputStream());

                    var date = new Date();
                    var response = String.format("""
                    HTTP/1.1 200 OK
                    
                    %s
                    """, date);

                    request.getOutputStream()
                            .write(response.getBytes(StandardCharsets.UTF_8));

                }
            }
        }
    }

    /**
     * Method prints anything in the InputStream
     * @param is InputStream to read from
     * @throws IOException An exception that might occur while reading input stream
     */
    private static void printRequest(InputStream is) throws IOException {
       var isr = new InputStreamReader(is);
       var bfr = new BufferedReader(isr);

       var line = bfr.readLine();
       while(!line.isEmpty()) {
           System.out.println(line);
           line = bfr.readLine();
       }
    }
}
