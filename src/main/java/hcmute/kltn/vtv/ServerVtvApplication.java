package hcmute.kltn.vtv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServerVtvApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerVtvApplication.class, args);

        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("\n\n" +"IP Address: " + ip.getHostAddress());
            System.out.println("Localhost:  http://localhost:8585/swagger-ui/index.html" + "\n\n");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}
