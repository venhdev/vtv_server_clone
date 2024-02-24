package hcmute.kltn.vtv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServerVtvApplication {

    private static final Logger logger = LoggerFactory.getLogger(ServerVtvApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ServerVtvApplication.class, args);

        try {
            InetAddress ip = InetAddress.getLocalHost();
            logger.info("""
                    \n                
                    IP Address: {}
                    Localhost:  http://localhost:8585/swagger-ui/index.html
                          
                    """, ip.getHostAddress());
        } catch (UnknownHostException e) {
            logger.error("An error occurred while getting the local host address.", e);
        }
    }

}
