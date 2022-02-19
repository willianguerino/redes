import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTime {
    
    public static void main(String[] args){
        String hostname = "localhost";
        int port = 6868;

        try (Socket socket = new Socket(hostname, port)){
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String time = reader.readLine();
            System.out.println(time);
        } catch (UnknownHostException e) {
            System.out.println("Server not found: "+e.getMessage());
        }catch (IOException e){
            System.out.println("I/O error: "+e.getMessage());
        }
    }
}